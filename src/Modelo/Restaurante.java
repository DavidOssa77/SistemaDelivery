package Modelo;

import java.util.*;
import java.time.LocalDateTime;


public class Restaurante {
	private String nombre;
	private String direccion;
	private Queue<Pedido> pedidos;
	private Queue<Repartidor> repartidores;
	private ArrayList<Cliente> clientes;
	private ArrayList<Pago> historialPagos;
	
	public Restaurante(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.pedidos = new LinkedList<>();
		this.repartidores = new LinkedList<>();
		this.clientes = new ArrayList<>();
		this.historialPagos = new ArrayList<>();
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
		repartidores.offer(r);
	}
	
	public void addPedido(String id, LocalDateTime fecha, double precio, String direccion, Pago pago, Cliente cliente) throws EPedidoEnCurso{
		Pedido actual = cliente.getPedido();
		if(actual != null && actual.getEstado() != Estado.ENTREGADO && actual.getEstado() != Estado.CANCELADO) {
			throw new EPedidoEnCurso();
		}
		Pedido p = new Pedido(id, fecha, precio, direccion, pago, cliente);
		cliente.setPedido(p);
		pedidos.offer(p);
	}
	
	public void asignarRepartidor() {
		while(!pedidos.isEmpty() && pedidos.peek().getEstado() == Estado.CANCELADO) {
			pedidos.poll();
		}
		if(pedidos.isEmpty() || repartidores.isEmpty()) {
			return;
		}
		Pedido p = pedidos.poll();
		Repartidor r = repartidores.poll();
		
		r.recibirPedido(p);
		p.setEstado(Estado.ENVIADO);
	}
	
	public void liberarRepartidor(Repartidor r) {
		if(!r.estaLibre()) {
			return;
		}
		repartidores.offer(r);
	}
	
	public void actualizarPedido(Pedido p, Estado e) throws ESinPedido{
		Repartidor r = p.getRepartidor();
		if(r != null && e == Estado.ENTREGADO) {
			if(!r.entregarPedido()) {
				throw new ESinPedido("Este repartidor no tiene ningun pedido asignado");
			}	
			liberarRepartidor(r);
		} else if(r!=null && e == Estado.CANCELADO) {
			if(!r.cancelarPedido()) {
				throw new ESinPedido("Este repartidor no tiene ningun pedido asignado");
			}
			liberarRepartidor(r);
		}else {
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

	public Queue<Pedido> getPedidos() {
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

	@Override
	public String toString() {
		return "Restaurante [nombre=" + nombre + ", direccion=" + direccion + ", pedidos=" + pedidos + ", repartidores="
				+ repartidores + ", clientes=" + clientes + ", historialPagos=" + historialPagos + "]";
	}
	
	
}
