package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

// Esta anotação permite que o JUnit utilize o SpringJUnit4ClassRunner para executar os testes.
// É o que permite você utilizar objetos gerenciados pelo Spring sem o contexto web.
@RunWith(SpringJUnit4ClassRunner.class)
//Para fazer testes usando banco de dados, você criou um novo database chamado livros_test. Com
//o Spring você pode possuir diferentes profiles de conexão com o banco, permitindo que você 
//indique qual banco é utilizado em qual situação. Você precisou criar uma classe chamada 
//DataSourceConfigurationTest (que está nos pacote ...conf no diretório dos pacotes de teste)!
//Nas suas classes de configuração de DataSource (nas duas, na JPAConfiguration.class também)
//você precisou especificar uma anotação @Profile dizendo qual dos perfis você estaria 
//utilizando.
// IMPORTANTE: ao utilizar diversos perfis de conexão com o banco você precisa também configurar
// a sua aplicação para que ela saiba qual perfil utilizará. Ver a classe ServletSpringMVC.class no
// método onStartUp
@ActiveProfiles("test")
// Declara todas as classes do contexto web que precisam ser utilizadas nesta classe:
// classes de configuração do Spring e classes que devem são gerenciadas na aplicação.
@ContextConfiguration(classes= {JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class})
public class ProdutoDAOTest {

	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Test
	// No curso ele não utilizou o @Transactional em nenhum outro ponto da aplicação mas falou aqui
	// como se tivesse utilizado. Não tenho maiores explicações sobre o @Transactional.
	@Transactional
	public void deveSomarTodosPrecosPorTipoDeLivro() {
		
		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN)
				.more(3).buildAll();
		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN)
				.more(3).buildAll();
		
		// O próprio Spring test cuida de, ao finalizar os testes, limpar os dados inseridos para que não
		// interfiram em testes futuros.
		livrosImpressos.forEach(produtoDAO::gravar);
		livrosEbook.forEach(produtoDAO::gravar);
		
		BigDecimal valor = produtoDAO.somaPrecosPorTipoPreco(TipoPreco.EBOOK);
		// BigDecimal com 2 casas decimais
		Assert.assertEquals(new BigDecimal(40).setScale(2), valor);
	}
}
