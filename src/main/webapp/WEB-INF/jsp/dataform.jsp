<%@taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<html>
<body>

	<form:form method="POST" action="/hcm/getDataNext.html" enctype="multipart/form-data" modelAttribute="ufiDBModel">
	
		<table border="0" align="left">
    		<tr>		
    			<td>Please select reporting date: </td>
    			<td align="left">
					<form:select path="insertDateValue" >
			            <form:options items="${ufiDBModel.insertDates}" />
			        </form:select>					
    			</td>
    		</tr>
    		<tr>
    			<td>Please select file to upload: </td>
    			<td align="left">  <input type="file" name="updatePositionIds" />	 </td>
    		</tr>
    		<tr>
    			<td>&nbsp;</td>
    			<td>&nbsp;</td>
    		</tr>
    		<tr>
    			<td align="left"><button type="submit">Submit</button></td>
    			<td> &nbsp; </td>
    		</tr>
    			
		</table>        
	</form:form>
	
</body>
</html>