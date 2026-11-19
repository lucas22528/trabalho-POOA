package servico;

import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;
import modelo.ItemVenda;
import modelo.Pedido;
import modelo.PedidoBuilder;
import modelo.PedidoObserver;
import pagamento.CriadorPagamento;
import pagamento.Pagamento;
import preco.CalculadoraPreco;
import preco.EstrategiaFrete;

public class CheckoutFacade {

	private List<Pedido> pedidos = new ArrayList<>();
	private List<PedidoObserver> observadores;

	public CheckoutFacade(List<PedidoObserver> observadores) {
		this.observadores = observadores;
	}

	public Pedido finalizarCompra(Cliente cliente, String endereco, List<ItemVenda> itens, EstrategiaFrete frete,
			CriadorPagamento criador) {

		if (itens.isEmpty()) {
			throw new IllegalArgumentException("O carrinho está vazio");
		}

		Pagamento pagamento = criador.criarPagamento();

		Pedido pedido = new PedidoBuilder().comId(pedidos.size() + 1).cliente(cliente).endereco(endereco).itens(itens)
				.pagamento(pagamento).construir();

		for (PedidoObserver observador : observadores) {
			pedido.adicionarObservador(observador);
		}

		new CalculadoraPreco(frete).aplicar(pedido);

		pagamento.processar(pedido.getTotal());

		pedido.avancarStatus();
		pedidos.add(pedido);
		return pedido;
	}

	public Pedido avancarStatus(int idPedido) {
		for (Pedido pedido : pedidos) {
			if (pedido.getId() == idPedido) {
				pedido.avancarStatus();
				return pedido;
			}
		}
		throw new IllegalArgumentException("Pedido id:" + idPedido + " não encontrado.");
	}

	public List<Pedido> listarPedidos() {
		return new ArrayList<>(pedidos);
	}
}
