package notificacao;
import modelo.ItemVenda;
import modelo.Pedido;
import modelo.PedidoObserver;
import modelo.StatusPedido;

public class ControleEstoque implements PedidoObserver {

	@Override
    public void atualizar(Pedido pedido) {
		
        if (pedido.getStatus() == StatusPedido.PAGO) {
            for (ItemVenda item : pedido.getItens()) {
                item.getProduto().baixarEstoque();
            }
            System.out.println("Estoque atualizado para o pedido de id:" + pedido.getId());
        }
    }


}
