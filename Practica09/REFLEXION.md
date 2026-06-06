# Reflexion

- ¿Qué es la serialización y cuándo es útil en comparación con guardar texto plano?

La serializacion es un proceso en el cual un objeto es convertido en bytes, usualmente para guardarlo en un archivo. Es util para guardar objetos con estructuras y relaciones complejas, ademas de ser mucho mas eficiente y seguro

- ¿Por qué usamos BufferedReader en lugar de leer byte a byte? ¿Qué mejora en rendimiento ofrece?

Al leer byte por byte el programa tiene que preguntarle al sistema operativo y este a su vez al disco por el siguiente byte del archivo, lo cual es muy ineficiente, un `BufferedReader` en cambio lee un "pedazo" del archivo en memoria, por lo que al leer el siguiente byte, es mucho mas rapido.

- ¿Qué riesgos tiene no cerrar un archivo después de usarlo? ¿Cómo los mitigaste?

El problema principal es que si no cierras un archivo correctamente, es posible que este se corrompa, ademas dependiendo del sistema operativo, el no cerrar un archivo puede provocar comportamientos inesperados (en Windows por un proceso solo puede abrir 512 archivos al mismo timepo y solo un programa puede abrir un archivo a la vez; en Linux existe un limite general al numero de archivos que pueden estar abiertos). La forma en que el programa evita es usando try-with-resourcesm, el cual se encarga de cerrar los archivos automaticamente una vez se dejan de utilizar
