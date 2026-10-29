
public class Repartidor {
	private String nombre;
	private String tipoDocumento;
	private String documento;
	private String vehiculo;
	// private Queue<Pedido> pedidos;
	
	public Repartidor(String nombre, String tipoDocumento, String documento, String vehiculo) {
		this.nombre = nombre;
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
		this.vehiculo = vehiculo;
		//this.pedidos = new LinkedList<Pedido>();
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
	
	/*
	  public void recibirPedido(Pedido pedido){
	 pedidos.add(pedido);
	 }
	 */
	
	/*
	 public void entregarPedido() throws ESinPedido{
	 if(pedidos.isEmpty())
	 throw new ESinPedido("Este repartidor no tiene ningun pedido asignado");
	 Pedido a = pedidos.poll();
	 System.out,println("Pedido "+a.getId()+" entregado con exito")
	 }
	 */
	
	/*public void cancelarPedido() throws ESinPedido{
	 if(pedidos.isEmpty())
	 throw new ESinPedido("Este repartidor no tiene ningun pedido asignado");
	 Pedido a = pedidos.poll();
	 System.out,println("Pedido "+a.getId()+" cancelado con exito")
	 }
	 */
	
	/*
	 public boolean estaLibre(){
	 if(pedidos.isEmpty())
	 return true;
	 else return false;
	 }
	 */
	
	
	
	
	
}
