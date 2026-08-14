# Bloom Drops

A Two Dots–style puzzle remixed as a **twilight meadow harvest**: link matching botanical essences, close a loop for a **Bloom Burst**, and clear goals before moves run out.

## Play

Open `index.html` in a browser (or serve the folder):

```bash
cd games/bloom-drops
python3 -m http.server 8080
# visit http://localhost:8080
```

## How to play

1. Drag across adjacent drops of the **same color**
2. Release to clear the chain
3. Close a **loop / square** to Bloom Burst — clear all of that color
4. Finish goal counts before moves hit zero

## AI pipeline

| Step | Tool | Script |
|------|------|--------|
| Remix concept / prompts | OpenAI | `node scripts/remix-concept.mjs` |
| Generate icon, bg, drops | OpenArt | `node scripts/generate-art.mjs` |

Requires `OPENAI_API_KEY` and `OPENART_API_KEY`.

Shipped assets in `assets/` were generated so the game works offline without keys.

## Concept

See [CONCEPT.md](./CONCEPT.md).
