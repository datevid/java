
```java
import java.io.IOException;
import java.net.InetAddress;

public class URLPingChecker {

    public static void main(String[] args) {
        String url = "https://www.example.com/a/b/c/d"; // URL que deseas verificar
        boolean isAccessible = checkURLPing(url);

        if (isAccessible) {
            System.out.println("Acceso ping exitoso a la URL: " + url);
        } else {
            System.out.println("No se pudo acceder ping a la URL: " + url);
        }
    }

    public static boolean checkURLPing(String url) {
        try {
            String domainName = getDomainName(url);
            InetAddress address = InetAddress.getByName(domainName);
            return address.isReachable(5000); // Tiempo de espera de 5 segundos
        } catch (IOException e) {
            System.out.println("Error al hacer ping a la URL: " + e.getMessage());
            return false;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain;
    }
}
```
Resultado
```
Acceso ping exitoso a la URL: https://www.example.com

Process finished with exit code 0
```
