receive one file and param

```java
@PostMapping("/upload")
public String handleFileUpload(
        @RequestParam("token") String token,
        @RequestParam("file") MultipartFile file,
        HttpServletRequest request, HttpServletResponse response) {
    try {
        log.info("hola mundo");
        String originalFilename = file.getOriginalFilename();
        log.info("originalFilename: " +originalFilename);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "Archivo recibido correctamente";
}

```

recibir un string y un objeto
```java
    @PostMapping("/uploadObj")
    public ResponseEntity<ResponseREST> uploadObj(
            @Valid @RequestParam("token") String token,
            @Valid @ModelAttribute Adjunto adjunto,
            BindingResult valid,
            HttpServletRequest request, HttpServletResponse response) {
        if (valid.hasErrors()) {
            return super.badRequest(MGS_ERROR_FIELDS,valid);
        }
        try {
            log.info("hola mundo");
            String originalFilename = adjunto.getArchivo().getOriginalFilename();
            log.info("originalFilename: " +originalFilename);
            return super.ok("Archivo recibido correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return super.badRequest(e.getMessage());
        }
    }

```
El Bean puede ser de la siguiente forma:

```java
public class Adjunto{
	
	@NotNull
	private MultipartFile archivo;
	
	@NotNull
	@Positive
	@Max(20)
	private Integer folios;
	
	@Size(min=4, max = 40)
	private String detalle;
}

```

Recibir varios archivos a la vez
```java
    @PostMapping("/uploads2")
    public ResponseEntity<ResponseREST> uploads2(
            @Valid @RequestParam("files") MultipartFile[] multipartFile,
            BindingResult valid,
            HttpServletRequest request, HttpServletResponse response) {
        if (valid.hasErrors()) {
            return super.badRequest(MGS_ERROR_FIELDS,valid);
        }
        try {
            log.info("hola mundo");
            //String originalFilename = adjuntos.getArchivo().getOriginalFilename();
            //log.info("originalFilename: " +originalFilename);

            return super.ok("Archivo recibido correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return super.badRequest(e.getMessage());
        }
        //return "Archivo recibido correctamente";
    }
```
Recibir varios archivos juntos a datos adicionales como cantidad de páginas y descripción de cada archivo
```java
    @PostMapping("/uploads3")
    public ResponseEntity<ResponseREST> uploads3(
            @Valid @RequestParam("folios") Integer[] folios,
            @Valid @RequestParam("detalles") String[] descripciones,
            @Valid @RequestParam("files") MultipartFile[] multipartFile
            ) {
        try {
            log.info("hola mundo");
            return super.ok("Archivo recibido correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return super.badRequest(e.getMessage());
        }
    }
```
Recibir varios archivos juntos a datos adicionales como cantidad de páginas y descripción de cada archivo. Mapeado en un objeto

```java
    @PostMapping("/upload4")
    public ResponseEntity<ResponseREST> upload4(
            @Valid @ModelAttribute Request1 expedienteReq,
            BindingResult valid,
            HttpServletRequest request, HttpServletResponse response) {
        if (valid.hasErrors()) {
            return super.badRequest(MGS_ERROR_FIELDS,valid);
        }
        try {
            log.info("hola mundo");
            MultipartFile adjunto1 = expedienteReq.getAdjuntos()[0];
            String originalFilename = adjunto1.getOriginalFilename();
            log.info("originalFilename: " +originalFilename);

            //AdjuntoCommand adjunto1 = expedienteReq.getAdjuntos()[0];
            //String originalFilename = adjunto1.getArchivo().getOriginalFilename();
            //log.info("originalFilename: " +originalFilename);
            return super.ok("Archivo recibido correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return super.badRequest(e.getMessage());
        }
    }
```

donde la clase request será de la siguiente forma:

```java
public class Request1 {

    @NotNull
    private MultipartFile principal;

    private MultipartFile[] adjuntos;

    private Integer[] folios;

    private String[] detalles;
}

```
