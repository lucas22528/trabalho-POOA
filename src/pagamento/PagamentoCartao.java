package pagamento;

public class PagamentoCartao implements Pagamento {

	
	@Override
	
	public String getNome() {
		return "Cartão de Crédito";
	}
	
	
	@Override
	public boolean processar(double valor) {
		System.out.println("Processando pagamento de R$" + valor + " com cartão de crédito.");
		return true;
	}
}
