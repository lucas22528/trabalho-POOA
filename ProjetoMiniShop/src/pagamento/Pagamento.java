package pagamento;

public interface Pagamento {

	String getNome();

	void processar(double valor);
}
