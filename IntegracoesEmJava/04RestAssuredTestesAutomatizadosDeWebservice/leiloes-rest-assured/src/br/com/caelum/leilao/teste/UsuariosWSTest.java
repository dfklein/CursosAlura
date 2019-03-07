package br.com.caelum.leilao.teste;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Usuario;

/**
 * Documentação do RestAssured: https://code.google.com/p/rest-assured/wiki/Usage
 */
public class UsuariosWSTest {
	
	private Usuario esperado1;
	private Usuario esperado2;

	@Before
	public void setUp() {
		esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
        
        // Por padrão o RestAssured utiliza "http://localhost:8080/" nos seus testes de url. Mas
        // você pode configurar isto se quiser alterando alguns atributos estáticos dele:
        RestAssured.baseURI = "http://www.meuendereco.com.br";
        RestAssured.port = 80;
	}

	@Test
	public void deveRetornarListaDeUsuarios() {
		
		// Esta biblioteca tem este objeto fácil de construir. Abaixo um exemplo de como fazer uma
		// requisição get solicitando um xml no header.
		XmlPath xmlPath = RestAssured
				.given()
				.header("Accept", "application/xml")
				.get("/usuarios")
				.andReturn()
				.xmlPath();
		
		// Abaixo a maneira de você ler o seu xml e convertê-lo para um objeto conhecido da sua aplicação.
		// O primeiro parâmetro é uma referência ao objeto no XML que você está acessando. Ao fim desta
		// classe você tem um exemplo do XML que a aplicação que está sendo testada deveria gerar.
		Usuario us1 = xmlPath.getObject("list.usuario[0]", Usuario.class);
		Usuario us2 = xmlPath.getObject("list.usuario[1]", Usuario.class);
		
		// Também funciona:
		List<Usuario> usuarios = xmlPath.getList("list.usuario", Usuario.class);
		
		// Por fim, os testes.
        Assert.assertEquals(esperado1, us1);
        Assert.assertEquals(esperado2, us2);
		
		
	}
	
	@Test // http://localhost:8080/usuarios/show?usuario.id=1&_format=json
	public void deveRetornarUsuarioPeloId() {
		JsonPath path = RestAssured
			.given()
			.parameter("usuario.id", 1)
			.header("Accept", "application/json")
			.get("/usuario/show")
			.andReturn()
			.jsonPath();
		
		List<Usuario> usuarios = path.getList("list.usuario", Usuario.class);

		Assert.assertEquals(esperado1, usuarios.get(0));
		Assert.assertEquals(esperado2, usuarios.get(1));
		
		// Mesmo se seu JSON não voltar um objeto que possa ser desserializado, a API de Path
		// (tanto de XML quanto de Json) possibilita que você apenas navegue por ele. Por exemplo,
		// se quiséssemos pegar apenas a String contendo o nome "Mauricio Aniche", poderíamos fazer:
		String nomeUsuario = path.getString("usuario.nome");
	}
	
	@Test
	public void deveAdicionarUmUsuario() {
		Usuario joao = new Usuario("Joao da Silva", "joao@dasilva.com");

		XmlPath retorno = RestAssured
			.given()
		        .header("Accept", "application/xml")
		        .contentType("application/xml")
		        .body(joao)
		    .expect()
                .statusCode(200) // Veja que aqui você pode fazer já o seu teste, na verdade. No curso ele disse que utilizava isso apenas quando era muito mais fácil pois entendia que utilizar o JUnit era mais usual e portanto comum a vários desenvolvedores
			.when()
				.post("/usuarios")
			.andReturn()
				.xmlPath();

		Usuario resposta = retorno.getObject("usuario", Usuario.class);

		Assert.assertEquals("Joao da Silva", resposta.getNome());
		Assert.assertEquals("joao@dasilva.com", resposta.getEmail());
		
		// aqui ele deleta o usuário criado:
		RestAssured.given()
		        .contentType("application/xml")
		        .body(resposta)
		    .expect()
		        .statusCode(200)
		    .when()
		        .delete("/leiloes/deletar")
		    .andReturn().asString();
	}
	
	
}
/**
<list>
	<usuario>
		<id>1</id>
		<nome>Mauricio Aniche</nome>
		<email>mauricio.aniche@caelum.com.br</email>
	</usuario>
	<usuario>
		<id>2</id>
		<nome>Guilherme Silveira</nome>
		<email>guilherme.silveira@caelum.com.br</email>
	</usuario>
</list>
*/
