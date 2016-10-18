--CREATE USERSR ACCORDING TO THE LDAP
CREATE OR REPLACE FUNCTION create_dbusers() RETURNS void AS $reffunc$
DECLARE
	v_dbuser_row  	dbuser%ROWTYPE;
BEGIN   			
	delete from dbUser;
	
	FOR v_dbuser_row IN 
			SELECT 	nextval('seq_user'), 
					lower(family_name), 
					'authentification through LDAP', 
					min(first_name) || '.' || min(family_name) || '@CTBTO.ORG' as email, 
					'Y' as ative, 
					'N' as blocked, 
					'user' as privilege_group, CURRENT_DATE as creation_date,  
					CURRENT_DATE as last_update_date, 
					'automatic'
			FROM sap_staffing_situation  
			WHERE
			family_name <> ''
			GROUP BY family_name	
	LOOP

		RAISE NOTICE 'UPDATING TABLE dbUser. Setting password to: %',v_dbuser_row.username;
		--UPDATE dbUser SET num = counter WHERE CURRENT OF c ;
		INSERT INTO dbUser VALUES (	v_dbuser_row.uid, 
									v_dbuser_row.username, 
									v_dbuser_row.password, 
									v_dbuser_row.email, 
									v_dbuser_row.active, 
									v_dbuser_row.locked, 
									v_dbuser_row.privilege_group, 
									v_dbuser_row.creation_date, 
									v_dbuser_row.last_update_date, 
									v_dbuser_row.privilege_added);
	END LOOP;
RETURN;
 
END;
$reffunc$ 
LANGUAGE plpgsql;

select create_dbusers(); 

	

UPDATE dbUser set privilege_group = 'administrator', privilege_added = 'manually' where username = 'hadzijusufovic';
UPDATE dbUser set privilege_group = 'administrator', privilege_added = 'manually' where username = 'alili';
UPDATE dbUser set privilege_group = 'administrator', privilege_added = 'manually' where username = 'thampi';

UPDATE dbUser set privilege_group = 'chief_hr', privilege_added = 'manually' where username = 'mekonnen';

UPDATE dbUser set privilege_group = 'hr_officer', privilege_added = 'manually' where username = 'sim';
UPDATE dbUser set privilege_group = 'hr_officer', privilege_added = 'manually' where username = 'khouni';


UPDATE dbUser set privilege_group = 'office_es', privilege_added = 'manually' where username = 'bauer';
UPDATE dbUser set privilege_group = 'office_es', privilege_added = 'manually' where username = 'cibi';
UPDATE dbUser set privilege_group = 'office_es', privilege_added = 'manually' where username = 'berrens';
UPDATE dbUser set privilege_group = 'office_es', privilege_added = 'manually' where username = 'dubourg';
UPDATE dbUser set privilege_group = 'office_es', privilege_added = 'manually' where username = 'zerbo';

UPDATE dbUser set privilege_group = 'budget_impl', privilege_added = 'manually' where username = 'khaskhanova';
UPDATE dbUser set privilege_group = 'budget_impl', privilege_added = 'manually' where username = 'higa';
UPDATE dbUser set privilege_group = 'budget_impl', privilege_added = 'manually' where username = 'dalgamouni';
UPDATE dbUser set privilege_group = 'budget_impl', privilege_added = 'manually' where username = 'mosoci';





/*    ADMIN THINGS - DO NOT DELETE!!!!

select *
from pg_stat_activity
where datname = 'hr'




GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA sap_hcm TO report_hcm;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA sap_hcm TO report_hcm;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA sap_hcm TO report_hcm;

*/
