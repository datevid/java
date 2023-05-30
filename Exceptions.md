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

Se puede crear una validación para las exceptions
```java
protected ResponseEntity<ResponseREST> badRequest(Exception e) {
	ResponseREST res = new ResponseREST(e.getMessage())
	return this.validaException(res, e);
	}


private ResponseEntity<ResponseREST> validaException(ResponseREST res, Exception e) {
	if (e instanceof ServiceException) {
		ResponseEntity<ResponseREST> resul =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		return resul;
	}else if (e instanceof DataAccessException) {
		System.out.println("Error: Base de datos");
		e.printStackTrace();
		return this.errorServer(e);
	} else if (e instanceof Exception500) {
		System.out.println("Error 500:");
		e.printStackTrace();
		String codigoInterno = ((Exception500) e).codigoInterno;
		if (codigoInterno != null) {
			return this.errorServer(e,codigoInterno);
		}else {
			return this.errorServer(e);
		}
	}else if (e instanceof Exception) {
		e.printStackTrace();
		return this.errorServer(e);
	}  else {
		e.printStackTrace();
		return this.errorServer(e);
	}
}


protected ResponseEntity<ResponseREST> errorServer(Exception e) {
	log.error("Error del servidor: "+e.toString());
	ResponseREST res = this.error(MSG_ERR_SERVER, null);
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
}

private ResponseEntity<ResponseREST> errorServer(Exception e, String codigoInterno) {
	log.error("Error del servidor: "+codigoInterno);
	log.error("Error del servidor: "+e.toString());
	ResponseREST res = this.error(MSG_ERR_SERVER+" Cod. Interno: "+codigoInterno, null);
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
}
```
