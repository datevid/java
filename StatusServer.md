
```java
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerStatus {

    public static boolean isServerUp(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        return responseCode == 200;
    }
}

```
this function can be used as follows:

```java
boolean isServerUp = ServerStatus.isServerUp("https://example.com/a/b");
if (isServerUp) {
    System.out.println("Server is up and running");
} else {
    System.out.println("Server is down");
}

```
