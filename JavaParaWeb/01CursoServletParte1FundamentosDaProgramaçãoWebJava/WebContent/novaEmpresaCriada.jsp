<!-- O trecho compreendido abaixo é o que se chama scriplet. -->
<!-- É o que te permite usar um código Java diretamente em uma página. -->
<!-- É o equivalente a utilizar a tag <script> para escrever JavaScript em uma página html normal -->
<!-- O código é executado no servidor ao carregar a página, como se fosse o main de uma classe. -->
<%
	String nomeEmpresaScriplet = "Empresa Teste";
	System.out.println(nomeEmpresaScriplet);

	String nomeEmpresaServelet = (String) request.getAttribute("empresa"); // "empresa" é o alias do objeto que foi setado no servelet.
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- JSTL: Java Standard Tag Library -->
<!-- taglib = define o pacote onde se encontra a biblioteca que você está importando -->
<!-- prefix = define o prefixo a ser utilizado quando for acionada uma tag desta lib -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<body>
		<h1>Exemplo de Java Server Page</h1>
		
		<!-- Quando você faz um JSP com scriplet você possui uma variável "out" disponível que é a saída do System.out do backend. -->
		<h2>O nome da empresa declarado no scriplet usando "out.println" é: <% out.println(nomeEmpresaScriplet); %></h2>
		
		<!-- você também pode usar o println de out com um atalho -->
		<h2>O nome da empresa declarado no scriplet usando o atalho é: <%= nomeEmpresaScriplet %></h2>
		
		
		<h2>O nome da empresa declarado no servlet NovaEmpresaServelet.java é: <%= nomeEmpresaServelet %></h2>
		
		
		<hr>
		
		<h1>Expression Langage</h1>
		
		<!-- EXPRESSION LANGUAGE -->
		<!-- Pode ser usada para realizar operações -->
		<h2>3 + 3 é ${3 + 3}</h2>
		
		<!-- Mas o mais importante é que ela pode buscar na requisição o valor de um atributo pelo nome que você deu a ele na hora de colocá-lo na requisição: -->
		<!-- ATENÇÃO: é o nome que você deu para o atributo na requisição, não o nome da variável no servidor -->
		<h2>A Empresa se chama ${empresa}</h2>
		
		<h2>Nome da empresa renderizado apenas se o valor não estiver vazio (ou seja, se a página não for chamada diretamente):
			<c:if test="${not empty empresa}">
				${ empresa }
			</c:if>
		</h2>
		
		<hr>
		
		<a href="/gerenciador/listaEmpresas">Listar empresas</a>
	</body>
</html>