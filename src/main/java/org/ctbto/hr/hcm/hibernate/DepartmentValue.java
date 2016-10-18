package org.ctbto.hr.hcm.hibernate;

public class DepartmentValue {

	public String department;
	public Integer value;
	
	public DepartmentValue (){
		
	}
	
	public DepartmentValue (String department, Integer value){
		this.department = department;
		this.value = value;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}
	
}
