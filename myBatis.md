Mapeo de datos a un Bean que no tenga subGuiones

Tabla:
```sql
CREATE TABLE estudiante (
    nombre_completo VARCHAR(100) NOT NULL,
    direccion_casa VARCHAR(200) NOT NULL,
    numero_telefono VARCHAR(20) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    CONSTRAINT estudiante_pk PRIMARY KEY (correo_electronico)
);
```
Bean a mapear
```java
public class Estudiante {
    private String nombreCompleto;
    private String direccionCasa;
    private String numeroTelefono;
    private String correoElectronico;
    private String fechaNacimiento;

    // Getters and Setters
}
```
Mapeo en mybatis
```java
public interface EstudianteMapper {
    @Results({
        @Result(property = "nombreCompleto", column = "nombre_completo"),
        @Result(property = "direccionCasa", column = "direccion_casa"),
        @Result(property = "numeroTelefono", column = "numero_telefono"),
        @Result(property = "correoElectronico", column = "correo_electronico"),
        @Result(property = "fechaNacimiento", column = "fecha_nacimiento")
    })
    @Select("SELECT * FROM estudiante")
    List<Estudiante> getAllEstudiantes();
}
```
