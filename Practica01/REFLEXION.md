# Reflexion

1. ¿Qué diferencia hay entre JDK, JRE y JVM? ¿Por qué instalamos el JDK?

El JDK (Java Development Kit) es el entorno de desarrollo para Java, es necesario para compilar y desarrollar (incluye el JRE);
el JRE (Java Runtime Environment) son unicamente los binarios y libreras necesarias para ejecutar un .jar (incluye el JVM);
el JVM (Java Virtual Machine) es una maquina virtual capas de entender el bytecode y
ejecutarlo en la plataforma que ejecuta. En este caso instalamos el JDK porque necesitamos compilar el codigo.

2. ¿Por qué Java es considerado «write once, run anywhere»? Explica con tus propias palabras.

En pocas palabras porque al ser compilado en bytecode para JVM, si tu plataforma tiene alguna implementacion de esta,
es posible ejecutarlo sin hacer ningun cambio a tu codigo

3. ¿Qué hace exactamente System.out.println()? ¿Qué clase y método estás usando?

System.out.println() imprime texto a la consola (stdout). La clase es System y el metodo es println().
> Nota: Mi codigo utiliza IO.println que es (para efectos practicos) un alias de System.out.println