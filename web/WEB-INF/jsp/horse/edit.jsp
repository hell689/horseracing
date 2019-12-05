<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty horse}"><jsp:useBean id="horse" class="domain.Horse"/></c:if>

<u:html title="${title}">
	<h2>Редактирование лошади ${horse.name}:</h2>
	<c:url var="urlHorseList" value="/horse/list.html"/>
	<c:url var="urlHorseSave" value="/horse/save.html"/>
	<form action="${urlHorseSave}" method="post">
	<c:if test="${not empty horse.id}"><input name="id" value="${horse.id}" type="hidden"></c:if>
		<label for="name">Имя лошади:</label>
		<input id="name" name="name" value="${horse.name}">
		<button class="save">Сохранить</button>
		<a class="back" href="${urlHorseList}">Отмена</a>
	</form>
	


</u:html>