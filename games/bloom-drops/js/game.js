(() => {
  "use strict";

  const COLORS = [
    { id: "coral", hex: "#FF6B6B", img: "assets/drop-coral.png", label: "Coral Rose" },
    { id: "mint", hex: "#5EEAD4", img: "assets/drop-mint.png", label: "Mint Dew" },
    { id: "gold", hex: "#FBBF24", img: "assets/drop-gold.png", label: "Sun Pollen" },
    { id: "sky", hex: "#60A5FA", img: "assets/drop-sky.png", label: "Sky Drop" },
    { id: "blossom", hex: "#F9A8D4", img: "assets/drop-blossom.png", label: "Night Blossom" },
  ];

  const LEVELS = [
    { size: 6, moves: 22, colors: 4, goals: { coral: 18, mint: 18, gold: 12 } },
    { size: 6, moves: 20, colors: 4, goals: { coral: 20, mint: 16, sky: 16 } },
    { size: 7, moves: 24, colors: 5, goals: { gold: 22, blossom: 18, mint: 18 } },
    { size: 7, moves: 22, colors: 5, goals: { coral: 20, sky: 20, gold: 16, mint: 12 } },
    { size: 8, moves: 26, colors: 5, goals: { coral: 24, mint: 24, gold: 20, blossom: 16 } },
  ];

  const canvas = document.getElementById("board");
  const ctx = canvas.getContext("2d");
  const goalsEl = document.getElementById("goals");
  const levelNum = document.getElementById("levelNum");
  const movesLeft = document.getElementById("movesLeft");
  const scoreVal = document.getElementById("scoreVal");
  const hint = document.getElementById("hint");
  const btnRestart = document.getElementById("btnRestart");
  const btnNext = document.getElementById("btnNext");
  const modal = document.getElementById("modal");
  const modalTitle = document.getElementById("modalTitle");
  const modalBody = document.getElementById("modalBody");
  const modalBtn = document.getElementById("modalBtn");

  const images = {};
  let ready = false;
  let levelIndex = 0;
  let grid = [];
  let size = 6;
  let cell = 60;
  let pad = 18;
  let moves = 0;
  let score = 0;
  let goals = {};
  let collected = {};
  let path = [];
  let dragging = false;
  let animating = false;
  let particles = [];
  let dropOffsets = [];
  let colorPool = COLORS.slice(0, 4);

  function colorById(id) {
    return COLORS.find((c) => c.id === id);
  }

  function loadImages() {
    return Promise.all(
      COLORS.map(
        (c) =>
          new Promise((resolve) => {
            const img = new Image();
            img.onload = () => {
              images[c.id] = img;
              resolve();
            };
            img.onerror = () => resolve();
            img.src = c.img;
          })
      )
    ).then(() => {
      ready = true;
    });
  }

  function resize() {
    const css = Math.min(420, Math.floor(window.innerWidth * 0.92));
    const dpr = Math.min(window.devicePixelRatio || 1, 2);
    canvas.style.width = css + "px";
    canvas.style.height = css + "px";
    canvas.width = Math.floor(css * dpr);
    canvas.height = Math.floor(css * dpr);
    ctx.setTransform(dpr, 0, 0, dpr, 0, 0);
    pad = Math.max(14, Math.floor(css * 0.045));
    cell = (css - pad * 2) / size;
    draw();
  }

  function randColor() {
    return colorPool[Math.floor(Math.random() * colorPool.length)].id;
  }

  function makeGrid() {
    grid = Array.from({ length: size }, () =>
      Array.from({ length: size }, () => randColor())
    );
    dropOffsets = Array.from({ length: size }, () => Array(size).fill(0));
  }

  function startLevel(index) {
    levelIndex = Math.max(0, Math.min(index, LEVELS.length - 1));
    const lvl = LEVELS[levelIndex];
    size = lvl.size;
    colorPool = COLORS.slice(0, lvl.colors);
    moves = lvl.moves;
    goals = { ...lvl.goals };
    collected = Object.fromEntries(Object.keys(goals).map((k) => [k, 0]));
    path = [];
    dragging = false;
    animating = false;
    particles = [];
    btnNext.hidden = true;
    makeGrid();
    levelNum.textContent = String(levelIndex + 1);
    movesLeft.textContent = String(moves);
    renderGoals();
    resize();
    hint.textContent = "Drag matching drops. Close a loop for a Bloom Burst.";
  }

  function renderGoals() {
    goalsEl.innerHTML = "";
    Object.entries(goals).forEach(([id, need]) => {
      const c = colorById(id);
      const got = collected[id] || 0;
      const el = document.createElement("div");
      el.className = "goal" + (got >= need ? " done" : "");
      el.innerHTML = `<span class="goal-orb" style="background-image:url('${c.img}');background-color:${c.hex}"></span><span>${Math.min(got, need)}/${need}</span>`;
      goalsEl.appendChild(el);
    });
  }

  function cellCenter(r, c) {
    return {
      x: pad + c * cell + cell / 2,
      y: pad + r * cell + cell / 2,
    };
  }

  function hitCell(x, y) {
    const c = Math.floor((x - pad) / cell);
    const r = Math.floor((y - pad) / cell);
    if (r < 0 || c < 0 || r >= size || c >= size) return null;
    return { r, c };
  }

  function getXY(e) {
    const rect = canvas.getBoundingClientRect();
    const src = e.touches ? e.touches[0] || e.changedTouches[0] : e;
    return {
      x: ((src.clientX - rect.left) / rect.width) * canvas.clientWidth,
      y: ((src.clientY - rect.top) / rect.height) * canvas.clientHeight,
    };
  }

  function sameAdj(a, b) {
    return Math.abs(a.r - b.r) + Math.abs(a.c - b.c) === 1;
  }

  function pathHas(cell) {
    return path.some((p) => p.r === cell.r && p.c === cell.c);
  }

  function isLoop(cell) {
    if (path.length < 3) return false;
    const first = path[0];
    // Two Dots style: revisiting any earlier cell that closes a cycle of 4+
    for (let i = 0; i < path.length - 2; i++) {
      const p = path[i];
      if (p.r === cell.r && p.c === cell.c && sameAdj(path[path.length - 1], cell)) {
        return true;
      }
    }
    return first.r === cell.r && first.c === cell.c && path.length >= 4 && sameAdj(path[path.length - 1], cell);
  }

  function drawDrop(x, y, colorId, radius, alpha = 1) {
    const c = colorById(colorId);
    ctx.save();
    ctx.globalAlpha = alpha;
    ctx.beginPath();
    ctx.arc(x, y, radius, 0, Math.PI * 2);
    ctx.closePath();
    ctx.clip();

    const img = images[colorId];
    if (img) {
      ctx.drawImage(img, x - radius, y - radius, radius * 2, radius * 2);
    } else {
      const g = ctx.createRadialGradient(x - radius * 0.3, y - radius * 0.35, radius * 0.1, x, y, radius);
      g.addColorStop(0, "#fff8");
      g.addColorStop(0.35, c.hex);
      g.addColorStop(1, shade(c.hex, -30));
      ctx.fillStyle = g;
      ctx.fillRect(x - radius, y - radius, radius * 2, radius * 2);
    }
    ctx.restore();

    ctx.beginPath();
    ctx.arc(x, y, radius, 0, Math.PI * 2);
    ctx.strokeStyle = "rgba(255,255,255,0.22)";
    ctx.lineWidth = 2;
    ctx.stroke();
  }

  function shade(hex, amt) {
    const n = hex.replace("#", "");
    const num = parseInt(n, 16);
    let r = (num >> 16) + amt;
    let g = ((num >> 8) & 0xff) + amt;
    let b = (num & 0xff) + amt;
    r = Math.max(0, Math.min(255, r));
    g = Math.max(0, Math.min(255, g));
    b = Math.max(0, Math.min(255, b));
    return `#${((r << 16) | (g << 8) | b).toString(16).padStart(6, "0")}`;
  }

  function draw() {
    const w = canvas.clientWidth;
    const h = canvas.clientHeight;
    ctx.clearRect(0, 0, w, h);

    // soft board wash
    ctx.fillStyle = "rgba(255,255,255,0.03)";
    for (let r = 0; r < size; r++) {
      for (let c = 0; c < size; c++) {
        if ((r + c) % 2 === 0) {
          ctx.fillRect(pad + c * cell, pad + r * cell, cell, cell);
        }
      }
    }

    const radius = cell * 0.34;
    for (let r = 0; r < size; r++) {
      for (let c = 0; c < size; c++) {
        const id = grid[r][c];
        if (!id) continue;
        const { x, y } = cellCenter(r, c);
        const oy = dropOffsets[r]?.[c] || 0;
        drawDrop(x, y + oy, id, radius);
      }
    }

    if (path.length) {
      const color = colorById(grid[path[0].r][path[0].c]);
      ctx.strokeStyle = color ? color.hex : "#fff";
      ctx.lineWidth = Math.max(4, cell * 0.08);
      ctx.lineCap = "round";
      ctx.lineJoin = "round";
      ctx.globalAlpha = 0.85;
      ctx.beginPath();
      path.forEach((p, i) => {
        const { x, y } = cellCenter(p.r, p.c);
        if (i === 0) ctx.moveTo(x, y);
        else ctx.lineTo(x, y);
      });
      ctx.stroke();
      ctx.globalAlpha = 1;

      path.forEach((p) => {
        const { x, y } = cellCenter(p.r, p.c);
        ctx.beginPath();
        ctx.arc(x, y, radius * 1.18, 0, Math.PI * 2);
        ctx.strokeStyle = "rgba(255,255,255,0.55)";
        ctx.lineWidth = 2;
        ctx.stroke();
      });
    }

    particles.forEach((p) => {
      ctx.globalAlpha = Math.max(0, p.life);
      ctx.fillStyle = p.color;
      ctx.beginPath();
      ctx.arc(p.x, p.y, p.size, 0, Math.PI * 2);
      ctx.fill();
      ctx.globalAlpha = 1;
    });
  }

  function spawnParticles(cells, colorId) {
    const hex = colorById(colorId).hex;
    cells.forEach(({ r, c }) => {
      const { x, y } = cellCenter(r, c);
      for (let i = 0; i < 8; i++) {
        particles.push({
          x,
          y,
          vx: (Math.random() - 0.5) * 4,
          vy: (Math.random() - 0.5) * 4 - 1,
          size: 2 + Math.random() * 3,
          life: 1,
          color: hex,
        });
      }
    });
  }

  function tickParticles() {
    particles.forEach((p) => {
      p.x += p.vx;
      p.y += p.vy;
      p.life -= 0.04;
    });
    particles = particles.filter((p) => p.life > 0);
  }

  function clearCells(cells, colorId, burst) {
    const unique = [];
    const key = (p) => `${p.r},${p.c}`;
    const seen = new Set();
    cells.forEach((p) => {
      const k = key(p);
      if (!seen.has(k)) {
        seen.add(k);
        unique.push(p);
      }
    });

    spawnParticles(unique, colorId);
    unique.forEach(({ r, c }) => {
      if (grid[r][c] === colorId) grid[r][c] = null;
    });

    const gain = unique.length * (burst ? 25 : 10);
    score += gain;
    if (collected[colorId] !== undefined) {
      collected[colorId] += unique.length;
    }
    scoreVal.textContent = String(score);
    renderGoals();
  }

  function applyGravity() {
    for (let c = 0; c < size; c++) {
      let write = size - 1;
      for (let r = size - 1; r >= 0; r--) {
        if (grid[r][c]) {
          if (write !== r) {
            grid[write][c] = grid[r][c];
            grid[r][c] = null;
            dropOffsets[write][c] = (r - write) * cell;
          }
          write--;
        }
      }
      while (write >= 0) {
        grid[write][c] = randColor();
        dropOffsets[write][c] = -(write + 2) * cell;
        write--;
      }
    }
  }

  function animateDrops(done) {
    animating = true;
    const step = () => {
      let moving = false;
      for (let r = 0; r < size; r++) {
        for (let c = 0; c < size; c++) {
          const o = dropOffsets[r][c];
          if (Math.abs(o) > 0.5) {
            dropOffsets[r][c] = o * 0.72;
            moving = true;
          } else {
            dropOffsets[r][c] = 0;
          }
        }
      }
      tickParticles();
      draw();
      if (moving || particles.length) {
        requestAnimationFrame(step);
      } else {
        animating = false;
        done && done();
      }
    };
    requestAnimationFrame(step);
  }

  function goalsMet() {
    return Object.entries(goals).every(([id, need]) => (collected[id] || 0) >= need);
  }

  function endMove() {
    if (goalsMet()) {
      hint.textContent = "Harvest complete!";
      btnNext.hidden = levelIndex >= LEVELS.length - 1;
      showModal(
        levelIndex >= LEVELS.length - 1 ? "Garden complete" : "Meadow cleared",
        levelIndex >= LEVELS.length - 1
          ? `Final score ${score}. Every essence bloomed.`
          : `Level ${levelIndex + 1} done. Score ${score}.`
      );
      return;
    }
    if (moves <= 0) {
      hint.textContent = "Dusk fell — try again.";
      showModal("Out of moves", "The meadow goes quiet. Restart and try a new path.");
    }
  }

  function resolvePath() {
    if (path.length < 2 || animating) {
      path = [];
      draw();
      return;
    }

    const colorId = grid[path[0].r][path[0].c];
    if (!colorId) {
      path = [];
      draw();
      return;
    }

    // Detect loop: path revisits a cell or forms closed square cycle
    let burst = false;
    const seen = new Map();
    for (let i = 0; i < path.length; i++) {
      const k = `${path[i].r},${path[i].c}`;
      if (seen.has(k)) {
        burst = true;
        break;
      }
      seen.set(k, i);
    }
    // Also burst if last connects back creating a cycle of length >= 4
    if (!burst && path.length >= 4) {
      const last = path[path.length - 1];
      for (let i = 0; i < path.length - 3; i++) {
        if (path[i].r === last.r && path[i].c === last.c) burst = true;
      }
      // square: path length 4 forming rectangle
      if (!burst && path.length === 4) {
        const set = new Set(path.map((p) => `${p.r},${p.c}`));
        if (set.size === 4) {
          const rs = path.map((p) => p.r);
          const cs = path.map((p) => p.c);
          if (Math.max(...rs) - Math.min(...rs) === 1 && Math.max(...cs) - Math.min(...cs) === 1) {
            burst = true;
          }
        }
      }
    }

    let toClear = path.slice();
    if (burst) {
      toClear = [];
      for (let r = 0; r < size; r++) {
        for (let c = 0; c < size; c++) {
          if (grid[r][c] === colorId) toClear.push({ r, c });
        }
      }
      hint.textContent = "Bloom Burst!";
    } else {
      hint.textContent = `Linked ${toClear.length} ${colorById(colorId).label}`;
    }

    moves -= 1;
    movesLeft.textContent = String(moves);
    clearCells(toClear, colorId, burst);
    path = [];
    applyGravity();
    animateDrops(endMove);
  }

  function onDown(e) {
    if (animating || moves <= 0 || goalsMet()) return;
    e.preventDefault();
    const { x, y } = getXY(e);
    const cellHit = hitCell(x, y);
    if (!cellHit || !grid[cellHit.r][cellHit.c]) return;
    dragging = true;
    path = [cellHit];
    draw();
  }

  function onMove(e) {
    if (!dragging || animating) return;
    e.preventDefault();
    const { x, y } = getXY(e);
    const cellHit = hitCell(x, y);
    if (!cellHit) return;
    const last = path[path.length - 1];
    if (last.r === cellHit.r && last.c === cellHit.c) return;

    // undo one step
    if (path.length >= 2) {
      const prev = path[path.length - 2];
      if (prev.r === cellHit.r && prev.c === cellHit.c) {
        path.pop();
        draw();
        return;
      }
    }

    if (!sameAdj(last, cellHit)) return;
    const colorId = grid[path[0].r][path[0].c];
    if (grid[cellHit.r][cellHit.c] !== colorId) return;

    if (pathHas(cellHit)) {
      // allow closing a loop by revisiting
      if (path.length >= 3) {
        path.push(cellHit);
        draw();
      }
      return;
    }

    path.push(cellHit);
    draw();
  }

  function onUp(e) {
    if (!dragging) return;
    e.preventDefault();
    dragging = false;
    resolvePath();
  }

  function showModal(title, body) {
    modalTitle.textContent = title;
    modalBody.textContent = body;
    modal.hidden = false;
  }

  function hideModal() {
    modal.hidden = true;
  }

  btnRestart.addEventListener("click", () => {
    hideModal();
    score = levelIndex === 0 ? 0 : score;
    // keep cumulative score across levels unless fully restarting level 1 from button mid-run
    startLevel(levelIndex);
  });

  btnNext.addEventListener("click", () => {
    hideModal();
    startLevel(levelIndex + 1);
  });

  modalBtn.addEventListener("click", () => {
    hideModal();
    if (goalsMet()) {
      if (levelIndex < LEVELS.length - 1) startLevel(levelIndex + 1);
      else startLevel(0);
    } else if (moves <= 0) {
      startLevel(levelIndex);
    }
  });

  canvas.addEventListener("mousedown", onDown);
  window.addEventListener("mousemove", onMove);
  window.addEventListener("mouseup", onUp);
  canvas.addEventListener("touchstart", onDown, { passive: false });
  canvas.addEventListener("touchmove", onMove, { passive: false });
  canvas.addEventListener("touchend", onUp, { passive: false });
  window.addEventListener("resize", resize);

  // ambient particle shimmer loop
  function ambience() {
    tickParticles();
    if (particles.length) draw();
    requestAnimationFrame(ambience);
  }

  loadImages().then(() => {
    score = 0;
    scoreVal.textContent = "0";
    startLevel(0);
    ambience();
  });
})();
