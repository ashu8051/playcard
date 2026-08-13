# Bloom Drops — Unity Game

Two Dots–style puzzle in Unity 2D. Theme: twilight botanical **Bloom Drops**.

## Play (2 steps)

1. **Unity Hub → Open** folder: `games/BloomDropsUnity` (Unity **2022.3 LTS**)
2. Open scene `Assets/Scenes/BloomDrops` and press **Play**

The game **auto-builds at runtime** (board, UI, levels, art). No manual wiring required.

Optional polish menu: **Bloom Drops → Setup Project And Scene** (creates prefabs/ScriptableObjects).

## Controls

- Drag matching adjacent drops
- Release to clear
- Close a loop / 2×2 square → **Bloom Burst** (clears that whole color)
- Beat goals before moves run out

## Build mobile

`File → Build Settings` → Android or iOS  
Product id: `com.playcard.bloomdrops` · Portrait

## Folder

```
games/BloomDropsUnity/
  Assets/Scenes/BloomDrops.unity   ← open this
  Assets/Scripts/                  ← C# gameplay
  Assets/Resources/                ← sprites loaded at runtime
  Assets/Art/                      ← source art
```
