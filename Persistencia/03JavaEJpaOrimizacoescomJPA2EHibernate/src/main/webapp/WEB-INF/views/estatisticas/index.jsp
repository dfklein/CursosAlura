<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="../template/top.jsp" />
<div class="col-sm-8">
	<div class="panel panel-default">
		<div class="panel-heading">Estatísticas</div>
		<div class="panel-body">
			<div class="container">
				<div class="col-sm-8">
					<a href="<c:url value="/estatisticas/limpar"/>">Limpar</a>
					<table class="table table-striped">
						<thead>
							<tr>
								<th></th>
								<th>Hit</th>
								<!-- (objs encontrado em cache) -->
								<th>Miss</th>
								<!-- (objs não encontrado em cache) -->
								<th>Conexões</th>
								<!-- (número de conexões no pool) -->
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Cache</td>
								<!-- Hit -->
								<td>${statistics.queryCacheHitCount}</td>
								<!-- Miss -->
								<td>${statistics.queryCacheMissCount}</td>
								<! -- Conections -->
								<td>${statistics.connectCount}</td>
							</tr>
						</tbody>
						
					</table>
						Ver mais em: http://blog.caelum.com.br/cacando-seus-gargalos-com-o-hibernate-statistics/<br><br>
						Também em: http://blog.caelum.com.br/os-7-habitos-dos-desenvolvedores-hibernate-e-jpa-altamente-eficazes/<br><br>
						Documentação: http://docs.jboss.org/hibernate/core/4.3/javadocs/org/hibernate/stat/Statistics.html
				</div>
			</div>
		</div>
	</div>
</div>
<c:import url="../template/down.jsp" />
