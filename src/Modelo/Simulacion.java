package Modelo;
import java.time.LocalDateTime;
import java.util.*;


//Pone a prueba una estructura de datos (cola, pila o lista) para demostrar cual es la mejor para atender los pedidos de un delivery
//Se crea un objeto Simulacion por cada estructura y cada uno guarda sus propios resultados

public class Simulacion {

	// Duracion de la simulacion, un turno es una unidad de tiempo abstracta
	private static final int TURNOS = 200;
	
	//estructura elegida para probar
	private GestorPedidos gestor;
	
	private double esperaPromedio; //espera media de todos los pedidos en turnos
	private int esperaMaxima; //lo que más espera un pedido, mide justicia y lógica
	private int fueraDeOrden; //pedidos que llegaron después y salieron antes que pedidos ya existentes
	private long tiempoMs; //tiempo en meter y sacar n pedidos en la estructura
	
	public Simulacion(GestorPedidos gestor) {
		this.gestor = gestor;
	}
	
	//Prueba de justicia, en cada turno llegan de 0 a 2 pedidos y sale 1. Como en promedio llega uno por turno y sale uno, 
	//a veces se forma fila, y ahi se nota a quien atiende primero cada estructura
	public void simular() throws EPedidoEnCurso, ESinPedido{
		Random rnd = new Random(67);
		
		// Restaurante real de prueba que guarda sus pedidos en la estructura que se esta evaluando
		Restaurante r = new Restaurante("Simulacion", "Calle 10", gestor);
		
		// Un solo repartidor: sale como maximo un pedido por turno
		r.addRepartidor("Repartidor", "CC", "1", "Moto");
		
		// Listas paralelas: la posicion i de las dos corresponde al mismo pedido.
		// La posicion tambien es el puesto de llegada (0 = el primero que llego)
		ArrayList<Pedido> llegados = new ArrayList<>(); //pedidos en orden de llegada
		ArrayList<Integer> turnoLlegada = new ArrayList<>(); //turno en que llega cada pedido
		
		int totalEspera = 0; //suma de todas las esperas
		int ultimoAtendido = 0; //puesto de llegada más alto entre los que ya salieron
		esperaMaxima = 0;
		fueraDeOrden = 0;
		
		//llegan los pedidos y se empiezan a manejar 
		for(int turno = 0; turno<TURNOS; turno++) {
			int llegan = rnd.nextInt(3); //se generan 0, 1 o 2 pedidos al azar para el turno dado
			for(int k = 0; k<llegan; k++) {
				int n = turnoLlegada.size() + 1; //numero de pedido, sirve para nombrar y numerar al cliente
				Cliente c = r.addCliente("Cliente " + n, "CC", "C", "300", "Calle 13");
				Pago pago = r.addPago(10000, Tipo.EFECTIVO);
				r.addPedido("P", LocalDateTime.now(), 10000, "Calle" + n, pago, c); //mete el pedido en la estructura
				llegados.add(c.getPedido());
				turnoLlegada.add(turno);
			}
			
			//sale un pedido 
			if(!r.getPedidos().estaVacio()) {
				Pedido p = r.getPedidos().consultar(); //consultar() dice cual pedido sale, acá depende de cada estructura
				//cola y lista = FIFO, pila = LIFO
				r.asignarRepartidor(); //saca el pedido y se lo da al repartidor
				r.actualizarPedido(p, Estado.ENTREGADO); //se entrega el pedido y se libera el repartidor
				
				//acá empieza el cálculo de las métricas
				int posicion = llegados.indexOf(p); //en que puesto llegó el pedido
				int espera = turno - turnoLlegada.get(posicion); //se calculan los turnos que espero
				totalEspera += espera;
				esperaMaxima = Math.max(esperaMaxima, espera);
				
				
				if(posicion < ultimoAtendido) {//si un pedido que llego antes que otro ya salió, alguien se colo (caso pila)
					fueraDeOrden++;
				}
				ultimoAtendido = Math.max(ultimoAtendido, posicion);
			}
		}
		
		//pedidos que nunca salieron
		// Se recorre 'llegados' por indice porque la posicion i es la misma en 'turnoLlegada'
		for(int i = 0; i<llegados.size(); i++) {
			if(llegados.get(i).getEstado() == Estado.PENDIENTE) {
				int espera = TURNOS - turnoLlegada.get(i); //turnos que llevan esperando hasta el final
				totalEspera += espera; //entran al promedio
				esperaMaxima = Math.max(esperaMaxima, espera); //pueden ser la espera máxima
			}
		}
		
		//el promedio sale igual en todas las estructuras porque se atiende un pedido por turno
		//lo que cambia es quien espera (en el caso de la pila)
		esperaPromedio = (double) totalEspera/llegados.size();
	}
	
	//prueba de eficiencia pura, no usa restaurante, con usar el gestor basta para poder ver que entra y sale en la estructura
	public void medirTiempo(GestorPedidos gestor, int cantidad) {
		Cliente c = new Cliente("Prueba", "CC", "0", "0", "Calle 0");
		long inicio = System.nanoTime(); //se mide en nanosegundos para tener precision computacional, luego se divide entre 1 millón
		for(int i = 0; i<cantidad; i++) {
			gestor.agregar(new Pedido("p" + i, null, 10000, "", null, c)); //no importa la fecha y el pago porque solo importa quien entra y sale
		}
		while (!gestor.estaVacio()) {
			gestor.sacar(); //se saca mientras no este vacío
		}
		tiempoMs = (System.nanoTime() - inicio)/1_000_000; //tiempo que tardo en completarse el proceso
	}

	public String getEstructura() {
		return gestor.getNombre();
	}

	public double getEsperaPromedio() {
		return esperaPromedio;
	}

	public int getEsperaMaxima() {
		return esperaMaxima;
	}

	public int getFueraDeOrden() {
		return fueraDeOrden;
	}

	public long getTiempoMs() {
		return tiempoMs;
	}
	
	@Override 
	public String toString() {
		return  getEstructura() + " [promedio= " + String.format("%.2f", getEsperaPromedio()) + " esperaMaxima= " + getEsperaMaxima() + " fueraDeOrden=" + getFueraDeOrden() +
				" tiempo= " + getTiempoMs() + " ms]";
	}
}