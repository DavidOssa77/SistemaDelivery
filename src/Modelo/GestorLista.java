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