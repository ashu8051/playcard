<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
 
<html>
 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><tiles:getAsString name="title" /></title>
   <link type="text/css" rel="stylesheet" href="<c:url value="/static/style/global.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/static/style/jquery-ui.css" />" />
<script type="text/javascript" src="<c:url value="/static/script/jquery-1.8.2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/script/jquery-ui.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/script/global.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/script/_lib/jquery.hotkeys.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/script/jquery.jstree.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/script/jquery-ui-timepicker-addon.js" />"></script>
<script type="text/javascript">

</script>
</head>
  
<body>
      <%--   <header id="header">
            <tiles:insertAttribute name="header" />
        </header>
     
        <section id="sidemenu">
            <tiles:insertAttribute name="menu" />
        </section>
             
        <section id="site-content">
            <tiles:insertAttribute name="body" />
        </section>
         
        <footer id="footer">
            <tiles:insertAttribute name="footer" />
        </footer> --%>
        
        
         <div class="wrap">
        <div class="header"><tiles:insertAttribute name="header" />
        </div>
        <div class="intern"><tiles:insertAttribute name="body" />
        </div>
        <!-- <div class="push"></div> -->
    </div>
    <div class="footer"><tiles:insertAttribute name="footer" />
    </div>
</body>
</html>