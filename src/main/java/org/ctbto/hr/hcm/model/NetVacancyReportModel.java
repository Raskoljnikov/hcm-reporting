package org.ctbto.hr.hcm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ctbto.hr.hcm.beans.NetVacancyRepDescBean;
import org.ctbto.hr.hcm.beans.ReportDescriptionBean;
import org.ctbto.hr.hcm.hibernate.Categories;
import org.ctbto.hr.hcm.hibernate.DateValue;
import org.ctbto.hr.hcm.hibernate.DepartmentValue;
import org.ctbto.hr.hcm.hibernate.NetVacancy;
import org.ctbto.hr.hcm.hibernate.ReportDescription;
import org.ctbto.hr.hcm.hibernate.ReportDescriptionClass;
import org.ctbto.hr.hcm.manager.DatabaseManager;
import org.ctbto.hr.hcm.utils.BusinessIntelligenceImpl;
import org.ctbto.hr.hcm.utils.UPropertiesUtil;

public class NetVacancyReportModel {
	
	public static final Logger logger = Logger.getLogger(NetVacancyReportModel.class.getName());
	private List<String> 	selectedDatesList;
	private List<String> 	notSelectedDatesList;
	private DatabaseManager dbManager;
	private String 			jspCode;

	

	
	public void splitList(List <String> insertDatesList){
		
		List<String> head = new ArrayList<String>();
		List<String> tail = new ArrayList<String>();

		int nrOfSelDates = new Integer(UPropertiesUtil.getProperty("hcm.report.number.of.selected.dates")).intValue();
		
		if(insertDatesList.size() > nrOfSelDates){
			head = insertDatesList.subList(0, nrOfSelDates);
			tail = insertDatesList.subList(nrOfSelDates, insertDatesList.size());
		}else{
			head = insertDatesList;
		}
				
		this.setSelectedDatesList(head);
		this.setNotSelectedDatesList(tail);
		
	}
	
	
	public List <ReportDescriptionBean> createReportDescriptions(List <String> selectedDates){
		List<ReportDescriptionBean> retVal = new ArrayList<ReportDescriptionBean>();
		
		for(String insertDate : selectedDates){
			ReportDescriptionBean repDescBean = new ReportDescriptionBean();
			
			List <NetVacancyRepDescBean> reportDescriptionList = createReportDescriptionList(insertDate);

			repDescBean.setInsertDate(insertDate);			
			repDescBean.setReportDescriptionList(reportDescriptionList);			
					
			retVal.add(repDescBean);
			
		}

		return retVal;
	}
	
	
	public List<NetVacancyRepDescBean> createReportDescriptionList (String insertDate){
		List <NetVacancyRepDescBean> retVal = new ArrayList <NetVacancyRepDescBean> ();
		
		List <ReportDescription> repDescList = dbManager.getAllReportDescriptionsByInsertDate(BusinessIntelligenceImpl.parseStringToDate(insertDate, "yyyy_MM_dd"));
		for(ReportDescription repDesc : repDescList){
			ReportDescriptionClass rdclass = dbManager.getReportDescriptionClassById(repDesc.getDescription_class_id());
			String description = rdclass.getDescription_class();
			
			NetVacancyRepDescBean nvdBean = new NetVacancyRepDescBean(repDesc, description);
			
			retVal.add(nvdBean);
		}
		
		return retVal;
	}
	
	
	public List <NetVacancy> createChanges(List <String> selectedDates){
		List<NetVacancy> retVal = new ArrayList<NetVacancy>();

//		Iterator <String> it = selectedDates.iterator();
//		while(it.hasNext()){
//			String insertDateStringCurr = it.next();
//			List <NetVacancy> nvListCurrent = dbManager.getAllNetVacanciesByInsertDate(BusinessIntelligenceImpl.parseStringToDate(insertDateStringCurr, "yyyy_MM_dd"));
//			if(it.hasNext()){
//				String insertDateStringPrev = it.next();
//				List <NetVacancy> nvListPrev = dbManager.getAllNetVacanciesByInsertDate(BusinessIntelligenceImpl.parseStringToDate(insertDateStringPrev, "yyyy_MM_dd"));	
//				
//				System.out.println("CHANGES FROM DATE: "+insertDateStringPrev+" TO DATE:"+insertDateStringCurr);
//				
//				int counter = 0;
//				//CHECK DEPARTURES
//				for(NetVacancy nvcurr : nvListCurrent){
//					for(NetVacancy nvprev : nvListPrev){
//						if(nvcurr.getPositionId().equals(nvprev.getPositionId())){
//							
//							String familyNameCurr = nvcurr.getFamily_name() == null ? "" : nvcurr.getFamily_name().trim();
//							String familyNamePrev = nvprev.getFamily_name() == null ? "" : nvprev.getFamily_name().trim();
//							if(!familyNameCurr.equals(familyNamePrev)){
//								counter++;
//								System.out.println("THERE IS A CHANGE at positionId: "+nvprev.getPositionId());								
//							}
//						}
//					}										
//				}
//				
//				
//				
//				//CHECK ARRIVALS
//				
//				
//
//				//ADD ALL SEPARATED FROM CTBTO
//				
//				
//				//ADD ALL NEWCOMERS TO CTBTO
//				
//				
//				
//			}
//		}
		return retVal;
	}
	
	
		
	public String createReportJS(List <String> selectedDates){
		logger.debug("IN createReportJS - selectedDates.size(): "+selectedDates.size());
		
		String retVal = "";
		retVal = 	getJSFunctionStart() + 
					getJSFunctionSeries(selectedDates) + 
					getJSFunctionDrillDownSeries(selectedDates) + 
					getJSFunctionEnd(); 
		
		return retVal;
	}
		
	
	public String getJSFunctionStart(){
		String retVal ="";
		retVal = retVal + "<style type='text/css'> \n"; 
		retVal = retVal + "\t" + "${demo.css} \n";
		retVal = retVal + "\t" + "\t" + "</style> \n";		
		retVal = retVal + "\t" + "\t" + "<script type=\"text/javascript\"> \n";		
		retVal = retVal + "\t" + "\t" + "$(function () {  \n";	
		retVal = retVal + "\t" + "\t" + "// Create the chart  \n";			
		retVal = retVal + "\t" + "\t" + " $('#container').highcharts({  \n";					
		retVal = retVal + "\t" + "\t" + "\t" + "chart: { \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t"+ "type: 'column' \n";
		retVal = retVal + "\t" + "\t" + "\t" + "}, \n";
		retVal = retVal + "\t" + "\t" + "\t" + "title: { \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t"+ "text: 'Weekly changes' \n";   
		retVal = retVal + "\t" + "\t" + "\t" + "}, \n";
		retVal = retVal + "\t" + "\t" + "\t" + "xAxis: { \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t"+ "type: 'category' \n";   
		retVal = retVal + "\t" + "\t" + "\t" + "}, \n";    
		retVal = retVal + "\t" + "\t" + "\t" + "legend: { \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t"+ "enabled: true \n";   
		retVal = retVal + "\t" + "\t" + "\t" + "}, \n";      
		retVal = retVal + "\t" + "\t" + "\t" + "tooltip: { \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t"+ "backgroundColor: \"rgba(247,247,247,0.85)\", \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "\t"+ "formatter: function () { \n";    		
		retVal = retVal + "\t" + "\t" + "\t" + "\t"+ "\t" +" return 'Total Nr of Positions: ' + this.point.stackTotal +	'<br/>' + this.series.name + ': ' + this.y + '<br/>' + ";
		retVal = retVal + " 'Percentage: '+ Math.round(this.y / this.point.stackTotal * 100 * 100)/100 +' %' ;	\n";
		
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "} \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "}, \n";              
		//plotOptions        
		retVal = retVal + "\t" + "\t" + "\t" + "plotOptions: { \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "series: { \n";   
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t"+ "borderWidth: 0, \n";   
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t"+ "dataLabels: { \n";   
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "\t"+ "enabled: true, \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "\t"+ "allowOverlap: true, \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "\t"+ "style: { \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t"+ "color: 'white', \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t"+ "textShadow: '0 0 2px black, 0 0 2px black' \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "} \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t"  + "}, \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t"  + "stacking: 'normal' \n"; 
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "} \n";     
		retVal = retVal + "\t" + "\t" + "\t" + "}, \n";                  
		       				

		return retVal;
	}
	

	public String getJSFunctionSeries(List <String> insertDates){
		String retVal ="";
		
		if(dbManager != null){			
			List <Categories> categories =  dbManager.getAllCategories();				
			retVal = retVal + "\t" + "\t" + "series: [ \n"; //series
			
			Iterator<Categories> entries = categories.iterator();
			while (entries.hasNext()){
				Categories cat = entries.next();
				Long id = cat.getId();
				String category = cat.getCategory();				
			
				retVal = retVal + "\t" + "\t" + "\t" + "{ \n";
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "name: '"+category+"', \n";
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "data: [ \n";
				
				
				Iterator <String> it = insertDates.iterator();
				while (it.hasNext()){
					String insertDateStr = it.next();
					logger.debug("CALLING dbManager.getJSFunctionSeriesData("+id+","+insertDateStr+")");
					DateValue dv = dbManager.getJSFunctionSeriesData(id, insertDateStr);

					
					retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "{ \n";
					retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "name: '"+dv.getFormatedInsertDate()+"', \n";
					retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "y: "+dv.getValue()+", \n";
					retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "drilldown: '"+category+"_"+dv.getFormatedInsertDate()+"' \n";
					
					retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "} ";
					if(it.hasNext())
						retVal = retVal + ",";
					retVal = retVal + "\n";
				}
							
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "] \n";
				retVal = retVal + "\t" + "\t" + "\t" + "}";	
				if(entries.hasNext())
					retVal = retVal + ",";
				retVal = retVal + "\n";
			}
	
		}
			
		retVal = retVal + "\t" + "\t" + "], \n";
//		System.out.println(retVal);
		
		return retVal;
	}
	
	
	
	//OVDJE POCINJE DRILL-DOWN DIO
	public String getJSFunctionDrillDownSeries(List <String> insertDateStrings){
		String retVal ="";
		
		retVal = retVal + "\t" + "\t" + "drilldown: \n";
		retVal = retVal + "\t" + "\t" + "\t" + "{ \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "activeDataLabelStyle: { \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "color: 'white', \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "textShadow: '0 0 2px black, 0 0 2px black' \n";
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "}, \n";
		
		retVal = retVal + "\t" + "\t" + "\t" + "\t" + "series: [ \n";
		
		
		List <Categories> categories =  dbManager.getAllCategories();
		Iterator<Categories> entries = categories.iterator();
		while (entries.hasNext()){
			Categories cat = entries.next();
			
			Long categoryId = cat.getId();  
			String category = cat.getCategory();
			
			
			Iterator<String> it_dates = insertDateStrings.iterator();
			while (it_dates.hasNext()){
				String dateString = it_dates.next();			
				
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "{ \n";
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "id: '"+category+"_"+dateString+"', \n";
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "name: '"+category+"_"+dateString+"', \n";
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "data: \n";
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "[ \n";
				logger.debug("CALLING dbManager.getJSFunctionDrillDownData("+categoryId.intValue()+","+dateString+")");				
				List<DepartmentValue> dvbList = dbManager.getJSFunctionDrillDownData(categoryId.intValue() , dateString);
				Iterator<DepartmentValue> it_dvb = dvbList.iterator();
				while(it_dvb.hasNext()){
					DepartmentValue dvb = it_dvb.next();
				
					retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "['"+dvb.getDepartment()+"', "+dvb.getValue()+"]";
					if(it_dvb.hasNext()){ retVal = retVal + ", ";}
					retVal = retVal +" \n";

				}
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "\t" + "] \n";//data
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + "} "; //serie x
				if(it_dates.hasNext()){
					retVal = retVal + ", ";
				}
				retVal = retVal + " \n";	
			}
			if(entries.hasNext()){
				retVal = retVal + "\t" + "\t" + "\t" + "\t" + ", ";
			}
			retVal = retVal + " \n";	
		}		
	
		
		retVal = retVal + "\t" + "\t" + "\t" + "] \n";  //series
		return retVal;
	}
	
	public String getJSFunctionEnd(){
		String retVal ="";
		
		retVal = retVal +  "\t" + "\t" + "\t" + "\t" + "\t" + "} \n";
		retVal = retVal +  "\t" + "\t" + "\t" + "\t" + "} \n";
		retVal = retVal +  "\t" + "\t" + "\t" + ") \n";
		retVal = retVal +  "\t" + "\t" + "}); \n";
		retVal = retVal +  "</script> \n";

		return retVal;
	}
	
	
	
	
	
	

	public DatabaseManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}


	public String getJspCode() {
		return jspCode;
	}

	public void setJspCode(String jspCode) {
		this.jspCode = jspCode;
	}	

	public List <String> getSelectedDatesList() {
		return selectedDatesList;
	}
	public void setSelectedDatesList(List <String>  selectedDatesList) {
		this.selectedDatesList = selectedDatesList;
	}
	public List <String>  getNotSelectedDatesList() {
		return notSelectedDatesList;
	}
	public void setNotSelectedDatesList(List <String>  notSelectedDatesList) {
		this.notSelectedDatesList = notSelectedDatesList;
	}
	
	
	

}
