#!/usr/bin/env bash
# Open Bloom Drops in Unity Hub on YOUR machine (macOS / Linux / Windows Git Bash).
# This cloud VM does not include Unity Hub.
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
echo "Project: $ROOT"
echo "Scene:   Assets/Scenes/BloomDrops.unity"
echo

if command -v unity-hub >/dev/null 2>&1; then
  echo "Launching via unity-hub CLI..."
  unity-hub -- --projectPath "$ROOT"
  exit 0
fi

# macOS Unity Hub
if [[ -d "/Applications/Unity Hub.app" ]]; then
  echo "Opening in Unity Hub (macOS)..."
  open -a "Unity Hub" "$ROOT"
  echo "In Hub: install Editor 2022.3 LTS if needed → Open project → Play BloomDrops scene."
  exit 0
fi

# Windows (Git Bash) common path
WIN_HUB="/c/Program Files/Unity Hub/Unity Hub.exe"
if [[ -f "$WIN_HUB" ]]; then
  echo "Opening in Unity Hub (Windows)..."
  "$WIN_HUB" -- --projectPath "$ROOT"
  exit 0
fi

cat <<'EOF'
Unity Hub was not found on this machine.

Install & run:
  1. Install Unity Hub: https://unity.com/download
  2. In Hub → Installs → add Editor 2022.3 LTS (2D)
  3. Hub → Open → select this folder (games/BloomDropsUnity)
  4. Open Assets/Scenes/BloomDrops
  5. Press Play

Optional menu after import: Bloom Drops → Setup Project And Scene
EOF
exit 1
