package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;

import java.io.File;

import org.junit.Test;

/**
 * Documentação do RestAssured: https://code.google.com/p/rest-assured/wiki/Usage
 */
public class OutrosTestes {

	@Test
    public void deveGerarUmCookie() {
        expect()
            .cookie("rest-assured", "funciona")
        .when()
            .get("/cookie/teste");
    }
	
	@Test
    public void deveGerarUmHeader() {
        expect()
            .header("novo-header", "abc")
        .when()
            .get("/cookie/teste");
    }
	
	@Test
	public void enviarArquivo() {
		given().multiPart(new File("/path/para/arquivo")).when().post("/upload");
	}
}
