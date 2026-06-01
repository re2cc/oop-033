# Reflexion

1. ¿Qué diferencia hay entre sobrescritura (override) y sobrecarga (overload)? Da un ejemplo de cada una desde tu código.

Un override ocurre cuando una funcion hija hereda una funcion de su clase padre y la reescribe, un ejemplo es el
metodo `@Override public boolean needsSpecialCare()`. Un overload ocurre cuando una clase define multiples metodos
con el mismo nombre pero con diferentes parametros, un ejemplo son los metodos `public abstract double estimateValue(double basePrice);`, `public abstract double estimateValue(double basePrice, int yearsElapsed);` y `public abstract double estimateValue(double basePrice, int yearsElapsed, boolean isRare);`.

2. ¿Por qué usaste instanceof antes de hacer un cast? ¿Qué excepción previene?

Porque nos permitite verificar la subclase especifica de un objeto, ayuda a prevenir un `ClassCastException` que ocurre cuando se intenta hacer cast de un tipo incompatible.

3. ¿Podrías instanciar tu clase abstracta directamente? ¿Por qué sí o por qué no?

No, en un lenguaje orientado a objetos, las clases abstractas no pueden ser instanciadas directamente, solo pueden ser heredadas. Generalmente no se puede porque representan "conceptos" (abstractos) y no algo concreto, ademas en la practica suelen tener metodos abstractos y atributos que no estan inicializados.
