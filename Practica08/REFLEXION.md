# Reflexion

- ¿Por qué usaste cada estructura de colección para cada tipo de dato? ¿Qué pasaría si usaras ArrayList para todo?

Cada tipo de coleccion tiene su utilidad, en este caso se utiliza ArrayList como coleccion principal o cuando es necesario iterar sobre todos los coleccionables, HashMap para obtener un coleccionable por nombre (es mucho mas rapido que iterar), HashSet se usar para poder saber que tipo de coleccionables tiene un Showcase y LinkedList se utiliza como historial porque se puede usar como una cola (FIFO). En principio puedes usar ArrayList para todo, es solo que es menos eficiente

- ¿Qué diferencia hay entre Comparable y Comparator? ¿Cuándo usarías cada uno?

Comparable define la forma predeterminada por la que se debe ordenar una clase, Comparator se utiliza para definir formas alternativas para ordenarla. Generalmente usas Comparable para ordenar de la forma obvia (alfabetico, ID, etc), y Comparator cuando necesitas comparadores compuestos

- Explica con tus palabras qué hace una operación Stream. ¿Por qué es más legible que un bucle for?

Un stream para similar a como funcionan los iteradores en Rust, basicamente convierte una coleccion en una secuencia de elementos, lo interesante de esto es que te permite utilizar codigo funcional como .filter, .map y .collect, que aunque tecnicamente se pueden implementar con un for, es mucho menos elegante.
