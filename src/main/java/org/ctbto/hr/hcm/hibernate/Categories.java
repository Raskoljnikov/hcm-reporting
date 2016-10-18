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
@Table(name = "categories")
public class Categories implements Serializable, UEntity {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long 	id;
	private String 	category;
	
	@Id 
	@Column(name = "id")
	@SequenceGenerator(name="gen_seq_category",sequenceName="seq_category")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="gen_seq_category")
	public Long getId() {
		return id;
	}	
	
	@Column(name = "category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
