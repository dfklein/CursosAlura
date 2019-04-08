<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livraria - muitos livros, blá blá blá</title>
</head>
<body>

	<div>${sucesso }</div>
	<div>${falha }</div>

	<table>
		<tr>
			<th>Título</th>
			<th>Descrição</th>
			<th>Páginas</th>
		</tr>
			<c:forEach items="${ produtos }" var="produto">
				<tr>
					<!-- 
						O "arg" é uma maneira de passar parâmetros para o seu controller.
						O primeiro argumento é o índice do argumento que você está passando (quase sempre será zero)
					 -->
					<td><a href="${ s:mvcUrl('PC#detalhe').arg(0, produto.id).build() }">${ produto.titulo }</a></td>
					<td>${ produto.descricao }</td>
					<td>${ produto.paginas }</td>
			
				</tr>
			</c:forEach>
	</table>


</body>
</html>