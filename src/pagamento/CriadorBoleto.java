package pagamento;

public class CriadorBoleto extends CriadorPagamento {
	
	@Override
	public Pagamento criarPagamento() {
		return new PagamentoBoleto();
	}

}
