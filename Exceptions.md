Manejo de excepciones en spring framework y spring boot:

Modelo 01:
```java
@RestController
@RequestMapping("/persona")
public class PersonaController {

    @GetMapping("/obtenerId/{id}")
    public ResponseEntity<ApiResponse> obtenerPersonaPorId(@PathVariable("id") Long id) {
        try {
            // Simulación de error 500
            if (id == 1) {
                throw new RuntimeException("Error interno en el servidor");
            }

            // Simulación de error 400
            if (id == 2) {
                throw new IllegalArgumentException("Id de persona inválido");
            }

            // Lógica para obtener la persona por su ID
            // ...

            Persona persona = new Persona(id, "John Doe", 25);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Operación exitosa", persona));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno en el servidor", null));
        }
    }

    // Clase para la estructura de respuesta genérica
    public static class ApiResponse {
        private int status;
        private String message;
        private Object data;

        public ApiResponse(int status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}

```
Modelo 02:

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    @GetMapping("/obtenerId/{id}")
    public ResponseEntity<ResponseData> obtenerPersonaPorId(@PathVariable("id") Long id) {
        try {
            // Lógica para obtener la persona por su ID
            Persona persona = obtenerPersonaDesdeLaBaseDeDatos(id);

            if (persona != null) {
                return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), "Persona encontrada", persona));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseData(HttpStatus.NOT_FOUND.value(), "No se encontró ninguna persona con el ID proporcionado", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error al procesar la solicitud", null));
        }
    }

    // Simulación de obtención de persona desde la base de datos
    private Persona obtenerPersonaDesdeLaBaseDeDatos(Long id) {
        // Lógica para obtener la persona desde la base de datos (puede ser una llamada a un repositorio, servicio, etc.)
        // Aquí se muestra un ejemplo básico
        if (id.equals(1L)) {
            return new Persona(1L, "John Doe");
        } else {
            return null;
        }
    }

    // Clase de modelo Persona (solo para referencia)
    private static class Persona {
        private Long id;
        private String nombre;

        public Persona(Long id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        // Getters y setters
        // ...
    }

    // Clase de modelo para la respuesta con status, message y data
    private static class ResponseData {
        private int status;
        private String message;
        private Object data;

        public ResponseData(int status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        // Getters y setters
        // ...
    }
}

```
