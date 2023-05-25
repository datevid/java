Manejo de excepciones en spring framework y spring boot:

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
