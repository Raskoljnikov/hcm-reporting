<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.ctbto.hr.hcm.utils.*" %>

<html>
<head>
	<div id="siteMeshJavaScript"> 
		<script type="text/javascript">
			  $().ready(function() {
			   $('#add').click(function() {
			    return !$('#select1 option:selected').remove().appendTo('#select2');
			   });
			   $('#remove').click(function() {
			    return !$('#select2 option:selected').remove().appendTo('#select1');
			   });
			  });
		</script>
	

		<script type="text/javascript">
		  	function selectDates(){
			    
		  		var selectedDates = document.getElementById('select2');	
		  		if(selectedDates != null){
		  			//alert("selectedDates is not null");
		  			
		  			for(i=0; i<selectedDates.length; i++){
			  			selectedDates.options[i].selected = true;	
		  			}	
		  		}
		  		
		  		var notSelectedDates = document.getElementById('select1');	
		  		if(notSelectedDates != null){
		  			//alert("selectedDates is not null");
		  			
		  			for(j=0; j<notSelectedDates.length; j++){
		  				notSelectedDates.options[j].selected = true;	
		  			}	
		  		}
		  	}
		</script>
					
			
		${jspCode}
			
	</div>
</head>
<body>

	<form:form method="POST" action="/hcm/netVacancyReportNext.html" modelAttribute="netVacancyReportModel">
    		
     		<div class='workingAreaSectMultiple'>							
			<div style='float:left;'>
				<form:select multiple="true" path="notSelectedDatesList" name="mySelect1" id="select1">
				    <form:options items="${netVacancyReportModel.notSelectedDatesList}" />
				</form:select> 
			  <a href="#" id="add">add &gt;&gt;</a>
			</div>
			<div style='float:left;'>
				<form:select multiple="true" path="selectedDatesList" name="mySelect2" id="select2">
				    <form:options items="${netVacancyReportModel.selectedDatesList}" />
				</form:select> 
			  <a href="#" id="remove">&lt;&lt; remove</a>
			</div>
			<div class='clearfloat'></div>						
		</div>
		<div class='clearfloat'></div>
		
		<br/>
		<input type='button' value='submit' onclick="selectDates(); this.form.submit();">
		<br/>
		<br/>
		<br/>
		<!-- REPORT -->
		<div class="report" id="container" style="min-width: 310px; max-width: 500px; height: 600px; margin: 1px auto"> </div>
		<div class="reportTableDiv" style="height: 500px; width: 400px">
     		<table class="reportTable">

	<c:forEach items="${reportDescriptions}" var="reportDescriptionBean">
				<tr >
			  		<th><c:out value="Changes in the week of  ${reportDescriptionBean.insertDate}"/></th>
			  	</tr>
 		<c:forEach items="${reportDescriptionBean.reportDescriptionList}" var="netVacancyRepDescBean" >  
				<tr>
					<td><c:out value="${netVacancyRepDescBean.description_class_value}"/> <c:out value="${netVacancyRepDescBean.description}"/></td>
				</tr>
		</c:forEach>     
	</c:forEach>	
     		</table>
     		
     	</div>
     	<div class='clearRep'></div>
     	
	</form:form> 	
</body>
</html>