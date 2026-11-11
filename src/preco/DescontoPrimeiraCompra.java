package preco;
import modelo.Cliente;

public class DescontoPrimeiraCompra implements EstrategiaDesconto {

	 @Override
	    public double calcular(double subtotal, Cliente cliente) {
	        if (cliente.isPrimeiraCompra()) {
	            return subtotal * 0.10;
	        }
	        return 0;
	    }
}
