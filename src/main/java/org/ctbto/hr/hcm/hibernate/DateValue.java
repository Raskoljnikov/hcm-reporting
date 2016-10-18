package org.ctbto.hr.hcm.hibernate;

import java.util.Date;
import java.text.SimpleDateFormat;


public class DateValue {

	public Date insertDate;
	public int value;
	
	//default constructor
	public DateValue(){}
	
	public DateValue(Date insertDate, int value){
		this.insertDate = insertDate;
		this.value = value;
	}


	public Date getInsertDate() {
		return insertDate;
	}


	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
	
	public String getFormatedInsertDate(){
		String retVal = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
		if(this.insertDate != null){
			retVal = sdf.format(this.insertDate);
		}
		return retVal;
	}
	
	
	
	
	
}
