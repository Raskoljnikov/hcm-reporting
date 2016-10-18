<%@ page language="java" pageEncoding="UTF-8"%><%-- --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%><%-- --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--  --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page import="org.ctbto.hr.hcm.utils.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
    
    	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" /> 
		<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
		<link rel="alternate" title="CTBTO reporting" href="rss.do" type="application/rss+xml" />
    	
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
		<!-- 		
		<script src="http://github.highcharts.com/highcharts.js"></script>
		<script src="http://github.highcharts.com/modules/drilldown.js"></script>	
 -->		
		<script type="text/javascript" src="./js/highcharts.js"></script>
		<script type="text/javascript" src="./js/drilldown.js"></script>
		
	    <title>CTBTO HR Reporting Portal</title>
	    <link href=./css/erecruitment.css rel="stylesheet" type="text/css" />
	       	    
	    <sitemesh:write property='div.siteMeshJavaScript'/> <!-- ADDS JAVASCRIPT -->
	   	    	    
    </head>
    <body>   
        <div class="mainArea" id="mainArea"><!-- Main area start -->  
			<div class='header' id='header' > <!-- Header start -->
		  		<div class='headerStatusLine' id='headerStatusLine'>
		        	<a href="http://www.ctbto.org">HOME</a>
		    	</div> 				
<!-- MENU AREA START -->			
				<div class='statusLine' id='statusLine' name='statusLine'> <!-- statusLine start -->
	    			<div style='float:left;'>
				  			<a style='color:white;border-bottom:white 1px dotted;' href="./index.html">Home</a> &nbsp;
				  		<sec:authorize access="hasAnyRole('ROLE_admin', 'ROLE_office_es', 'ROLE_chief_hr', 'ROLE_hr_assistant', 'ROLE_hr_officer' )">
    						<a style='color:white;border-bottom:white 1px dotted;' href="./netVacancyReport.html">Net Vacancy Report</a> &nbsp;
    					</sec:authorize>
    					<sec:authorize access="hasAnyRole('ROLE_admin', 'ROLE_hr_assistant', 'ROLE_hr_officer' )">
				  			<a style='color:white;border-bottom:white 1px dotted;' href="./getSAPdata.html">Update Vacancies</a> &nbsp;
				  		</sec:authorize>
				  		

<!--			  		
 		<sec:authorize access="hasRole('ROLE_admin')">
		JUHUUU					
		</sec:authorize>
	 	<sec:authorize ifNotGranted="ROLE_admin">	    
			NEMA JUHUUUU        
		</sec:authorize>
-->		
		
			
				  	</div>				
					<div style='float:right;'>
				   	  	<div style='padding-right: 25px;'>
					    	<a href="http://www.facebook.com/pages/The-Comprehensive-Nuclear-Test-Ban-Treaty-Organization/85643604046?ref=ts#!/pages/The-Comprehensive-Nuclear-Test-Ban-Treaty-Organization/85643604046" title="Facebook" target="_blank"> 
								<span style='text-decoration: none; border-bottom: none;'> 
								  <img border="0" width="16" height="16" src="images/sn_facebook32.png">
								</span>
							</a>
							<a href="http://twitter.com/#!/ctbto_alerts" title="Twitter" target="_blank">
							  	<span style='text-decoration: none;  border-bottom: none;'>
							  		<img border="0" src="images/sn_twitter32.png" width="16" height="16">
								</span>
							</a>
							<a href="http://www.flickr.com/photos/ctbto" title="FlickR" target="_blank">
								<span style='text-decoration: none;  border-bottom: none;'>
									<img border="0" src="images/sn_flickr32.png" width="16" height="16">
								</span>
							</a>
							<a href="http://www.youtube.com/user/CTBTO" title="YouTube" target="_blank">
								<span style='text-decoration: none;  border-bottom: none;'>
									<img border="0" src="images/sn_youtube32.png" width="16" height="16">
								</span>
							</a>
					  	</div>  
				    </div>
					<div class='clearfloat'></div>		    
				</div>
<!-- MENU AREA END -->
<!-- BACKGROUND AREA START -->				
	<!-- WORKING AREA START -->				
				 <div class='bgArea'>  
  					<div class='workingArea' id='workingArea'>   
        				<div class='homePageArea'> <!-- homePageArea Start -->
        					<div class='workingAreaSectionNiceTitle'>
								HCM Reporting
        					</div>
														
							<section id="content">
					    		<sitemesh:write property='body'/>
					    	</section>
							
							
    					</div>
    				</div>
	<!-- WORKING AREA END -->
  
    				<div class='clearfloat'></div>
	<!-- FOOTER AREA START -->   				
	   				 <div id='footer' class='footer'>  
				        For enquiries and/or technical support, please contact us at <a href="mailto:jobs@ctbto.org">jobs@ctbto.org</a>.<br/>
				        The more information you provide the better we can assist you.
				        <p>	
				        Copyright, 2008-2016 CTBTO Preparatory Commission
				    </div>
				    <div class='clearfloat'></div>
	<!-- FOOTER AREA END -->				    		
				</div>	
<!-- BACKGROUND AREA END -->					
			</div> <!-- Header end -->  	
		</div> <!-- Main area end --> 	 
    </body>
</html>


