package org.ctbto.hr.hcm.hibernate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "report_description")
public class ReportDescription implements Serializable, UEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long 	id;
	private Date	insertDate;
	private Long	description_class_id;
	private String 	description ;

	
	@Id 
	@Column(name = "id")
	@SequenceGenerator(name="gen_seq_rep_desc",sequenceName="seq_rep_desc ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="gen_seq_rep_desc")
	public Long getId() {
		return id;
	}	
	
		
	

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInsertDate() {
		return insertDate;
	}
	
	@Column(name = "insertDate")
	public void setInsertDate(Date insertDate) {
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


		
	
	
	
}
