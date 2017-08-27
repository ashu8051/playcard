<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<h1>Tutorial Name</h1>
<c:if test="${!empty tutorialList}">
	<table  class=showtbl1>
	<colgroup>
                             <col width="70%" />
                            <col width="30%" />
                            
                            
                           
                        </colgroup>
                        <thead>
                        <tr><th></th>
                        <th></th>
                        </tr>
                        
                      
                        </thead>
                         <tbody>
                        <!--  <tr></tr>
                         <tr></tr>
                          <tr></tr>
                         <tr></tr> -->
	<c:forEach items="${tutorialList}" var="tutorial">
		<tr>
			<td><h3 ><a href="<c:url value="/topic/topiclist?tutorialId=${tutorial.id}" />" />${tutorial.tutorialName}</h3></td>
		</tr>
		<%-- <c:forEach items="${tutorial.topicList}" var="topic">
		<tr>
			<td><h5><a href="<c:url value="/topic/topiclist?tutorialId=${tutorial.id}" />" />${topic.topic}<h5></td>
		</tr>
		</c:forEach> --%>
	</c:forEach>
	 </tbody>
	  <tfoot>
	  </tfoot>
	  </table>
</c:if>