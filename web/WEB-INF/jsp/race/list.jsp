<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<u:html title="${title}">
	<h2>Список забегов:</h2>
	<table>
	<tr>
	<th>Название забега</th>
	<th>Дата забега</th>
	<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
	<th></th>
	</c:if>
	</tr>
	<c:forEach var="race" items="${races}">
		<tr>
			<td>
			<c:url var="urlRaceRunnerList" value="/runner/list.html">
        			<c:param name="raceId" value="${race.id}"/></c:url>     		
			<a href="${urlRaceRunnerList}" class="urlList">${race.name}</a>
			</td>
			<td><fmt:formatDate pattern="dd.MM.yyyy, HH:mm" value="${race.date}"/></td>
			<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
			<td>
				<c:url var="urlRaceEdit" value="/race/edit.html">
        			<c:param name="id" value="${race.id}"/>
        		</c:url>
        		<a href="${urlRaceEdit}" class="edit">редактировать</a><br>
        	</td>
        	</c:if>
		</tr>
	</c:forEach>
	</table>
	<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
		<c:url var="addUrl" value="/race/edit.html"/>
		<FORM action="${addUrl}">
			<BUTTON class="addButton" type="submit">Добавить забег</BUTTON>	
		</FORM>
	</c:if>


</u:html>