package org.ctbto.hr.hcm.beans;

import org.ctbto.hr.hcm.hibernate.ReportDescriptionClass;

public class ReportDescCategoryBean {
	private Long 	id;
	private String 	repDescCategory;
	private String 	change;
	
	//default constructor
	public ReportDescCategoryBean(){}
	//overloaded constructor
	public ReportDescCategoryBean(ReportDescriptionClass rdc, String change){
		this.repDescCategory = rdc.getDescription_class();
		this.id = rdc.getId();
		this.change = change;
	}
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRepDescCategory() {
		return repDescCategory;
	}
	public void setRepDescCategory(String repDescCategory) {
		this.repDescCategory = repDescCategory;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
		
	
}
