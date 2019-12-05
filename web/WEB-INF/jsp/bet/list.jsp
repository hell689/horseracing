<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<u:html title="${title}">
	<h2>Список ставок:</h2>
	<table>
	<tr>
	<th>Клиент</th>
	<th>Забег</th>
	<th>Лошадь</th>
	<th>Вид ставки</th>
	<th>Коэффициент</th>
	<th>Ставка</th>
	<th>Возможный выигрыш</th>
	<th>Результат</th>
	</tr>
	<c:forEach var="bet" items="${bets}">
		<tr>
			<td>${bet.user.login}</td>
			<td>${bet.runner.race.name}</td>
			<td>${bet.runner.horse.name}</td>
			<td>${bet.betType}</td>
			<td>${bet.finalRate}</td>
			<td>${bet.amount}</td>
			<td><fmt:formatNumber value="${bet.finalRate * bet.amount}" type="number" pattern="#.##" maxFractionDigits="2" minFractionDigits="2"/></td>
			<td>
			<c:if test="${bet.runner.race.fixResult}">
				<c:choose>
					<c:when test="${bet.win}">
						<c:url var="winImgUrl" value="/img/win.png"/>
						<img alt="win" src="${winImgUrl}">
					</c:when>
					<c:otherwise>
						<c:url var="lossImgUrl" value="/img/loss.png"/>
						<img alt="loss" src="${lossImgUrl}">
					</c:otherwise>
				</c:choose>
			</c:if>
			</td>

		</tr>
	</c:forEach>
	</table>	
</u:html>