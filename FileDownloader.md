
```java

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader {

    public static byte[] downloadFile(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return outputStream.toByteArray();
    }

    public static void main(String[] args) {
        String fileUrl = "https://url/sample.pdf";

        try {
            byte[] fileData = FileDownloader.downloadFile(fileUrl);
            // Aquí puedes procesar los datos del archivo o guardarlos en una variable
            System.out.println("ok");

        } catch (Exception e) {
            // Manejar la excepción en caso de error de descarga
            e.printStackTrace();

        }
    }
}

```
```
```
