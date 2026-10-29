import java.time.LocalDateTime;

public class Pago {
	private double valor;
	private Tipo tipo;
	private LocalDateTime fecha;
	
	public Pago (double valor, Tipo tipo) {
		this.valor = valor;
		this.tipo = tipo;
		this.fecha = LocalDateTime.now();
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Pago: valor = " + valor + ", tipo = " + tipo + ", fecha = " + fecha;
	}
	
	
	
	

}
