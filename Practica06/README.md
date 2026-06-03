# Practica 6

## Combinaciones

Todas las clases concretas de la clase abstracta `Collectible` tambien derivan de la clase abstracta `PhysicalCollectible`, la cual implementa `Preservable`, que tiene sentido porque los objetos fisicos se pueden preservar.

### OilPaintCollectible (Preservable, Displayable y Valuable)

Una pintura de oleo se puede preservar, se puede mostrar en una pared y generalmente tiene un valor monetario.

### PvcFigureCollectible (Preservable y Displayable)

Una figura de PVC se puede preservar y mostrar en una vitrina o estante, pero generalmente no es valiosa despues de que se saca de su empaque original.

### WoodCollectible (Preservable y Valuable)

Una escultura de madera se puede preservar y es valiosa, pero no se puede mostrar tan facilmente (en realidad en este caso, no se implementa meramente para tener combinaciones de todos los casos).
