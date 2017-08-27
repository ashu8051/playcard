<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<script  type="text/javascript">

<!--
$(document).ready(function(e) { 
	
	$('#topic').submit(function() {
		
		 var tutorialId = $('#tutorialId').val();
		 
	        if($.trim(tutorialId).length <= 0) {
	        	
	            $('#tutorialId').css('background', 'yellow');
	            $('#tutorialId').focus();
	            return false;
	        } else {
	            $('#tutorialId').css('background', 'white');
	        }
	        
	        var topic = $('#topicId').val();
	        if($.trim(topic).length <= 0) {
	        	
	            $('#topic').css('background', 'yellow');
	            $('#topic').focus();
	            return false;
	        } else {
	            $('#topic').css('background', 'white');
	        }
	});


});
//-->
</script>


<h1>
	Add a Topic
</h1>

<c:url var="addAction" value="/topic/add" ></c:url>

<form:form action="${addAction}" modelAttribute="topic">
<table  class="showtbl">
<colgroup>
                            <col width="30%" />
                            <col width="70%" />
                        </colgroup>
                         <tbody>
	
	
	<tr>
		<td>
			<form:label path="tutorial.id">
				<spring:message text="Tutorial Id"/>
			</form:label>
		</td>
		<td>
		 <select id="tutorialId" name="tutorial.id" class="selform">
                                        <c:forEach items="${tutorialList}" var="tutorial">
                                            <option value="${tutorial.id }"> ${tutorial.tutorialName}</option>
                                        </c:forEach>
                                        </select>
			
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="topic">
				<spring:message text="Topic"/>
			</form:label>
		</td>
		<td>
			<form:input id="topicId" path="topic" cssClass="txtform" />
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