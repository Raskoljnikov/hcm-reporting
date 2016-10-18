<%@ page language="java" pageEncoding="UTF-8"%><%-- 
--%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%><%--
--%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%-- 
--%><!DOCTYPE html>
<html>
    <head>    	
    	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    	
	    <title>CTBTO HR Reporting Portal</title>
	    <link href="./css/erecruitment.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
    	<header>
    		<h1>DECORATOR Header</h1>    	
    		<nav>
				<ul>
					<li><a href="/hcm">Home</a></li>
					<li><a href="query.html">Query</a></li>
					<li><a href="getSAPdata.html">SAPData</a></li>					
				</ul>
			</nav>

    	</header>
    	<section id="content">
    		<sitemesh:write property='body'/>
    	</section>
    	<footer id="contentinfo" class="body">
		 	<h1>Footer</h1>		 
		</footer>
    </body>
</html>
