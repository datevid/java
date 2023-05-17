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
