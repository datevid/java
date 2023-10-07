Validar la conexion exitosa a una base de datos Postgresql

```
@Override
public boolean validateConectDB() {
    StringBuffer sql = new StringBuffer();
    sql.append("select 'León 1' as ap_paterno, 'Vilca 2' as ap_materno FROM dual");
    Estudiante estudiante = null;
    try {
        estudiante = this.jdbcTemplate.queryForObject(sql.toString(), BeanPropertyRowMapper.newInstance(Estudiante.class), new Object[]{});
        if (estudiante == null) {
            return false;
        }
        return true;
    } catch (EmptyResultDataAccessException e) {
        return false;
    } catch (Exception e) {
        return false;
    }
}
```
