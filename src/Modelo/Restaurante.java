package Modelo;

import java.util.*;
import java.time.LocalDateTime;


public class Restaurante {
	private String nombre;
	private String direccion;
	private GestorPedidos pedidos;                    // pedidos esperando repartidor, puede ser cola, pila o lista
	private Queue<Repartidor> repartidores;           // solo los repartidores libres, en orden de llegada
	private ArrayList<Cliente> clientes;
	private ArrayList<Pago> historialPagos;
	private ArrayList<Pedido> historialPedidos;       // todos los pedidos, para mostrarlos en la tabla
	private ArrayList<Repartidor> todosRepartidores;  // todos los repartidores, libres u ocupados

	// si no dicen la estructura se usa una cola, this llama al otro constructor
	public Restaurante(String nombre, String direccion) {
		this(nombre, direccion, new GestorCola());
	}
	// recibe la estructura de pedidos, asi la simulacion puede probar cola, pila y lista
	public Restaurante(String nombre, String direccion, GestorPedidos pedidos) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.pedidos = pedidos;
		this.repartidores = new LinkedList<>();
		this.clientes = new ArrayList<>();
		this.historialPagos = new ArrayList<>();
		this.historialPedidos = new ArrayList<>();             
		this.todosRepartidores = new ArrayList<>();            
	}
	
	public Pago addPago(double valor, Tipo tipo) {
		Pago p = new Pago(valor, tipo);
		historialPagos.add(p);
		return p;
	}
	
	public Cliente addCliente(String nombre, String tipoDocumento, String documento, String telefono, String direccion) {
		Cliente c = new Cliente(nombre, tipoDocumento, documento, telefono, direccion);
		clientes.add(c);
		return c;
	}

	public void addRepartidor(String nombre, String tipoDocumento, String documento, String vehiculo) {
		Repartidor r = new Repartidor(nombre, tipoDocumento, documento, vehiculo);
		repartidores.offer(r); // entra libre a la cola de disponibles
		todosRepartidores.add(r); // y a la lista completa para la tabla
	}

	// un cliente no puede pedir si tiene un pedido que no se ha entregado ni cancelado
	public void addPedido(String id, LocalDateTime fecha, double precio, String direccion, Pago pago, Cliente cliente) throws EPedidoEnCurso{
		Pedido actual = cliente.getPedido();
		if(actual != null && actual.getEstado() != Estado.ENTREGADO && actual.getEstado() != Estado.CANCELADO) {
			throw new EPedidoEnCurso();
		}
		Pedido p = new Pedido(id, fecha, precio, direccion, pago, cliente);
		cliente.setPedido(p); // el cliente queda con este como su pedido actual
		pedidos.agregar(p); // entra a la estructura a esperar repartidor
		historialPedidos.add(p); // y queda guardado en el historial
	}
	
	public void asignarRepartidor() {
		// los pedidos cancelados se botan sin asignarles repartidor
		while(!pedidos.estaVacio() && pedidos.consultar().getEstado() == Estado.CANCELADO) {
			pedidos.sacar();
		}
		// si no hay pedidos o no hay repartidores libres no se hace nada
		if(pedidos.estaVacio() || repartidores.isEmpty()) {
			return;
		}
		// se empareja el siguiente pedido con el siguiente repartidor libre
		Pedido p = pedidos.sacar();
		Repartidor r = repartidores.poll();
		
		r.recibirPedido(p);
		p.setEstado(Estado.ENVIADO);
	}

	// el repartidor vuelve a la cola de libres solo si ya no lleva pedidos
	public void liberarRepartidor(Repartidor r) {
		if(!r.estaLibre()) {
			return;
		}
		repartidores.offer(r);
	}
	
	public void actualizarPedido(Pedido p, Estado e) throws ESinPedido{
		Repartidor r = p.getRepartidor();
		// entregado con repartidor, el repartidor lo entrega y queda libre
		if(r != null && e == Estado.ENTREGADO) {
			if(!r.entregarPedido()) {
				throw new ESinPedido("Este repartidor no tiene ningun pedido asignado");
			}	
			liberarRepartidor(r);
		// cancelado con repartidor, el repartidor lo cancela y queda libre
		} else if(r!=null && e == Estado.CANCELADO) {
			if(!r.cancelarPedido()) {
				throw new ESinPedido("Este repartidor no tiene ningun pedido asignado");
			}
			liberarRepartidor(r);
		}else { // cualquier otro cambio, pero sin repartidor no se puede pasar a enviado ni entregado
			if(r==null && (e == Estado.ENVIADO || e == Estado.ENTREGADO)) {
				throw new ESinPedido("El pedido no tiene repartidor asignado");
			}
			p.setEstado(e);
		}
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public GestorPedidos getPedidos() {
		return pedidos;
	}

	public Queue<Repartidor> getRepartidores() {
		return repartidores;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public ArrayList<Pago> getHistorialPagos() {
		return historialPagos;
	}
	public ArrayList<Pedido> getHistorialPedidos() {
	    return historialPedidos;
	}

	public ArrayList<Repartidor> getTodosRepartidores() {
	    return todosRepartidores;
	}

	@Override
	public String toString() {
		return "Restaurante [nombre=" + nombre + ", direccion=" + direccion + ", pedidos=" + pedidos + ", repartidores="
				+ repartidores + ", clientes=" + clientes + ", historialPagos=" + historialPagos + "]";
	}	
}