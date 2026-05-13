# Reflexion

1. ¿Cuál es la diferencia entre una clase y un objeto? Da un ejemplo con tu propio código.

Una clase es como una plantilla, un objeto es una instancia en memoria creada a partir de una clase, con valores de los
atributos de definidos

Por ejemplo:
```
Collectible defaultCollectible = new Collectible("Random thing");
```
En este código, la clase es `Collectible` define el constructor y los atributos que tiene. En esa línea creamos un objeto
(instancia) de esa clase llamado `defaultCollectible`.

2. ¿Por qué usaste 3 constructores distintos? ¿Qué problema resuelve cada uno?

Porque la práctica especificaba un mínimo de 3, pero además son para casos de uso distintos, en este caso, 
- `Collectible(String name)`: En caso de que solo conozcas el nombre. Asume valores por defecto con excepción del nombre.
- `Collectible(String name, String material)`: Similar al caso anterior, pero en este caso si sabes por lo menos de que
  material está hecho el objeto, los valores por defecto son más apropiados.
- `Collectible(String name, float uvaThreshold,  float uvbThreshold, float temperatureThreshold, float humidityThreshold)`:
  Se usa cuando tienes todos los datos técnicos y necesitas precision. Además, se utiliza en los otros constructores.

3. ¿Qué pasaría si no tuvieras constructores definidos? ¿Java sigue
   funcionando? ¿Por qué?

Nada, si no se define un constructor, Java crea uno por defecto.
