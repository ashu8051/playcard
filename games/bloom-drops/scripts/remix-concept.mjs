#!/usr/bin/env node
/**
 * Remix the Bloom Drops concept with OpenAI.
 * Requires: OPENAI_API_KEY
 *
 * Usage: node scripts/remix-concept.mjs ["optional seed idea"]
 */
import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const root = path.resolve(__dirname, "..");
const key = process.env.OPENAI_API_KEY;

if (!key) {
  console.error("Missing OPENAI_API_KEY. Add it to the environment and retry.");
  process.exit(1);
}

const seed =
  process.argv.slice(2).join(" ").trim() ||
  "Reimagine Two Dots as a kids botanical twilight meadow puzzle called Bloom Drops.";

const prompt = `You are a game designer. Remix the classic mobile game Two Dots into a fresh kids-friendly concept.

Seed: ${seed}

Return JSON only with keys:
- title (string)
- tagline (string)
- fantasy (2-3 sentences)
- essences (array of 5 objects: id, name, hex, feel)
- bloomBurst (string explaining the closed-loop super clear)
- levelIdeas (array of 3 short level pitches)
- openArtPrompts (object with icon, background, and drops[5] image prompts for OpenArt)`;

const res = await fetch("https://api.openai.com/v1/chat/completions", {
  method: "POST",
  headers: {
    Authorization: `Bearer ${key}`,
    "Content-Type": "application/json",
  },
  body: JSON.stringify({
    model: "gpt-4o-mini",
    temperature: 0.85,
    response_format: { type: "json_object" },
    messages: [
      { role: "system", content: "Return valid JSON only." },
      { role: "user", content: prompt },
    ],
  }),
});

if (!res.ok) {
  console.error("OpenAI error", res.status, await res.text());
  process.exit(1);
}

const data = await res.json();
const text = data.choices?.[0]?.message?.content || "{}";
const outPath = path.join(root, "generated-concept.json");
fs.writeFileSync(outPath, text);
console.log("Wrote", outPath);
console.log(text);
