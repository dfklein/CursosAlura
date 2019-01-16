<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="bodyClass" required="false"%>
<%@ attribute name="titulo" required="true" %>
<!-- O atributo fragment indica que você tem uma parte de código que pode ser incluída a este template -->
<!-- É utilizado aqui para a inclusão de scripts de JS que não são gerais de todo o código, mas específico de cada página. -->
<!-- (Porque se fosse geral era só você colocar o script aqui) -->
<!-- Para incluir algum código neste trecho você utiliza a tag jsp:attribute e insere nele o código que você deseja. -->
<!-- Lembre-se de que para isso funcionar a sua página precisa estar envolta na tag jsp:body -->
<!-- Ver a implementação em itens.jsp -->
<%@ attribute name="extraScripts" required="false" fragment="true" %>

<!-- 
	Este é um tag criado com o template da aplicação.
	Lembre-se de que os templates do JSP devem ser criados em {...}/WEB-INF/tags nesta aplicação.
	Para utilizar o template você deve incluir no seu JSP a declaração deste caminho:
	<%-- <%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %> --%>
 -->
<!DOCTYPE html>
<html>
<head>
<c:url value="/" var="contextPath" />
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="icon" href="//cdn.shopify.com/s/files/1/0155/7645/t/177/assets/favicon.ico?11981592617154272979" type="image/ico" />
<link href="https://plus.googlecom/108540024862647200608" rel="publisher" />

<title>${ titulo } - Casa do Código</title>

<link href="${contextPath}resources/css/cssbase-min.css" rel="stylesheet" type="text/css" media="all" />
<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet' />
<link href="${contextPath}resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="${contextPath}resources/css/fontello-ie7.css" rel="stylesheet" type="text/css" media="all" />
<link href="${contextPath}resources/css/fontello-embedded.css" rel="stylesheet" type="text/css" media="all" />
<link href="${contextPath}resources/css/fontello.css" rel="stylesheet" type="text/css" media="all" />
<link href="${contextPath}resources/css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${contextPath}resources/css/layout-colors.css" rel="stylesheet" type="text/css" media="all" />
<link href="${contextPath}resources/css/responsive-style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${contextPath}resources/css/guia-do-programador-style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${contextPath}resources/css/produtos.css" rel="stylesheet" type="text/css" media="all" />


<link rel="canonical" href="http://www.casadocodigo.com.br/" />
<link href="${contextPath}resources/css/book-collection.css" rel="stylesheet" type="text/css" media="all" />


<link rel="canonical" href="http://www.casadocodigo.com.br/" />
</head>

<body class="${ bodyClass }">
	<%@include file="/WEB-INF/views/cabecalho.jsp"%>
	<!-- Esta é a tag que indica onde será inserido o conteúdo desejado -->
	<jsp:doBody />
	
	<%@include file="/WEB-INF/views/rodape.jsp"%>
	
	<!-- Para a inclusão de fragmentos - código de JS no caso. -->
	<!-- Lembre-se de que para isso funcionar a sua página precisa estar envolta na tag jsp:body (ver itens.jsp) -->
	<jsp:invoke fragment="extraScripts"></jsp:invoke>
	<script>
		// Qualquer script que você queira adicionar e que é comum a todas as páginas da aplicação.
	</script>
</body>
</html>
