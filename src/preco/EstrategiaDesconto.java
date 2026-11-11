package preco;
import modelo.Cliente;

public interface EstrategiaDesconto {
	
	    double calcular(double subtotal, Cliente cliente);

}
