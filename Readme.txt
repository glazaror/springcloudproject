Spring Cloud
------------

Spring Cloud Config
- Para crear un archivo de configuración para un determinado microservicio. Si es que el microservicio ya contiene su propio archivo properties entonces las propiedades que existen en este properties de spring cloud config se sobreescribira (tienen mayor prioridad las propiedades que existan en el spring cloud config).
    Formato: echo [texto] > nombremicroservicio
    Ejemplo: echo server.port=8005 > servicio-items.properties
