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

	<table>
		<tr>
			<th>Título</th>
			<th>Descrição</th>
			<th>Páginas</th>
		</tr>
			<c:forEach items="${ produtos }" var="produto">
				<tr>
					<td>${ produto.titulo }</td>
					<td>${ produto.descricao }</td>
					<td>${ produto.paginas }</td>
			
				</tr>
			</c:forEach>
	</table>


</body>
</html>