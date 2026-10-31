import Modelo.*;

public class Main {

    public static void main(String[] args) {
        try {
        	Simulacion cola = new Simulacion(new GestorCola());
        	cola.simular();
        	cola.medirTiempo(new GestorCola(), 100000);

        	Simulacion pila = new Simulacion(new GestorPila());
        	pila.simular();
        	pila.medirTiempo(new GestorPila(), 100000);

        	Simulacion lista = new Simulacion(new GestorLista());
        	lista.simular();
        	lista.medirTiempo(new GestorLista(), 100000);

        	System.out.println(cola);
        	System.out.println(pila);
        	System.out.println(lista);
        	} catch (EPedidoEnCurso | ESinPedido e) {
        		System.out.println("Error en la simulación: " + e.getMessage());
        		}
        }
}