CREATE OR REPLACE FUNCTION get_user(
    usrname character varying,
    pwd character varying)
  RETURNS refcursor AS $BODY$
DECLARE	
  ref_cursor 		refcursor;
  v_tmp_pwd		dbUser.password%TYPE;
  
BEGIN
	--check if username & password matchen
	SELECT password INTO v_tmp_pwd 
	FROM dbUser dbu
	WHERE dbu.userName = usrname;

	IF FOUND THEN
		IF v_tmp_pwd = pwd THEN  -- user/password match
			OPEN ref_cursor FOR
				SELECT 1 as returnVal, username, email, locked, privilege_group
				FROM dbUser dbu
				WHERE dbu.userName = usrname;

		ELSE			-- user/password do not match
			OPEN ref_cursor FOR
				SELECT -1 as returnVal, null, null, null, null;
			
		END IF;	
	END IF;
	
	RETURN ref_cursor;

	CLOSE ref_cursor;

	EXCEPTION WHEN OTHERS THEN
	RAISE;		
END;
$BODY$ LANGUAGE plpgsql 

-- Function: public.check_authentication(character varying, character varying)
-- DROP FUNCTION public.check_authentication(character varying, character varying);

CREATE OR REPLACE FUNCTION check_authentication(
    IN usrname character varying,
    IN pwd character varying,
    OUT retval integer)
  RETURNS integer AS $$
DECLARE	
  v_tmp_pwd		varchar;
BEGIN
retVal := -1;

	--check if username & password matchen
	SELECT password INTO v_tmp_pwd 
	FROM dbUser dbu
	WHERE dbu.userName = usrname;

	IF FOUND THEN
		IF v_tmp_pwd = pwd THEN
			retVal := 1;
		END IF;	
	END IF;

END;
  $$ LANGUAGE plpgsql;


-- Function: selectDistinctInsertDates() - returns refcursor of all distinct insertion dates 
CREATE OR REPLACE FUNCTION selectDistinctInsertDates() RETURNS refcursor AS  $$
DECLARE	
 c2 refcursor; 
BEGIN
	
OPEN c2 FOR
	select insertdate from test
	group by insertdate
	order by insertdate desc;
RETURN retVal;
END;
$$ LANGUAGE plpgsql ;   
  



-- STARA FUNCTION getJSFunctionSeriesData - SAMO SA BROJEM STACKBAROVA
CREATE OR REPLACE FUNCTION getjsfunctiondata(categoryid integer, nr_of_bars_in_barchart integer) RETURNS SETOF date_value_record_type AS $reffunc$
DECLARE
	my_date_value_record	date_value_record_type;
  	v_insert_date				date;
BEGIN   	
	FOR v_insert_date IN	
		SELECT distinct insertdate 
		FROM sap_staffing_situation
		GROUP BY sap_staffing_situation.insertdate
		ORDER BY sap_staffing_situation.insertdate desc
		LIMIT nr_of_bars_in_barchart
	LOOP
		SELECT v_insert_date INTO my_date_value_record.insertdate;

		RAISE NOTICE 'v_insert_date: %',v_insert_date;

		IF categoryId = 1 THEN			--VACANT 
			SELECT count (*) INTO my_date_value_record.value	
			FROM sap_staffing_situation  
			WHERE
			positionid != 20250115 AND
			position_eg IN('International Staff', 'Local Staff') AND
			position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
			fund = 'GF' AND
			ees_gr IS NULL AND
			sta_in_ft_position IS NULL AND
			not_advertized IS NULL AND
			position_offered IS NULL AND
			sap_staffing_situation.insertdate = v_insert_date;
		ELSIF categoryId = 2 THEN		--OFFERS ISSUED OK 
			SELECT count (*) INTO my_date_value_record.value 		
			FROM sap_staffing_situation  
			WHERE
			positionid != 20250115 AND 
			position_eg IN('International Staff', 'Local Staff') AND
			position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
			fund = 'GF' AND
			ees_gr IS NULL AND
			position_offered = 'x' AND
			positionid NOT IN (SELECT positionid FROM sap_staffing_situation WHERE sta_in_ft_position = 'x' AND insertdate = v_insert_date) AND
			sap_staffing_situation.insertdate = v_insert_date;
		ELSIF categoryId = 3 THEN	--NOT ADVERTIZED - OK
			SELECT count (*) INTO my_date_value_record.value 		
			FROM sap_staffing_situation  
			WHERE
			positionid != 20250115 AND
			position_eg IN('International Staff', 'Local Staff') AND 
			position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
			fund = 'GF' AND
			ees_gr IS NULL AND
			not_advertized = 'x' AND
			insertdate = v_insert_date;
		ELSIF categoryId = 4 THEN	--STA PERFORMING AS VA - OK
			SELECT count (*) INTO my_date_value_record.value
			FROM sap_staffing_situation  
			WHERE
			positionid != 20250115 AND 
			position_eg IN('International Staff', 'Local Staff') AND
			position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
			fund = 'GF' AND
			ees_gr IS NULL AND
			sta_in_ft_position = 'x' AND
			positionid NOT IN (SELECT positionid FROM sap_staffing_situation WHERE not_advertized = 'x' AND insertdate = v_insert_date) AND
			insertdate = v_insert_date;
		ELSIF categoryId = 5 THEN	--REGULAR INCUMBENT
			SELECT count (*) INTO my_date_value_record.value
			FROM sap_staffing_situation  
			WHERE
			positionid != 20250115 AND
			position_eg IN('International Staff', 'Local Staff') AND
			position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
			fund = 'GF' AND
			ees_gr IS NOT NULL AND
			insertdate = v_insert_date;
		ELSE
			RAISE EXCEPTION	'SOME ERROR OCCURED!';

		END IF;

		RETURN NEXT my_date_value_record;  
	END LOOP;
RETURN;
 
END;
$reffunc$ LANGUAGE plpgsql;


--FUNKCIJA ZA DRILLDOWN
CREATE OR REPLACE FUNCTION get_js_function_drilldown(categoryId INTEGER, selectDate VARCHAR) RETURNS refcursor AS $refcurfunc$
DECLARE
  ref_cursor 		refcursor;
  v_insert_date		DATE;
BEGIN 
		v_insert_date := to_date(selectDate, 'YYYY_MM_DD');
	
		IF categoryId = 1 THEN			--VACANT  
			OPEN ref_cursor FOR
				SELECT func_area, count(positionid) 	
				FROM sap_staffing_situation  
				WHERE
				positionid != 20250115 AND
				position_eg IN('International Staff', 'Local Staff') AND
				position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
				fund = 'GF' AND
				ees_gr IS NULL AND
				sta_in_ft_position IS NULL AND
				not_advertized IS NULL AND
				position_offered IS NULL AND
				sap_staffing_situation.insertdate = v_insert_date
				GROUP BY func_area;

		ELSIF categoryId = 2 THEN		--OFFERS ISSUED
			OPEN ref_cursor FOR
				SELECT func_area, count(positionid)	
				FROM sap_staffing_situation  
				WHERE 
				positionid != 20250115 AND 
				position_eg IN('International Staff', 'Local Staff') AND
				position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
				fund = 'GF' AND
				ees_gr IS NULL AND
				position_offered = 'x' AND
				positionid NOT IN (SELECT positionid FROM sap_staffing_situation WHERE sta_in_ft_position = 'x' AND insertdate = v_insert_date) AND
				sap_staffing_situation.insertdate = v_insert_date
				GROUP BY func_area;
		ELSIF categoryId = 3 THEN	--NOT ADVERTIZED
			OPEN ref_cursor FOR
				SELECT func_area, count(positionid) 		
				FROM sap_staffing_situation  
				WHERE 
				positionid != 20250115 AND
				position_eg IN('International Staff', 'Local Staff') AND 
				position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
				fund = 'GF' AND
				ees_gr IS NULL AND
				not_advertized = 'x' AND
				insertdate = v_insert_date
				GROUP BY func_area;
		ELSIF categoryId = 4 THEN	--STA PERFORMING AS VA 
			OPEN ref_cursor FOR
				SELECT func_area, count(positionid)
				FROM sap_staffing_situation  
				WHERE 
				positionid != 20250115 AND 
				position_eg IN('International Staff', 'Local Staff') AND
				position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
				fund = 'GF' AND
				ees_gr IS NULL AND
				sta_in_ft_position = 'x' AND
				positionid NOT IN (SELECT positionid FROM sap_staffing_situation WHERE not_advertized = 'x' AND insertdate = v_insert_date) AND
				insertdate = v_insert_date
				GROUP BY func_area;
		ELSIF categoryId = 5 THEN	--REGULAR INCUMBENT
			OPEN ref_cursor FOR
				SELECT func_area, count(positionid)
				FROM sap_staffing_situation  
				WHERE
				positionid != 20250115 AND
				position_eg IN('International Staff', 'Local Staff') AND
				position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
				fund = 'GF' AND
				ees_gr IS NOT NULL AND
				insertdate = v_insert_date
				GROUP BY func_area;
						
		END IF; 
	RETURN ref_cursor;

	CLOSE ref_cursor;

	EXCEPTION WHEN OTHERS THEN
	RAISE;		
END;
$refcurfunc$ LANGUAGE plpgsql;




-- FUNCTION get_user_privileges(IN usr_privilege_group VARCHAR)
CREATE OR REPLACE FUNCTION get_user_privileges(IN usr_privilege_group VARCHAR) RETURNS refcursor AS $refcurfunc$
DECLARE	
  ref_cursor 			refcursor;
  v_usr_priv_group		dbUser.password%TYPE;
  
BEGIN
	OPEN ref_cursor FOR
		SELECT 1 as returnVal, privilege 
		FROM privleges prvlg
		WHERE prvlg.prvg_group = usr_privilege_group;
		
	
	RETURN ref_cursor;

	CLOSE ref_cursor;

	EXCEPTION WHEN OTHERS THEN
	RAISE;		
END;
$refcurfunc$ LANGUAGE plpgsql; 



CREATE OR REPLACE FUNCTION get_js_function_data_from_date_string(categoryid INTEGER, insertdateStr VARCHAR) RETURNS  date_value_record_type AS $reffunc$
DECLARE
	my_date_value_record	date_value_record_type;
  	v_insert_date		DATE;

BEGIN   			
	v_insert_date := to_date(insertdateStr, 'YYYY_MM_DD');
	
	SELECT v_insert_date INTO my_date_value_record.insertdate;


	IF categoryId = 1 THEN			--VACANT 
		SELECT count (*) INTO my_date_value_record.value	
		FROM sap_staffing_situation  
		WHERE
		positionid != 20250115 AND
		position_eg IN('International Staff', 'Local Staff') AND
		position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
		fund = 'GF' AND
		ees_gr IS NULL AND
		sta_in_ft_position IS NULL AND
		not_advertized IS NULL AND
		position_offered IS NULL AND
		sap_staffing_situation.insertdate = v_insert_date;
	ELSIF categoryId = 2 THEN		--OFFERS ISSUED OK 
		SELECT count (*) INTO my_date_value_record.value 		
		FROM sap_staffing_situation  
		WHERE
		positionid != 20250115 AND 
		position_eg IN('International Staff', 'Local Staff') AND
		position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
		fund = 'GF' AND
		ees_gr IS NULL AND
		position_offered = 'x' AND
		positionid NOT IN (SELECT positionid FROM sap_staffing_situation WHERE sta_in_ft_position = 'x' AND insertdate = v_insert_date) AND
		sap_staffing_situation.insertdate = v_insert_date;
	ELSIF categoryId = 3 THEN	--NOT ADVERTIZED - OK
		SELECT count (*) INTO my_date_value_record.value 		
		FROM sap_staffing_situation  
		WHERE
		positionid != 20250115 AND
		position_eg IN('International Staff', 'Local Staff') AND 
		position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
		fund = 'GF' AND
		ees_gr IS NULL AND
		not_advertized = 'x' AND
		insertdate = v_insert_date;
	ELSIF categoryId = 4 THEN	--STA PERFORMING AS VA - OK
		SELECT count (*) INTO my_date_value_record.value
		FROM sap_staffing_situation  
		WHERE
		positionid != 20250115 AND 
		position_eg IN('International Staff', 'Local Staff') AND
		position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
		fund = 'GF' AND
		ees_gr IS NULL AND
		sta_in_ft_position = 'x' AND
		positionid NOT IN (SELECT positionid FROM sap_staffing_situation WHERE not_advertized = 'x' AND insertdate = v_insert_date) AND
		insertdate = v_insert_date;
	ELSIF categoryId = 5 THEN	--REGULAR INCUMBENT
		SELECT count (*) INTO my_date_value_record.value
		FROM sap_staffing_situation  
		WHERE
		positionid != 20250115 AND
		position_eg IN('International Staff', 'Local Staff') AND
		position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
		fund = 'GF' AND
		ees_gr IS NOT NULL AND
		insertdate = v_insert_date;
	ELSE
		RAISE EXCEPTION	'SOME ERROR OCCURED!';

	END IF;

RETURN my_date_value_record;
 
END;
$reffunc$ LANGUAGE plpgsql;

--1
CREATE OR REPLACE FUNCTION get_total_net_vacancy(insertDateStr VARCHAR) RETURNS INTEGER AS $reffunc$
DECLARE
	v_ret_val 	INTEGER;
	v_insert_date 	DATE;
  	
BEGIN   	
	select to_date(insertDateStr, 'YYYY_MM_DD') INTO v_insert_date;
	
	v_ret_val := 0;

	SELECT count (*) INTO v_ret_val
	FROM sap_staffing_situation  
	WHERE
	positionid != 20250115 AND
	position_eg IN('International Staff', 'Local Staff') AND
	position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
	fund = 'GF' AND
	ees_gr IS NULL AND
	sta_in_ft_position IS NULL AND
	not_advertized IS NULL AND
	position_offered IS NULL AND
	sap_staffing_situation.insertdate = v_insert_date;		
		
RETURN v_ret_val;
 
END;
$reffunc$ LANGUAGE plpgsql;


--2
CREATE OR REPLACE FUNCTION get_offers_issued(insertDateStr VARCHAR) RETURNS INTEGER AS $reffunc$
DECLARE
	v_ret_val 	INTEGER;
	v_insert_date 	DATE;
  	
BEGIN   	
	select to_date(insertDateStr, 'YYYY_MM_DD') INTO v_insert_date;
	
	v_ret_val := 0;

	SELECT count (*) INTO v_ret_val		
	FROM sap_staffing_situation  
	WHERE
	positionid != 20250115 AND 
	position_eg IN('International Staff', 'Local Staff') AND
	position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
	fund = 'GF' AND
	ees_gr IS NULL AND
	position_offered = 'x' AND
	positionid NOT IN (SELECT positionid FROM sap_staffing_situation WHERE sta_in_ft_position = 'x' AND insertdate = v_insert_date) AND
	sap_staffing_situation.insertdate = v_insert_date;		
		
RETURN v_ret_val;
 
END;
$reffunc$ LANGUAGE plpgsql;

--3
CREATE OR REPLACE FUNCTION get_not_advertized(insertDateStr VARCHAR) RETURNS INTEGER AS $reffunc$
DECLARE
	v_ret_val 	INTEGER;
	v_insert_date 	DATE;
  	
BEGIN   	
	select to_date(insertDateStr, 'YYYY_MM_DD') INTO v_insert_date;
	
	v_ret_val := 0;

	SELECT count (*) INTO v_ret_val						
	FROM sap_staffing_situation  
	WHERE
	positionid != 20250115 AND
	position_eg IN('International Staff', 'Local Staff') AND 
	position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
	fund = 'GF' AND
	ees_gr IS NULL AND
	not_advertized = 'x' AND
	insertdate = v_insert_date;
		
RETURN v_ret_val;
 
END;
$reffunc$ LANGUAGE plpgsql;

--4
CREATE OR REPLACE FUNCTION get_sta_performing_as_va(insertDateStr VARCHAR) RETURNS INTEGER AS $reffunc$
DECLARE
	v_ret_val 	INTEGER;
	v_insert_date 	DATE;
  	
BEGIN   	
	select to_date(insertDateStr, 'YYYY_MM_DD') INTO v_insert_date;
	
	v_ret_val := 0;

	SELECT count (*) INTO v_ret_val		 		
	FROM sap_staffing_situation  
	WHERE
	positionid != 20250115 AND 
	position_eg IN('International Staff', 'Local Staff') AND
	position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
	fund = 'GF' AND
	ees_gr IS NULL AND
	sta_in_ft_position = 'x' AND
	positionid NOT IN (SELECT positionid FROM sap_staffing_situation WHERE not_advertized = 'x' AND insertdate = v_insert_date) AND
	insertdate = v_insert_date;
		
		
RETURN v_ret_val;
 
END;
$reffunc$ LANGUAGE plpgsql;


--5
CREATE OR REPLACE FUNCTION get_regular_incumbent(insertDateStr VARCHAR) RETURNS INTEGER AS $reffunc$
DECLARE
	v_ret_val 	INTEGER;
	v_insert_date 	DATE;
  	
BEGIN   	
	select to_date(insertDateStr, 'YYYY_MM_DD') INTO v_insert_date;
	
	v_ret_val := 0;

	SELECT count (*) INTO v_ret_val		 		
	FROM sap_staffing_situation  
	WHERE
	positionid != 20250115 AND
	position_eg IN('International Staff', 'Local Staff') AND
	position_esg IN ('Director','Professional','General Service', 'General Service Int.') AND
	fund = 'GF' AND
	ees_gr IS NOT NULL AND
	insertdate = v_insert_date;
		
		
RETURN v_ret_val;
 
END;
$reffunc$ LANGUAGE plpgsql;


-- compare_values_by_date compares based on categoryId a

-- compare_values_by_date compares based on categoryId a
CREATE OR REPLACE FUNCTION compare_values_by_date(categoryid INTEGER, currentDateStr VARCHAR, prevDateString VARCHAR) RETURNS VARCHAR AS $reffunc$
DECLARE
	v_ret_val 		INTEGER;
	v_ret_val_varchar	VARCHAR;
	v_currentDate_value 	INTEGER;
	v_previousDate_value 	INTEGER;
  	
BEGIN   	
	v_ret_val_varchar := '';
	v_ret_val := 0;
		
	IF categoryId = 1 THEN			--VACANT 
		select get_total_net_vacancy(currentDateStr) INTO v_currentDate_value;
		select get_total_net_vacancy(prevDateString) INTO v_previousDate_value;

		v_ret_val := v_currentDate_value - v_previousDate_value;
		
	ELSIF categoryId = 2 THEN		--OFFERS EXTENDED -> can not give 
		select get_offers_issued(currentDateStr) INTO v_currentDate_value;
		select get_offers_issued(prevDateString) INTO v_previousDate_value;

		v_ret_val := v_currentDate_value - v_previousDate_value;	
	ELSIF categoryId = 3 THEN		--NOT ADVERTIZED - OK
		select get_not_advertized(currentDateStr) INTO v_currentDate_value;
		select get_not_advertized(prevDateString) INTO v_previousDate_value;

		v_ret_val := v_currentDate_value - v_previousDate_value;
	ELSIF categoryId = 4 THEN		--STA PERFORMING AS VA - OK
		select get_sta_performing_as_va(currentDateStr) INTO v_currentDate_value;
		select get_sta_performing_as_va(prevDateString) INTO v_previousDate_value;

		v_ret_val := v_currentDate_value - v_previousDate_value;
	ELSIF categoryId = 5 THEN		--REGULAR INCUMBENT
		select get_regular_incumbent(currentDateStr) INTO v_currentDate_value;
		select get_regular_incumbent(prevDateString) INTO v_previousDate_value;

		v_ret_val := v_currentDate_value - v_previousDate_value;
	ELSIF categoryId = 6 THEN
		v_ret_val := 0;	
	ELSE
		RAISE EXCEPTION	'SOME ERROR OCCURED!';

	END IF;


	IF v_ret_val <> 0 THEN
		IF v_ret_val > 0 THEN
			v_ret_val_varchar := ' increased by ' || v_ret_val;
		ELSE
			--v_ret_val_varchar := ' decreased by ' || (@ v_ret_val);
			v_ret_val_varchar := ' decreased by ' || (v_ret_val);
		END IF;
	END IF;

		
RETURN v_ret_val_varchar;
 
END;
$reffunc$ LANGUAGE plpgsql;

