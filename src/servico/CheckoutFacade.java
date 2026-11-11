package servico;
import java.util.List;

import modelo.Carrinho;
import modelo.Cliente;
import modelo.Endereço;
import modelo.ItemVenda;
import modelo.Pedido;
import modelo.PedidoBuilder;
import modelo.PedidoObserver;
import modelo.Produto;
import pagamento.CriadorPagamento;
import pagamento.Pagamento;
import preco.CalculadoraPreco;
import preco.EstrategiaDesconto;
import preco.EstrategiaFrete;


public class CheckoutFacade {

    private RepositorioPedidos repositorio;
    private List<PedidoObserver> observadores;

    public CheckoutFacade(RepositorioPedidos repositorio, List<PedidoObserver> observadores) {
        this.repositorio = repositorio;
        this.observadores = observadores;
    }

    public Pedido finalizarCompra(Cliente cliente, Endereço endereço, Carrinho carrinho,
                                  EstrategiaFrete frete, EstrategiaDesconto desconto,
                                  CriadorPagamento criador) {

        if (carrinho.isVazio()) {
            System.out.println("O carrinho esta vazio.");
        }
        verificarEstoque(carrinho);

       
        Pagamento pagamento = criador.criarPagamento();

       
        Pedido pedido = new PedidoBuilder()
                .comId(repositorio.proximoId())
                .cliente(cliente)
                .endereço(endereço)
                .itens(carrinho.getItens())
                .pagamento(pagamento)
                .construir();

    
        for (PedidoObserver observador : observadores) {
            pedido.adicionarObservador(observador);
        }

     
        new CalculadoraPreco(frete, desconto).aplicar(pedido);

        if (!pagamento.processar(pedido.getTotal())) {
            System.out.println("Pagamento recusado.");
        }

        pedido.avancarStatus(); 
        cliente.registrarCompra();
        carrinho.esvaziar();
        repositorio.salvar(pedido);
        return pedido;
    }

    public Pedido avancarStatus(int idPedido) {
        Pedido pedido = repositorio.buscar(idPedido);
        if (pedido == null) {
           System.out.println("Pedido id: " + idPedido + " não encontrado.");
        }
        pedido.avancarStatus();
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return repositorio.listar();
    }

    
    private void verificarEstoque(Carrinho carrinho) {
        List<ItemVenda> itens = carrinho.getItens();
        
        for (ItemVenda item : itens) {
        	
            Produto produto = item.getProduto();
            int quantidadeNoCarrinho = 0;
            
            for (ItemVenda outro : itens) {
                if (outro.getProduto() == produto) {
                    quantidadeNoCarrinho++;
                }
            }
            if (produto.getEstoque() < quantidadeNoCarrinho) {
                System.out.println("Estoque insuficiente para " + produto.getNome()
                        + " (disponivel: " + produto.getEstoque() + ").");
            }
        }
    }
}
