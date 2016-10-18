-- ***** SEQUENCES ******
CREATE SEQUENCE seq_user MINVALUE 1;
CREATE SEQUENCE seq_vacancy MINVALUE 1;
CREATE SEQUENCE seq_category MINVALUE 1;
CREATE SEQUENCE seq_privilege MINVALUE 1;
CREATE SEQUENCE seq_privilege_group MINVALUE 1;
CREATE SEQUENCE seq_rep_desc MINVALUE 1;


-- ***** TABLES ******
CREATE TABLE dbUser (
   	uid INTEGER PRIMARY KEY DEFAULT nextval('seq_user'),
	userName VARCHAR NOT NULL,
	password VARCHAR NOT NULL,
	email VARCHAR NOT NULL,
	active VARCHAR NOT NULL,
	locked varchar NOT NULL,
	privilege_group varchar NOT NULL,
	creation_date DATE,
	last_update_date DATE,
	privilege_added VARCHAR
);

CREATE TABLE test (
    id INTEGER PRIMARY KEY DEFAULT nextval('seq_vacancy'),		
    insertDate DATE,
    Org_unit_Title VARCHAR,
    Org_ID INTEGER,
    Org_unit_type VARCHAR,
    Position_ID INTEGER,
    Position_Title VARCHAR,
    Position_EG VARCHAR,
    Position_ESG VARCHAR,
    Family_Name VARCHAR,
    First_Name VARCHAR,
    Personnel_No INTEGER
);

CREATE TABLE categories (
	id INTEGER PRIMARY KEY DEFAULT nextval('seq_category'),
	category VARCHAR
);


CREATE TABLE not_advertized (
	id INTEGER PRIMARY KEY DEFAULT nextval('seq_category'),
	not_advertized				VARCHAR,
	positionId 					INTEGER,
	active						VARCHAR,
	last_update_date			DATE,  
	last_update_by				VARCHAR	
);


CREATE TABLE SAP_Staffing_Situation (
	position_offered			VARCHAR,
	sta_in_ft_position			VARCHAR,
	not_advertized				VARCHAR,
	id INTEGER PRIMARY KEY DEFAULT nextval('seq_vacancy'),		
	insertDate 					DATE,   
	org_unit_title 				VARCHAR,     
	orgId 						INTEGER,     
	org_unit_type 				VARCHAR,     
	positionId 					INTEGER, 	
	position_title 				VARCHAR,     
	position_eg 				VARCHAR,     
	position_esg				VARCHAR,     
	position_grade 				VARCHAR,     
	amount_assig				VARCHAR,     
	amount_assig_curr			VARCHAR,     
	funcArea					VARCHAR,     
	func_area					VARCHAR,     
	fund						VARCHAR,     
	cost_center					VARCHAR,     
	vacant_position				VARCHAR,     
	family_name					VARCHAR,     
	first_name					VARCHAR,     
	personnelNo 				VARCHAR,     
	partTime_emp				VARCHAR,     
	ees_gr						VARCHAR,     
	ees_step					VARCHAR,     
	ees_eg						VARCHAR,     
	ees_esg						VARCHAR,     
	positionOccupiedDate    	DATE,
	ees_fund					VARCHAR,     
	eesFuncArea 				VARCHAR,     
	ees_func_Area				VARCHAR,     
	ees_cost_center				VARCHAR,     
	nationality					VARCHAR, 
	region						VARCHAR,
	signatory					VARCHAR,
	grulac						VARCHAR,
	group77						VARCHAR,
	nam_countries				VARCHAR,
	eu							VARCHAR,
	gender						VARCHAR,     
	marital_status				VARCHAR,     
	noOfDependents				INTEGER,     
	noOfElFamMembForHL			INTEGER,     
	dobDate						DATE,        
	entryOnDutyDate				DATE,        
	contractEndDate				DATE,        
	post_adjustment				VARCHAR,     
	net_base_salary				VARCHAR,     
	salary_per_month			VARCHAR,     
	usd_weekly					VARCHAR,     
	usd_daily					VARCHAR,     
	eos_allowance_elig			VARCHAR, 
	repatriation_grant_elig		VARCHAR, 
	costs_removal_elig			VARCHAR, 
	place_of_residence			VARCHAR, 
	place_of_recruitment		VARCHAR, 
	home_leave					VARCHAR  
);


CREATE TABLE report_description_class (
    id INTEGER PRIMARY KEY,		
    description_class VARCHAR UNIQUE
);

CREATE TABLE report_description (
    id INTEGER PRIMARY KEY DEFAULT nextval('seq_rep_desc'),		
    insertDate DATE,
    description_class_id INTEGER REFERENCES report_description_class(id),
    description VARCHAR
);


CREATE TYPE date_value_record_type AS (
	insertdate date, 
	value int
);





