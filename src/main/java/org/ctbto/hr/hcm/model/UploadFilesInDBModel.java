package org.ctbto.hr.hcm.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.ctbto.hr.hcm.beans.ReportDescCategoryBean;
import org.ctbto.hr.hcm.beans.ReportDescriptionBean;
import org.ctbto.hr.hcm.controllers.MainController;
import org.ctbto.hr.hcm.hibernate.NetVacancy;
import org.ctbto.hr.hcm.hibernate.ReportDescription;
import org.ctbto.hr.hcm.hibernate.ReportDescriptionClass;
import org.ctbto.hr.hcm.manager.DatabaseManager;
import org.ctbto.hr.hcm.utils.BusinessIntelligenceImpl;
import org.ctbto.hr.hcm.utils.UPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;


public class UploadFilesInDBModel {
	
	@Autowired
    ServletContext servletContext;

	
	MultipartFile 	multipartFile;
	List <String> 	insertDates;
	String 			insertDateId;
	String 			insertDateValue;
	String			previousInsertDate;
	String 			errorMessage;
	DatabaseManager dbManager;
//	List <ReportDescriptionClass> reportDescriptionClassList;
	List <ReportDescCategoryBean> repDescBeanClassList;
	
	
	public static final Logger logger = Logger.getLogger(UploadFilesInDBModel.class.getName());
	private static int maxFileSize = 50 * 1024;
	private static int maxMemSize = 4 * 1024;
	
	
	//DEFAULT CONSTRUCTOR
	public UploadFilesInDBModel(){
		
	}
	//OVERLOADED CONSTRUCTOR
	public UploadFilesInDBModel(ServletContext servletContext){		
		this.servletContext = servletContext;
	}
	
	public String populateModelForRepDesc(ServletContext servletContext, MultipartFile multipartFile){
		String retVal = "";
		
		//update DB with data from excel
		retVal = this.UpdateDBwithHRInfo(servletContext, multipartFile);		
		//set Previous insertDate
		this.setPreviousInsertDate(this.getPreviousInsertDate(this.getInsertDateValue()));
		//create List of RepordDescriptionBeans
		this.setRepDescBeanClassList(this.createRepDescBeanList(this.getInsertDateValue(), this.getPreviousInsertDate()));
		
		
//		this.setReportDescriptionClassList();
		
		return retVal;
	}
	
	
	public List<ReportDescCategoryBean> createRepDescBeanList(String currentDateStr, String prevDateString){
		List <ReportDescCategoryBean> retVal = new ArrayList<ReportDescCategoryBean>();
		
		List <ReportDescriptionClass> repDesClassList = dbManager.getAllReportDescriptionsClasses();
		for(ReportDescriptionClass rdc : repDesClassList){
			ReportDescCategoryBean rdcbean = new ReportDescCategoryBean();
			rdcbean.setId(rdc.getId());
			rdcbean.setRepDescCategory(rdc.getDescription_class());
			rdcbean.setChange(this.getReportDescChange(rdcbean.getId(), currentDateStr, prevDateString));
			
			retVal.add(rdcbean);
		}
		
		return retVal;
	}
	
	public String getReportDescChange (Long categoryid, String currentDateStr, String prevDateString){
		String retVal = "";
		if(prevDateString != null && !"".equals(prevDateString)){
			retVal = dbManager.getComparedValueByDates(categoryid.intValue(), currentDateStr, prevDateString);	
		}
		return retVal;
	}
	
	
	
	//FUNCTIONS
	public boolean hasNewData(){	
		boolean retVal = false;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1);
		Date lastFileDate = cal.getTime();
		Date lastDBDate = cal.getTime();
			
		//GET last insert Datein the FILES
		List <String> allInsertDatesList = BusinessIntelligenceImpl.getFileDatesAsString(false, BusinessIntelligenceImpl.sortDESC);
		List <Date> fileDates = BusinessIntelligenceImpl.getFileDates(allInsertDatesList, BusinessIntelligenceImpl.sortDESC);
		if(fileDates != null && fileDates.size() > 0){
			lastFileDate = fileDates.get(0);
		}	
		
		
		//GET last insert Date in DB
		List<Date> allDBInsertDates = dbManager.getInsertDates(BusinessIntelligenceImpl.sortDESC);
		if(allDBInsertDates != null && allDBInsertDates.size() > 0){
			lastDBDate = allDBInsertDates.get(0);
		}	

		
		//IF (lastFileDate.after(lastDBDate)) -> return true 		
		if(lastFileDate.after(lastDBDate)){
			System.out.println("LAST_DATE IN FILE SYSTEM("+lastFileDate+") > LAST_DATE IN DB("+lastDBDate+") - UPDATING DATABASE!!!");
			retVal = true;				
		}else{
			System.out.println("DB IS UP TO UPDATE, NO NEW FILES ADDED");
		}
		
		return retVal;
	}
	
	
		
	/**
	 * Gets the list of all file dates (file_date) in form "yyyy_MM_dd" in descending order
	 * Checks the last inserted Date in DB (db_date) and if a file_date is newer than db_date then 
	 * it calls function insertDataFromCSVFile(fileDate, pathToCSVDir, fileType);
	 *  <p>
	 * @param 		pathToCSVDir - is a String needed for compliance reasons in jsp. It shows the path where .csv files are stored
	 * @param 		CSVFileDelimiterType - defines the .csv file type delimiter   
	 * @return      void
	 */
	public void insertNewDataFromFileListToDB(){
		
		String pathToCSVDir = UPropertiesUtil.getProperty("path.to.sap.files");
		String beginDateString = "1970_01_01";					
		
		//INITIALIZE BOTH DATES WITH 1.1.1970
		Date fileDate = BusinessIntelligenceImpl.parseStringToDate(beginDateString, "yyyy_MM_dd");
		Date lastDBDate = BusinessIntelligenceImpl.parseStringToDate(beginDateString,"yyyy_MM_dd");

		List <Date> dbDateList = dbManager.getInsertDates(BusinessIntelligenceImpl.sortDESC);
		if(dbDateList != null && dbDateList.size() > 0 ){
			lastDBDate = dbDateList.get(0);
		}
		
		List <String> fileDateList = BusinessIntelligenceImpl.getFileDatesAsString(false, BusinessIntelligenceImpl.sortASC);	
		Iterator <String> it = fileDateList.iterator();
		while (it.hasNext()){
			String fileDateString = it.next();					
			fileDate = BusinessIntelligenceImpl.parseStringToDate(fileDateString,"yyyy_MM_dd");
			if (fileDate.after(lastDBDate)){
				String csvFile = pathToCSVDir + UPropertiesUtil.getProperty("sap.csv.file.name") + fileDateString +".csv";
				System.out.println("INSERTING DATA FROM FILE: "+csvFile);
				insertDataFromCSVFile(csvFile, fileDate);
			}
		}		
	}
	
	
	/**
	 * Inserting Data from CSV File
	 * <p>
	 * @param		fullFileName - full path to .csv file in form /
	 * @return      void
	 */
	
	public void insertDataFromCSVFile(String csvFile, Date fileDate){
		
		
		//CREATE LIST of netVacancies !!!!!!
		List <NetVacancy> netVacancyList = createNVList(csvFile, fileDate);
				
		if(netVacancyList != null && netVacancyList.size() > 0){
			dbManager.insertNetVacancyFromList(netVacancyList);
//			dbManager.insertNetVacancy(netVacancyList.get(0));  -- ovo radi
		}	
	}
	
	
	List <NetVacancy> createNVList(String csvFile, Date fileDate){
		
		List <NetVacancy> nvbList = new ArrayList<NetVacancy>();
		char delimiter	= UPropertiesUtil.getProperty("csv.delimiter").charAt(0);		
		String[] row = null;
		
		try {
			int skipLines = 1;

			CSVReader csvReader = new CSVReader(new FileReader(csvFile), delimiter, CSVParser.DEFAULT_QUOTE_CHARACTER, skipLines);

			while((row = csvReader.readNext()) != null) {
				if(row != null){
					//populate NetVacancyBean
					NetVacancy nvb = createNetVacancy(row, fileDate);				
					nvbList.add(nvb);
				}
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			logger.error("createNVBList(String csvFileName, String CSVFileDelimiterType) throw a FileNotFoundException e", e);
		} catch (IOException e) {
			logger.error("createNVBList(String csvFileName, String CSVFileDelimiterType) throw a IOException e", e);
		} 		return nvbList;
	}
	
	
	/**
	 * Delivers the last insertDate before the one passed as parameter 
	 * <p>
	 * 
	 * @return      NetVacancy
	 */
	public String getPreviousInsertDate (String insertDateStr){
		String retVal = "";
		List <String> allInsertDatesList = BusinessIntelligenceImpl.getFileDatesAsString(false, BusinessIntelligenceImpl.sortDESC);
		Iterator<String> it = allInsertDatesList.iterator();
		while(it.hasNext()){
			String insertDateString = it.next();
			if(insertDateString.equals(insertDateStr)){
				if(it.hasNext()){
					retVal = it.next();
					break;
				}
			}
		}
		
		return retVal;
	}
	
	
	/**
	 * Creates a NetVacancy Bean from one line in .csv document
	 * <p>
	 * 
	 * @return      NetVacancy
	 */
	public NetVacancy createNetVacancy(String[] row, Date fileDate){
		NetVacancy nvb = new NetVacancy();

		//replace ' chars with empty strings 
		for(int i=0; i<row.length; i++){
			if(row[i].contains("'")){
				row[i] = row[i].replace("'", "");				
				row[i] = row[i].replace("`", "");
			}			
		}
			
		if(row != null){
			int i = 0;
			nvb.setPosition_offered(null);
			nvb.setSta_in_ft_position(null);
			nvb.setNot_advertized(null);			
			nvb.setInsertDate(fileDate);
			//id set automatically
//System.out.println("row ["+i+"]: "+row[i]);			
			nvb.setOrg_unit_title((row[i] == null || "".equals(row[i].trim()) ? null : row[i])); ++i;			
//System.out.println("row ["+i+"]: "+row[i]);				
			nvb.setOrgId((row[i] == null || "".equals(row[i].trim()) ? 0 : new Integer(row[i])));  ++i;
//System.out.println("row ["+i+"]: "+row[i]);				
			nvb.setOrg_unit_type((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));  ++i; 
//System.out.println("setPositionId - row ["+i+"]: "+row[i]);				
			nvb.setPositionId((row[i] == null || "".equals(row[i].trim()) ? 0 : new Integer(row[i]))); ++i;
//System.out.println("setPosition_title - row ["+i+"]: "+row[i]);					
			nvb.setPosition_title((row[i] == null || "".equals(row[i].trim()) ? null : row[i])); ++i;
			nvb.setPosition_eg((row[i] == null || "".equals(row[i].trim()) ? null : row[i])); ++i;
			nvb.setPosition_esg((row[i] == null || "".equals(row[i].trim()) ? null : row[i])); ++i;																		
			nvb.setPosition_grade((row[i] == null || "".equals(row[i].trim()) ? null : row[i])); ++i;
			nvb.setAmount_assig((row[i] == null || "".equals(row[i].trim()) ? null : row[i])); ++i;
			nvb.setAmount_assig_curr((row[i] == null || "".equals(row[i].trim()) ? null : row[i])); ++i;
			nvb.setFuncArea((row[i] == null || "".equals(row[i].trim()) ? null : row[i].trim())); ++i;
			nvb.setFunc_area((row[i] == null || "".equals(row[i].trim()) ? null : row[i].trim())); ++i;
			nvb.setFund((row[i] == null || "".equals(row[i].trim()) ? null : row[i].trim()));	++i;
			nvb.setCost_center((row[i] == null || "".equals(row[i].trim()) ? null : row[i].trim()));	++i;
			nvb.setVacant_position((row[i] == null || "".equals(row[i].trim()) ? null : row[i].trim()));	++i;
			nvb.setFamily_name((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setFirst_name((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;						
			nvb.setPersonnelNo((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setPartTime_emp((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setEes_gr((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setEes_step((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setEes_eg((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setEes_esg((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;			
			nvb.setPositionOccupiedDate(row[i++], "dd.MM.yyyy");
			nvb.setEes_fund((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setEesFuncArea((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setEes_func_Area((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setEes_cost_center((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setNationality((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setRegion((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setSignatory((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setGrulac((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setGroup77((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setNam_countries((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setEu((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setGender((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setMarital_status((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			String nrOfDependents = (row[i] == null || "".equals(row[i].trim()) ? "0" : row[i]);  ++i;  
			nvb.setNoOfDependents(new Integer(nrOfDependents));																
			String noOfElFamMemb = row[i] == null || "".equals(row[i].trim()) ? "0" : row[i]; 
			nvb.setNoOfElFamMembForHL(new Integer (noOfElFamMemb));  ++i;
			nvb.setDobDate(row[i++], "dd.MM.yyyy");
			nvb.setEntryOnDutyDate(row[i++], "dd.MM.yyyy");	
			nvb.setContractEndDate(row[i++], "dd.MM.yyyy"); 
			nvb.setPost_adjustment((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setNet_base_salary((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setSalary_per_month((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;	
			nvb.setUsd_weekly((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setUsd_daily((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;						
			nvb.setEos_allowance_elig((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;	
			nvb.setRepatriation_grant_elig((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;			
			nvb.setCosts_removal_elig((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;				
			nvb.setPlace_of_residence((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;
			nvb.setPlace_of_recruitment((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));	++i;		
			nvb.setHome_leave((row[i] == null || "".equals(row[i].trim()) ? null : row[i]));							
		}
		
		return nvb;
	}
	
	
	
	
	public String UpdateDBwithHRInfo(ServletContext servletContext, MultipartFile multipartFile){
//		String retVal = "dataformsuccess";
		String retVal = "dataformRepDescription";
		
		String originalFileName = multipartFile.getOriginalFilename();
        String fileExtention = FilenameUtils.getExtension(originalFileName);
        
        Date insertDate = BusinessIntelligenceImpl.parseStringToDate(this.getInsertDateValue(), "yyyy_MM_dd");
        
        
                
        if(	originalFileName == null ||	originalFileName.equals("") || fileExtention == null ||  !"csv".equals(fileExtention.toLowerCase())){
        	errorMessage = "Please select the .CSV File containing  4 columns: 'Possition offered', 'STA in FT position', 'Not Advertized', 'Position ID'";
        	this.setErrorMessage(errorMessage);
        	retVal = "dataformerror";
        }else{
        	// Create a factory for disk-based file items
    		DiskFileItemFactory factory = new DiskFileItemFactory();
    		// maximum size that will be stored in memory
    		factory.setSizeThreshold(maxMemSize);
    		  
    		// Configure a repository (to ensure a secure temp location is used)
    		File repositoryFile = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
    		factory.setRepository(repositoryFile);

    		// Create a new file upload handler
    		ServletFileUpload upload = new ServletFileUpload(factory);
    		// maximum file size to be uploaded.
    		upload.setSizeMax( maxFileSize );

    		//CHECK IF positionIds provided in dem .csv File are contained in the DB and if yes, create list of UpdatePositions Beans to update the DB
			try {
				InputStream uploadedStream = multipartFile.getInputStream();
				CSVReader csvReader = new CSVReader(new InputStreamReader(uploadedStream), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 1);
				
				List <NetVacancy> nvList = dbManager.getAllNetVacanciesByInsertDate(insertDate);				
				insertDate = BusinessIntelligenceImpl.parseStringToDate(this.getInsertDateValue(), "yyyy_MM_dd"); // // Get selection date from dataform jsp and parse it to standard Date form								
				String[] row = null;
				while((row = csvReader.readNext()) != null) {
					String positionIdStr = row[3];
					if(positionIdStr != null && !positionIdStr.trim().equals("")){					
						Integer positionId = new Integer(positionIdStr);
						for(NetVacancy nv : nvList){
							if(nv.getPositionId().equals(positionId)){
								nv.setPositionId(positionId);
								nv.setPosition_offered((row[0] == null || "".equals(row[0]))? null : "x");
								nv.setSta_in_ft_position((row[1] == null || "".equals(row[1]))? null : "x");
								nv.setNot_advertized((row[2] == null || "".equals(row[2]))? null : "x");	
								
								dbManager.updatePosition(nv);
							}
						}	
					}
				}		
				
				csvReader.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return retVal;
	}
	
	
	
	public String InsertUpdateReportDescriptions(	String insertDateStr, Map<String, String[]> parameters){
		String retVal = "dataformsuccess";
		
		Date insertDate = BusinessIntelligenceImpl.parseStringToDate(insertDateStr, "yyyy_MM_dd");
		
		List <ReportDescription> rdList = dbManager.getAllReportDescriptionsByInsertDate(insertDate);
		if(rdList.size() > 0){
			for(ReportDescription rd : rdList){			
				for (String key : parameters.keySet()){
					System.out.println("JUHUUUU key:"+key);
					if(key.equals("explanation_"+rd.getDescription_class_id().toString())){
						String [] value = parameters.get(key);
						String description = value[0] == null ? "" : value[0];
						System.out.println("JUHUUUU value[0]:"+value[0]);
						rd.setDescription(description);
					}
				}
				rd.setInsertDate(insertDate);
				
				dbManager.saveReportDescription(insertDate, rd);
			}
		}else{
			for (String key : parameters.keySet()){
				System.out.println("JUHUUUU key:"+key);
				for (int i=1; i<=6; i++){
					if(key.equals("explanation_"+new Integer(i).toString())){
						String [] value = parameters.get(key);
						String description = value[0] == null ? "" : value[0];
						System.out.println("JUHUUUU value[0]:"+value[0]);
						ReportDescription rd = new ReportDescription();
						rd.setInsertDate(insertDate);
						rd.setDescription(description);
						rd.setDescription_class_id(new Long(i));
						dbManager.saveReportDescription(insertDate, rd);
					}
				}
			}
			
		}
				
		return retVal;
	}
	

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}



	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}


	public List<String> getInsertDates() {
		return insertDates;
	}



	public void setInsertDates(List<String> insertDates) {
		this.insertDates = insertDates;
	}



	public String getInsertDateId() {
		return insertDateId;
	}



	public void setInsertDateId(String insertDateId) {
		this.insertDateId = insertDateId;
	}



	public String getInsertDateValue() {
		return insertDateValue;
	}



	public void setInsertDateValue(String insertDateValue) {
		this.insertDateValue = insertDateValue;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public DatabaseManager getDbManager() {
		return dbManager;
	}
	public void setDbManager(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}
//	public List<ReportDescriptionClass> getReportDescriptionClassList() {
//		return reportDescriptionClassList;
//	}
//	public void setReportDescriptionClassList(List<ReportDescriptionClass> reportDescriptionClassList) {
//		this.reportDescriptionClassList = reportDescriptionClassList;
//	}
	public String getPreviousInsertDate() {
		return previousInsertDate;
	}
	public void setPreviousInsertDate(String previousInsertDate) {
		this.previousInsertDate = previousInsertDate;
	}
	public List<ReportDescCategoryBean> getRepDescBeanClassList() {
		return repDescBeanClassList;
	}
	public void setRepDescBeanClassList(List<ReportDescCategoryBean> repDescBeanClassList) {
		this.repDescBeanClassList = repDescBeanClassList;
	}
	
	
	

	
	
	
	

}
