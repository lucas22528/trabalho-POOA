package notificacao;
import modelo.Pedido;
import modelo.PedidoObserver;

public class NotificaçaoEmail implements PedidoObserver {

	@Override
	public void atualizar(Pedido pedido) {
		System.out.println("Enviando email para o cliente: " + pedido.getCliente().getEmail());
		System.out.println("Atualização do Pedido id:" + pedido.getId());
		System.out.println("Status atualizado: " + pedido.getStatus());
	}

}
