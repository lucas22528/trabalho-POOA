package notificacao;
import modelo.Pedido;
import modelo.PedidoObserver;

public class RegistroLog implements PedidoObserver {

	@Override
	public void atualizar(Pedido pedido) {
		System.out.println("Pedido id:" + pedido.getId() + " mudou para o status " + pedido.getStatus());
	}

}
