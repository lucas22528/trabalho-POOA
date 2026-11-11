package pagamento;

public class CriadorPix extends CriadorPagamento {

	@Override
	public Pagamento criarPagamento() {
		
		return new PagamentoPix();
	}

}
