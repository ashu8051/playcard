<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<h1>
	Add Tutorial
</h1>

<c:url var="addAction" value="/tutorial/add" ></c:url>

<form:form action="${addAction}" commandName="tutorial">
<table  class="showtbl">
<colgroup>
                            <col width="30%" />
                            <col width="70%" />
                        </colgroup>
                         <tbody>
	
	<tr>
		<td>
			<form:label path="tutorialName">
				<spring:message text="TutorialName"/>
			</form:label>
		</td>
		<td>
			<form:input path="tutorialName" cssClass="txtform"/>
		</td> 
	</tr>
	
	</tbody>
	  <tfoot>
	<tr>
	<td><input type="hidden"
			name="${_csrf.parameterName}"
			value="${_csrf.token}" /></td>
	
		<td ><form:button id="submit" name="submit" value="submit" class="btnform"/></td>
	</tr>
	</tfoot>
</table>		
</form:form>