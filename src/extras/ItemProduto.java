package extras;
import modelo.ItemVenda;
import modelo.Produto;

public class ItemProduto implements ItemVenda{
	
	    private Produto produto;

	    public ItemProduto(Produto produto) {
	        this.produto = produto;
	    }

	    @Override
	    public String getDescricao() {
	        return produto.getNome();
	    }

	    @Override
	    public double getPreco() {
	        return produto.getPreco();
	    }

	    @Override
	    public Produto getProduto() {
	        return produto;
	    }
}
