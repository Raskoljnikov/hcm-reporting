
/* FUNCTIONS */
DROP FUNCTION get_user (character varying, character varying);
DROP FUNCTION check_authentication( IN character varying, IN character varying, OUT integer);
DROP FUNCTION selectDistinctInsertDates();
DROP FUNCTION getjsfunctiondata(integer, integer);
DROP FUNCTION get_js_function_data_from_date(integer,date);
DROP FUNCTION get_js_function_data_from_date_array(integer,date[]);
DROP FUNCTION get_js_function_drilldown(INTEGER, VARCHAR);
DROP FUNCTION get_user_privileges(IN VARCHAR);
DROP FUNCTION get_js_function_data_from_date_string(INTEGER, VARCHAR);
DROP FUNCTION create_dbusers();


/* TABLES */
DROP TABLE dbUser; 
DROP TABLE categories; 
DROP TABLE SAP_Staffing_Situation; 
DROP TABLE report_description;
DROP TABLE report_description_class;



/* SEQUENCES */
DROP SEQUENCE seq_user;                         -- Kunde
DROP SEQUENCE seq_vacancy;                      -- Vacancy
DROP SEQUENCE seq_category;						-- Category
DROP SEQUENCE seq_privilege;					-- PRIVILEGE
DROP SEQUENCE seq_privilege_group;				-- Privilege Group
DROP SEQUENCE seq_rep_desc;						-- Report description


/* CUSTOM VALUE TYPES */
DROP TYPE date_value_record_type;




