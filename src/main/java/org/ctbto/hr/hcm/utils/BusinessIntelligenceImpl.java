package org.ctbto.hr.hcm.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public final class BusinessIntelligenceImpl {
	public static final Logger logger = Logger.getLogger(BusinessIntelligenceImpl.class.getName());
	public static final String sortASC = "ASC";
	public static final String sortDESC = "DESC";
	public static final char cvsSeparatorSEMICOLON = ';';
	public static final char cvsSeparatorCOMMA = ',';
	
	/**
	 * Returns a List of File names <String> of .csv files exported from SAP
	 * look like this "SAP_Staff_2016_06_01.csv"
	 * This function copies the date part "2016_06_01", 
	 * if parameter transform is true then transforms it to the format "2016 06 01"
	 * and adds it to the list. 
	 * <p>
	 * @param		tranform a boolean if the date String needs to be transformed or not
	 * @param 		ASC_DESC	String - if ASC -> sort the list ascending 
	 * @return      List of Strings
	 */
	public static List <String> getFileDatesAsString(boolean transform, String ASC_DESC){
		List <String> results = new ArrayList<String>();
		List <String> tempResults = new ArrayList<String>();
		String pathToCSVDir = UPropertiesUtil.getProperty("path.to.sap.files");
		
		
		File[] files = new File(pathToCSVDir).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 
		if(files != null){
			for (File file : files) {
				String extension = "";
				String dateString = "";
			    if (file.isFile()) {
			    	if (file.getName() != null && !"".equals(file.getName())){
			    		extension = file.getName().substring(file.getName().length() - 3,file.getName().length());			    		
			    		if(extension != null && extension.equalsIgnoreCase("csv")){
			    			dateString = file.getName().substring(10,20).trim();
				        	if (transform){
					        	if(!"".equals(dateString)){
					        		String xyz = dateString.substring(0,4) +" "+ dateString.substring(5,7) +" "+ dateString.substring(8,dateString.length());
						        	dateString = xyz;
						        	logger.debug("getFileDatesAsString(boolean transform, String pathToCSVDir) -adding dateString:"+dateString);
					        	}
				        	}
				        	tempResults.add(dateString);	
			    		}
			        }
			    }
			}
			List <Date> fileDates = getFileDates(tempResults, ASC_DESC);
			Iterator <Date> it = fileDates.iterator();
			while(it.hasNext()){
				Date fileDate = it.next();
				String fileDateString = BusinessIntelligenceImpl.parseJavaUtilDatetoString(fileDate);
				results.add(fileDateString);
			}
		}
		return results;
	}
	
	
	
	/**
	 * Returns a List of Dates in format yyyy_MM_dd
	 * <p>
	 * @param		fileDatesStringList - List of Strings in format yyyy_MM_dd
	 * @param 		ASC_DESC	String - if ASC -> sort the list ascending 
	 * @return      List of Dates
	 */
	public static List <Date> getFileDates(List <String> fileDatesStringList, String ASC_DESC){
		List <Date> results = new ArrayList <Date> ();
		if (fileDatesStringList != null){
			Iterator <String> it = fileDatesStringList.iterator();
			while (it.hasNext()){
				String fileDatesString = it.next();	
				if(!"".equals(fileDatesString)){
					Date parseDate = BusinessIntelligenceImpl.parseStringToDate(fileDatesString, "yyyy_MM_dd");
					results.add(parseDate);
				}	
			}
		}
		if(ASC_DESC.equals(sortDESC)){
			Collections.sort(results, new OutcomeDescComparator());
		}else if(ASC_DESC.equals(sortASC)){
			Collections.sort(results, new OutcomeAscComparator());
		}
		
		return results;
	}
	
	
	public static java.sql.Date parseStringToSQLDate(String insertDate, String format){
		java.sql.Date retVal = new java.sql.Date(new Date().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(format);		
		try {
			retVal =  new java.sql.Date(sdf.parse(insertDate).getTime());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static Date parseStringToDate(String insertDate, String format){
		Date retVal = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);		
		try {
			retVal =  sdf.parse(insertDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static String parseJavaUtilDatetoString(Date date){
		String retVal = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");		
		retVal =  sdf.format(date);
		return retVal;
	}
	
	public static String parseJavaUtilDatetoDatabaseString(Date date){
		String retVal = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		retVal =  sdf.format(date);
		return retVal;
	}
	

}
