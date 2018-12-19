<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 
		Depois você pode observar no ProdutosController que não é necessário especificar
		no mapeamento se ele está recebendo um método GET ou POST.
	 -->
	<form action="/casadocodigo/produtos" method="POST">
	
		<div>
			<label>Título</label>
			<input type="text" name="titulo">
		</div>
	
		<div>
			<label>Descrição</label>
			<textarea rows="10" cols="20" 
				name="descricao">
			</textarea>
		</div>
	
		<div>
			<label>Paginas</label>
			<input type="text" name="paginas">
		</div>
	
		<button type="submit">Cadastrar</button>
		
	</form>
</body>
</html>