package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import extras.EmbalagemPresente;
import extras.GarantiaEstendida;
import extras.ItemProduto;
import modelo.Cliente;
import modelo.ItemVenda;
import modelo.Pedido;
import modelo.Produto;
import pagamento.CriadorCartao;
import pagamento.CriadorPagamento;
import pagamento.CriadorPix;
import preco.EstrategiaFrete;
import preco.FreteExpresso;
import preco.FreteNormal;
import servico.CheckoutFacade;

public class MenuConsole {

	private List<Produto> produtos;
	private CheckoutFacade facade;
	private Scanner sc;
	private List<ItemVenda> carrinho = new ArrayList<>();
	private Cliente cliente;

	public MenuConsole(List<Produto> produtos, CheckoutFacade facade, Scanner sc) {
		this.produtos = produtos;
		this.facade = facade;
		this.sc = sc;
	}

	public void executar() {
		System.out.print("Nome: ");
		String nome = sc.nextLine().trim();
		System.out.print("Email: ");
		String email = sc.nextLine().trim();
		cliente = new Cliente(nome, email);

		int opcao = -1;

		while (opcao != 0) {
			mostrarMenu();
			opcao = lerNumero("Escolha: ");

			try {
				executarOpcao(opcao);
			} catch (Exception e) {
				System.out.println("- " + e.getMessage());
			}
		}
		System.out.println("Obrigado por usar a MiniShop!");
	}

	private void mostrarMenu() {
		System.out.println("\nMENU:");
		System.out.println("1 - Ver catálogo");
		System.out.println("2 - Adicionar item no carrinho");
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
			System.out.println("Opção invalida");
		}
	}

	private void verCatalogo() {
		for (Produto p : produtos) {
			System.out.printf("%d - %s: R$ %.2f%n", p.getId(), p.getNome(), p.getPreco());
		}
	}

	private void adicionarItem() {
		verCatalogo();
		int id = lerNumero("Código do produto: ");

		if (id < 1 || id > produtos.size()) {
			throw new IllegalArgumentException("Produto não encontrado.");
		}

		ItemVenda item = new ItemProduto(produtos.get(id - 1));
		if (perguntar("Embalagem para presente (+ R$ 8,00?)")) {
			item = new EmbalagemPresente(item);
		}
		if (perguntar("Garantia estendida (+ 12%)?")) {
			item = new GarantiaEstendida(item);
		}
		carrinho.add(item);
		System.out.printf("Adicionado: %s - R$ %.2f%n", item.getDescricao(), item.getPreco());
	}

	private void removerItem() {
		verCarrinho();
		if (carrinho.isEmpty()) {
			return;
		}
		int numero = lerNumero("Numero do item que deseja remover: ");
		if (numero < 1 || numero > carrinho.size()) {
			throw new IllegalArgumentException("Este item não existe no carrinho");
		}
		carrinho.remove(numero - 1);
		System.out.println("Item removido");
	}

	private void verCarrinho() {
		if (carrinho.isEmpty()) {
			System.out.println("Carrinho vazio");
			return;
		}
		double subtotal = 0;
		for (int i = 0; i < carrinho.size(); i++) {
			ItemVenda item = carrinho.get(i);
			System.out.printf("%d) %s - R$ %.2f%n", i + 1, item.getDescricao(), item.getPreco());
			subtotal = subtotal + item.getPreco();
		}
		System.out.printf("Subtotal: R$ %.2f%n", subtotal);
	}

	private void finalizarCompra() {
		verCarrinho();
		if (carrinho.isEmpty()) {
			return;
		}

		System.out.println("Frete: 1 - Normal (+ R$15,00)   2 - Expresso( + R$30,00)");
		int opcaoFrete = lerNumero("Opção: ");
		EstrategiaFrete frete;
		if (opcaoFrete == 1) {
			frete = new FreteNormal();
		} else if (opcaoFrete == 2) {
			frete = new FreteExpresso();
		} else {
			throw new IllegalArgumentException("Opção de frete inválida.");
		}

		System.out.print("Endereço de entrega: ");
		String endereco = sc.nextLine().trim();

		System.out.println("Pagamento: 1 - Pix   2 - Cartão");
		int opcaoPagamento = lerNumero("Opção: ");
		CriadorPagamento criador;
		if (opcaoPagamento == 1) {
			criador = new CriadorPix();
		} else if (opcaoPagamento == 2) {
			criador = new CriadorCartao();
		} else {
			throw new IllegalArgumentException("Opção de pagamento inválida");
		}

		Pedido pedido = facade.finalizarCompra(cliente, endereco, carrinho, frete, criador);
		System.out.println(pedido.resumo());
		carrinho.clear();

	}

	private void verPedidos() {
		List<Pedido> pedidos = facade.listarPedidos();
		if (pedidos.isEmpty()) {
			System.out.println("Nenhum pedido ainda");
		}
		for (Pedido pedido : pedidos) {
			System.out.println(pedido.resumo());
			System.out.println();
		}
	}

	private void avancarStatus() {
		verPedidos();
		int id = lerNumero("Numero do pedido: ");
		Pedido pedido = facade.avancarStatus(id);
		System.out.println("Novo status do pedido id:" + pedido.getId() + ": " + pedido.getStatus());
	}

	private int lerNumero(String mensagem) {
		System.out.print(mensagem);
		String texto = sc.nextLine().trim();
		try {
			return Integer.parseInt(texto);
		} catch (NumberFormatException e) {
			System.out.println("Digite um numero válido");
			return -1;
		}
	}

	private boolean perguntar(String pergunta) {
		System.out.print(pergunta + " (s/n): ");
		return sc.nextLine().trim().equalsIgnoreCase("s");
	}
}
