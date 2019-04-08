package br.com.casadocodigo.loja.controllers;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.casadocodigo.loja.conf.AppWebConfiguration;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.conf.SecurityConfiguration;

// Ver ProdutoDAO sobre estas anotações
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {JPAConfiguration.class,
		// Você precisa adicionar as suas configurações web ao contexto para testar os controllers
		AppWebConfiguration.class, 
		SecurityConfiguration.class, 
		DataSourceConfigurationTest.class})
@ActiveProfiles("test")
// Esta anotação é a que configura o sistema para podermos rodar todo o contexto web do spring na
// execução deste teste
@WebAppConfiguration
public class ProdutosControllerTest {
	
	// Este é um objeto do Spring que permite que você faça o mock de 
	private MockMvc mockMvc;

	// O Spring já possui esse bean para ser injetado, não necessita outras configurações
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private Filter springSecurityFilterChain;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				// Para o teste de segurança funcionar (ele efetivamente testar o usuário logado) você precisa adicionar o filtro do Security ao mock do MVC.
				// Não esqueça de adicionar o SecurityConfiguration.class no @ContextConfiguration
				.addFilter(springSecurityFilterChain)
				.build();
	}
	
	@Test
	public void deveRetornarParaHomeComOsLivros() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	// Para o teste de segurança funcionar (ele efetivamente testar o usuário logado) você precisa adicionar o filtro do Security ao mock do MVC. 
	// Ver o método setUp()
	@Test
	public void somenteAdminDeveAcessarProdutosForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form")
				// Te permite mockar um usuário logado com essas características ao realizar a requisição acima
				.with(SecurityMockMvcRequestPostProcessors
						.user("user@casadocodigo.com.br").password("123456")
						.roles("USER")))
				// Espera retornar status 403 na requisição (ou seja: acesso negado - só o role ADMIN pode)
				.andExpect(MockMvcResultMatchers.status().is(403));
		
	}

}
