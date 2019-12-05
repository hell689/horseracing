<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<u:html title="${title}">
	<h2>Список лошадей:</h2>

	<c:forEach var="horse" items="${horses}">
	<c:url var="horseImgUrl" value="/img/horse.png"/>
		<img src=${horseImgUrl} > ${horse.name}
		<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
		<c:url var="urlHorseEdit" value="/horse/edit.html">
        	<c:param name="id" value="${horse.id}"/>
        </c:url>
        <a href="${urlHorseEdit}" class="edit">редактировать</a>
        </c:if>
        <br>

	</c:forEach>
	<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
		<c:url var="editUrl" value="/horse/edit.html"/>
		<FORM action="${editUrl}"><BUTTON type="submit">Добавить лошадь</BUTTON></FORM>
	</c:if>


</u:html>