<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkEntradaServlet"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form action="${ linkEntradaServlet }" method="post">
	
		login: <input type="text" name="login"  /><br />
		Senha: <input type="password" name="senha"  /><br />
	
		<input type="submit" />
		<input type="hidden" name="acao" value="Login" />
	</form>

</body>
</html>