# Bloom Drops (Unity)

Unity 2D remake of **Two Dots**, remixed as a twilight botanical puzzle (**Bloom Drops**).

## Requirements

- Unity **2022.3 LTS** (or newer 2D-capable editor)
- Open the folder `games/BloomDropsUnity` in Unity Hub

## Setup (first open)

1. Open `games/BloomDropsUnity` in Unity Hub
2. Wait for package import
3. Menu: **Bloom Drops → Setup Project And Scene**
4. Press **Play**

That menu will:
- Mark drop/UI PNGs as Sprites
- Create `EssenceCatalog` + `LevelPack` ScriptableObjects
- Build `Drop` / `GoalChip` prefabs
- Create playable `Assets/Scenes/BloomDrops.unity`

## How to play

- Drag across adjacent drops of the **same color**
- Release to clear the chain
- Close a **loop / 2×2 square** for a **Bloom Burst** (clears all of that color)
- Finish goal counts before moves run out

## Project layout

```
Assets/
  Art/Drops/          essence sprites
  Art/UI/             icon + background
  Editor/             one-click setup
  Scripts/Core/       board, input, game manager
  Scripts/Data/       ScriptableObject types
  Scripts/UI/         HUD + popup
  Scenes/             created by setup menu
  Prefabs/            created by setup menu
  Resources/          catalog + levels
```

## AI art / concept (optional)

From the web prototype scripts (same keys):

```bash
# in games/bloom-drops
export OPENAI_API_KEY=...
export OPENART_API_KEY=...
node scripts/remix-concept.mjs
node scripts/generate-art.mjs
# copy new PNGs into Assets/Art/ then re-run Setup
```

## Platforms

Configured product id: `com.playcard.bloomdrops` (Android / iOS / Standalone). Portrait recommended.
