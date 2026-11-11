package modelo;

public class Produto {
	
	  private final int id;
	    private String nome;
	    private double preco;
	    private int estoque;

	    public Produto(int id, String nome, double preco, int estoque) {
	        this.id = id;
	        this.nome = nome;
	        this.preco = preco;
	        this.estoque = estoque;
	    }

	    public int getId() {
	        return id;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public double getPreco() {
	        return preco;
	    }

	    public int getEstoque() {
	        return estoque;
	    }

	    public void baixarEstoque() {
	        estoque = estoque - 1;
	    }
}
