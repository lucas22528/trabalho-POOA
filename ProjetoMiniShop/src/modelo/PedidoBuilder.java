package modelo;

import java.util.ArrayList;
import java.util.List;

import pagamento.Pagamento;

public class PedidoBuilder {

	private int id;
	private Cliente cliente;
	private String endereco;
	private List<ItemVenda> itens = new ArrayList<>();
	private Pagamento pagamento;

	public PedidoBuilder comId(int id) {
		this.id = id;
		return this;
	}

	public PedidoBuilder cliente(Cliente cliente) {
		this.cliente = cliente;
		return this;
	}

	public PedidoBuilder endereco(String endereco) {
		this.endereco = endereco;
		return this;
	}

	public PedidoBuilder itens(List<ItemVenda> itens) {
		this.itens.addAll(itens);
		return this;
	}

	public PedidoBuilder pagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
		return this;
	}

	public Pedido construir() {
		if (cliente == null) {
			throw new IllegalStateException("Pedido sem cliente");
		}
		if (endereco == null || endereco.trim().isEmpty()) {
			throw new IllegalStateException("Pedido sem endereço");
		}
		if (itens.isEmpty()) {
			throw new IllegalStateException("Pedido sem itens");
		}
		if (pagamento == null) {
			throw new IllegalStateException("Pedido sem forma de pagamento");
		}
		return new Pedido(this);
	}

	int getId() {
		return id;
	}

	Cliente getCliente() {
		return cliente;
	}

	String getEndereco() {
		return endereco;
	}

	List<ItemVenda> getItens() {
		return itens;
	}

	Pagamento getPagamento() {
		return pagamento;
	}
}
