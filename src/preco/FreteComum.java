package preco;

public class FreteComum implements EstrategiaFrete {

    @Override
    public double calcular(double subtotal) {
        if (subtotal >= 200.00) {
            return 0;
        }
        return 15.00;
    }

}
