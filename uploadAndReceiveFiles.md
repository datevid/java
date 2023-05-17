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
public class AdjuntoCommand{
	
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
