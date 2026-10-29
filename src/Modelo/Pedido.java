package Modelo;

import java.time.LocalDateTime;

public class Pedido {
	private String id;
	private LocalDateTime fecha;
	private Estado estado;
	private double precio;
	private String direccion;
	private Pago pago;
	private Cliente cliente;
	private Repartidor repartidor;
	
	public Pedido(String id, LocalDateTime fecha, double precio, String direccion, Pago pago, Cliente cliente) {
		this.id = id;
		this.fecha = fecha;
		this.estado = Estado.PENDIENTE;
		this.precio = precio;
		this.direccion = direccion;
		this.pago = pago;
		this.cliente = cliente;
		
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Repartidor getRepartidor() {
		return repartidor;
	}

	public void setRepartidor(Repartidor repartidor) {
		this.repartidor = repartidor;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public double getPrecio() {
		return precio;
	}

	public String getDireccion() {
		return direccion;
	}

	public Pago getPago() {
		return pago;
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	@Override
	public String toString() {
	    String rep = (repartidor == null) ? "sin asignar" : repartidor.getNombre();
	    
	    return "Pedido [id=" + id + ", fecha=" + fecha + ", estado=" + estado + ", precio=" + precio
	            + ", direccion=" + direccion + ", pago=" + pago
	            + ", cliente=" + cliente.getNombre() + ", repartidor=" + rep + "]";
	}
}