<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="now" value="<%= new java.util.Date() %>" />

<div class="flt">
<h1 style="color: red;font-style:inherit ;font-size: 30px;">makemytutorial.com</h1>
</div>
<div class="flt" >
	<c:url value="/logout" var="logoutUrl" />

		<!-- csrt support -->
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden"
			name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>


</div>

<div class="frt">
                <div style="width: 100%; text-align: right; float: right;">
                    <div class="headersec">
                        <div class="tr pt3 logout">Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="javascript:formSubmit()"> Logout</a></div>
                        <div class="tr pt7 timeinfo"><fmt:formatDate value="${now}" type="both" dateStyle="full" timeStyle="full" /></div>
                    </div>
                    <div id="navbar" style="height: 24px;">
                        <ul>
                            <li><a href="<c:url value="/home/index" />"><span>Home</span></a></li>
                            <li>
                                <a href="<c:url value="/tutorial/index" />"><span>Tutorial</span></a>
                               <%--  <ul>
                                    <li><a href="<c:url value="/common/vehiclefinder.html" />">Vehicle Finder</a></li>
                                    <li><a href="<c:url value="/common/shortestpathfinder.html" />">Shortest Path Finder</a></li>
                                    <li><a href="<c:url value="/report/trackhistoryplay.html" />">Track History Play</a></li>
                                </ul> --%>
                            </li>
                           
                        </ul>
                    </div>
                </div>
            </div>
            <div class="clr"></div>