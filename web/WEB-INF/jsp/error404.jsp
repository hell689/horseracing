<%@page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>

		<c:set var="message" value="Страница не найдена"/>

<c:url var="cssUrl" value="/css/error.css"/>
<u:html title="${message}" >
	<DIV class="block">
		<H2 class="error">${message}</H2>
		<c:url var="indexUrl" value="/index.html"/>
		<FORM action="${indexUrl}"><BUTTON type="submit">Перейти на главную страницу</BUTTON></FORM>
	</DIV>
</u:html>