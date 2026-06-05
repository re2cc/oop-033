# Reflexion

- ¿Cuál es la diferencia entre una excepción chequeada (checked) y una no chequeada (unchecked)?

Basicamente las excepciones chequeadas heredan de `Exception`, el compilador verifica que esten en un bloque `try`, se utilizan para errore que se consideran previsibles o no criticos.
Una exception no chequeada heread de `RuntimeException`, el compilador no te obliga a que esten en un bloque `try` y suelen ser errores criticos.

- ¿Por qué creaste una jerarquía de excepciones en lugar de usar Exception directamente?

Porque la practica lo pedia, pero tiene varias ventajas, te permite utilizar el polimorfismo para diferenciar excepciones esperables (que la vitrina este llena) de errores inesperados (un archivo esta ocupado), ademas permite heredar parte de la logica de la excepcion.

- ¿Qué ventaja tiene try-with-resources sobre un bloque finally tradicional?

try-with-resources es basicamente una version mejorada de finally, se ve mas limpio, evita try-catch anidados y automaticamente cierra los recursos en el orden de delcaracion.
