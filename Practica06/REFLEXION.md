# Reflexion

1. ¿Cuándo preferirías una clase abstracta sobre una interfaz? ¿Y al revés?

Prefieres una interfaz cuando quieres definir un comportamiento para clases completamente distintas, por ejemplo, `Valuable` es implementado por `OilPaintingCollectible` pero podria ser implementado por `StockShare` y seguiria tenenindo sentido.

Prefieres una clase abstracta cuando quieres compartir un comportamiendo definido entre clases muy realcionadas, en general si se puede definir una relacion "es un" (Por ejemplo, una pintura de oleo `OilPaintingCollectible` es un objeto coleccionable `PhysicalCollectible`).

2. ¿Una clase puede implementar varias interfaces? ¿Por qué Java permite eso pero no herencia múltiple de clases?

Si. Una clase no puede heredar multiples clases para evitar el "problema del diamante", donde una clase hereda de dos clases que heredan de una misma clase, generando ambiguedad sobre que implementacion usar.

3. Si agregas un método nuevo a una de tus interfaces, ¿qué clases se ven afectadas? ¿Cómo lo resolverías con un método default?

Todas las clases concretas que implementen la interfaz, por lo que no compilarian por que no tienen una implementacion. El metodo default permite definir una implementacion por defecto para el nuevo metodo, por lo que el compilador no fallaria.
