
### Test Controller

Un ejemplo de controlador para testeo

```
@RequestMapping("/")
@RestController
public class HomeController {
    @GetMapping(value = "/")
    public ResponseEntity<?> home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return new ResponseEntity<String>("Home", HttpStatus.OK);
    }

    @GetMapping("/a")
    public String a() {
        return "a";
    }

    @GetMapping("b")
    public String home1() {
        return "content b";
    }
}
```
