package preco;
import modelo.Cliente;

public class CupomDesconto implements EstrategiaDesconto {

    private double percentual;

    public CupomDesconto(String codigo) {
        String cupom = codigo.trim().toUpperCase();
        if (cupom.equals("PROMO10")) {
            percentual = 10;
        } else if (cupom.equals("VIP20")) {
            percentual = 20;
        } else {
            System.out.println("Cupom inválido: " + codigo);
        }
    }

    @Override
    public double calcular(double subtotal, Cliente cliente) {
        return subtotal * percentual / 100;
    }
}
