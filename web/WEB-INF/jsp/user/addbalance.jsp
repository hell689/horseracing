<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty user}"><jsp:useBean id="user" class="domain.User"/></c:if>

<u:html title="${title}">	
	<h2>Пополнение счета пользователя ${user.login}:</h2>
	<c:if test="${not empty param.message}">
        <p class="mess">${param.message}</p>
    </c:if>
	<c:url var="urlUserChangeBalance" value="/user/changebalance.html"/>
	<form action="${urlUserChangeBalance}" method="post">
	<c:if test="${not empty user.id}"><input name="id" value="${user.id}" type="hidden"></c:if>
		<label for="addbalance">Сумма пололнения:</label>
		<input id="addbalance" name="addbalance"">
		<button class="save">Пополнить</button>
	</form>
</u:html>