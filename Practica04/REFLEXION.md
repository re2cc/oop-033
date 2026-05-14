# Reflexion

1. ¿Qué ventaja concreta te dio la herencia en este ejercicio? ¿Qué código
evitaste repetir?

La ventaja principal es la reutilización del código y la extensibilidad. Se redujo la repeticion de atributos
comunes (`uvaThreshold`, `temperatureThreshold`, etc.), sus getters y setters, y la lógica de métodos generales como
`elevateThreshold` y `uvIndexAproxThreshold` en cada una de las tres clases hijas de `PhysicalColletible`.

2. ¿Cuándo es apropiado usar super() y cuándo no es necesario?

Básicamente, cuando quieres inicializar los atributos de la clase padre o cuando quieres "ampliar" lo que hace una función,
no es necesario usarlo cuando planear cambiar por completo el comportamiento de una función o clase.

3. ¿Qué pasa si una clase hija no sobrescribe un método de la clase padre?
   ¿Cuál versión se ejecuta?

Nada, se ejecuta la función definida en la clase padre (a menos que sea privada).
