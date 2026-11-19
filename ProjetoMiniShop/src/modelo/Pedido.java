package modelo;

import java.util.ArrayList;
import java.util.List;

import pagamento.Pagamento;

public class Pedido {

	private int id;
	private Cliente cliente;
	private String endereco;
	private List<ItemVenda> itens;
	private Pagamento pagamento;
	private StatusPedido status = StatusPedido.CRIADO;
	private double frete = 0;
	private List<PedidoObserver> observadores = new ArrayList<>();

	Pedido(PedidoBuilder builder) {
		this.id = builder.getId();
		this.cliente = builder.getCliente();
		this.endereco = builder.getEndereco();
		this.itens = new ArrayList<>(builder.getItens());
		this.pagamento = builder.getPagamento();
	}

	public void adicionarObservador(PedidoObserver observador) {
		observadores.add(observador);
	}

	private void notificarObservadores() {
		for (PedidoObserver observador : observadores) {
			observador.atualizar(this);
		}
	}

	public void avancarStatus() {
		if (status.isFinal()) {
			throw new IllegalStateException("O pedido foi entregue");
		}
		status = status.proximo();
		notificarObservadores();
	}

	public double getSubtotal() {
		double soma = 0;
		for (ItemVenda item : itens) {
			soma = soma + item.getPreco();
		}
		return soma;
	}

	public void aplicarFrete(double frete) {
		this.frete = frete;
	}

	public double getTotal() {
		return getSubtotal() + frete;
	}

	public int getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<ItemVenda> getItens() {
		return new ArrayList<>(itens);
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public String resumo() {
		String texto = "Pedido id:" + id + " - " + cliente.getNome() + " (" + status + ")\n";

		for (ItemVenda item : itens) {
			texto += item.getDescricao() + ": R$ " + String.format("%.2f", item.getPreco()) + "\n";
		}

		texto += "Subtotal: R$ " + String.format("%.2f", getSubtotal()) + "\n";
		texto += "Frete: R$ " + String.format("%.2f", frete) + "\n";
		texto += "Total: R$ " + String.format("%.2f", getTotal()) + "\n";
		texto += "Pagamento: " + pagamento.getNome() + "\n";
		texto += "Entrega: " + endereco;
		return texto;
	}
}
