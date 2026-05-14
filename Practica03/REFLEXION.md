# Reflexion

1. ¿Por qué marcamos atributos como private? ¿Qué riesgo evitamos?

Para evitar que se modifique el estado interno de los objetos accidentalmente, lo que podría ocasionar comportamiento
inesperado o corrupción datos.

2. ¿Cuál es la diferencia entre private, protected y public? Ilustra con un ejemplo de tu código

- private: Solo accesible dentro de la propia clase (`humidityThreshold`)
- protected: Accesible por la clase, clases del mismo paquete y subclases (`capacity`)
- public: Accesible desde cualquier clase en cualquier paquete

3. ¿Qué validación incluiste en un setter? ¿Qué pasa si el valor recibido es inválido?

Validación de rango en setHumidityThreshold (0% a 100%) y límite físico en setTemperatureThreshold (mínimo -273.15°C). Si el
valor es inválido, el método lanza IllegalArgumentException.
