package notificacao;

import modelo.Pedido;
import modelo.PedidoObserver;

public class NotificadorEmail implements PedidoObserver {

	@Override
	public void atualizar(Pedido pedido) {
		System.out.println("Email Para " + pedido.getCliente().getEmail() + ": seu pedido id:" + pedido.getId()
				+ " está " + pedido.getStatus());
	}
}
