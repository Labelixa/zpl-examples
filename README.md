# ZPL Examples

[![ZPL validated with Labelixa](https://labelixa.com/static/badge-zpl-validated.svg)](https://labelixa.com/tools/zpl-preview)

Working, tested **Zebra ZPL** label examples — shipping labels, product
barcodes, QR codes, shelf labels — plus copy-paste integration snippets
for calling the [Labelixa](https://labelixa.com) rendering API from
curl, Python, Node.js and C#.

Every `.zpl` file in this repository is rendered and linted against the
live ZPL engine in CI, so the examples cannot silently rot.

## Quick start — see a label without a printer

Paste any example into the free
[ZPL viewer](https://labelixa.com/tools/zpl-preview), or render it from
the command line:

```sh
curl -X POST "https://api.labelixa.com/v1/printers/8dpmm/labels/4x6/0" \
     --data-binary @examples/shipping-label-4x6.zpl > label.png
```

No API key required for the free tier, and no plan watermarks the
output; see the [API reference](https://labelixa.com/docs/api) for
authentication, PDF output, ZPL→EPL2 translation and limits.

There is also a CLI in the official Node SDK:

```sh
npx labelixa preview examples/shipping-label-4x6.zpl --width 4 --height 6 --out label.png
```

## Examples

| File | Size | Shows |
|---|---|---|
| [`examples/shipping-label-4x6.zpl`](examples/shipping-label-4x6.zpl) | 4×6 in | Address blocks, Code 128 tracking barcode, separator lines (`^GB`), fonts (`^CF`) |
| [`examples/product-label-ean13-2x1.zpl`](examples/product-label-ean13-2x1.zpl) | 2×1 in | Retail EAN-13 barcode (`^BE`) with product name and price |
| [`examples/qr-url-2x2.zpl`](examples/qr-url-2x2.zpl) | 2×2 in | QR code (`^BQ`) pointing to a URL |
| [`examples/datamatrix-serial-1x1.zpl`](examples/datamatrix-serial-1x1.zpl) | 1×1 in | Compact Data Matrix (`^BX`) with human-readable serial |
| [`examples/shelf-label-3x1.zpl`](examples/shelf-label-3x1.zpl) | 3×1 in | Border box, mixed font sizes, price + SKU barcode |
| [`examples/multi-label-batch-2x1.zpl`](examples/multi-label-batch-2x1.zpl) | 2×1 in ×3 | Multiple `^XA…^XZ` labels in one stream (render one page or all as PDF) |

All examples target **8 dpmm (203 dpi)** — the most common Zebra
resolution. `^PW`/`^LL` values are dots: inches × 203.

## Integrations

| File | Stack |
|---|---|
| [`integrations/curl.sh`](integrations/curl.sh) | curl — PNG, diagnostics, multi-label PDF |
| [`integrations/python-requests.py`](integrations/python-requests.py) | Python (`requests`) — or use the official SDK: `pip install labelixa` ([PyPI](https://pypi.org/project/labelixa/)) |
| [`integrations/node-fetch.mjs`](integrations/node-fetch.mjs) | Node.js 18+ (built-in `fetch`, no deps) — or use the official SDK: `npm install labelixa` ([npm](https://www.npmjs.com/package/labelixa)) |
| [`integrations/csharp-httpclient.cs`](integrations/csharp-httpclient.cs) | C# (`HttpClient`, .NET 6+) |
| [`integrations/java-httpclient.java`](integrations/java-httpclient.java) | Java 11+ (`java.net.http`, no deps) |
| [`integrations/go-nethttp.go`](integrations/go-nethttp.go) | Go (stdlib `net/http`, no deps) |

All snippets do the same thing — POST raw ZPL, get a PNG back — so pick
your language and adapt. Quota errors return `429` with a `Retry-After`
header; respect it instead of hammering.

## ZPL cheat notes

- A label is `^XA` … `^XZ`. Fields start with `^FO x,y` (position in
  dots) and end with `^FS`.
- `^FD` carries the data of the preceding field/barcode command.
- `^BY` sets barcode module width; `^BCN,180,Y,N,N` = Code 128, height
  180 dots, print interpretation line.
- Full per-command reference with parameters and rendered examples:
  [labelixa.com/zpl-commands](https://labelixa.com/zpl-commands).

## License

MIT — use these labels freely in your own projects.
