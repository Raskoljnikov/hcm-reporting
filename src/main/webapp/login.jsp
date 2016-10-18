<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body> 	
	<form method="POST" action="authenticate.html" class="form-signin">
    	<div class='rightAreaSection'> <!-- loginArea Start -->
            <div class="rightAreaSectionTitle">
                reporting Login           
            </div>
            <div class="rightAreaSubSection">
                <c:if test="${not empty param.login_error}">
            		<div><font color="red">
    				Invalid username or password.
            		</font></div>
            	</c:if>
	            <input name="j_username" type="text" class="form-control" placeholder="Username"
	                   autofocus="true"/>
	            <input name="j_password" type="password" class="form-control" placeholder="Password"/>
	
	            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
                   
            </div> 
            <div class='rightAreaSectionList'>
               	<ul>
                   	<li><a href="mailto:adis.hadzijusufovic@ctbto.org"/><img src='images/bullet1.png' border=0/>&nbsp;Forgot Password?</a> 
                   </ul>
               
               </div>                         
		</div> 
	</form>            			
</body>
</html>