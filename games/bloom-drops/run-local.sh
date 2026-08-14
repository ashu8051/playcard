#!/usr/bin/env bash
# Run Bloom Drops locally (web prototype).
# Unity: open games/BloomDropsUnity in Unity Hub, then Play.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
PORT="${PORT:-8765}"
cd "$ROOT"
echo "Bloom Drops → http://127.0.0.1:${PORT}/"
echo "Drag matching drops. Loop = Bloom Burst."
python3 -m http.server "$PORT"
