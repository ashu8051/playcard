# Bloom Drops — Two Dots Concept Remix

## Original mechanic
**Two Dots**: connect adjacent same-color dots; clear the chain. Closing a loop clears every dot of that color. Gravity refills the board. Beat color goals before moves run out.

## Remixed concept (AI)
**Bloom Drops** turns plain color dots into **bioluminescent botanical essences** in a twilight meadow:

| Essence | Color | Feel |
|---------|-------|------|
| Coral Rose | `#FF6B6B` | Warm petal glow |
| Mint Dew | `#5EEAD4` | Cool leaf light |
| Sun Pollen | `#FBBF24` | Golden nectar |
| Sky Drop | `#60A5FA` | Rain-kissed blue |
| Night Blossom | `#F9A8D4` | Soft dusk pink |

### Theme fantasy
You are a meadow keeper. Each cleared chain waters the garden. A closed loop triggers a **Bloom Burst** — every matching essence on the board blooms at once. Clear the nightly harvest goals before dusk (moves) ends.

### Design notes
- Soft dusk garden atmosphere (deep teal / indigo), not neon arcade
- Orbs feel like dew / pollen, not candy
- Gentle motion: link trail sparkles, soft pop on clear, settle gravity
- Kid-friendly, no violence, short sessions

## Art pipeline
1. **OpenAI** (`scripts/remix-concept.mjs`) — remix theme / level copy from a seed prompt
2. **OpenArt** (`scripts/generate-art.mjs`) — generate icon, background, and drop sprites
3. Assets land in `assets/` and are referenced by the web game

Until API keys are set, baked assets from the Cursor image generator ship with the game.
