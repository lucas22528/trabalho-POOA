package modelo;

	import java.util.ArrayList;
	import java.util.List;
	import pagamento.Pagamento;

	public class PedidoBuilder {

		private int id;
	    private Cliente cliente;
	    private Endereço endereço;
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

	    public PedidoBuilder endereço(Endereço endereço) {
	        this.endereço = endereço;
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
	            System.out.println("Pedido sem cliente.");
	        }
	        if (itens.isEmpty()) {
	        	 System.out.println("Pedido sem itens.");
	        }
	        if (pagamento == null) {
	        	 System.out.println("Pedido sem forma de pagamento.");
	        }
	        return new Pedido(this);
	    }

	    
	    int getId() {
	        return id;
	    }

	    Cliente getCliente() {
	        return cliente;
	    }

	    Endereço getEndereço() {
	        return endereço;
	    }

	    List<ItemVenda> getItens() {
	        return itens;
	    }

	    Pagamento getPagamento() {
	        return pagamento;
	    }
	}

