Spring Cloud
------------

Spring Cloud Config
- Para crear un archivo de configuración para un determinado microservicio. Si es que el microservicio ya contiene su propio archivo properties entonces las propiedades que existen en este properties de spring cloud config se sobreescribira (tienen mayor prioridad las propiedades que existan en el spring cloud config).
    Formato: echo [texto] > nombremicroservicio
    Ejemplo: echo server.port=8005 > servicio-items.properties

- Una vez desplegada la aplicación Spring Cloud Config se puede consultar la configuración de cada microservicio:
    Formato: http://servidor:puerto/nombremicroservicio/nombreambienteOprofile
    Donde: nombreambienteOprofile es el nombre del ambiente configurado, si es que no se tiene ninguno entonces se puede usar el "default"
    
    Ejemplo: http://localhost:8888/servicio-items/default
    
- Para trabajar con un repositorio local el archivo properties debe estar confirmado (commit)

- Se puede tener un archivo de configuración por cada ambiente (desarrollo, calidad, produccion, etc):
    Formato: {nombremicroservicio}-{profile}.properties

- Para refrescar los cambios de los properties del servidor de configuracion en los microservicios (@Value) ... sin necesidad de reiniciar el servidor
    POST -> localhost:8005/actuator/refresh
	
Spring Security (OAuth 2)
- El esquema de tablas de usuario:
	usuarios (1)---(1..*) usuario_roles (1..*)---(1) roles


Spring Sleuth (trace id, span id)
- Para trabajar con Zipkin descargar: https://zipkin.io/pages/quickstart.html
	java -jar zipkin-server-2.16.2-exec.jar

  Puerto por defecto de la consola: 9411
	http://localhost:9411/zipkin/

- Si es que configuramos dependencia Zipkin en nuestro proyecto entonces por defecto se adiciona la dependencia Sleuth