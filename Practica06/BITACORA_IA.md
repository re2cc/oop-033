- Hey, I am getting this errors (list of errors about IO), the code still works but the IDE still shows them. What is wrong?

En pocas palabras por razones varias (mayormente porque cambie a VS Code), el IDE no entiende que estoy usando Java 25 y por lo tranto no encuentra `java.lang.IO`, simplemente especifique el toolchain en `build.gradle.kts` y listo. Adicionalmente especifique la aplicación principal en `application` para que `gradle run` funcione correctamente (Va a haber que modificar los otros comandos, aparentemente IDEA no estaba usando gradle en la practica).

- What is the diamon problem?

Se explica en `REFLEXION.md`
