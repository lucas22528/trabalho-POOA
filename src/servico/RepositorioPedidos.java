package servico;
import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;

public class RepositorioPedidos {
	
	 private List<Pedido> pedidos = new ArrayList<>();

	   
	    public int proximoId() {
	        return pedidos.size() + 1;
	    }

	    public void salvar(Pedido pedido) {
	        pedidos.add(pedido);
	    }

	 
	    public Pedido buscar(int id) {
	        for (Pedido pedido : pedidos) {
	            if (pedido.getId() == id) {
	                return pedido;
	            }
	        }
	        return null;
	    }

	    public List<Pedido> listar() {
	        return new ArrayList<>(pedidos);
	    }
}
