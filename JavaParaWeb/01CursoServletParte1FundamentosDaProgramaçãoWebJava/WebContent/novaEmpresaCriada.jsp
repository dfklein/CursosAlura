<!-- O trecho compreendido abaixo é o que se chama scriplet. -->
<!-- É o que te permite usar um código Java diretamente em uma página. -->
<!-- É o equivalente a utilizar a tag <script> para escrever JavaScript em uma página html normal -->
<!-- O código é executado no servidor ao carregar a página, como se fosse o main de uma classe. -->
<%
	String nomeEmpresaScriplet = "Empresa Teste";
	System.out.println(nomeEmpresaScriplet);

	String nomeEmpresaServelet = (String) request.getAttribute("empresa"); // "empresa" é o alias do objeto que foi setado no servelet.
%>
<html>
	<body>
		<h1>Exemplo de Java Server Page</h1>
		
		<!-- Quando você faz um JSP com scriplet você possui uma variável "out" disponível que é a saída do System.out do backend. -->
		<h2>O nome da empresa declarado no scriplet usando "out.println" é: <% out.println(nomeEmpresaScriplet); %></h2>
		
		<!-- você também pode usar o println de out com um atalho -->
		<h2>O nome da empresa declarado no scriplet usando o atalho é: <%= nomeEmpresaScriplet %></h2>
		
		
		<h2>O nome da empresa declarado no servlet NovaEmpresaServelet.java é: <%= nomeEmpresaServelet %></h2>
		
		<a href="/gerenciador/listaEmpresas">Listar empresas</a>
		
	</body>
</html>