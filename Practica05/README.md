# Practica 5

## Template Method

La clase abstracta `PhysicalCollectible` utiliza patron "Template Method" en el metodo `calculateMaintenanceCost`,
basicamente define la forma qen la que se debe calcular el costo de mantenimiento usando metodos abstractos para ello (`getRiskMultiplier` y `getBaseDepreciationRate`). Este tipo de patron evita la duplicacion de codigo para formulas
fijas y si alguna vez cambia la formula, solo hay que modificarla en un lugar.
