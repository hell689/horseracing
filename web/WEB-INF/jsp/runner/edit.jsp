<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${empty runner}"><jsp:useBean id="runner" class="domain.Runner"/></c:if>

<u:html title="${title}">
	<c:choose>
		<c:when test="${currentUser.role == 'BOOKMAKER' }">
			<h2>Редактирование коэффициента участника ${runner.horse.name} забега ${runner.race.name}:</h2>
			<c:url var="urlRunnerList" value="/runner/list.html">
				<c:param name="raceId" value="${runner.race.id}"></c:param>
			</c:url>
			<c:url var="urlRunnerSave" value="/runner/save.html"/>
			<form action="${urlRunnerSave}" method="post">
			<c:if test="${not empty runner.id}">
				<input name="id" value="${runner.id}" type="hidden">
				<input name="horseId" value="${runner.horse.id}" type="hidden">
				<input name="raceId" value="${runner.race.id}" type="hidden">
			</c:if>
			<label for="rate">Коэффициент: </label>
			<input id="rate" name="rate" value="${runner.rate}"><br>
			<button class="save">Сохранить</button>
				<a class="back" href="${urlRunnerList}">Отмена</a>
			</form>
		</c:when>
		<c:when test="${currentUser.role == 'ADMINISTRATOR' }">
			<h2>Фининшное место участника ${runner.horse.name} забега ${runner.race.name}:</h2>
			<c:url var="urlRunnerList" value="/runner/list.html">
				<c:param name="raceId" value="${runner.race.id}"></c:param>
			</c:url>
			<c:url var="urlRunnerSave" value="/runner/save.html"/>
			<form action="${urlRunnerSave}" method="post">
			<c:if test="${not empty runner.id}">
				<input name="id" value="${runner.id}" type="hidden">
				<input name="horseId" value="${runner.horse.id}" type="hidden">
				<input name="raceId" value="${runner.race.id}" type="hidden">
				<input name="rate" value="${runner.rate}" type="hidden">
			</c:if>
			<label for="place">Место: </label>
			<div>
			<select name="place">
			<option></option>
			<c:forEach var="place" items="${places}">
				<option value=${place}>${place}</option>
			</c:forEach>			
			</select>
			</div>
			<!--<input id="place" name="place" value="${runner.place}">--><br>
			<button class="save">Сохранить</button>
				<a class="back" href="${urlRunnerList}">Отмена</a>
			</form>
		</c:when>
	</c:choose>


</u:html>