<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<h3>Role List</h3>
<c:if test="${!empty listroles}">
	<table class="tg">
	<tr>
		<th width="80">Role ID</th>
		<th width="120">Role Name</th>
		
		<!-- <th width="60">Edit</th>
		<th width="60">Delete</th> -->
	</tr>
	<c:forEach items="${listroles}" var="role">
		<tr>
			<td>${role.id}</td>
			<td>${role.roleName}</td>
			
			
		</tr>
	</c:forEach>
	</table>
</c:if>