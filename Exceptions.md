Exception para errores de servidor sería de la siguiente manera:

```java
/**
 * Excepcion con codigo interno que permita identificar el error ocurrido
 */
public class Exception500 extends Exception {

	public String codigoInterno;

	public Exception500(String message) {
		super(message);
	}

	public Exception500(String message, String codigoInterno) {
		super(message);
		this.codigoInterno=codigoInterno;
	}

	public Exception500(Throwable cause) {
		super(cause);

	}

	public Exception500(String message, Throwable cause) {
		super(message, cause);

	}

}
```

Los errores de estas excepciones de status 500 pueden listarse en un enum o constantes:

```java
public enum ErrorExceptionCodes {

    ERROR_WS_001("Error 001. duplicado"),
    ERROR_WS_002("Error 503. no se pudo enviar data al servidor interno"),
    ERROR_WS_003("Error 003. No se pudo conectar al servidor principal");

    private String message;

    private ErrorExceptionCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return this.name();
    }

    public String getMessageClient() {
        return "Ha ocurrido un error en el servidor. " + this.name();
    }
}
```
Cómo usarlo:
```java
	throw new Exception500(
                        ErrorExceptionCodes.ERROR_WS_001.getMessage(),
                        ErrorExceptionCodes.REPO_SGD_412.getCode()
                );
```

La excepcion debe escalar hasta el controlador donde se valida y se muestra un mensaje al cliente dependiendo de qué exception se presenta
