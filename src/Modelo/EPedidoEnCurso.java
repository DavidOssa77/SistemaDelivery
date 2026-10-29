package Modelo;

public class EPedidoEnCurso extends Exception{
	public EPedidoEnCurso() {
		super("Ya hay un pedido en curso");
	}
}