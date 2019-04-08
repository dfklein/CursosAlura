package br.com.casadocodigo.loja.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

// NUNCA SE ESQUEÇA QUE POR PADRÃO UMA CLASSE @Component É UM SINGLETON - > Ver maiores explicações em CarrinhoCompras.java
@Component
// Mas você pode definir o escopo com o @Scope:
// @Scope(value=WebApplicationContext.SCOPE_APPLICATION) // -> Este é o escopo padrão (ou seja: escopo de aplicação / singleton)
// LEMBRE-SE de que ao alterar o escopo de um Bean, ele precisa "caber" no escopo dos seus controllers (ou seja: você também 
// altera o escopo do controller, indicando como cada instância dele deve ser injetada), sendo que os
// controllers precisam ter um escopo MENOR ou igual ao do Bean que ele estiver injetando (lembre-se:
// neste caso aqui você tem um CarrinhoCompraController com escopo de requisição que recebe a injeção
// de um CarrinhoCompras que é um bean com escopo de sessão).
 @Scope(value=WebApplicationContext.SCOPE_SESSION,
 // O atributo "proxyMode" vai criar um proxy na classe alvo para indicar ao Spring que ele mesmo
 // deve lidar com a diferença de escopos. Este recurso foi apresentado quando foi criado o PagamentoController,
 // como uma alternativa para mantê-lo no escopo padrão (ou seja: escopo de aplicação). No entanto o instrutor
 // do curso frisou que ele concorda que um controller sem particularidades especiais deve ter escopo de
 // requisição.
		 proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable {

	private static final long serialVersionUID = 1605314890348342136L;
	
	// O Integer é a quantidade
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();
	
	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);
		
	}

	public Integer getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)) {
			itens.put(item, 0);
		} 
		
		return itens.get(item);
	}
	
	public int getQuantidade() {
	    return itens.values().stream()
	        .reduce(0, (proximo, acumulador) -> proximo + acumulador);
	}

	public BigDecimal getTotal(CarrinhoItem item) {
		return item.getTotal(getQuantidade(item));
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for(CarrinhoItem item : itens.keySet()) {
			total = total.add(getTotal(item));
		}
		return total;
	}
	
	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		produto.setId(produtoId);
		
		itens.remove(new CarrinhoItem(produto, tipoPreco));
		
	}


}
