// Render a ZPL file to PNG via the Labelixa REST API (Java 11+,
// java.net.http — no dependencies).
// Free tier needs no key and output carries NO watermark; set the
// X-API-Key header to use your own quota.
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class RenderLabel {
    public static void main(String[] args) throws Exception {
        HttpClient http = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(
                "https://api.labelixa.com/v1/printers/8dpmm/labels/4x6/0"))
            // .header("X-API-Key", "lbx_...")
            .POST(HttpRequest.BodyPublishers.ofFile(
                Path.of("../examples/shipping-label-4x6.zpl")))
            .build();
        HttpResponse<Path> resp = http.send(
            req, HttpResponse.BodyHandlers.ofFile(Path.of("label.png")));
        if (resp.statusCode() != 200) {
            throw new RuntimeException("HTTP " + resp.statusCode());
        }
    }
}
