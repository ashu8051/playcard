<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true" %>

<script  type="text/javascript">

<!--
$(document).ready(function(e) { 


$('#tutorialId').change(function() {
    fetchUser($(this).val());
   
});

function fetchUser(tutorialId) {
	
	
    if($.trim(tutorialId).length <= 0) {
        $('#topicId').attr('disabled', 'disabled');
    } else {
        var appURL = "<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>";
        var ajaxurl = appURL + "/topic/topiclistmap?tutorialId=" + tutorialId.replace("{", "").replace("}", "");
        alert("Refresh Again "+ajaxurl);
        $.support.cors = true;
        $.ajax({
            type: "GET",
            url: ajaxurl,
            error: function (err) {
                alert("Refresh Again");
            },
            success: function (data) {
                $('select#topicId option').remove();
                $.each(data, function(i, item) {
                    var options = $('#topicId').prop('options');
                    options[options.length] = new Option(item, i, true, true);
                });
                $('#topicId').removeAttr('disabled');
            }
        });
    }
}

$('#topicContent').submit(function() {
	
	 var tutorialId = $('#tutorialId').val();
	 
       if($.trim(tutorialId).length <= 0) {
       	
           $('#tutorialId').css('background', 'yellow');
           $('#tutorialId').focus();
           return false;
       } else {
           $('#tutorialId').css('background', 'white');
       }
       
       var topicId = $('#topicId').val();
       if($.trim(topicId).length <= 0) {
       	
           $('#topicId').css('background', 'yellow');
           $('#topicId').focus();
           return false;
       } else {
           $('#topicId').css('background', 'white');
       }
       
     
       
       var content = $('#subContent').val();
       if($.trim(subContent).length <= 0) {
       	
           $('#subContent').css('background', 'yellow');
           $('#subContent').focus();
           return false;
       } else {
           $('#subContent').css('background', 'white');
       }
});

});
//-->
</script>

<h1>
	Add a TopicContent
</h1>

<c:url var="addAction" value="/subcontent/add" ></c:url>

<form:form action="${addAction}" modelAttribute="subContent">
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
		 <form:select id="tutorialId"  name="tutorialId" path="tutorial.id" class="selform">
                                        <c:forEach items="${tutorialList}" var="tutorial">
                                            <option value="${tutorial.id }"> ${tutorial.tutorialName}</option>
                                        </c:forEach>
                                        </form:select>
			
		</td> 
	</tr>
	
	<tr>
		<td>
			<form:label path="topic.id">
				<spring:message text="Topic Id"/>
			</form:label>
		</td>
		<td>
		<form:select id="topicId" name="topicId"  path="topic.id" class="selform" >
                                        <c:forEach items="${topicList}" var="topic">
                                            <option value="${topic.id }"> ${topic.topic}</option>
                                        </c:forEach>
                                        </form:select>
                                        <c:set var="topicId" value="${topic.id }" scope="request"/>
			
		</td> 
	</tr>
	

	
	<tr>
		<td>
			<form:label path="subContent">
				<spring:message text="SubContent"/>
			</form:label>
		</td>
		<td>
			<form:input path="subContent"  cssClass="txtform"/>
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

