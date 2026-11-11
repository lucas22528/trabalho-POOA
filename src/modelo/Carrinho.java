package modelo;
import java.util.ArrayList;

public class Carrinho {

	private ArrayList<ItemVenda> itens = new ArrayList<>();
	
	
	 public void adicionar(ItemVenda item) {
	        itens.add(item);
	    }
	
	 public void remover(int posicao) {
	        if (posicao < 0 || posicao >= itens.size()) {
	           System.out.println("Item não existe no carrinho.");
	        }
	        itens.remove(posicao);
	    }
	 
	 public void esvaziar() {
	        itens.clear();
	    }

	    public boolean isVazio() {
	        return itens.isEmpty();
	    }
	    
	    public ArrayList<ItemVenda> getItens() {
	        return new ArrayList<>(itens); 
	        
	    }
	    
	    public double getSubtotal() {
	        double soma = 0;
	        for (ItemVenda item : itens) {
	            soma = soma + item.getPreco();
	        }
	        
	        return soma;
	    }
}
