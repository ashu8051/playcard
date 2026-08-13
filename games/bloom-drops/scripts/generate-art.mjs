#!/usr/bin/env node
/**
 * Generate Bloom Drops art with OpenArt.
 * Requires: OPENART_API_KEY
 * Optional: reads games/bloom-drops/generated-concept.json for prompts
 *
 * Usage: node scripts/generate-art.mjs
 */
import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const root = path.resolve(__dirname, "..");
const assets = path.join(root, "assets");
const key = process.env.OPENART_API_KEY;

if (!key) {
  console.error("Missing OPENART_API_KEY. Add it to the environment and retry.");
  process.exit(1);
}

const conceptPath = path.join(root, "generated-concept.json");
let prompts = {
  icon: "App icon: five glowing botanical dew orbs (coral, mint, gold, sky blue, blossom pink) clustered like linked puzzle dots on deep teal night garden, soft luminous glow, no text",
  background:
    "Full-bleed mobile game background: twilight meadow garden, deep teal indigo sky, bioluminescent fireflies, soft mist, warm amber horizon, no UI no text",
  drops: [
    "Circular coral-rose glowing botanical dew drop orb game sprite, translucent jelly highlight, plain dark teal backdrop, no text",
    "Circular mint glowing botanical dew drop orb game sprite, translucent jelly highlight, plain dark teal backdrop, no text",
    "Circular warm gold glowing botanical dew drop orb game sprite, translucent jelly highlight, plain dark teal backdrop, no text",
    "Circular sky-blue glowing botanical dew drop orb game sprite, translucent jelly highlight, plain dark teal backdrop, no text",
    "Circular blossom-pink glowing botanical dew drop orb game sprite, translucent jelly highlight, plain dark teal backdrop, no text",
  ],
};

if (fs.existsSync(conceptPath)) {
  try {
    const concept = JSON.parse(fs.readFileSync(conceptPath, "utf8"));
    if (concept.openArtPrompts) {
      prompts = {
        icon: concept.openArtPrompts.icon || prompts.icon,
        background: concept.openArtPrompts.background || prompts.background,
        drops: concept.openArtPrompts.drops || prompts.drops,
      };
    }
  } catch (e) {
    console.warn("Could not parse generated-concept.json, using defaults.");
  }
}

async function generate(prompt, filename) {
  const res = await fetch("https://api.openart.ai/v1/images/generations", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${key}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      prompt,
      num_images: 1,
      output_format: "url",
    }),
  });

  if (!res.ok) {
    throw new Error(`OpenArt ${filename}: ${res.status} ${await res.text()}`);
  }

  const data = await res.json();
  const url =
    data?.data?.[0]?.url ||
    data?.images?.[0]?.url ||
    data?.url ||
    data?.result?.[0]?.url;

  if (!url) {
    throw new Error(`No image URL in OpenArt response for ${filename}: ${JSON.stringify(data).slice(0, 400)}`);
  }

  const img = await fetch(url);
  if (!img.ok) throw new Error(`Download failed for ${filename}`);
  const buf = Buffer.from(await img.arrayBuffer());
  const out = path.join(assets, filename);
  fs.writeFileSync(out, buf);
  console.log("Saved", out);
}

fs.mkdirSync(assets, { recursive: true });

const jobs = [
  [prompts.icon, "bloom-drops-icon.png"],
  [prompts.background, "bloom-drops-bg.png"],
  [prompts.drops[0], "drop-coral.png"],
  [prompts.drops[1], "drop-mint.png"],
  [prompts.drops[2], "drop-gold.png"],
  [prompts.drops[3], "drop-sky.png"],
  [prompts.drops[4], "drop-blossom.png"],
];

for (const [prompt, file] of jobs) {
  await generate(prompt, file);
}

console.log("OpenArt generation complete.");
