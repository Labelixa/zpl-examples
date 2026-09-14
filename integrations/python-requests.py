"""Render and lint a ZPL file via the Labelixa REST API (requests only).

Free tier needs no key and output carries NO watermark; pass your key in the
X-API-Key header to use your own quota.
"""
import requests

zpl = open("../examples/shipping-label-4x6.zpl", "rb").read()

# ZPL -> PNG (label 0 of the stream, 8 dpmm = 203 dpi, 4x6 inches)
r = requests.post(
    "https://api.labelixa.com/v1/printers/8dpmm/labels/4x6/0",
    data=zpl,  # headers={"X-API-Key": "lbx_..."}
)
r.raise_for_status()
open("label.png", "wb").write(r.content)

# Lint before printing: structured diagnostics with line/column positions
report = requests.post(
    "https://api.labelixa.com/v1/diagnostics?dpmm=8&width=4&height=6",
    data=zpl,
).json()
for d in report["diagnostics"]:
    print(d["severity"], d["code"], d["message"])
