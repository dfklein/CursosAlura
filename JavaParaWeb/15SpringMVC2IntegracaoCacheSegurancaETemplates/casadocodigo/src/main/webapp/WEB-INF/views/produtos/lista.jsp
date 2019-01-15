<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais - Casa do Código</title>

<!-- 
	Para implementar o bootstrap, lembre-se de que os arquivos de CSS e JS dele são chamados através
	de uma requisição que passa pela servlet do Spring. Sem configurações adicionais ele acha que você
	está tentando invocar alguma JSP com esses nomes.
	
	Veja em AppWebConfiguration.class que você precisou extender a classe WebMvcConfigurerAdapter e
	sobrescrever o método configureDefaultServletHandling(...) 
 -->
 <c:url value="/resources/css" var="cssPath" />
<link rel="stylesheet" href="${ cssPath }/bootstrap.min.css"  />
<link rel="stylesheet" href="${ cssPath }/bootstrap-theme.min.css"  />
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous"> -->
<style type="text/css">
	body {
		padding-top:60px; 
	}
</style>
</head>
<body>
	<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Casa do Código</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${ s:mvcUrl('PC#listar').build() }">Lista de Produtos</a></li>
				<li><a href="${ s:mvcUrl('PC#form').build() }">Cadastro de Produtos</a></li>
				<li class="nav-item"><a href="<c:url value="/logout" />">Sair</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<!-- principal é o nome da variável que referencia o usuário logado. -->
			  <li><a href="#">
			  	<!-- As duas formas vão exibir o nome do usuário: -->
			  	<!-- <security:authentication property="principal.username" /> -->
			  	<security:authentication property="principal" var="usuario" />
			  		${ usuario.username }
			  </li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	</nav>
	<div class="container">
		<h1>Lista de Produtos</h1>
		
		<div>${sucesso }</div>
		<div>${falha }</div>
		
		<table class="table table-bordered table-striped table-hover">
			<tr>
				<th>Título</th>
				<th>Descrição</th>
				<th>Páginas</th>
			</tr>
			<c:forEach items="${produtos }" var="produto">
				<tr>
					<td><a href="${s:mvcUrl('PC#detalhe').arg(0, produto.id).build() }">${produto.titulo }</a> </td>
					<td>${produto.descricao }</td>
					<td>${produto.paginas }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
</body>
</html>