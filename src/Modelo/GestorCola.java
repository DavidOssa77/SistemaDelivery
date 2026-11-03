package Modelo;
import java.util.*;

public class GestorCola implements GestorPedidos {
	private Queue<Pedido> cola = new LinkedList<>();

	@Override
	public void agregar(Pedido p) {
		cola.add(p);
	}

	@Override
	public Pedido sacar() {
		// poll devuelve null si la cola esta vacia, no lanza error
		return cola.poll();
	}

	@Override
	public Pedido consultar() {
		// peek devuelve null si la cola esta vacia, no lanza error
		return cola.peek();
	}

	@Override
	public boolean estaVacio() {
		return cola.isEmpty();
	}

	@Override
	public String getNombre() {
		return "Cola";
	}
}