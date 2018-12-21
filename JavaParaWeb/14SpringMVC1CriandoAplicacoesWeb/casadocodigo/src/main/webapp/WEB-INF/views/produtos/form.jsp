<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livraria - muitos livros, blá blá blá</title>
</head>
<body>
	<!-- 
		Apesar de eu ter achado isso horrível, o que esta variável declarada dentro do action
		faz é procurar por uma classe controller pelas suas iniciais (analisando o nome da classe
		pelo camel case).
		No caso: PC = ProdutosController
				 #gravar = submeter o formulário chamando o método gravar() do controller.
	 -->
	<form:form action="${s:mvcUrl('PC#gravar').build() }" method="post" commandName="produto">
	
		<div>
			<label>Título</label>
			<input type="text" name="titulo">
			<!-- O path do form:errors é o nome do 
			 atributo cuja validação falhou. Veja os arquivos  
			 ProdutoValidation.java e AppWebConfiguration para ver
			 as configurações. Aqui ele está concatenando a string "titulo" com
			 produto", que é o que está definido no atributo "commandName" da tag form:form	 -->
			<form:errors path="titulo" ></form:errors>
		</div>
	
		<div>
			<label>Descrição</label>
			<textarea rows="10" cols="20" 
				name="descricao">
			</textarea>
			<form:errors path="descricao"/>
		</div>
	
		<div>
			<label>Paginas</label>
			<input type="text" name="paginas">
			<form:errors path="paginas"/>
		</div>
	
		<!-- A variável ${tipos} foi atachada à requisição em ProdutosController - public ModelAndView form() -->
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
	        <div>
	            <label>${tipoPreco}</label>
	            <input type="text" name="precos[${status.index}].valor" />
	            <input type="hidden" name="precos[${status.index}].tipo" value="${tipoPreco}" />
	        </div>
	    </c:forEach>
		
		<button type="submit">Cadastrar</button>
	</form:form>
</body>
</html>