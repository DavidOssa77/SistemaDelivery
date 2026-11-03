package Modelo;
import java.util.*;

public class GestorPila implements GestorPedidos {
	private Stack<Pedido> pila = new Stack<>();
	
	@Override
	public void agregar(Pedido p) {
		pila.push(p);
	}

	@Override
	public Pedido sacar() {
		// pop lanza error si la pila esta vacia, por eso se revisa antes y se devuelve null igual que la cola
		if(pila.isEmpty()) {
			return null;
		}
		return pila.pop();
	}

	@Override
	public Pedido consultar() {
		if(pila.isEmpty()) {
			return null;
		}
		return pila.peek();
	}

	@Override
	public boolean estaVacio() {
		return pila.isEmpty();
	}

	@Override
	public String getNombre() {
		return "Pila";
	}
}