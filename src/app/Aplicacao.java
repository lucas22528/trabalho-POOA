package app;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.PedidoObserver;
import modelo.Produto;
import notificacao.ControleEstoque;
import notificacao.NotificaçaoEmail;
import notificacao.RegistroLog;
import servico.Catalogo;
import servico.CheckoutFacade;
import servico.RepositorioPedidos;
public class Aplicacao {

	public static void main(String[] args) {
		
        Catalogo catalogo = new Catalogo();
        catalogo.adicionar(new Produto(1, "Notebook Pro 14", 3500.00, 5));
        catalogo.adicionar(new Produto(2, "Monitor 27 polegadas", 900.00, 4));
        catalogo.adicionar(new Produto(3, "Mouse sem fio", 80.00, 20));
        catalogo.adicionar(new Produto(4, "Teclado mecânico", 180.00, 10));
        catalogo.adicionar(new Produto(5, "Fone Bluetooth", 250.00, 8));

        List<PedidoObserver> observadores = new ArrayList<>();
        observadores.add(new NotificaçaoEmail());
        observadores.add(new RegistroLog());
        observadores.add(new ControleEstoque());

        CheckoutFacade facade = new CheckoutFacade(new RepositorioPedidos(), observadores);

        System.out.println("===== MiniShop - Loja Virtual =====");
        System.out.println("1 - Demonstração automática");
        System.out.println("2 - Modo interativo (menu)");
        System.out.print("Escolha: ");

        Scanner sc = new Scanner(System.in);
        int escolha = sc.nextInt();

       
            new Menu(catalogo, facade, sc).executar();
        
    }
}
