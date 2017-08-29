<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<h3><span><a href="<c:url value="/subcontent/add" />">
			Add SubContent<%-- <img src="<c:url value="/static/images/create.png" />" width="20" height="20" alt=""> --%></a></span></h3>
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
			<table><tr>
				<td>
			<a href="<c:url value="/subcontent/update?subContentId=${subContent.id}" />"/>
			<img src="<c:url value="/static/images/detail.png" />" width="20" height="20" alt=""></td>
			<td>
			</td>
			</tr></table>
			</td>
		</tr>
	</c:forEach>
	 </tbody>
	  <tfoot>
	  </tfoot>
	  </table>
</c:if>