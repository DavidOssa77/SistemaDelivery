# SistemaDelivery

Proyecto de Estructuras de Datos que modela el sistema de delivery de un restaurante y demuestra, con un simulador, que la **cola** es la estructura más adecuada para atender los pedidos, comparándola con una **pila** y una **lista**.

## Idea central

En un delivery los pedidos deben atenderse **en orden de llegada**: el primero que pide es el primero que sale. Eso es exactamente lo que garantiza una cola (FIFO), y lo hace en O(1).

Para comprobarlo, el restaurante no depende de una estructura fija: los pedidos en espera se guardan detrás de la interfaz `GestorPedidos`, que tiene tres implementaciones:

| Clase | Estructura | Qué pedido sale primero |
|---|---|---|
| `GestorCola` | `Queue` (`LinkedList`) | El que llegó primero |
| `GestorPila` | `Stack` | El que llegó último |
| `GestorLista` | `ArrayList` con `remove(0)` | El que llegó primero, desplazando todos los demás |

El código del restaurante es el mismo en los tres casos; solo cambia el gestor que recibe en el constructor. Por eso cualquier diferencia en los resultados se debe a la estructura.

## Simulador y métricas

La clase `Simulacion` hace funcionar un `Restaurante` real durante **200 turnos** con un repartidor:

- En cada turno llegan entre 0 y 2 pedidos nuevos.
- En cada turno sale un pedido: se asigna al repartidor y se entrega.
- Se usa una **semilla fija** en `Random`, así las tres estructuras reciben exactamente los mismos pedidos en los mismos turnos.

Sobre esa corrida se miden cuatro métricas:

| Métrica | Qué mide | Qué revela |
|---|---|---|
| **Espera promedio** | Turnos que esperó cada pedido entre su llegada y su salida, en promedio. Los que nunca salieron cuentan hasta el final. | Cuánta espera hay en total. |
| **Espera máxima** | La mayor espera de un solo pedido. | Si algún pedido queda olvidado. Es la métrica clave de **justicia**. |
| **Fuera de orden** | Cuántas veces sale un pedido que llegó antes que otro que ya había salido. | Si se respeta el orden de llegada. |
| **Tiempo (ms)** | Tiempo en meter y sacar 100.000 pedidos solo en la estructura (`medirTiempo`). | El **costo computacional** de cada estructura. |

## Resultados

| Estructura | Espera promedio | Espera máxima | Fuera de orden | Tiempo (100.000 pedidos) |
|---|---|---|---|---|
| **Cola** | 2.91 | **8** | **0** | ~35–70 ms |
| Pila | 2.91 | 89 | 56 | ~40–55 ms |
| Lista | 2.91 | **8** | **0** | ~470–620 ms |

*Los tiempos varían un poco entre ejecuciones; las demás métricas son siempre iguales por la semilla fija.*

**Interpretación**

- **La espera promedio es igual en las tres.** Todas atienden un pedido por turno, así que la espera total es la misma; la estructura solo cambia **quién** espera. Por eso el promedio no basta y se mide también la espera máxima.
- **Pila — falla en justicia.** Cada pedido nuevo pasa adelante de los anteriores: un pedido llegó a esperar 89 turnos (frente a 8 en la cola) y 56 salieron fuera de orden.
- **Lista — falla en eficiencia.** Atiende en orden, pero sacar el primero cuesta O(n) porque desplaza todos los elementos: es cerca de 10 veces más lenta que la cola. Además, permite acceder a cualquier posición, así que el orden depende de quien la use, no de la estructura.
- **Cola — la única que es justa y eficiente a la vez.**

## Cómo ejecutar

Proyecto de Eclipse. Desde `src/`:

- **`Main.java`**: corre la simulación con las tres estructuras e imprime las métricas en consola.
- **`Vista/VentanaPrincipal.java`**: interfaz gráfica (Swing) del sistema: clientes, repartidores, pedidos y pagos.

## Estructura del proyecto

```
src/
├── Main.java
├── Modelo/
│   ├── Restaurante.java      # Lógica del sistema: pedidos, repartidores, pagos
│   ├── Pedido.java, Cliente.java, Repartidor.java, Pago.java
│   ├── Estado.java, Tipo.java                # Enums
│   ├── EPedidoEnCurso.java, ESinPedido.java  # Excepciones propias
│   ├── GestorPedidos.java                    # Interfaz común
│   ├── GestorCola.java, GestorPila.java, GestorLista.java
│   └── Simulacion.java                       # Simulador y métricas
└── Vista/
    └── VentanaPrincipal.java
```

## Supuestos de alcance

- El pago se registra al crear el pedido
- No se modela la ubicación física ni distancias
- Todos los pedidos tienen la misma prioridad, se atienden por orden de llegada
- Un cliente no puede hacer un pedido nuevo mientras tenga otro en curso
