package preco;

public class FreteExpresso implements EstrategiaFrete {

    @Override
    public double calcular(double subtotal) {
        return 30.00;
    }

}
