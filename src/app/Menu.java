package app;
import java.util.List;
import java.util.Scanner;

import extras.EmbalagemPresente;
import extras.GarantiaEstendida;
import extras.ItemProduto;
import extras.SeguroTransporte;
import modelo.Carrinho;
import modelo.Cliente;
import modelo.Endereço;
import modelo.ItemVenda;
import modelo.Pedido;
import modelo.Produto;
import pagamento.CriadorBoleto;
import pagamento.CriadorCartao;
import pagamento.CriadorPagamento;
import pagamento.CriadorPix;
import preco.CupomDesconto;
import preco.DescontoPrimeiraCompra;
import preco.EstrategiaDesconto;
import preco.EstrategiaFrete;
import preco.FreteExpresso;
import preco.FreteComum;
import preco.RetiradaNaLoja;
import servico.Catalogo;
import servico.CheckoutFacade;
public class Menu {
	 private Catalogo catalogo;
	    private CheckoutFacade facade;
	    private Scanner teclado;
	    private Carrinho carrinho = new Carrinho();
	    private Cliente cliente;

	    public Menu(Catalogo catalogo, CheckoutFacade facade, Scanner teclado) {
	        this.catalogo = catalogo;
	        this.facade = facade;
	        this.teclado = teclado;
	    }

	    public void executar() {
	        System.out.print("Seu nome: ");
	        String nome = teclado.nextLine().trim();
	        System.out.print("Seu e-mail: ");
	        String email = teclado.nextLine().trim();
	        cliente = new Cliente(nome, email);

	        int opcao = -1;
	        while (opcao != 0) {
	            mostrarMenu();
	            opcao = lerNumero("Escolha: ");
	            try {
	                executarOpcao(opcao);
	            } catch (RuntimeException e) {
	                System.out.println(">> " + e.getMessage());
	            }
	        }
	        System.out.println("Até logo!");
	    }

	    private void mostrarMenu() {
	        System.out.println("\n--- MENU ---");
	        System.out.println("1 - Ver catálogo");
	        System.out.println("2 - Adicionar item ao carrinho");
	        System.out.println("3 - Remover item do carrinho");
	        System.out.println("4 - Ver carrinho");
	        System.out.println("5 - Finalizar compra");
	        System.out.println("6 - Meus pedidos");
	        System.out.println("7 - Avançar status de um pedido");
	        System.out.println("0 - Sair");
	    }

	    private void executarOpcao(int opcao) {
	        switch (opcao) {
	            case 1:
	                verCatalogo();
	                break;
	            case 2:
	                adicionarItem();
	                break;
	            case 3:
	                removerItem();
	                break;
	            case 4:
	                verCarrinho();
	                break;
	            case 5:
	                finalizarCompra();
	                break;
	            case 6:
	                verPedidos();
	                break;
	            case 7:
	                avancarStatus();
	                break;
	            case 0:
	                break;
	            default:
	                System.out.println("Opção inválida.");
	        }
	    }

	    private void verCatalogo() {
	        for (Produto p : catalogo.listar()) {
	            System.out.printf("%d - %s: R$ %.2f (estoque: %d)%n", p.getId(), p.getNome(), p.getPreco(), p.getEstoque());
	        }
	    }

	    private void adicionarItem() {
	        verCatalogo();
	        int id = lerNumero("Código do produto: ");
	        Produto produto = catalogo.buscar(id);
	        if (produto == null) {
	            throw new IllegalArgumentException("Produto não encontrado.");
	        }

	        ItemVenda item = new ItemProduto(produto);
	        if (perguntar("Embalagem para presente (+ R$ 8,00)?")) {
	            item = new EmbalagemPresente(item);
	        }
	        if (perguntar("Garantia estendida (+ 12%)?")) {
	            item = new GarantiaEstendida(item);
	        }
	        if (perguntar("Seguro de transporte (+ R$ 5,00)?")) {
	            item = new SeguroTransporte(item);
	        }
	        carrinho.adicionar(item);
	        System.out.printf("Adicionado: %s - R$ %.2f%n", item.getDescricao(), item.getPreco());
	    }

	    private void removerItem() {
	        verCarrinho();
	        if (!carrinho.isVazio()) {
	            int numero = lerNumero("Número do item a remover: ");
	            carrinho.remover(numero - 1);
	            System.out.println("Item removido.");
	        }
	    }

	    private void verCarrinho() {
	        if (carrinho.isVazio()) {
	            System.out.println("Carrinho vazio.");
	            return;
	        }
	        List<ItemVenda> itens = carrinho.getItens();
	        for (int i = 0; i < itens.size(); i++) {
	            System.out.printf("%d) %s - R$ %.2f%n", i + 1, itens.get(i).getDescricao(), itens.get(i).getPreco());
	        }
	        System.out.printf("Subtotal: R$ %.2f%n", carrinho.getSubtotal());
	    }

	    private void finalizarCompra() {
	        verCarrinho();
	        if (carrinho.isVazio()) {
	            return;
	        }

	        // STRATEGY: o usuário escolhe a estratégia de frete.
	        System.out.println("Frete: 1 - Normal   2 - Expresso   3 - Retirada na loja");
	        int opcaoFrete = lerNumero("Opção: ");
	        EstrategiaFrete frete;
	        if (opcaoFrete == 1) {
	            frete = new FreteComum();
	        } else if (opcaoFrete == 2) {
	            frete = new FreteExpresso();
	        } else if (opcaoFrete == 3) {
	            frete = new RetiradaNaLoja();
	        } else {
	            throw new IllegalArgumentException("Opção de frete inválida.");
	        }

	        Endereço endereço = null;
	        if (opcaoFrete != 3) {
	            System.out.print("Rua e número: ");
	            String rua = teclado.nextLine().trim();
	            System.out.print("Cidade: ");
	            String cidade = teclado.nextLine().trim();
	            System.out.print("CEP: ");
	            String cep = teclado.nextLine().trim();
	            endereço = new Endereço(rua, cidade, cep);
	        }

	        // STRATEGY: o usuário escolhe a estratégia de desconto.
	        System.out.print("Cupom (Enter para nenhum; exemplos: PROMO10, VIP20): ");
	        String cupom = teclado.nextLine().trim();
	        EstrategiaDesconto desconto;
	        if (cupom.isEmpty()) {
	            desconto = new DescontoPrimeiraCompra();
	        } else {
	            desconto = new CupomDesconto(cupom);
	        }

	        // FACTORY METHOD: o usuário escolhe qual criador de pagamento será usado.
	        System.out.println("Pagamento: 1 - Pix   2 - Cartão   3 - Boleto");
	        int opcaoPagamento = lerNumero("Opção: ");
	        CriadorPagamento criador;
	        if (opcaoPagamento == 1) {
	            criador = new CriadorPix();
	        } else if (opcaoPagamento == 2) {
	            criador = new CriadorCartao();
	        } else if (opcaoPagamento == 3) {
	            criador = new CriadorBoleto();
	        } else {
	            throw new IllegalArgumentException("Opção de pagamento inválida.");
	        }

	        Pedido pedido = facade.finalizarCompra(cliente, endereço, carrinho, frete, desconto, criador);
	        System.out.println(pedido.resumo());
	    }

	    private void verPedidos() {
	        List<Pedido> pedidos = facade.listarPedidos();
	        if (pedidos.isEmpty()) {
	            System.out.println("Nenhum pedido ainda.");
	        }
	        for (Pedido pedido : pedidos) {
	            System.out.println(pedido.resumo());
	        }
	    }

	    private void avancarStatus() {
	        verPedidos();
	        int id = lerNumero("Número do pedido: ");
	        Pedido pedido = facade.avancarStatus(id);
	        System.out.println("Novo status do pedido #" + pedido.getId() + ": " + pedido.getStatus());
	    }

	    // ---------- leitura do teclado ----------
	    private int lerNumero(String mensagem) {
	        while (true) {
	            System.out.print(mensagem);
	            String texto = teclado.nextLine().trim();
	            try {
	                return Integer.parseInt(texto);
	            } catch (NumberFormatException e) {
	                System.out.println("Digite um número válido.");
	            }
	        }
	    }

	    private boolean perguntar(String pergunta) {
	        System.out.print(pergunta + " (s/n): ");
	        return teclado.nextLine().trim().equalsIgnoreCase("s");
	    }
}
