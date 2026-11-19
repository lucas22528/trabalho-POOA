package preco;

import modelo.Pedido;

public class CalculadoraPreco {

	private EstrategiaFrete estrategiaFrete;

	public CalculadoraPreco(EstrategiaFrete estrategiaFrete) {
		this.estrategiaFrete = estrategiaFrete;
	}

	public void aplicar(Pedido pedido) {
		double frete = estrategiaFrete.calcular(pedido.getSubtotal());
		pedido.aplicarFrete(frete);
	}
}
