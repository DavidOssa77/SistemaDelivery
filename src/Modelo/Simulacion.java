package Modelo;
import java.time.LocalDateTime;
import java.util.*;

public class Simulacion {

	private static final int TURNOS = 200;
	
	private GestorPedidos gestor;
	private double esperaPromedio;
	private int esperaMaxima;
	private int fueraDeOrden;
	private long tiempoMs;
	
	public Simulacion(GestorPedidos gestor) {
		this.gestor = gestor;
	}
	
	public void simular() throws EPedidoEnCurso, ESinPedido{
		Random rnd = new Random(67);
		Restaurante r = new Restaurante("Simulacion", "Calle 10", gestor);
		r.addRepartidor("Repartidor", "CC", "1", "Moto");
		
		ArrayList<Pedido> llegados = new ArrayList<>();
		ArrayList<Integer> turnoLlegada = new ArrayList<>();
		int totalEspera = 0;
		int ultimoAtendido = 0;
		esperaMaxima = 0;
		fueraDeOrden = 0;
		
		for(int turno = 0; turno<TURNOS; turno++) {
			int llegan = rnd.nextInt(3);
			for(int k = 0; k<llegan; k++) {
				int n = turnoLlegada.size() + 1;
				Cliente c = r.addCliente("Cliente " + n, "CC", "C", "300", "Calle 13");
				Pago pago = r.addPago(10000, Tipo.EFECTIVO);
				r.addPedido("P", LocalDateTime.now(), 10000, "Calle" + n, pago, c);
				llegados.add(c.getPedido());
				turnoLlegada.add(turno);
			}
			
			if(!r.getPedidos().estaVacio()) {
				Pedido p = r.getPedidos().consultar();
				r.asignarRepartidor();
				r.actualizarPedido(p, Estado.ENTREGADO);
				int posicion = llegados.indexOf(p);
				int espera = turno - turnoLlegada.get(posicion);
				totalEspera += espera;
				esperaMaxima = Math.max(esperaMaxima, espera);
				if(posicion < ultimoAtendido) {
					fueraDeOrden++;
				}
				ultimoAtendido = Math.max(ultimoAtendido, posicion);
			}
		}
		for(int i = 0; i<llegados.size(); i++) {
			if(llegados.get(i).getEstado() == Estado.PENDIENTE) {
				int espera = TURNOS - turnoLlegada.get(i);
				totalEspera += espera;
				esperaMaxima = Math.max(esperaMaxima, espera);
			}
		}
		
		esperaPromedio = (double) totalEspera/llegados.size();
	}
	
	public void medirTiempo(GestorPedidos gestor, int cantidad) {
		Cliente c = new Cliente("Prueba", "CC", "0", "0", "Calle 0");
		long inicio = System.nanoTime();
		for(int i = 0; i<cantidad; i++) {
			gestor.agregar(new Pedido("p" + i, null, 10000, "", null, c));
		}
		while (!gestor.estaVacio()) {
			gestor.sacar();
		}
		tiempoMs = (System.nanoTime() - inicio)/1_000_000;
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