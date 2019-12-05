<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<u:html title="Авторизация">
    <h2>Авторизация</h2>
    <c:if test="${not empty param.message}">
        <p class="error">${param.message}</p>
    </c:if>
    <c:url var="urlLogin" value="/login.html"/>
    <form action="${urlLogin}" method="post">
        <label for="login">Логин:</label>
        <input id="login" name="login">
        <label for="password">Пароль:</label>
        <input id="password" name="password" type="password">
        <button class="login">Войти</button>
        <c:url var="urlRegister" value="/registration.html"/>
        <p><a href="${urlRegister}">Регистрация</a></p>
    </form>
</u:html>