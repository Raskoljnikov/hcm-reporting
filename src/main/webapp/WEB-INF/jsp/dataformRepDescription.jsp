<%@taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<html>
<head>
	<div id="siteMeshJavaScript"> 
	<script type="text/javascript">
	  	function skipAndMove(){
		    window.location="netVacancyReport.html";
	  	}
	</script>

	</div>
</head>
<body>

	<h3> YOUR DATA HAS BEEN UPLOADED SUCCESSFULLY! </h3>
	<br/>
	<p align="left">
		Reporting date: <b>${ufiDBModel.insertDateValue} </b> <br/><br/><br/>
	<!-- 	
    	Last Reporting date: <b>${ufiDBModel.previousInsertDate} </b> <br/><br/>    	
     -->	
    	<b>CHANGES TO LAST WEEK</b>
	
	<form:form method="POST" action="/hcm/getDataFormReportDescription.html" enctype="multipart/form-data" modelAttribute="ufiDBModel">
		
		<table class="reportTable">
			<tr>		   			
    			<th align="center">CATEGORY</th>
    			<th align="center">CHANGE</th>
    			<th align="center">EXPLANATION OF CHANGES FOR PERIOD ${ufiDBModel.previousInsertDate} TO ${ufiDBModel.insertDateValue}</th>
    		</tr>
<c:forEach items="${ufiDBModel.repDescBeanClassList}" var="reportDescCategoryBean">     		
    		<tr>		   			
    			
    		<c:if test="${reportDescCategoryBean.id < 6}">
    			<td align="left"><c:out value="${reportDescCategoryBean.repDescCategory}"/></td>	
    			<td align="left"><nobr>${reportDescCategoryBean.change}<nobr></td>
    			<td align="left"><input type="text" name="explanation_${reportDescCategoryBean.id}" size="90"></td>
    		</c:if>
    		<c:if test="${reportDescCategoryBean.id >= 6}">
    			<td align="left"><b><c:out value="${reportDescCategoryBean.repDescCategory}"/></b></td>	
    			<td align="left">&nbsp;</td>
    			<td align="left"><textarea name="explanation_${reportDescCategoryBean.id}" cols="92" rows="4"></textarea></td>
    		</c:if>
    		</tr>
</c:forEach>    		
    			
		</table>
		<br/>
		<br/>
		<table border="0" align="center">
			<tr>
				<button type="submit">Submit</button> &nbsp; &nbsp; <input type="button" name="skip_button" value="Skip" onclick="javascript: skipAndMove();" >
			</tr>
		</table>
		
		
		<input type="hidden" name="insertDate" value="${ufiDBModel.insertDateValue}">        
	</form:form>
	
</body>
</html>