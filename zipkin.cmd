@echo off
REM todo script de windows debe comenzar con "@echo off"... en linux: "#!/bin/sh"
REM constante RABBIT_ADDRESSES... es el servidor broker de mensajeria para que Zipkin se configure como consumidor
set RABBIT_ADDRESSES=localhost:5672
REM constantes STORAGE_TYPE, MYSQL_USER, MYSQL_PASS para que zipkin almacene la información de los logs en mysql (puede ser cassandra, elasticsearch, etc)
REM las constantes estan en https://github.com/openzipkin/zipkin/tree/master/zipkin-server
set MYSQL_HOST=192.168.1.37
set MYSQL_TCP_PORT=3306
set STORAGE_TYPE=mysql
set MYSQL_USER=zipkin
set MYSQL_PASS=zipkin
java -jar ./zipkin-server-2.16.2-exec.jar