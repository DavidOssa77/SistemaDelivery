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