package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.PedidoObserver;
import modelo.Produto;
import notificacao.NotificadorEmail;
import notificacao.RegistroLog;
import servico.CheckoutFacade;

public class Main {

	public static void main(String[] args) {

		List<Produto> produtos = new ArrayList<>();
		produtos.add(new Produto(1, "Notebook", 3500.00));
		produtos.add(new Produto(2, "Monitor 27 polegadas", 900.00));
		produtos.add(new Produto(3, "Mouse sem fio", 80.00));
		produtos.add(new Produto(4, "Teclado mecânico", 180.00));
		produtos.add(new Produto(5, "Fone Bluetooth", 250.00));

		List<PedidoObserver> observadores = new ArrayList<>();
		observadores.add(new NotificadorEmail());
		observadores.add(new RegistroLog());

		CheckoutFacade facade = new CheckoutFacade(observadores);

		System.out.println("MiniShop - Loja Virtual");

		Scanner sc = new Scanner(System.in);
		new MenuConsole(produtos, facade, sc).executar();
	}
}
