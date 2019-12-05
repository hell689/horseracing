<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<u:html title="${title}">
	<h2>Список пользователей:</h2>
	<table>
	<tr>
		<th>Логин</th>
		<th>Имя</th>
		<th>Фамилия</th>
		<th>E-mail</th>
		<th>Роль</th>
	</tr>
	<c:forEach var="user" items="${users}">
		<tr>
			<td>${user.login}</td>
			<td>${user.name}</td>
			<td>${user.surname}</td>
			<td>${user.email}</td>
			<td>${user.role.name}</td>
		</tr>
	</c:forEach>
	</table>
	<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
		<c:url var="addUrl" value="/user/edit.html"/>
		<FORM action="${addUrl}">
			<BUTTON class="addButton" type="submit">Добавить пользователя</BUTTON>	
		</FORM>
	</c:if>
</u:html>