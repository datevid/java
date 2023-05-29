Codigo que permite una petición a un servidor usando el body
```java
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        WebClient client = WebClient.builder()
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();

        MediaType mediaType = MediaType.APPLICATION_JSON;
        String requestBody = "{\n" +
                    "    \"coModulo\": \"MODULO_PRINCIPAL\",\n" +
                    "    \"usuario\": {\n" +
                    "        \"nombrePC\": \"datevid-pc\",\n" +
                    "        \"coUsuario\": \"12345678\",\n" +
                    "        \"ipPC\": \"127.0.0.1\",\n" +
                    "        \"usuPc\": \"datevid\"\n" +
                    "    }\n" +
                    "}";

        String response = client.post()
                .uri("https://urlAConsultar.com")
                .contentType(mediaType)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
                .flatMap(clientResponse -> {
                    HttpStatus status = clientResponse.statusCode();
                    if (status.equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else if (status.is4xxClientError()) {
                        return Mono.error(new RuntimeException("Error de cliente: " + status));
                    } else if (status.is5xxServerError()) {
                        return Mono.error(new RuntimeException("Error del servidor: " + status));
                    } else {
                        return Mono.error(new RuntimeException("Error desconocido: " + status));
                    }
                })
                .timeout(Duration.ofSeconds(10))
                .block();

        System.out.println(response);
    }
}

```
