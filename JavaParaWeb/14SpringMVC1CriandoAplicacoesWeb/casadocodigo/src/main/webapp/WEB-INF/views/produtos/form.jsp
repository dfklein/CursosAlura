<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livraria - muitos livros, blá blá blá</title>
</head>
<body>
	<!-- 
		Depois você pode observar no ProdutosController que não é necessário especificar
		no mapeamento se ele está recebendo um método GET ou POST.
	 -->
	<form action="/casadocodigo/produtos" method="POST">
	
		<div>
			<label>Título</label>
			<input type="text" name="titulo">
		</div>
	
		<div>
			<label>Descrição</label>
			<textarea rows="10" cols="20" 
				name="descricao">
			</textarea>
		</div>
	
		<div>
			<label>Paginas</label>
			<input type="text" name="paginas">
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
		
	</form>
</body>
</html>