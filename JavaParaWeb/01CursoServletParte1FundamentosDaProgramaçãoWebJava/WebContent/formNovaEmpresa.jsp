<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- JSTL: Java Standard Tag Library -->
<!-- taglib = define o pacote onde se encontra a biblioteca que voc� est� importando -->
<!-- prefix = define o prefixo a ser utilizado quando for acionada uma tag desta lib -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url value="/novaEmpresa" var="linkServeletNovaEmpresa" />

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<!-- O problema desta abordagem de declarar o form � que o contexto ("gerenciador") pode ser alterado. -->
	<!-- Voc� pode fazer isso, por exemplo, indo nos properties do projeto e em Web Project Settings e alterar o Context Root -->
	<!-- <form action="/gerenciador/novaEmpresa" method="post"> -->
	W
	<!-- Para resolver isto voc� pode usar a tag URL do core, que vai buscar a defini��o de contexto do projeto. -->
	<!-- <form action="<c:url value="/novaEmpresa" />" method="post"> -->
	
	<!-- Melhor ainda � declarar como uma vari�vel, o que voc� fez l� em cima nos imports -->
	<form action="${linkServeletNovaEmpresa}" method="post">
		<table>
			<tr>
				<td>Nome:</td>
				<td><input type="text" name="nome"/></td>
			</tr>
			<tr>
				<td>Data:</td>
				<td><input type="text" name="data"/></td>
			</tr>
			<!-- 
			<tr>
				<td>Numero:</td>
				<td><input type="text" name="numero"/></td>
			</tr>
			-->
		</table>
		<input type="submit"/>
	</form>

</body>
</html>