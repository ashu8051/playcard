using System;
using System.Collections;
using System.Collections.Generic;
using BloomDrops.Data;
using UnityEngine;

namespace BloomDrops.Core
{
    /// <summary>
    /// Two Dots-style board: link adjacent same-color drops; closed loop = Bloom Burst.
    /// </summary>
    public class BoardController : MonoBehaviour
    {
        [Header("Refs")]
        public EssenceCatalog catalog;
        public DropView dropPrefab;
        public Transform boardRoot;
        public LineRenderer linkLine;

        [Header("Layout")]
        public float cellSize = 1.1f;
        public float settleSpeed = 14f;

        public event Action<EssenceId, int, bool> OnCleared; // essence, count, burst
        public event Action OnBoardSettled;

        DropView[,] _grid;
        int _size;
        int _colorCount;
        readonly List<DropView> _path = new List<DropView>();
        bool _inputLocked;
        Camera _cam;

        public int Size => _size;
        public bool InputLocked => _inputLocked;
        public IReadOnlyList<DropView> CurrentPath => _path;

        void Awake()
        {
            _cam = Camera.main;
            if (boardRoot == null) boardRoot = transform;
            if (linkLine != null)
            {
                linkLine.positionCount = 0;
                linkLine.startWidth = 0.12f;
                linkLine.endWidth = 0.12f;
            }
        }

        public void Build(int size, int colorCount)
        {
            ClearBoard();
            _size = size;
            _colorCount = Mathf.Clamp(colorCount, 2, catalog.essences.Length);
            _grid = new DropView[_size, _size];

            for (int r = 0; r < _size; r++)
            for (int c = 0; c < _size; c++)
                SpawnDrop(r, c, RandomEssence(), true);
        }

        public void ClearBoard()
        {
            _path.Clear();
            RedrawLine();
            if (_grid == null) return;
            for (int r = 0; r < _size; r++)
            for (int c = 0; c < _size; c++)
            {
                if (_grid[r, c] != null)
                {
                    Destroy(_grid[r, c].gameObject);
                    _grid[r, c] = null;
                }
            }
        }

        EssenceId RandomEssence()
        {
            int i = UnityEngine.Random.Range(0, _colorCount);
            return catalog.essences[i].id;
        }

        DropView SpawnDrop(int row, int col, EssenceId id, bool instant)
        {
            var def = catalog.Get(id);
            var drop = Instantiate(dropPrefab, boardRoot);
            drop.gameObject.SetActive(true);
            drop.Setup(id, def.sprite, Color.white, row, col);
            drop.transform.position = CellToWorld(row, col) + (instant ? Vector3.zero : Vector3.up * (_size + 2) * cellSize);
            _grid[row, col] = drop;
            return drop;
        }

        public Vector3 CellToWorld(int row, int col)
        {
            float ox = -(_size - 1) * cellSize * 0.5f;
            float oy = -(_size - 1) * cellSize * 0.5f;
            // row 0 at top
            return boardRoot.position + new Vector3(ox + col * cellSize, oy + (_size - 1 - row) * cellSize, 0f);
        }

        public DropView HitDrop(Vector3 screenPos)
        {
            if (_inputLocked || _grid == null) return null;
            var world = _cam.ScreenToWorldPoint(screenPos);
            world.z = 0f;
            DropView best = null;
            float bestDist = cellSize * 0.55f;
            for (int r = 0; r < _size; r++)
            for (int c = 0; c < _size; c++)
            {
                var d = _grid[r, c];
                if (d == null) continue;
                float dist = Vector2.Distance(world, d.transform.position);
                if (dist < bestDist)
                {
                    bestDist = dist;
                    best = d;
                }
            }
            return best;
        }

        public void BeginPath(DropView drop)
        {
            if (_inputLocked || drop == null) return;
            ClearPathSelection();
            _path.Clear();
            _path.Add(drop);
            drop.SetSelected(true);
            RedrawLine();
        }

        public void ExtendPath(DropView drop)
        {
            if (_inputLocked || drop == null || _path.Count == 0) return;

            var last = _path[_path.Count - 1];
            if (last == drop) return;

            // Undo one step
            if (_path.Count >= 2 && _path[_path.Count - 2] == drop)
            {
                last.SetSelected(false);
                _path.RemoveAt(_path.Count - 1);
                RedrawLine();
                return;
            }

            if (!IsAdjacent(last, drop)) return;
            if (drop.Essence != _path[0].Essence) return;

            // Allow revisit to close a loop
            if (_path.Contains(drop))
            {
                if (_path.Count >= 3)
                {
                    _path.Add(drop);
                    RedrawLine();
                }
                return;
            }

            _path.Add(drop);
            drop.SetSelected(true);
            RedrawLine();
        }

        public void EndPath()
        {
            if (_inputLocked) return;
            if (_path.Count < 2)
            {
                ClearPathSelection();
                _path.Clear();
                RedrawLine();
                return;
            }

            StartCoroutine(ResolvePathRoutine());
        }

        bool IsAdjacent(DropView a, DropView b)
        {
            return Mathf.Abs(a.Row - b.Row) + Mathf.Abs(a.Col - b.Col) == 1;
        }

        bool PathIsBurst()
        {
            // Revisited cell => loop
            var seen = new HashSet<DropView>();
            foreach (var d in _path)
            {
                if (!seen.Add(d)) return true;
            }

            // 2x2 square of 4 unique cells
            if (_path.Count == 4)
            {
                int minR = int.MaxValue, maxR = int.MinValue, minC = int.MaxValue, maxC = int.MinValue;
                var uniq = new HashSet<DropView>(_path);
                if (uniq.Count == 4)
                {
                    foreach (var d in uniq)
                    {
                        minR = Mathf.Min(minR, d.Row);
                        maxR = Mathf.Max(maxR, d.Row);
                        minC = Mathf.Min(minC, d.Col);
                        maxC = Mathf.Max(maxC, d.Col);
                    }
                    if (maxR - minR == 1 && maxC - minC == 1) return true;
                }
            }
            return false;
        }

        IEnumerator ResolvePathRoutine()
        {
            _inputLocked = true;
            var essence = _path[0].Essence;
            bool burst = PathIsBurst();

            var toClear = new List<DropView>();
            if (burst)
            {
                for (int r = 0; r < _size; r++)
                for (int c = 0; c < _size; c++)
                {
                    var d = _grid[r, c];
                    if (d != null && d.Essence == essence) toClear.Add(d);
                }
            }
            else
            {
                // Unique path cells
                var uniq = new HashSet<DropView>();
                foreach (var d in _path)
                {
                    if (uniq.Add(d)) toClear.Add(d);
                }
            }

            ClearPathSelection();
            _path.Clear();
            RedrawLine();

            int count = toClear.Count;
            foreach (var d in toClear)
            {
                _grid[d.Row, d.Col] = null;
                Destroy(d.gameObject);
            }

            OnCleared?.Invoke(essence, count, burst);

            yield return ApplyGravityAndRefill();
            _inputLocked = false;
            OnBoardSettled?.Invoke();
        }

        IEnumerator ApplyGravityAndRefill()
        {
            // Collapse down (higher row index = bottom visually? We use row 0 at top)
            for (int c = 0; c < _size; c++)
            {
                int write = _size - 1;
                for (int r = _size - 1; r >= 0; r--)
                {
                    if (_grid[r, c] != null)
                    {
                        if (write != r)
                        {
                            _grid[write, c] = _grid[r, c];
                            _grid[r, c] = null;
                            _grid[write, c].SetGridPos(write, c);
                        }
                        write--;
                    }
                }
                while (write >= 0)
                {
                    SpawnDrop(write, c, RandomEssence(), false);
                    write--;
                }
            }

            // Animate settle
            float t = 0f;
            while (t < 0.55f)
            {
                t += Time.deltaTime;
                for (int r = 0; r < _size; r++)
                for (int c = 0; c < _size; c++)
                {
                    var d = _grid[r, c];
                    if (d == null) continue;
                    var target = CellToWorld(r, c);
                    d.transform.position = Vector3.Lerp(d.transform.position, target, Time.deltaTime * settleSpeed);
                }
                yield return null;
            }

            // Snap
            for (int r = 0; r < _size; r++)
            for (int c = 0; c < _size; c++)
            {
                if (_grid[r, c] != null)
                    _grid[r, c].transform.position = CellToWorld(r, c);
            }
        }

        void ClearPathSelection()
        {
            foreach (var d in _path) if (d != null) d.SetSelected(false);
        }

        void RedrawLine()
        {
            if (linkLine == null) return;
            if (_path.Count == 0)
            {
                linkLine.positionCount = 0;
                return;
            }

            var def = catalog.Get(_path[0].Essence);
            linkLine.startColor = def.color;
            linkLine.endColor = def.color;
            linkLine.positionCount = _path.Count;
            for (int i = 0; i < _path.Count; i++)
            {
                var p = _path[i].transform.position;
                p.z = -0.1f;
                linkLine.SetPosition(i, p);
            }
        }
    }
}
