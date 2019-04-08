<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- JSTL: Java Standard Tag Library -->
<!-- taglib = define o pacote onde se encontra a biblioteca que você está importando -->
<!-- prefix = define o prefixo a ser utilizado quando for acionada uma tag desta lib -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url value="/editarEmpresaServlet" var="linkEditarEmpresaServlet" />

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<!-- O problema desta abordagem de declarar o form é que o contexto ("gerenciador") pode ser alterado. -->
	<!-- Você pode fazer isso, por exemplo, indo nos properties do projeto e em Web Project Settings e alterar o Context Root -->
	<!-- <form action="/gerenciador/novaEmpresa" method="post"> -->
	<!-- Para resolver isto você pode usar a tag URL do core, que vai buscar a definição de contexto do projeto. -->
	<!-- <form action="<c:url value="/novaEmpresa" />" method="post"> -->
	
	<!-- Melhor ainda é declarar como uma variável, o que você fez lá em cima nos imports -->
	<form action="${linkEditarEmpresaServlet}" method="post">
		<table>
			<tr>
				<td>Nome:</td>
				<td><input type="text" name="nome" value="${ empresa.nome }"/></td>
			</tr>
			<tr>
				<td>Data:</td>
				<td><input type="text" name="data" value="<fmt:formatDate value="${ empresa.dataAbertura }" pattern="dd/MM/yyyy"/>"/></td>
			</tr>
		</table>
		<input type="hidden" name="id" value="${ empresa.id }"/>
		<input type="submit"/>
	</form>

</body>
</html>