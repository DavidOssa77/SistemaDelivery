package Modelo;

public interface GestorPedidos {
	
	void agregar(Pedido p);
	Pedido sacar();
	Pedido consultar();
	boolean estaVacio();
	String getNombre();
}