<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<h3></h3>
<c:if test="${!empty contentList}">
	
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
	<c:forEach items="${contentList}" var="topicContent">
		<tr>
			
			<c:if test="${topicContent.contentType == 'H2' }">
			<td><h2>${topicContent.content}</h2></td>
			</c:if>
			
			<c:if test="${topicContent.contentType == 'P' }">
			<td>* ${topicContent.content}</td>
			</c:if>
			
			<c:if test="${topicContent.contentType == 'Program' }">
			<td><p  style="color: black;background-color: silver; ;font-size: 20px;"> ${topicContent.content}</p></td>
			</c:if>
			<td>
			<a href="<c:url value="/topiccontent/contentDelete?contentId=${topicContent.id}&topicId=${topicContent.topic.id}" />"/>
			Delete Content</td>
			<td>
			<a href="<c:url value="/topiccontent/add" />"/>
			Add Content</td>
			<td><a href="<c:url value="/topiccontent/update?contentId=${topicContent.id}" />"/>
			Update Content</td>
			
			
		</tr>
	</c:forEach>
	 </tbody>
	  <tfoot>
	  </tfoot>
	  </table>
</c:if>