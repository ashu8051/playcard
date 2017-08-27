<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<h1>
	Add a Person
</h1>

<c:url var="addAction" value="/user/add" ></c:url>

<form:form action="${addAction}" commandName="user">
<table>
	<c:if test="${!empty user.userName}">
	<tr>
		<td>
			<form:label path="id">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="id" readonly="true" size="8"  disabled="true" />
			<form:hidden path="id" />
		</td> 
	</tr>
	</c:if>
	<tr>
		<td>
			<form:label path="userName">
				<spring:message text="userName"/>
			</form:label>
		</td>
		<td>
			<form:input path="userName" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="passcode">
				<spring:message text="passcode"/>
			</form:label>
		</td>
		<td>
			<form:input path="passcode" />
		</td>
	</tr>
	<tr>
	<td><input type="hidden"
			name="${_csrf.parameterName}"
			value="${_csrf.token}" /></td>
	
		<td><form:button id="submit" name="submit" value="submit"/></td>
	</tr>
</table>	
</form:form>