// Render and lint a ZPL file via the Labelixa REST API (Node 18+, no deps).
// Free tier needs no key (output is watermarked); add an X-API-Key header
// to use your own quota.
import { readFile, writeFile } from "node:fs/promises";

const zpl = await readFile("../examples/shipping-label-4x6.zpl");

// ZPL -> PNG
const png = await fetch(
  "https://api.labelixa.com/v1/printers/8dpmm/labels/4x6/0",
  { method: "POST", body: zpl },
);
await writeFile("label.png", Buffer.from(await png.arrayBuffer()));

// Lint before printing
const report = await fetch(
  "https://api.labelixa.com/v1/diagnostics?dpmm=8&width=4&height=6",
  { method: "POST", body: zpl },
).then((r) => r.json());
for (const d of report.diagnostics) console.log(d.severity, d.code, d.message);
