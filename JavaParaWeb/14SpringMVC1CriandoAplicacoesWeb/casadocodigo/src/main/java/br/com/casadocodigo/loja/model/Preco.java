package br.com.casadocodigo.loja.model;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Preco {

	// Um @Embeddable não precisa de @Id
	
	private BigDecimal valor;
	// @Enumerated(EnumType.STRING)
    private TipoPreco tipo;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoPreco getTipo() {
        return tipo;
    }

    public void setTipo(TipoPreco tipo) {
        this.tipo = tipo;
    }
	
	
	
}
