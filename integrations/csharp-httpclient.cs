// Render a ZPL file to PNG via the Labelixa REST API (.NET 6+).
// Free tier needs no key (output is watermarked); set the X-API-Key
// header to use your own quota.
using var http = new HttpClient();
// http.DefaultRequestHeaders.Add("X-API-Key", "lbx_...");

var zpl = await File.ReadAllBytesAsync("../examples/shipping-label-4x6.zpl");
var resp = await http.PostAsync(
    "https://api.labelixa.com/v1/printers/8dpmm/labels/4x6/0",
    new ByteArrayContent(zpl));
resp.EnsureSuccessStatusCode();
await File.WriteAllBytesAsync("label.png",
    await resp.Content.ReadAsByteArrayAsync());
