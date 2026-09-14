// Render a ZPL file to PNG via the Labelixa REST API (Go, stdlib only).
// Free tier needs no key and output carries NO watermark; set the
// X-API-Key header to use your own quota.
package main

import (
	"bytes"
	"fmt"
	"io"
	"net/http"
	"os"
)

func main() {
	zpl, err := os.ReadFile("../examples/shipping-label-4x6.zpl")
	if err != nil {
		panic(err)
	}
	req, _ := http.NewRequest("POST",
		"https://api.labelixa.com/v1/printers/8dpmm/labels/4x6/0",
		bytes.NewReader(zpl))
	// req.Header.Set("X-API-Key", "lbx_...")
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	if resp.StatusCode != 200 {
		body, _ := io.ReadAll(resp.Body)
		panic(fmt.Sprintf("HTTP %d: %s", resp.StatusCode, body))
	}
	out, _ := os.Create("label.png")
	defer out.Close()
	io.Copy(out, resp.Body)
}
