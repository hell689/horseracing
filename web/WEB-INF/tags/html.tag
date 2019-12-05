<%@tag language="java" pageEncoding="UTF-8"%>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Система "Скачки"</title>
        <c:url var="urlCss" value="/css/style.css"/>
        <link href="${urlCss}" rel="stylesheet">
        <c:url var="mainJsUrl" value="/js/main.js"/>
		<SCRIPT type="text/javascript" src="${mainJsUrl}"></SCRIPT>
    </head>
    <body>
    <div id="container">
    <div id="header">
        <h1><fmt:message key="application.title"/></h1>
    </div>
        <c:if test="${not empty currentUser}">
        <div id="userinfo">
           <c:url var="urlLogout" value="/logout.html"/>
           <p>
           Добро пожаловать, ${currentUser.login}
           <c:choose>
            <c:when test="${currentUser.role == 'CLIENT'}">
            <c:url var="urlAddBalance" value="/user/addbalance.html"/>
             (${currentUser.balance}$)
        	 <a class="addbalance" href="${urlAddBalance}">Пополнить баланс</a>
            </c:when>
            <c:otherwise>
             (${currentUser.role.name}).
            </c:otherwise>
           </c:choose>
           <a href="${urlLogout}">Выйти</a>.
            </p>
        </div>
        </c:if> 
        <div class="menu">
        <c:url var="horseListUrl" value="/horse/list.html"/>
        <c:url var="raceListUrl" value="/race/list.html"/>
        <c:url var="runnerListUrl" value="/runner/list.html"/>
        <c:url var="betListUrl" value="/bet/list.html"/>
        <c:url var="userListUrl" value="/user/list.html"/>
        <c:if test="${empty currentUser}">
        	<c:url var="loginUrl" value="/login.html"/>
        	<li><a href="${loginUrl}">Войти</a></li>
        </c:if> 
        	<li><a href="${horseListUrl}">Лошади</a></li>
        	<li><a href="${raceListUrl}">Забеги</a></li>
        	<li><a href="${betListUrl}">Список ставок</a></li>
        	<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
        		<li><a href="${userListUrl}">Список пользователей</a></li>
        	</c:if>
        </div>
        <div id="content">
            <jsp:doBody/>
        </div>
        </div>
    </body>
</html>