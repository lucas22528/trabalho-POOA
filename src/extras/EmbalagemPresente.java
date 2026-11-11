package extras;
import modelo.ItemVenda;

public class EmbalagemPresente extends ItemDecorator {

    public EmbalagemPresente(ItemVenda item) {
        super(item);
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + embalagem para presente";
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 8.00;
        
    }

}
