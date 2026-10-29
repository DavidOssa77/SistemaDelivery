package Modelo;

import java.util.*;

public class Repartidor {
	private String nombre;
	private String tipoDocumento;
	private String documento;
	private String vehiculo;
	private Queue<Pedido> pedidos;
	
	public Repartidor(String nombre, String tipoDocumento, String documento, String vehiculo) {
		this.nombre = nombre;
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
		this.vehiculo = vehiculo;
		this.pedidos = new LinkedList<Pedido>();
	}
	
	public void recibirPedido(Pedido p) {
	    pedidos.offer(p);
	    p.setRepartidor(this);
	}
	
	public boolean entregarPedido(){
		if(pedidos.isEmpty()) {
			return false;
		}
		Pedido p = pedidos.poll();
		p.setEstado(Estado.ENTREGADO);
		return true;
	}
	
	public boolean cancelarPedido(){
		if(pedidos.isEmpty()) {
			return false;
		}
		Pedido p = pedidos.poll();
		p.setEstado(Estado.CANCELADO);
		return true;
	}
	
	public boolean estaLibre() {
		return pedidos.isEmpty();
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}
	
	public Queue<Pedido> getPedidos(){
		return pedidos;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	@Override
	public String toString() {
		return "Repartidor: nombre = " + nombre + ", tipoDocumento = " + tipoDocumento + ", documento = " + documento
				+ ", vehiculo = " + vehiculo;
	}	
}