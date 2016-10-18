package org.ctbto.hr.hcm.controllers;
 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.ctbto.hr.hcm.beans.NetVacancyRepDescBean;
import org.ctbto.hr.hcm.beans.ReportDescriptionBean;
import org.ctbto.hr.hcm.hibernate.NetVacancy;
import org.ctbto.hr.hcm.hibernate.ReportDescription;
import org.ctbto.hr.hcm.hibernate.ReportDescriptionClass;
import org.ctbto.hr.hcm.manager.DatabaseManager;
import org.ctbto.hr.hcm.model.NetVacancyReportModel;
import org.ctbto.hr.hcm.model.UploadFilesInDBModel;
import org.ctbto.hr.hcm.utils.BusinessIntelligenceImpl;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
 
/*
 * author: Crunchify.com
 * 
 */
 
@Controller
public class MainController {
	public static final Logger logger = Logger.getLogger(MainController.class.getName());
		
	@Autowired
    private DatabaseManager dbManager;
	@Autowired
    private ServletContext servletContext; 
	
	
	//FIRST PAGE (WELCOME)
	@RequestMapping(value="/index.html", method = RequestMethod.GET)
	public String templateExample(
			@RequestParam(required = false, value = "username", defaultValue = "") String username,
			@RequestParam(required = false, value = "password", defaultValue = "") String password,			
			Model model){ 
		
		UploadFilesInDBModel ufiDBModel = new UploadFilesInDBModel();
		ufiDBModel.setDbManager(dbManager);
		if(ufiDBModel.hasNewData()){    		
    		ufiDBModel.insertNewDataFromFileListToDB();    		
    	}
		model.addAttribute("username", username);
		
		return "index";
	}

	//*************************   NET VACANCY REPORT PAGE  ********************************************
	@RequestMapping(value="/netVacancyReport.html", method = RequestMethod.GET)
	public String netVacancyReport(	
			Model model){
	
		List <String> allInsertDatesList = BusinessIntelligenceImpl.getFileDatesAsString(false, BusinessIntelligenceImpl.sortDESC);	
		//CREATE THE MODEL AND INITIATE ALL LISTS LIKE (selectedDatesList, notSelectedDatesList, selectedDates[], notSelectedDates [])
		NetVacancyReportModel netVacancyReportModel = new NetVacancyReportModel();
		netVacancyReportModel.setDbManager(dbManager);	
		netVacancyReportModel.splitList(allInsertDatesList);
		String jspCode = netVacancyReportModel.createReportJS(netVacancyReportModel.getSelectedDatesList());

//		List<NetVacancy> changes = netVacancyReportModel.createChanges(netVacancyReportModel.getSelectedDatesList());
		
		List<ReportDescriptionBean> reportDescriptions = netVacancyReportModel.createReportDescriptions(netVacancyReportModel.getSelectedDatesList());
		
		model.addAttribute("reportDescriptions", reportDescriptions);
		model.addAttribute("jspCode", jspCode);
		model.addAttribute("netVacancyReportModel", netVacancyReportModel);

		return "netVacancyReport";
	}
	
	//*************************   NET VACANCY REPORT NEXT PAGE  ********************************************	
	@RequestMapping(value="/netVacancyReportNext.html", method = RequestMethod.POST)
	public String netVacancyReportNext(			
			@ModelAttribute NetVacancyReportModel netVacancyReportModel,
			Model model){ 	
		
		List<String> selectedDatesList = netVacancyReportModel.getSelectedDatesList();
		List<String> notSelectedDatesList = netVacancyReportModel.getNotSelectedDatesList();
		
		netVacancyReportModel = new NetVacancyReportModel();
		netVacancyReportModel.setDbManager(dbManager);
		netVacancyReportModel.setSelectedDatesList(selectedDatesList);
		netVacancyReportModel.setNotSelectedDatesList(notSelectedDatesList);
				
		String jspCode = netVacancyReportModel.createReportJS(selectedDatesList);
		List<ReportDescriptionBean> reportDescriptions = netVacancyReportModel.createReportDescriptions(netVacancyReportModel.getSelectedDatesList());
		
		model.addAttribute("reportDescriptions", reportDescriptions);
		model.addAttribute("jspCode", jspCode);
		model.addAttribute("netVacancyReportModel", netVacancyReportModel);
		
		return "netVacancyReportNext";
	}
	
	//*************************   UPDATE SAP STAFFING RECORDS (select screen) - output is getDataNext or error page  ********************************************
	@RequestMapping(value="/getSAPdata.html", method = RequestMethod.GET)
	public String getPositionsData(			
			Model model){ 
				
			UploadFilesInDBModel ufiDBModel = new UploadFilesInDBModel();
			ufiDBModel.setInsertDates(BusinessIntelligenceImpl.getFileDatesAsString(false, BusinessIntelligenceImpl.sortDESC));

			model.addAttribute("ufiDBModel", ufiDBModel);
			
		return "dataform";
	}
	
	
	//*************************   UPDATE SAP STAFFING RECORDS - output is dataformRepDescription or error page  ********************************************
	@RequestMapping(value = "/getDataNext.html", method = RequestMethod.POST)
	public String savePositionsData(
			@RequestParam(value = "updatePositionIds", required = true) MultipartFile multipartFile,		
			@ModelAttribute UploadFilesInDBModel ufiDBModel,
			Model model){ 
		
		ufiDBModel.setDbManager(dbManager);
		
		String retVal = ufiDBModel.populateModelForRepDesc(servletContext, multipartFile);

		model.addAttribute("errorMessage", ufiDBModel.getErrorMessage());
		model.addAttribute("ufiDBModel", ufiDBModel);	
        	
		
		return retVal;
	}
		
	//*************************    output is success or error page  ********************************************	
	@RequestMapping(value = "/getDataFormReportDescription.html", method = RequestMethod.POST)
	public String saveReportDescriptionData(			
			@RequestParam(required = false, value = "insertDate", defaultValue = "") String insertDate,
			WebRequest webRequest,
			@ModelAttribute UploadFilesInDBModel ufiDBModel,
			Model model){ 

		ufiDBModel.setDbManager(dbManager);
		String retVal = ufiDBModel.InsertUpdateReportDescriptions(insertDate, webRequest.getParameterMap());
    	
		model.addAttribute("errorMessage", ufiDBModel.getErrorMessage());
		model.addAttribute("ufiDBModel", ufiDBModel);	
        	
		
		return retVal;
	}

	
	
	
	

	
	
	

}