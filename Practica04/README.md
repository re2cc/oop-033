# Practica 4

## Diagrama

![](media/diagram.png)

```plantuml
@startuml
skinparam classAttributeIconSize 0

class Collectible {
  - String name
  + Collectible(String name)
  + String getName()
  + void setName(String name)
  + String toString()
}

class PhysicalCollectible {
  - float uvaThreshold
  - float uvbThreshold
  - float temperatureThreshold
  - float humidityThreshold
  + PhysicalCollectible(String, float, float, float, float)
  + float getHumidityThreshold()
  + void setHumidityThreshold(float)
  + float getTemperatureThreshold()
  + void setTemperatureThreshold(float)
  + float getUvaThreshold()
  + void setUvaThreshold(float)
  + float getUvbThreshold()
  + void setUvbThreshold(float)
  + void elevateThreshold(float)
  + float uvIndexAproxThreshold()
  + boolean hasExceedTemperatureThreshold(float)
  + float getSuggestedTemperature()
  + String toString()
}

class WoodCollectible {
  + WoodCollectible(String name)
  + void setUvbThreshold(float)
  + void setHumidityThreshold(float)
  + String toString()
}

class PvcFigureCollectible {
  + PvcFigureCollectible(String name)
  + void setUvbThreshold(float)
  + void setUvaThreshold(float)
  + void setTemperatureThreshold(float)
  + String toString()
}

class OilPaintingCollectible {
  + OilPaintingCollectible(String name)
  + void setUvbThreshold(float)
  + void setUvaThreshold(float)
  + String toString()
}

class Showcase {
  - ArrayList<PhysicalCollectible> storedCollectibles
  # int capacity
  + Showcase()
  + void addCollectible(PhysicalCollectible)
  + Optional<PhysicalCollectible> searchCollectible(String)
  + void showCollectibles()
}

' Relaciones
Collectible <|-- PhysicalCollectible : hereda de
PhysicalCollectible <|-- WoodCollectible : hereda de
PhysicalCollectible <|-- PvcFigureCollectible : hereda de
PhysicalCollectible <|-- OilPaintingCollectible : hereda de
Showcase o-- PhysicalCollectible : contiene

@enduml
```

## Justificación

Es este caso, la forma de manejar el material y tipo de coleccionable era muy limitante, creando la clase `Collectible`
que tuviera la estructura básica y luego la clase hija `PhysicalCollectible`, se abre la puerta a guardar coleccionables
no tangibles (e.g. Un NFT of arte digital). Una vez teniendo la clase `PhysicalCollectible`, tenía sentido crear clases
hijas que tengan restricciones y valores predeterminados lógicos (en este caso no tienen sentido del todo, pero es un
ejemplo), pero sin necesidad de agregar restricciones repetidas o innecesarias.
