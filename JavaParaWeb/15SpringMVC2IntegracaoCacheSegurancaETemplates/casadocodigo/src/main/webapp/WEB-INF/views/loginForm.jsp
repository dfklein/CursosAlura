<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

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
<%--  <c:url value="/resources/css" var="cssPath" /> --%>
<!-- Você comentou a sua referência para o bootstrap porque ele deu um erro esquisito e decidiu pegar via CDN -->
<%-- <link rel="stylesheet" href="${ cssPath }/bootstrap.min.css"  /> --%>
<%-- <link rel="stylesheet" href="${ cssPath }/bootstrap-theme.min.css"  /> --%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
<style type="text/css">
	body {
		padding-top:60px 0px; 
	}
</style>
</head>
<body>
	<!-- 
		Lembre-se que para utilizar a sua página personalizada de login você precisa ajustar a sua
		classe de configurações de segurança (SecurityConfiguration.class)
	 -->
	<div class="container">
		<h1>Tela de login:</h1>
		
		<form:form 
			servletRelativeAction="/login" 
			method="POST" >
			<div class="form-group">
				<label>E-mail</label>
				<input name="username" type="text" class="form-control"/>
				<form:errors path="titulo" />
			</div>
			<div class="form-group">
		        <label>Senha</label>
				<input name="password" type="password" class="form-control"/>
		        <form:errors path="descricao" />
			</div>
			<button type="submit" class="btn btn-primary" style="margin-bottom: 30px;">Logar</button>
		</form:form>
	</div>
</body>
</html>