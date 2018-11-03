<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- A @ define a declara��o como pertencente � p�gina. -->
<!-- Abaixo o import de classes java para o uso em scriplets  -->
<%@ page import ="java.util.List, br.com.gerenciador.servlet.Empresa"%>
<!-- j� o import seguinte � de uma biblioteca de tags. No caso, o JSTL  -->
<!-- JSTL: Java Standard Tag Library -->
<!-- taglib = define o pacote onde se encontra a biblioteca que voc� est� importando -->
<!-- prefix = define o prefixo a ser utilizado quando for acionada uma tag desta lib -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Lista de empresas (vers�o sem o JSTL): <br />
	<!-- Atente-se para os imports necess�rios para o funcionamento dos scriplets nas primeiras linhas do c�digo desta p�gina!!! -->
	<ul>
		<% 
			List<Empresa> lista = (List<Empresa>) request.getAttribute("empresas");
			for (Empresa empresa : lista) {
		%>
		<li><%= empresa.getNome()  %></li>
		<% } %>
	</ul>
	<hr>
	Lista de empresas (vers�o com JSTL, usando forEach): <br />
	
	<ul>
		<!-- Lembre-se que "items" � o nome que voc� deu para a vari�vel na hora de coloc�-la na requisi��o e n�o o nome dela no backend. -->
		<c:forEach items="${empresas}" var="empresa">
			<!-- Observe que voc� coloca o nome do atributo, mas que s� funciona se ele tiver um getter -->
			
			<li>Nome: ${ empresa.nome } / Data abertura: <fmt:formatDate value="${ empresa.dataAbertura }" pattern="dd/MM/yyyy HH:mm"/></li>
		</c:forEach>
	</ul>
	
	<hr>
	
	Usando um outro tipo de itera��o com o forEach:<br />
	
	<c:forEach var="i" begin="1" end="10" step="2">
       | ${i} |
     </c:forEach>
	<hr>
	
	<a href="/gerenciador/formNovaEmpresa.html">Nova empresa</a>
</body>
</html>