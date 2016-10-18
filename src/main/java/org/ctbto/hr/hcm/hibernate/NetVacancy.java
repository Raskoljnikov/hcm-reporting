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

import org.ctbto.hr.hcm.utils.BusinessIntelligenceImpl;


	
	@Entity
	@Table(name="sap_staffing_situation")
public class NetVacancy implements Serializable, UEntity {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private Long id;
		private String	position_offered;			
		private String	sta_in_ft_position;			
		private String	not_advertized;
		private Date	insertDate; 					
		private String	org_unit_title; 				
		private Integer	orgId; 						
		private String	org_unit_type; 				
		private Integer	positionId; 					
		private String	position_title; 				
		private String	position_eg; 				
		private String	position_esg;				
		private String	position_grade; 				
		private String	amount_assig;				
		private String	amount_assig_curr;			
		private String	funcArea;					
		private String	func_area;					
		private String	fund;						
		private String	cost_center;					
		private String	vacant_position;				
		private String	family_name;					
		private String	first_name;					
		private String	personnelNo; 				
		private String	partTime_emp;				
		private String	ees_gr;						
		private String	ees_step;					
		private String	ees_eg;						
		private String	ees_esg;						
		private Date	positionOccupiedDate;    	
		private String	ees_fund;					
		private String	eesFuncArea; 				
		private String	ees_func_Area;				
		private String	ees_cost_center;				
		private String	nationality;					
		private String	region;						
		private String	signatory;					
		private String	grulac;						
		private String	group77;						
		private String	nam_countries;				
		private String	eu;							
		private String	gender;						
		private String	marital_status;				
		private Integer	noOfDependents;				
		private Integer	noOfElFamMembForHL;			
		private Date	dobDate;						
		private Date	entryOnDutyDate;				
		private Date	contractEndDate;				
		private String	post_adjustment;				
		private String	net_base_salary;				
		private String	salary_per_month;			
		private String	usd_weekly;					
		private String	usd_daily;					
		private String	eos_allowance_elig;			
		private String	repatriation_grant_elig;		
		private String	costs_removal_elig;			
		private String	place_of_residence;			
		private String	place_of_recruitment;		
		private String  home_leave;					
		
		
		
		@Id
	    @Column(name = "id")
		@SequenceGenerator(name="gen_seq_vacancy", sequenceName="seq_vacancy")
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_seq_vacancy")
		public Long getId() {
			// TODO Auto-generated method stub
			return this.id;
		}

		

		@Column(name = "position_offered")
		public String getPosition_offered() {
			return position_offered;
		}



		public void setPosition_offered(String position_offered) {
			this.position_offered = position_offered == null ? null : position_offered.trim();
		}


		@Column(name = "sta_in_ft_position")
		public String getSta_in_ft_position() {
			return sta_in_ft_position;
		}



		public void setSta_in_ft_position(String sta_in_ft_position) {
			this.sta_in_ft_position = sta_in_ft_position == null ? null : sta_in_ft_position.trim();
		}


		@Column(name = "not_advertized")
		public String getNot_advertized() {
			return not_advertized;
		}


		public void setNot_advertized(String not_advertized) {
			this.not_advertized = not_advertized == null ? null : not_advertized.trim();
		}


		@Column(name = "insertdate")
		public Date getInsertDate() {
			return insertDate;
		}



		public void setInsertDate(Date insertDate) {
			this.insertDate = insertDate;
		}


		@Column(name = "org_unit_title")
		public String getOrg_unit_title() {
			return org_unit_title;
		}



		public void setOrg_unit_title(String org_unit_title) {
			this.org_unit_title = org_unit_title == null ? null : org_unit_title.trim();
		}


		@Column(name = "orgid")
		public Integer getOrgId() {
			return orgId;
		}



		public void setOrgId(Integer orgId) {
			this.orgId = orgId == null ? 0 : orgId;
		}


		@Column(name = "org_unit_type")
		public String getOrg_unit_type() {
			return org_unit_type;
		}



		public void setOrg_unit_type(String org_unit_type) {
			this.org_unit_type = org_unit_type == null ? null : org_unit_type.trim();
		}


		@Column(name = "positionid")
		public Integer getPositionId() {
			return positionId;
		}



		public void setPositionId(Integer positionId) {
			this.positionId = positionId == null ? 0 : positionId;
		}


		@Column(name = "position_title")
		public String getPosition_title() {
			return position_title;
		}



		public void setPosition_title(String position_title) {
			this.position_title = position_title == null ? null : position_title.trim();
		}


		@Column(name = "position_eg")
		public String getPosition_eg() {
			return position_eg;
		}



		public void setPosition_eg(String position_eg) {
			this.position_eg = position_eg == null ? null : position_eg.trim();
		}


		@Column(name = "position_esg")
		public String getPosition_esg() {
			return position_esg;
		}



		public void setPosition_esg(String position_esg) {
			this.position_esg = position_esg == null ? null : position_esg.trim();
		}


		@Column(name = "position_grade")
		public String getPosition_grade() {
			return position_grade;
		}



		public void setPosition_grade(String position_grade) {
			this.position_grade = position_grade == null ? null : position_grade.trim();
		}


		@Column(name = "amount_assig")
		public String getAmount_assig() {
			return amount_assig;
		}



		public void setAmount_assig(String amount_assig) {
			this.amount_assig = amount_assig == null ? null : amount_assig.trim();
		}


		@Column(name = "amount_assig_curr")
		public String getAmount_assig_curr() {
			return amount_assig_curr;
		}



		public void setAmount_assig_curr(String amount_assig_curr) {
			this.amount_assig_curr = amount_assig_curr == null ? null : amount_assig_curr.trim();
		}


		@Column(name = "funcarea")
		public String getFuncArea() {
			return funcArea;
		}



		public void setFuncArea(String funcArea) {
			this.funcArea = funcArea == null ? null : funcArea.trim();
		}


		@Column(name = "func_area")
		public String getFunc_area() {
			return func_area;
		}



		public void setFunc_area(String func_area) {
			this.func_area = func_area == null ? null : func_area.trim();
		}


		@Column(name = "fund")
		public String getFund() {
			return fund;
		}



		public void setFund(String fund) {
			this.fund = fund == null ? null : fund.trim();
		}


		@Column(name = "cost_center")
		public String getCost_center() {
			return cost_center;
		}



		public void setCost_center(String cost_center) {
			this.cost_center = cost_center == null ? null : cost_center.trim();
		}


		@Column(name = "vacant_position")
		public String getVacant_position() {
			return vacant_position;
		}



		public void setVacant_position(String vacant_position) {
			this.vacant_position = vacant_position == null ? null : vacant_position.trim();
		}


		@Column(name = "family_name")
		public String getFamily_name() {
			return family_name;
		}



		public void setFamily_name(String family_name) {
			this.family_name = family_name == null ? null : family_name.trim();
		}


		@Column(name = "first_name")
		public String getFirst_name() {
			return first_name;
		}



		public void setFirst_name(String first_name) {
			this.first_name = first_name == null ? null : first_name.trim();
		}


		@Column(name = "personnelno")
		public String getPersonnelNo() {
			return personnelNo;
		}



		public void setPersonnelNo(String personnelNo) {
			this.personnelNo = personnelNo == null ? null : personnelNo.trim();
		}


		@Column(name = "parttime_emp")
		public String getPartTime_emp() {
			return partTime_emp;
		}



		public void setPartTime_emp(String partTime_emp) {
			this.partTime_emp = partTime_emp == null ? null : partTime_emp.trim();
		}


		@Column(name = "ees_gr")
		public String getEes_gr() {
			return ees_gr;
		}



		public void setEes_gr(String ees_gr) {
			this.ees_gr = ees_gr == null ? null : ees_gr.trim();
		}


		@Column(name = "ees_step")
		public String getEes_step() {
			return ees_step;
		}



		public void setEes_step(String ees_step) {
			this.ees_step = ees_step == null ? null : ees_step.trim();
		}


		@Column(name = "ees_eg")
		public String getEes_eg() {
			return ees_eg;
		}



		public void setEes_eg(String ees_eg) {
			this.ees_eg = ees_eg == null ? null : ees_eg.trim();
		}


		@Column(name = "ees_esg")
		public String getEes_esg() {
			return ees_esg;
		}



		public void setEes_esg(String ees_esg) {
			this.ees_esg = ees_esg == null ? null : ees_esg.trim();
		}


		@Column(name = "positionoccupieddate")
		public Date getPositionOccupiedDate() {
			return positionOccupiedDate;
		}



		public void setPositionOccupiedDate(Date positionOccupiedDate) {
			this.positionOccupiedDate = positionOccupiedDate;
		}
		
		
		public void setPositionOccupiedDate(String positionOccupiedDateStr, String format) {
			Date positionOccupiedDate = null;
			if(positionOccupiedDateStr != null && !positionOccupiedDateStr.equals("")){
				positionOccupiedDate = BusinessIntelligenceImpl.parseStringToDate(positionOccupiedDateStr, format);
			}
			this.positionOccupiedDate = positionOccupiedDate;
		}
		
		


		@Column(name = "ees_fund")
		public String getEes_fund() {
			return ees_fund;
		}



		public void setEes_fund(String ees_fund) {
			this.ees_fund = ees_fund == null ? null : ees_fund.trim();
		}


		@Column(name = "eesfuncarea")
		public String getEesFuncArea() {
			return eesFuncArea;
		}



		public void setEesFuncArea(String eesFuncArea) {
			this.eesFuncArea = eesFuncArea == null ? null : eesFuncArea.trim();
		}


		@Column(name = "ees_func_area")
		public String getEes_func_Area() {
			return ees_func_Area;
		}



		public void setEes_func_Area(String ees_func_Area) {
			this.ees_func_Area = ees_func_Area == null ? null : ees_func_Area.trim();
		}


		@Column(name = "ees_cost_center")
		public String getEes_cost_center() {
			return ees_cost_center;
		}



		public void setEes_cost_center(String ees_cost_center) {
			this.ees_cost_center = ees_cost_center == null ? null : ees_cost_center.trim();
		}


		@Column(name = "nationality")
		public String getNationality() {
			return nationality;
		}



		public void setNationality(String nationality) {
			this.nationality = nationality == null ? null : nationality.trim();
		}


		@Column(name = "region")
		public String getRegion() {
			return region;
		}



		public void setRegion(String region) {
			this.region = region == null ? null : region.trim();
		}


		@Column(name = "signatory")
		public String getSignatory() {
			return signatory;
		}



		public void setSignatory(String signatory) {
			this.signatory = signatory == null ? null : signatory.trim();
		}


		@Column(name = "grulac")
		public String getGrulac() {
			return grulac;
		}



		public void setGrulac(String grulac) {
			this.grulac = grulac == null ? null : grulac.trim();
		}


		@Column(name = "group77")
		public String getGroup77() {
			return group77;
		}



		public void setGroup77(String group77) {
			this.group77 = group77 == null ? null : group77.trim();
		}


		@Column(name = "nam_countries")
		public String getNam_countries() {
			return nam_countries;
		}



		public void setNam_countries(String nam_countries) {
			this.nam_countries = nam_countries == null ? null : nam_countries.trim();
		}


		@Column(name = "eu")
		public String getEu() {
			return eu;
		}



		public void setEu(String eu) {
			this.eu = eu == null ? null : eu.trim();
		}


		@Column(name = "gender")
		public String getGender() {
			return gender;
		}



		public void setGender(String gender) {
			this.gender = gender == null ? null : gender.trim();
		}


		@Column(name = "marital_status")
		public String getMarital_status() {
			return marital_status;
		}



		public void setMarital_status(String marital_status) {
			this.marital_status = marital_status == null ? null : marital_status.trim();
		}


		@Column(name = "noofdependents")
		public Integer getNoOfDependents() {
			return noOfDependents;
		}



		public void setNoOfDependents(Integer noOfDependents) {
			this.noOfDependents = noOfDependents == null ? 0 : noOfDependents;
		}


		@Column(name = "noofelfammembforhl")
		public Integer getNoOfElFamMembForHL() {
			return noOfElFamMembForHL;
		}



		public void setNoOfElFamMembForHL(Integer noOfElFamMembForHL) {
			this.noOfElFamMembForHL = noOfElFamMembForHL == null ? 0 : noOfElFamMembForHL;
		}


		@Column(name = "dobdate")
		public Date getDobDate() {
			return dobDate;
		}



		public void setDobDate(Date dobDate) {
			this.dobDate = dobDate;
		}
		
		public void setDobDate(String dobDateStr, String format) {
			Date dobDate = null;
			if(dobDateStr != null && !dobDateStr.equals("")){
				dobDate = BusinessIntelligenceImpl.parseStringToDate(dobDateStr, format);
			}
			this.dobDate = dobDate;
		}


		@Column(name = "entryondutydate")
		public Date getEntryOnDutyDate() {
			return entryOnDutyDate;
		}



		public void setEntryOnDutyDate(Date entryOnDutyDate) {
			this.entryOnDutyDate = entryOnDutyDate;
		}
		
		public void setEntryOnDutyDate(String entryOnDutyDateStr, String format) {
			Date entryOnDutyDate = null;
			if(entryOnDutyDateStr != null && !entryOnDutyDateStr.equals("")){
				entryOnDutyDate = BusinessIntelligenceImpl.parseStringToDate(entryOnDutyDateStr, format);
			}
			this.entryOnDutyDate = entryOnDutyDate;
		}


		@Column(name = "contractenddate")
		public Date getContractEndDate() {
			return contractEndDate;
		}



		public void setContractEndDate(Date contractEndDate) {
			this.contractEndDate = contractEndDate;
		}
		
		public void setContractEndDate(String contractEndDateStr, String format) {
			Date contractEndDate = null;
			if(contractEndDateStr != null && !contractEndDateStr.equals("")){
				contractEndDate = BusinessIntelligenceImpl.parseStringToDate(contractEndDateStr, format);
			}
			this.contractEndDate = contractEndDate;
		}


		@Column(name = "post_adjustment")
		public String getPost_adjustment() {
			return post_adjustment;
		}



		public void setPost_adjustment(String post_adjustment) {
			this.post_adjustment = post_adjustment == null ? null : post_adjustment.trim();
		}


		@Column(name = "net_base_salary")
		public String getNet_base_salary() {
			return net_base_salary;
		}



		public void setNet_base_salary(String net_base_salary) {
			this.net_base_salary = net_base_salary == null ? null : net_base_salary.trim();
		}


		@Column(name = "salary_per_month")
		public String getSalary_per_month() {
			return salary_per_month;
		}



		public void setSalary_per_month(String salary_per_month) {
			this.salary_per_month = salary_per_month == null ? null : salary_per_month.trim();
		}


		@Column(name = "usd_weekly")
		public String getUsd_weekly() {
			return usd_weekly;
		}



		public void setUsd_weekly(String usd_weekly) {
			this.usd_weekly = usd_weekly == null ? null : usd_weekly.trim();
		}


		@Column(name = "usd_daily")
		public String getUsd_daily() {
			return usd_daily;
		}



		public void setUsd_daily(String usd_daily) {
			this.usd_daily = usd_daily == null ? null : usd_daily.trim();
		}


		@Column(name = "eos_allowance_elig")
		public String getEos_allowance_elig() {
			return eos_allowance_elig;
		}



		public void setEos_allowance_elig(String eos_allowance_elig) {
			this.eos_allowance_elig = eos_allowance_elig == null ? null : eos_allowance_elig.trim();
		}


		@Column(name = "repatriation_grant_elig")
		public String getRepatriation_grant_elig() {
			return repatriation_grant_elig;
		}



		public void setRepatriation_grant_elig(String repatriation_grant_elig) {
			this.repatriation_grant_elig = repatriation_grant_elig == null ? null : repatriation_grant_elig.trim();
		}


		@Column(name = "costs_removal_elig")
		public String getCosts_removal_elig() {
			return costs_removal_elig;
		}



		public void setCosts_removal_elig(String costs_removal_elig) {
			this.costs_removal_elig = costs_removal_elig == null ? null : costs_removal_elig.trim();
		}


		@Column(name = "place_of_residence")
		public String getPlace_of_residence() {
			return place_of_residence;
		}



		public void setPlace_of_residence(String place_of_residence) {
			this.place_of_residence = place_of_residence == null ? null : place_of_residence.trim();
		}


		@Column(name = "place_of_recruitment")
		public String getPlace_of_recruitment() {
			return place_of_recruitment;
		}



		public void setPlace_of_recruitment(String place_of_recruitment) {
			this.place_of_recruitment = place_of_recruitment == null ? null : place_of_recruitment.trim();
		}


		@Column(name = "home_leave")
		public String getHome_leave() {
			return home_leave;
		}



		public void setHome_leave(String home_leave) {
			this.home_leave = home_leave == null ? null : home_leave.trim();
		}



		public void setId(Long id) {
			this.id = id;
		}
	
		
		
	

}
