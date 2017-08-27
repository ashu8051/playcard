<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<h3></h3>
<c:if test="${!empty tutorialList}">
	<table  class=showtbl1>
	<colgroup>
                             <col width="30%" />
                            <col width="35%" />
                            <col width="35%" />
                            
                           
                        </colgroup>
                        <thead>
                        
                        <th>Tutorial Name</th>
                        <th><a href="<c:url value="/tutorial/add" />"/>
			Add Tutorial</th>
			<th></th>
                        </thead>
                         <tbody>
	<c:forEach items="${tutorialList}" var="tutorial">
		<tr>
			
			<td><a href="<c:url value="/topic/topiclist?tutorialId=${tutorial.id}" />"/>${tutorial.tutorialName}</td>
			
			<td><a href="<c:url value="/tutorial/update?tutorialId=${tutorial.id}" />"/>
			Update Tutorial</td>
			<td><a href="<c:url value="/topic/add" />"/>
			Add Topic</td>
			
		</tr>
	</c:forEach>
	 </tbody>
	  <tfoot>
	  </tfoot>
	  </table>
</c:if>