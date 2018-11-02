<!-- O trecho compreendido abaixo � o que se chama scriplet. -->
<!-- � o que te permite usar um c�digo Java diretamente em uma p�gina. -->
<!-- � o equivalente a utilizar a tag <script> para escrever JavaScript em uma p�gina html normal -->
<!-- O c�digo � executado no servidor ao carregar a p�gina, como se fosse o main de uma classe. -->
<%
	String nomeEmpresaScriplet = "Empresa Teste";
	System.out.println(nomeEmpresaScriplet);

	String nomeEmpresaServelet = (String) request.getAttribute("empresa"); // "empresa" � o alias do objeto que foi setado no servelet.
%>
<html>
	<body>
		<h1>Exemplo de Java Server Page</h1>
		
		<!-- Quando voc� faz um JSP com scriplet voc� possui uma vari�vel "out" dispon�vel que � a sa�da do System.out do backend. -->
		<h2>O nome da empresa declarado no scriplet usando "out.println" �: <% out.println(nomeEmpresaScriplet); %></h2>
		
		<!-- voc� tamb�m pode usar o println de out com um atalho -->
		<h2>O nome da empresa declarado no scriplet usando o atalho �: <%= nomeEmpresaScriplet %></h2>
		
		
		<h2>O nome da empresa declarado no servlet NovaEmpresaServelet.java �: <%= nomeEmpresaServelet %></h2>
		
		<a href="/gerenciador/listaEmpresas">Listar empresas</a>
		
	</body>
</html>