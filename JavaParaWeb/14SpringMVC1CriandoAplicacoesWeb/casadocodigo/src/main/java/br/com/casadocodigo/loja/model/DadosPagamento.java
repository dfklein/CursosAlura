package br.com.casadocodigo.loja.model;

import java.math.BigDecimal;

/**
 * Esta classe foi criada para representar o JSON que precisa ser enviado na requisição para o meio de pagamento.
 * 
 * O Serviço que era chamado no exemplo exigia que fosse: {'value':VALOR_DO_PAGAMENTO}
 * 
 * Por isso o atributo deste objeto único:
 * 	1 - Se chama value -> o Spring vai atribuir à chave do objeto o nome do seu atributo na classe
 *	2 - É do tipo BigDecimal -> porque o serviço utilizado no exemplo pede um valor numérico que representa um valor monetário.
 *
 * ATENÇÃO: O método postForObject do objeto RestTemplate utilizado para fazer esta conversão precisa da
 * dependência do Jackson para realizar esta operação. Se seu projeto não possuir a dependência o código
 * compila normalmente e só quebra em tempo de execução. Veja o pom.xml
 */
public class DadosPagamento {

	private BigDecimal value;
	
	public DadosPagamento(BigDecimal value) {
		this.value = value;
		
	}
	
	public DadosPagamento() {
		super();
	}



	public BigDecimal getValue() {
		return value;
	}
}
