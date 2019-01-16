<!-- Quando voc� utiliza uma p�gina em um template (exceto pelos JSPs que comp�em o corpo do template) voc� deve omitir a diretiva inicial -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>


    <header id="layout-header">
		<div class="clearfix container">
			<a href="/" id="logo"> </a>
			<div id="header-content">
				<nav id="main-nav">

					<ul class="clearfix">
						<!-- Esta tag far� com que o conte�do s� seja exibido se o Spring Security retornar que a conex�o est� autenticada -->
						<!-- Em lista.jsp voc� usou a tag que mostra o usu�rio logado. -->
						<!-- Duas formas poss�veis: -->
						<!-- <security:authorize access="isAuthenticated()" /> -->
						<security:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="${s:mvcUrl('PC#listar').build() }" rel="nofollow">Listagem de Produtos</a></li>
	    					<li><a href="${s:mvcUrl('PC#form').build() }" rel="nofollow">Cadastro de Produtos</a></li>
    					</security:authorize>
						<li>
							<a href="${s:mvcUrl('CCC#itens').build() }" rel="nofollow">
								<!-- Existem duas formas de utilizar o bundle. Uma � com a tag fmt -->
								<!-- 
								<fmt:message key="menu.carrinho" >
									<fmt:param value="${carrinhoCompras.quantidade }"></fmt:param>
								</fmt:message>
								-->
								<!-- A outra � utilizando uma tag que � pr�pria do Spring: -->
								<s:message code="menu.carrinho" arguments="${carrinhoCompras.quantidade}" />
							</a>
						</li>
						<li><a href="/pages/sobre-a-casa-do-codigo" rel="nofollow"><fmt:message key="menu.sobre"></fmt:message></a></li>
						<!-- 
							Para fazer a altera��o de idioma na aplica��o como nesses �tens do menu � necess�rio
							que voc� tenha um interceptor que entenda que o Spring precisa captar o Locale da requisi��o
							quando ela � alterada desta forma.
							
							Ver em AppWebConfiguration o m�todo addInterceptors  
						-->
						<li>
						    <a href="?locale=pt" rel="nofollow">
						        <fmt:message key="menu.pt"/>
						    </a>
						</li>
						
						<li>
						    <a href="?locale=en_US" rel="nofollow">
						        <fmt:message key="menu.en"/>
						    </a>
						</li>
<!-- 						<li><a href="/pages/perguntas-frequentes" rel="nofollow">Perguntas Frequentes</a></li> -->
					</ul>
				</nav>
			</div>
		</div>
	</header>
	<nav class="categories-nav">
		<ul class="container">
			<li class="category"><a href="http://www.casadocodigo.com.br"><fmt:message key="navegacao.categoria.home"></fmt:message></a></li>
			<li class="category"><a href="/collections/livros-de-agile"><fmt:message key="navegacao.categoria.agile"></fmt:message></a></li>
			<li class="category"><a href="/collections/livros-de-front-end"><fmt:message key="navegacao.categoria.front_end"></fmt:message></a></li>
			<li class="category"><a href="/collections/livros-de-games"><fmt:message key="navegacao.categoria.games"></fmt:message></a></li>
			<li class="category"><a href="/collections/livros-de-java"><fmt:message key="navegacao.categoria.java"></fmt:message></a></li>
			<li class="category"><a href="/collections/livros-de-mobile"><fmt:message key="navegacao.categoria.mobile"></fmt:message></a></li>
			<li class="category"><a href="/collections/livros-desenvolvimento-web"><fmt:message key="navegacao.categoria.dev_web"></fmt:message></a></li>
			<li class="category"><a href="/collections/outros"><fmt:message key="navegacao.categoria.outros"></fmt:message></a></li>
		</ul>
	</nav>