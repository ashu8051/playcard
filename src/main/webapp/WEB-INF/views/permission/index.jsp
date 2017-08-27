<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<h3>Persons List</h3>
<c:if test="${!empty listPermissions}">
	<table class="tg">
	<tr>
		<th width="80">permission ID</th>
		<th width="120">permission Name</th>
		
		<!-- <th width="60">Edit</th>
		<th width="60">Delete</th> -->
	</tr>
	<c:forEach items="${listPermissions}" var="permission">
		<tr>
			<td>${permission.id}</td>
			<td>${permission.name}</td>
			
			
		</tr>
	</c:forEach>
	</table>
</c:if>