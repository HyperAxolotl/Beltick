# Beltick

Instrucciones de intalación

```
- Descargar proyecto
- Crear una nueva base de datos
- En la carpeta src/sql del proyecto, correr los archivos BaseDatos.sql, restricciones.sql y disparadores.sql en ese orden en la base de datos creada
- Abrir netbeans
- Hacer click en File -> New Project -> Java Web -> Web Application with Existing Sources
- En Location, buscar el directorio del proyecto Beltick, dar click en Next
- El servidor debe ser GlassFish Server 4.1.1 y la versión de Java EE debe ser Java EE 7 Web, dar click en Next y por último en Finish.
- Agregar los archivos .jar localizados en /jars a Libraries
- Modificar archivo src/java/xml/hibernate.cfg.xml con la información de la base de datos previamente creada
- Agregar las siguientes bibliotecas a Libraries:
  - PostgreSQL JDBC Driver
  - Hibernate 4.3.x
  - Hibernate 4.3.x(JPA2.1)
Ya se puede correr la aplicación
```