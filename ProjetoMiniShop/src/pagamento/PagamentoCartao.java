package pagamento;

public class PagamentoCartao implements Pagamento {

	@Override
	public String getNome() {
		return "Cartão de crédito";
	}

	@Override
	public void processar(double valor) {
		System.out.printf("Pagamento Cartão aprovado: R$ %.2f%n", valor);
	}
}
