package pagamento;

public class PagamentoBoleto implements Pagamento {
	
	@Override
	public String getNome() {
		return "Pagamento Boleto";
	}

	@Override
	public boolean processar(double valor) {
		
		System.out.println("Processando pgamento de R$" + valor + " com boleto.");
		return true;
	}
}
