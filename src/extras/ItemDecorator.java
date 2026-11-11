package extras;
import modelo.ItemVenda;
import modelo.Produto;

public abstract class ItemDecorator implements ItemVenda {

    protected ItemVenda item;

    public ItemDecorator(ItemVenda item) {
        this.item = item;
    }

    @Override
    public Produto getProduto() {
        return item.getProduto();
    }

}
