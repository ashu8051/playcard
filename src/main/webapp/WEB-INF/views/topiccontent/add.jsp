<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true" %>

<script  type="text/javascript">

<!--
$(document).ready(function(e) { 

//	$("#topicId").prop("disabled",true);
//	$("#subContentId").prop("disabled",true);
$('#tutorialId').change(function() {
	fetchTopic($(this).val());
   
});

function fetchTopic(tutorialId) {
	
	
    if($.trim(tutorialId).length <= 0) {
        $('#topicId').attr('disabled', 'disabled');
    } else {
        var appURL = "<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>";
        var ajaxurl = appURL + "/topic/topiclistmap?tutorialId=" + tutorialId.replace("{", "").replace("}", "");
        
        $.support.cors = true;
        $.ajax({
            type: "GET",
            url: ajaxurl,
            error: function (err) {
            	alert("Refresh Again");
            },
            success: function (data) {
            	$("#topicId").prop("disabled",false);
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

//SubTopic
$('#topicId').change(function() {
	fetchSubTopic($(this).val());
   
});

function fetchSubTopic(subContentId) {
	
	
    if($.trim(subContentId).length <= 0) {
        $('#subContentId').attr('disabled', 'disabled');
    } else {
        var appURL = "<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>";
        var ajaxurl = appURL + "/subcontent/subcontentlist?topicId=" + subContentId.replace("{", "").replace("}", "");
        $.support.cors = true;
        $.ajax({
            type: "GET",
            url: ajaxurl,
            error: function (err) {
                alert("Refresh Again"+ajaxurl);
            },
            success: function (data) {
            	$("#subContentId").prop("disabled",false);
                $('select#subContentId option').remove();
                $.each(data, function(i, item) {
                    var options = $('#subContentId').prop('options');
                    options[options.length] = new Option(item, i, true, true);
                });
                $('#subContentId').removeAttr('disabled');
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
       
       var subContentId = $('#subContentId').val();
       if($.trim(subContentId).length <= 0) {
       	
           $('#subContentId').css('background', 'yellow');
           $('#subContentId').focus();
           return false;
       } else {
           $('#subContentId').css('background', 'white');
       }
       var contentType = $('#contentType').val();
       if($.trim(contentType).length <= 0) {
       	
           $('#contentType').css('background', 'yellow');
           $('#contentType').focus();
           return false;
       } else {
           $('#contentType').css('background', 'white');
       }
       
      
       
       var content = $('#content').val();
       if($.trim(content).length <= 0) {
       	
           $('#content').css('background', 'yellow');
           $('#content').focus();
           return false;
       } else {
           $('#content').css('background', 'white');
       }
});

});
//-->
</script>

<h1>
	Add a TopicContent
</h1>

<c:url var="addAction" value="/topiccontent/add" ></c:url>

<form:form action="${addAction}" modelAttribute="topicContent">
<table  class="showtbl">
<colgroup>
                            <col width="30%" />
                            <col width="50%" />
                             <col width="20%" />
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
		                                  <option value=""> --Select--</option>
                                        <c:forEach items="${tutorialList}" var="tutorial">
                                            <option value="${tutorial.id }"> ${tutorial.tutorialName}</option>
                                        </c:forEach>
                                        </form:select>
			
		</td> 
		<td><a href="<c:url value="/tutorial/add" />"/>Add Tutorial</td>
	</tr>
	
	<tr>
		<td>
			<form:label path="topic.id">
				<spring:message text="Topic Id"/>
			</form:label>
		</td>
		<td>
		<form:select id="topicId" name="topicId"  path="topic.id" class="selform" >
		                                <option value=""> --Select--</option>
                                        <c:forEach items="${topicList}" var="topic">
                                            <option value="${topic.id }"> ${topic.topic}</option>
                                        </c:forEach>
                                        </form:select>
                                        <c:set var="topicId" value="${topic.id }" scope="request"/>
			
		</td> 
		<td><a href="<c:url value="/topic/add" />"/>Add Topic</td>
	</tr>
	
	<tr>
		<td>
			<form:label path="topic.id">
				<spring:message text="SubContent Id"/>
			</form:label>
		</td>
		<td>
		<form:select id="subContentId" name="subContentId"  path="subContent.id" class="selform" >
		                                  <option value=""> --Select--</option>
                                        <c:forEach items="${subContentList}" var="subtopic">
                                            <option value="${subtopic.id }"> ${subtopic.subContent}</option>
                                        </c:forEach>
                                        </form:select>
                                      
			
		</td> 
		<td><a href="<c:url value="/subcontent/add" />"/>Add SubTopic</td>
	</tr>
	
	<tr>
		<td>
			<form:label path="contentType">
				<spring:message text="contentType"/>
			</form:label>
		</td>
		<td>
		<form:select id="contentType" name="contentType"  path="contentType" class="selform" >
                                        <c:forEach items="${contentType}" var="topic">
                                            <option value="${topic }"> ${topic}</option>
                                        </c:forEach>
                                        </form:select>
			
		</td> 
	</tr>
	
	<tr>
		<td>
			<form:label path="content">
				<spring:message text="Content"/>
			</form:label>
		</td>
		<td>
			<form:textarea path="content"  cssClass="txtform"/>
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

