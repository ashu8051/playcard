<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>




<h3></h3>
<c:if test="${!empty subContentList}">
	
	<table  class=showtbl1>
	<colgroup>
                             <col width="70%" />
                            <col width="10%" />
                             <col width="10%" />
                             <col width="10%" />
                           
                        </colgroup>
                        <thead>
                        
                        
                         <th>${topicName}</th>
                         <th></th>
                        
                        </thead>
                         <tbody>
	<c:forEach items="${subContentList}" var="subContent">
		<tr>
			
		
			<td><a href="<c:url value="/topiccontent/contentlist?subContentId=${subContent.id}" />"/>${subContent.subContent}</td>
			
			
			
			
			
			<%-- <td><a href="<c:url value="/topiccontent/contentDelete?contentId=${topicContent.id}&topicId=${topicContent.topic.id}" />"/>
			Delete</td> --%>
			<td>
			<a href="<c:url value="/topiccontent/add" />"/>
			Add Content</td>
			<td>
			<a href="<c:url value="/subcontent/update?subContentId=${subContent.id}" />"/>
			Update SubContent</td>
			
			
		</tr>
	</c:forEach>
	 </tbody>
	  <tfoot>
	  </tfoot>
	  </table>
</c:if>