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
 * Documenta��o do RestAssured: https://code.google.com/p/rest-assured/wiki/Usage
 */
public class UsuariosWSTest {
	
	private Usuario esperado1;
	private Usuario esperado2;

	@Before
	public void setUp() {
		esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
        
        // Por padr�o o RestAssured utiliza "http://localhost:8080/" nos seus testes de url. Mas
        // voc� pode configurar isto se quiser alterando alguns atributos est�ticos dele:
        RestAssured.baseURI = "http://www.meuendereco.com.br";
        RestAssured.port = 80;
	}

	@Test
	public void deveRetornarListaDeUsuarios() {
		
		// Esta biblioteca tem este objeto f�cil de construir. Abaixo um exemplo de como fazer uma
		// requisi��o get solicitando um xml no header.
		XmlPath xmlPath = RestAssured
				.given()
				.header("Accept", "application/xml")
				.get("/usuarios")
				.andReturn()
				.xmlPath();
		
		// Abaixo a maneira de voc� ler o seu xml e convert�-lo para um objeto conhecido da sua aplica��o.
		// O primeiro par�metro � uma refer�ncia ao objeto no XML que voc� est� acessando. Ao fim desta
		// classe voc� tem um exemplo do XML que a aplica��o que est� sendo testada deveria gerar.
		Usuario us1 = xmlPath.getObject("list.usuario[0]", Usuario.class);
		Usuario us2 = xmlPath.getObject("list.usuario[1]", Usuario.class);
		
		// Tamb�m funciona:
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
		
		// Mesmo se seu JSON n�o voltar um objeto que possa ser desserializado, a API de Path
		// (tanto de XML quanto de Json) possibilita que voc� apenas navegue por ele. Por exemplo,
		// se quis�ssemos pegar apenas a String contendo o nome "Mauricio Aniche", poder�amos fazer:
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
                .statusCode(200) // Veja que aqui voc� pode fazer j� o seu teste, na verdade. No curso ele disse que utilizava isso apenas quando era muito mais f�cil pois entendia que utilizar o JUnit era mais usual e portanto comum a v�rios desenvolvedores
			.when()
				.post("/usuarios")
			.andReturn()
				.xmlPath();

		Usuario resposta = retorno.getObject("usuario", Usuario.class);

		Assert.assertEquals("Joao da Silva", resposta.getNome());
		Assert.assertEquals("joao@dasilva.com", resposta.getEmail());
		
		// aqui ele deleta o usu�rio criado:
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
