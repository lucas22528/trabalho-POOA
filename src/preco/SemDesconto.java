package preco;
import modelo.Cliente;

public class SemDesconto implements EstrategiaDesconto {

    @Override
    public double calcular(double subtotal, Cliente cliente) {
        return 0;
    }

}
