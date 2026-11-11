package extras;

import modelo.ItemVenda;

	public class GarantiaEstendida extends ItemDecorator {

	    public GarantiaEstendida(ItemVenda item) {
	        super(item);
	    }

	    @Override
	    public String getDescricao() {
	        return item.getDescricao() + " + garantia estendida";
	    }

	    @Override
	    public double getPreco() {
	        return item.getPreco() * 1.12; // acrescenta 12%
	    }
	}
