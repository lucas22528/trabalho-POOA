package preco;
import modelo.Pedido;

public class CalculadoraPreco {
	
	 private EstrategiaFrete estrategiaFrete;
	    private EstrategiaDesconto estrategiaDesconto;

	    public CalculadoraPreco(EstrategiaFrete estrategiaFrete, EstrategiaDesconto estrategiaDesconto) {
	        this.estrategiaFrete = estrategiaFrete;
	        this.estrategiaDesconto = estrategiaDesconto;
	    }

	    public void aplicar(Pedido pedido) {
	        double subtotal = pedido.getSubtotal();
	        double desconto = estrategiaDesconto.calcular(subtotal, pedido.getCliente());
	        double frete = estrategiaFrete.calcular(subtotal);
	        pedido.aplicarPrecificacao(desconto, frete);
	    }
}
