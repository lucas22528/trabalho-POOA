package pagamento;

public interface  Pagamento {
	
	 String getNome();
	 
	 boolean processar(double valor);
}
