package org.ctbto.hr.hcm.beans;

import java.util.List;

import org.ctbto.hr.hcm.hibernate.ReportDescription;

public class ReportDescriptionBean {
	private String insertDate;
	private List<NetVacancyRepDescBean> reportDescriptionList;
	
	
	
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	
	public List<NetVacancyRepDescBean> getReportDescriptionList() {
		return reportDescriptionList;
	}
	public void setReportDescriptionList(List<NetVacancyRepDescBean> reportDescriptionList) {
		this.reportDescriptionList = reportDescriptionList;
	}
	
	
	
	
	
}
