@echo off
REM todo script de windows debe comenzar con "@echo off"... en linux: "#!/bin/sh"
REM constante RABBIT_ADDRESSES... es el servidor broker de mensajeria para que Zipkin se configure como consumidor
set RABBIT_ADDRESSES=localhost:5672
java -jar ./zipkin-server-2.16.2-exec.jar