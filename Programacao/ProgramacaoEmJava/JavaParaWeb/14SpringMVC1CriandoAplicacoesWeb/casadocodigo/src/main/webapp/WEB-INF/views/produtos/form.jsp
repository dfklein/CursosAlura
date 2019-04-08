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
				 
				 XXXXXXXXXXX
				 
		O atributo enctype é como você informa que a requisição enviará arquivos além de texto.
	 -->
	<form:form action="${s:mvcUrl('PC#gravar').build() }" 
		method="post" 
		commandName="produto"
		enctype="multipart/form-data">
	
		<div>
			<label>Título</label>
<!-- 			<input type="text" name="titulo"> -->
			<!-- Note que ao usar o form:input não é necessário especificar o atributo type dele -->
			<form:input path="titulo" />
			<!-- O path do form:errors é o nome do 
			 atributo cuja validação falhou. Veja os arquivos  
			 ProdutoValidation.java e AppWebConfiguration para ver
			 as configurações. Aqui ele está concatenando a string "titulo" com
			 produto", que é o que está definido no atributo "commandName" da tag form:form	 -->
			<form:errors path="titulo" ></form:errors>
		</div>
	
		<div>
			<label>Descrição</label>
			<form:textarea rows="10" cols="20" 
				path="descricao" />
			<form:errors path="descricao"/>
		</div>
	
		<div>
			<label>Paginas</label>
			<form:input path="paginas" />
			<form:errors path="paginas"/>
		</div>
	
		<div>
			<label>Data de lançamento</label>
			<form:input path="dataLancamento" />
			<form:errors path="dataLancamento"/>
		</div>
	
		<!-- A variável ${tipos} foi atachada à requisição em ProdutosController - public ModelAndView form() -->
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
	        <div>
	            <label>${tipoPreco}</label>
	            <form:input path="precos[${status.index}].valor" />
	            <!-- Abaixo o equivalente a um input de type=hidden -->
	            <!-- Este input hidden serve apenas para que você traga na requisição o id do produto e possa utilizá-lo no forEach -->
	            <form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}" />
	        </div>
	    </c:forEach>
		
		<div>
			<label>Sumário</label>
			<input type="file" name="sumario">
		</div>
		
		<button type="submit">Cadastrar</button>
	</form:form>
</body>
</html>