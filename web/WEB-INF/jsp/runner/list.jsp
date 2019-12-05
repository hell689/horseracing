<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<u:html title="${title}">
	<h2>Список участников забега:</h2>
	<div id='rateMessage' style='display: none;'>
	</div>
	
	<c:if test="${not empty param.message}">
        <p class="error">${param.message}</p>
    </c:if>
    <c:if test="${not empty closed}">
    	<p class="mess">(Прием ставок завершен<c:if test="${fixed}">, результаты зафиксированы</c:if>)</p>
    </c:if>
	<table>
	<tr>
	<th>Забег</th>
	<th>Лошадь</th>
	<th>Базовый коэффициент</th>
	<c:if test="${not empty closed}">
	<th>Место</th>
	</c:if>
		<c:if test="${currentUser.role == 'CLIENT'}">
		<c:if test="${not empty param.raceId}">
			<th>Ставка</th>
			<th>Вид ставки</th>
		</c:if>
		<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
			<th></th>
			</c:if>
		</c:if>
	</tr>
	<c:forEach var="runner" items="${runners}">
		<tr>
			<td>${runner.race.name}</td>
			<td>${runner.horse.name}</td>
			<td>
			<c:choose>
			<c:when test="${currentUser.role == 'BOOKMAKER' && empty closed}">
				<c:url var="urlRunnerEdit" value="/runner/edit.html">
        			<c:param name="id" value="${runner.id}"/>
        			<c:param name="raceId" value="${runner.race.id}"/>
        			<c:param name="horseId" value="${runner.horse.id}"/>
        			<c:param name="rate" value="${runner.rate}"/>
        		</c:url>
        		<a href="${urlRunnerEdit}" class="edit">${runner.rate}</a><br>
			</c:when>
			<c:otherwise>${runner.rate}
			</c:otherwise>
			</c:choose></td>
			<c:if test="${not empty closed}">
			<td>
			<c:choose>
			<c:when test="${currentUser.role == 'ADMINISTRATOR'}">
				<c:url var="urlRunnerEdit" value="/runner/edit.html">
        			<c:param name="id" value="${runner.id}"/>
        			<c:param name="raceId" value="${runner.race.id}"/>
        			<c:param name="horseId" value="${runner.horse.id}"/>
        			<c:param name="rate" value="${runner.rate}"/>
        		</c:url>
        		<a href="${urlRunnerEdit}" class="edit">${runner.place}</a><br>
			</c:when>
			<c:otherwise>
				${runner.place}
			</c:otherwise>
			</c:choose>
			</td>
			</c:if>
			<c:if test="${currentUser.role == 'CLIENT'}">
			<c:if test="${not empty param.raceId}">
				<c:url var="urlSaveBet" value="/bet/save.html"/>
        		<form action="${urlSaveBet}" method="post">
				<td>
					<input id="amount" name="amount" size="5" value="">
				</td>
		 		<td>
					<select name="betType" onChange="Selected(this)">
					<c:forEach var="betType" items="${betTypes}">
						<option value=${betType.id}>${betType.name}</option>
					</c:forEach>			
					</select>
					<input name="runnerId" value="${runner.id}" type="hidden">
					<input name="raceId" value="${param.raceId}" type="hidden">
					<input name="userId" value="${currentUser.id}" type="hidden">
        		</td>
				<td>
					<BUTTON type="submit" ${closed}>Сделать ставку</BUTTON>
				</td>
				
        	</form>
        	</c:if>
        	</c:if>
        	<c:if test="${currentUser.role == 'ADMINISTRATOR'}">
        		<td>
        		<c:url var="runnerDeleteUrl" value="/runner/delete.html"/>
        			<form action="${runnerDeleteUrl}" method="post">
        				<c:if test="${not empty runner.id}"><input name="deleteId" value="${runner.id}" type="hidden"></c:if>
        				<c:if test="${not empty param.raceId}"><input name="raceId" value="${param.raceId}" type="hidden"></c:if>
        				<button class="delete" ${closed}>Удалить</button>
        			</form>
        		</td>
        	</c:if>
		</tr>
	</c:forEach>
	</table>
	<c:if test="${currentUser.role == 'ADMINISTRATOR' && not empty horses && not fixed}">
	<c:url var="addUrl" value="/runner/save.html"/>
	<FORM action="${addUrl}" method="post">
	<c:if test="${not empty param.raceId}">
		<input name="raceId" value="${param.raceId}" type="hidden">
		<input name="rate" value="1.5" type="hidden">
	</c:if>
		<select name="horseId">
			<c:forEach var="horse" items="${horses}">
				<option value=${horse.id}>${horse.name}</option>
			</c:forEach>			
		</select>			
			<BUTTON type="submit" ${closed}>Добавить участника</BUTTON>	
	</FORM>
	</c:if>
	<c:if test="${currentUser.role == 'ADMINISTRATOR' && not empty closed && not fixed}">
		<c:url var="fixedUrl" value="/runner/fixed.html"/>
		<FORM action="${fixedUrl}" method="post">
		<input name="raceId" value="${param.raceId}" type="hidden">
		<BUTTON type="submit">Зафиксировать результаты забега</BUTTON>	
	</c:if>
</u:html>