package Modelo;


public interface GestorPedidos {

	void agregar(Pedido p);   // mete un pedido en la estructura
	Pedido sacar();           // saca el siguiente pedido, o null si esta vacia
	Pedido consultar();       // mira el siguiente sin sacarlo
	boolean estaVacio();
	String getNombre();       // nombre de la estructura para mostrarlo en la simulacion
}