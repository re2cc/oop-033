- Azul vs Oracle vs Temurin

Basicamente me dio una tabla compartiva con las distribuciones, Oracle siendo utilizado basicamente en empresas que usen el ecosistema de Oracle; Elipse Temurin siendo la distribucion gratuita y "pura" de OpenJDK por excelencia y Azul Zulu teniendo como mayor ventaja el soporte para versiones viejas.

- Can Java interpret hexadecimal and decimal values and represent them as UTF-16?

Basicamente me dijo como hacerlo, es bastante sencillo porque aparentemente Java usa UFT-16 internamente. 

- Is IO.println (Java 25) just an alias of System.out.println? Does it self import it? Or is it like the rust prelude ?

En pocas palabras no es un alias, pero para efectos practicos si (segun entiendo su clase es java.io.IO, esta pensada
para simplificar el I/O basico). Si se auto-importa y si, para efectos practicos es lo mismo que el "prelude" de Rust.