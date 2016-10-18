package org.ctbto.hr.hcm.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "report_description_class")
public class ReportDescriptionClass implements Serializable, UEntity {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long 	id;
	private String 	description_class ;
	
	@Id 
	@Column(name = "id")
	@SequenceGenerator(name="gen_seq_category",sequenceName="seq_category")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="gen_seq_category")
	public Long getId() {
		return id;
	}	
	
	@Column(name = "description_class")
	public String getDescription_class() {
		return description_class;
	}

	public void setDescription_class(String description_class) {
		this.description_class = description_class;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
