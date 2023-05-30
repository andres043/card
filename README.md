# Tarjetas

## Documentación

* Para poder iniciar el proyecto se debe contar con una base de datos postgres con el nombre **credibanco**


### Base de datos

<img src="C:\projects\pruebas\credibanco\card\src\main\resources\static\diagram.png"/>

* No es necesario ejecutar scripts, ya que el proyecto cuenta con flyway, el cual se encarga de gestionar las versiones de la BD

### Variables de entorno
Se deben establecer las variables de entorno con los valores para la conexión a BD
* `DB_URL`=jdbc:postgresql://localhost:5432/database_name
* `DB_USER`=username
* `DB_PASSWORD`=password

### Swagger
Una vez iniciada la aplicación, se puede acceder a Swagger desde los siguientes links
* [Api docs](http://localhost:8080/v2/api-docs)
* [Swagger viewer](http://localhost:8080/swagger-ui.html#/)
