package br.com.casadocodigo.loja.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
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
 @Scope(value=WebApplicationContext.SCOPE_SESSION)
public class CarrinhoCompras {

	// O Integer é a quantidade
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();
	
	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);
		
	}

	private int getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)) {
			itens.put(item, 0);
		} 
		
		return itens.get(item);
	}
	
	public int getQuantidade() {
	    return itens.values().stream()
	        .reduce(0, (proximo, acumulador) -> proximo + acumulador);
	}

}
