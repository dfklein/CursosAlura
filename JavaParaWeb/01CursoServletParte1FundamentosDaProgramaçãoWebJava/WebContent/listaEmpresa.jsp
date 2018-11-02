<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.List, br.com.gerenciador.servlet.Empresa"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Lista de empresas: <br />
	<!-- Atente-se para os imports necessários para o funcionamento dos scriplets nas primeiras linhas do código desta página!!! -->
	<ul>
		<% 
			List<Empresa> lista = (List<Empresa>) request.getAttribute("empresas");
			for (Empresa empresa : lista) {
		%>
		<li><%= empresa.getNome()  %></li>
		<% } %>
	</ul>
	<a href="/gerenciador/formNovaEmpresa.html">Nova empresa</a>
</body>
</html>