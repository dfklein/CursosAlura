<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tags:pageTemplate titulo="Produto não encontrado">

    <section id="index-section" class="container middle">
        <h2>O produto informado não foi encontrado - só em ProdutosController</h2>
    </section>
    <!-- 
    	${exception.message} 
    	<c:forEach items="${exception.stackTrace}" var="stk">
    		${stk}
    	</c:forEach>
    
    -->

</tags:pageTemplate>