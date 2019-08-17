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
