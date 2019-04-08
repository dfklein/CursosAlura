<!-- O trecho compreendido abaixo � o que se chama scriplet. -->
<!-- � o que te permite usar um c�digo Java diretamente em uma p�gina. -->
<!-- � o equivalente a utilizar a tag <script> para escrever JavaScript em uma p�gina html normal -->
<!-- O c�digo � executado no servidor ao carregar a p�gina, como se fosse o main de uma classe. -->
<%
	String nomeEmpresaScriplet = "Empresa Teste";
	System.out.println(nomeEmpresaScriplet);

	String nomeEmpresaServelet = (String) request.getAttribute("empresa"); // "empresa" � o alias do objeto que foi setado no servelet.
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- JSTL: Java Standard Tag Library -->
<!-- taglib = define o pacote onde se encontra a biblioteca que voc� est� importando -->
<!-- prefix = define o prefixo a ser utilizado quando for acionada uma tag desta lib -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<body>
		<h1>Exemplo de Java Server Page</h1>
		
		<!-- Quando voc� faz um JSP com scriplet voc� possui uma vari�vel "out" dispon�vel que � a sa�da do System.out do backend. -->
		<h2>O nome da empresa declarado no scriplet usando "out.println" �: <% out.println(nomeEmpresaScriplet); %></h2>
		
		<!-- voc� tamb�m pode usar o println de out com um atalho -->
		<h2>O nome da empresa declarado no scriplet usando o atalho �: <%= nomeEmpresaScriplet %></h2>
		
		
		<h2>O nome da empresa declarado no servlet NovaEmpresaServelet.java �: <%= nomeEmpresaServelet %></h2>
		
		
		<hr>
		
		<h1>Expression Langage</h1>
		
		<!-- EXPRESSION LANGUAGE -->
		<!-- Pode ser usada para realizar opera��es -->
		<h2>3 + 3 � ${3 + 3}</h2>
		
		<!-- Mas o mais importante � que ela pode buscar na requisi��o o valor de um atributo pelo nome que voc� deu a ele na hora de coloc�-lo na requisi��o: -->
		<!-- ATEN��O: � o nome que voc� deu para o atributo na requisi��o, n�o o nome da vari�vel no servidor -->
		<h2>A Empresa se chama ${empresa}</h2>
		
		<h2>Nome da empresa renderizado apenas se o valor n�o estiver vazio (ou seja, se a p�gina n�o for chamada diretamente):
			<c:if test="${not empty empresa}">
				${ empresa }
			</c:if>
		</h2>
		
		<hr>
		
		<a href="/gerenciador/listaEmpresas">Listar empresas</a>
	</body>
</html>