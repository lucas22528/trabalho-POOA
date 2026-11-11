package extras;
import modelo.ItemVenda;

public class SeguroTransporte extends ItemDecorator {

    public SeguroTransporte(ItemVenda item) {
        super(item);
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + seguro de transporte";
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 5.00;
    }

}
