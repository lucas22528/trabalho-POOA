package pagamento;

public class PagamentoPix implements Pagamento {
	
	@Override
	
	public String getNome() {
		return "Pgamento Pix";
	}

	@Override
	
	public boolean processar(double valor) {
		System.out.println("Processando pagamento em Pix no valor de: " + valor);
		return true;
	}
}
