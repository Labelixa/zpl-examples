#!/bin/sh
# Render a ZPL file to PNG with a single request. Free tier needs no key
# (output is watermarked); add -H "X-API-Key: lbx_..." for your own quota.
curl -X POST "https://api.labelixa.com/v1/printers/8dpmm/labels/4x6/0" \
     --data-binary @../examples/shipping-label-4x6.zpl > label.png

# Lint the same file before printing (structured diagnostics as JSON):
curl -X POST "https://api.labelixa.com/v1/diagnostics?dpmm=8&width=4&height=6" \
     --data-binary @../examples/shipping-label-4x6.zpl

# All labels of a multi-label stream as one PDF (note the trailing slash):
curl -X POST "https://api.labelixa.com/v1/printers/8dpmm/labels/2x1/" \
     -H "Accept: application/pdf" \
     --data-binary @../examples/multi-label-batch-2x1.zpl > batch.pdf
