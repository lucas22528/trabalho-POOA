package pagamento;

public class CriadorCartao extends CriadorPagamento {

	@Override
	public Pagamento criarPagamento() {
		
		return new PagamentoCartao();
	}

}
