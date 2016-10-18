INSERT into dbUser VALUES
	(nextval('seq_user'), 'hadzijusufovic', 'car', 'Adis.Hadzijusufovic@CTBTO.ORG', 'Y', 'N', 'administrator', CURRENT_DATE,  CURRENT_DATE, 'manually'),
	(nextval('seq_user'), 'sbauer', 'x', 'Sabine.Bauer@CTBTO.ORG', 'Y', 'N', 'office_es', CURRENT_DATE,  CURRENT_DATE, 'manually')
	(nextval('seq_user'), 'mekonnen', 'x', 'Yeshiareg.Mekonnen@CTBTO.ORG', 'Y', 'N', 'chief_hr', CURRENT_DATE,  CURRENT_DATE, 'manually');


INSERT into test VALUES
	--28.04.2016
	(nextval('seq_vacancy'), '2016-04-28', 'Comprehensive Nuclear-Test-Ban Treaty Or'	, 40002110 , 		null		, 20250020 ,	'Executive Secretary'	,'International Staff'	, 	'Executive Secretary'	,'ZERBO', 'Lassina'		, 836796 ),
	(nextval('seq_vacancy'), '2016-04-28', 'Office of the Executive Secretary, OES'		, 40002113 , 		'Sections'	, 20250021 ,	'Personal Assistant'	,'Local Staff'			,	'General Service'		,'THOMAS', 'Alphonsa S.', 800329 ),
	(nextval('seq_vacancy'), '2016-04-28', 'Office of the Executive Secretary, OES'		, 40002113 , 		'Sections'	, 20250252 ,	'Policy and Strategy Officer'	,'International Staff'	,	'Professional'		,'BALLESTAS DE DIETRICH', 'Diana', 961558 ),
	--28.05.2016
	(nextval('seq_vacancy'), '2016-05-28', 'Comprehensive Nuclear-Test-Ban Treaty Or'	, 40002110 , 		null		, 20250020 ,	'Executive Secretary'	,'International Staff'	, 	'Executive Secretary'	,'ZERBO', 'Lassina'		, 836796 ),
	(nextval('seq_vacancy'), '2016-05-28', 'Office of the Executive Secretary, OES'		, 40002113 , 		'Sections'	, 20250021 ,	'Personal Assistant'	,'Local Staff'			,	'General Service'		,'THOMAS', 'Alphonsa S.', 800329 ),
	(nextval('seq_vacancy'), '2016-05-28', 'Office of the Executive Secretary, OES'		, 40002113 , 		'Sections'	, 20250252 ,	'Policy and Strategy Officer'	,'International Staff'	,	'Professional'		,'BALLESTAS DE DIETRICH', 'Diana', 961558 ),
	--01.06.2016
	(nextval('seq_vacancy'), '2016-06-01', 'Comprehensive Nuclear-Test-Ban Treaty Or'	, 40002110 , 		null		, 20250020 ,	'Executive Secretary'	,'International Staff'	, 	'Executive Secretary'	,'ZERBO', 'Lassina'		, 836796 ),
	(nextval('seq_vacancy'), '2016-06-01', 'Office of the Executive Secretary, OES'		, 40002113 , 		'Sections'	, 20250021 ,	'Personal Assistant'	,'Local Staff'			,	'General Service'		,'THOMAS', 'Alphonsa S.', 800329 ),
	(nextval('seq_vacancy'), '2016-06-01', 'Office of the Executive Secretary, OES'		, 40002113 , 		'Sections'	, 20250252 ,	'Policy and Strategy Officer'	,'International Staff'	,	'Professional'		,'BALLESTAS DE DIETRICH', 'Diana', 961558 ),
	--07.06.2016
	(nextval('seq_vacancy'), '2016-06-07', 'Comprehensive Nuclear-Test-Ban Treaty Or'	, 40002110 , 		null		, 20250020 ,	'Executive Secretary'	,'International Staff'	, 	'Executive Secretary'	,'ZERBO', 'Lassina'		, 836796 ),
	(nextval('seq_vacancy'), '2016-06-07', 'Office of the Executive Secretary, OES'		, 40002113 , 		'Sections'	, 20250021 ,	'Personal Assistant'	,'Local Staff'			,	'General Service'		,'THOMAS', 'Alphonsa S.', 800329 ),
	(nextval('seq_vacancy'), '2016-06-07', 'Office of the Executive Secretary, OES'		, 40002113 , 		'Sections'	, 20250252 ,	'Policy and Strategy Officer'	,'International Staff'	,	'Professional'		,'BALLESTAS DE DIETRICH', 'Diana', 961558 );
	
	
	
--	id							date	    Org unit Title VARCHAR,	    				Org_ID INTEGER,	    Org unit ,	  Position_ID	 Position Title	    	Position EG	    				Position ESG						

INSERT into categories VALUES
	(1, 'Vacant'),
	(2, 'Offers_issued'),
	(3, 'Not_advertized'),
	(4, 'STA_performing_as_VA'),
	(5, 'Regular_Incumbent');	
	

INSERT into report_description_class VALUES
	(1, 'Total Net Vacancy Rate'),
	(2, 'Total of offers issued'),
	(3, 'Total of STAs performing in FT positions'),
	(4, 'Total budgeted FT Vacant positions'),
	(5, 'Total FT budgeted positions not advertised'),
	(6, 'Additional Info');

	

INSERT into report_description VALUES
(nextval('seq_rep_desc'), '2016-10-04', 1, ''),
(nextval('seq_rep_desc'), '2016-10-04', 2, 'increased from 7 to 8 following the offer made to Head,Recruitment & Staff DevelopmentUnit ADM/HRS'),
(nextval('seq_rep_desc'), '2016-10-04', 3, ''),
(nextval('seq_rep_desc'), '2016-10-04', 4, 'increased from 61 to 62 following the separation of Mr Kilgour from IMS/MFS, effective 30/09/2016'),
(nextval('seq_rep_desc'), '2016-10-04', 5, ''),
(nextval('seq_rep_desc'), '2016-10-04', 6, ''),
(nextval('seq_rep_desc'), '2016-09-27', 1, 'decreased from 37 to 35 following the extended offers of appointment to Ms Hongmei, IDC/OPS/MFO and Mr Jezrawi, OES/IA'),
(nextval('seq_rep_desc'), '2016-09-27', 2, 'increased from 5 to 7 following two offers made (ODC/OPS and OES/IA)'),
(nextval('seq_rep_desc'), '2016-09-27', 3, ''),
(nextval('seq_rep_desc'), '2016-09-27', 4, ''),
(nextval('seq_rep_desc'), '2016-09-27', 5, ''),
(nextval('seq_rep_desc'), '2016-10-04', 6, ''),
(nextval('seq_rep_desc'), '2016-09-20', 1, 'decreased from 38 to 37 following the Fixed-term appointment of Mr. Pathirana effective 16 Sep 16'),
(nextval('seq_rep_desc'), '2016-09-20', 2, 'increased from 3 to 5 following two Offer made (ADM/PS and OSI/PPO)'),
(nextval('seq_rep_desc'), '2016-09-20', 3, 'decreased from 9 to 8 following the Fixed-term appointment of Mr. Pathirana effective 16 Sep 16'),
(nextval('seq_rep_desc'), '2016-09-20', 4, ''),
(nextval('seq_rep_desc'), '2016-09-20', 5, ''),
(nextval('seq_rep_desc'), '2016-10-04', 6, ''),
(nextval('seq_rep_desc'), '2016-09-14', 1, 'increased from 37 to 38 following the separation of Ms. Sekulovska ADM/FS, effective 7 Sep 2016'),
(nextval('seq_rep_desc'), '2016-09-14', 2, 'decreased from 5 to 3 following the appointment of Mr. Holen OSI/PPO and Ms. SIM ADM/HRS'),
(nextval('seq_rep_desc'), '2016-09-14', 3, 'decreased from 10 to 9 following the separation of Ms. Sekulovska from ADM/FS effective 7 Sep 2016'),
(nextval('seq_rep_desc'), '2016-09-14', 4, 'decreased from 63 to 61 following the appointment of Mr. Holen in OSI/PPO, effective 8 Sep 2016 and Ms. Sim in ADM/HRS effective 9 Sep 2016'),
(nextval('seq_rep_desc'), '2016-09-14', 5, '');


	
	