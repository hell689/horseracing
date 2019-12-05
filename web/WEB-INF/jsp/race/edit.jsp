<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${empty race}"><jsp:useBean id="race" class="domain.Race"/></c:if>

<u:html title="${title}">
	<h2>Редактирование забега ${race.name}:</h2>
	<c:url var="urlRaceList" value="/race/list.html"/>
	<c:url var="urlRaceSave" value="/race/save.html"/>
	<form action="${urlRaceSave}" method="post">
	<c:if test="${not empty race.id}"><input name="id" value="${race.id}" type="hidden"></c:if>
	<label for="name">Название забега</label>
	<input id="name" name="name" value="${race.name}"><br>
	<label for="date">Дата забега</label>
	<input name="date" type="date" value="${startDate}">
	<input name="time" type="time" value="${startTime}"><br>
	<button class="save">Сохранить</button>
		<a class="back" href="${urlRaceList}">Отмена</a>
	</form>
	


</u:html>