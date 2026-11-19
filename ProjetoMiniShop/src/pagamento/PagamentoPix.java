package pagamento;

public class PagamentoPix implements Pagamento {

	@Override
	public String getNome() {
		return "Pix";
	}

	@Override
	public void processar(double valor) {
		System.out.printf("Pagamento Pix aprovado: R$ %.2f%n", valor);
	}
}
