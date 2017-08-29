<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<h3><span><a href="<c:url value="/topic/add" />">
			Add Topic<%-- <img src="<c:url value="/static/images/create.png" />" width="20" height="20" alt=""> --%></a></span></h3>
<c:if test="${!empty topicList}">

<table  class=showtbl1>
<colgroup>
                             <col width="70%" />
                            <col width="15%" />
                            <col width="15%" />
                           
                        </colgroup>
                        <thead>
                        <th>Topic List</th>
                        <th></th>
                        </thead>
                         <tbody>
                         <c:forEach items="${topicList}" var="topic">
		<tr>
			
			<%-- <td>
			<a href="<c:url value="/topiccontent/contentlist?topicId=${topic.id}" />"/>
			${topic.topic}</td> --%>
			<td><a href="<c:url value="/subcontent/subcontentlistbyid?topicId=${topic.id}" />"/>
			${topic.topic}</td>
			<td><table><tr>
				<td>
			<a href="<c:url value="/topic/update?topicId=${topic.id}" />"/>
			<img src="<c:url value="/static/images/detail.png" />" width="20" height="20" alt=""></td>
			<td>
			</td>
			</tr></table></td>
		
			
			
		</tr>
	</c:forEach>
                         </tbody>
	  <tfoot>
	  </tfoot>
	  </table>
	
</c:if>