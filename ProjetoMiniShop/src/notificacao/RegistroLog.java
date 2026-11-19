package notificacao;

import modelo.Pedido;
import modelo.PedidoObserver;

public class RegistroLog implements PedidoObserver {

	@Override
	public void atualizar(Pedido pedido) {
		System.out.println("log pedido id:" + pedido.getId() + " - " + pedido.getStatus());
	}
}
