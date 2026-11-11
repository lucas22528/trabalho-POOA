package servico;
import java.util.ArrayList;
import java.util.List;

import modelo.Produto;

public class Catalogo {

    private List<Produto> produtos = new ArrayList<>();

    public void adicionar(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> listar() {
        return new ArrayList<>(produtos);
    }

    
    public Produto buscar(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }
}
