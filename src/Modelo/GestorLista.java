package Modelo;
import java.util.*;

public class GestorLista implements GestorPedidos {
	private ArrayList<Pedido> lista = new ArrayList<>();
	
	@Override
	public void agregar(Pedido p) {
		lista.add(p);
	}

	@Override
	public Pedido sacar() {
		if(lista.isEmpty()) {
			return null;
		}
		// remove(0) corre todos los demas una posicion, por eso es mas lenta que la cola
		return lista.remove(0);
	}

	@Override
	public Pedido consultar() {
		if(lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	@Override
	public boolean estaVacio() {
		return lista.isEmpty();
	}

	@Override
	public String getNombre() {
		return "Lista";
	}
}