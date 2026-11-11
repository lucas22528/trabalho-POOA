package modelo;

public class Endereço {

	 private String rua;
	    private String cidade;
	    private String cep;

	    public Endereço (String rua, String cidade, String cep) {
	        this.rua = rua;
	        this.cidade = cidade;
	        this.cep = cep;
	    }

	    @Override
	    public String toString() {
	        return rua + ", " + cidade + " - CEP " + cep;
	    }
}
