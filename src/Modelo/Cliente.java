package Modelo;


public class Cliente {
	private String nombre;
	private String tipoDocumento;
	private String documento;
	private String telefono;
	private Pedido pedido;
	private String direccion;

	public Cliente(String nombre, String tipoDocumento, String documento, String telefono, String direccion) {
		this.nombre = nombre;
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	public boolean cancelarPedido() {
		if(pedido == null) {
			return false;
		}
		if(pedido.getEstado() != Estado.PENDIENTE && pedido.getEstado() != Estado.EN_PREPARACION) {
			return false;
		}
		pedido.setEstado(Estado.CANCELADO);
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public String getTelefono() {
		return telefono;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public String getDireccion() {
		return direccion;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public String toString() {
	    String ped = (pedido == null) ? "sin pedido" : pedido.getId() + " (" + pedido.getEstado() + ")";
	    return "Cliente [nombre=" + nombre + ", tipoDocumento=" + tipoDocumento + ", documento=" + documento
	            + ", telefono=" + telefono + ", pedido=" + ped + ", direccion=" + direccion + "]";
	}	
}