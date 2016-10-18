package org.ctbto.hr.hcm.beans;

import org.ctbto.hr.hcm.hibernate.ReportDescription;
import org.ctbto.hr.hcm.utils.BusinessIntelligenceImpl;

public class NetVacancyRepDescBean {
	private static final long serialVersionUID = 1L;
	private Long 	id;
	private String	insertDate;
	private Long	description_class_id;
	private String 	description ;
	private String  description_class_value;
	
	
	public NetVacancyRepDescBean() {}

	public NetVacancyRepDescBean(ReportDescription rd, String descritptionClassValue) {
		this.id = rd.getId();
		this.insertDate = BusinessIntelligenceImpl.parseJavaUtilDatetoDatabaseString(rd.getInsertDate());
		this.description_class_id = rd.getDescription_class_id();
		this.description = rd.getDescription();
		
		this.description_class_value = descritptionClassValue;		
	}

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public Long getDescription_class_id() {
		return description_class_id;
	}
	public void setDescription_class_id(Long description_class_id) {
		this.description_class_id = description_class_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription_class_value() {
		return description_class_value;
	}
	public void setDescription_class_value(String description_class_value) {
		this.description_class_value = description_class_value;
	}
	
	
	

}
