package org.ctbto.hr.hcm.manager;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import org.ctbto.hr.hcm.hibernate.Categories;
import org.ctbto.hr.hcm.hibernate.DBUser;
import org.ctbto.hr.hcm.hibernate.DateValue;
import org.ctbto.hr.hcm.hibernate.DepartmentValue;
import org.ctbto.hr.hcm.hibernate.NetVacancy;
import org.ctbto.hr.hcm.hibernate.ReportDescription;
import org.ctbto.hr.hcm.hibernate.ReportDescriptionClass;
import org.ctbto.hr.hcm.hibernate.dao.CategoriesDAO;
import org.ctbto.hr.hcm.hibernate.dao.DBUserDAO;
import org.ctbto.hr.hcm.hibernate.dao.DateValueDAO;
import org.ctbto.hr.hcm.hibernate.dao.DepartmentValueDAO;
import org.ctbto.hr.hcm.hibernate.dao.NetVacancyDAO;
import org.ctbto.hr.hcm.hibernate.dao.ReportDescriptionClassDAO;
import org.ctbto.hr.hcm.hibernate.dao.ReportDescriptionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


public class DatabaseManager {
	
	@Autowired
    private DBUserDAO dbUserDAO;
	@Autowired
	private CategoriesDAO categoriesDAO;
	@Autowired
	private DateValueDAO dateValueDAO;
	@Autowired
	private DepartmentValueDAO departmentValueDAO;
	@Autowired
	private NetVacancyDAO netVacancyDAO;
	@Autowired
	private ReportDescriptionDAO reportDescriptionDAO;
	@Autowired
	private ReportDescriptionClassDAO reportDescriptionClassDAO;
	
	
	
//DBUser DAO
	@Transactional(readOnly = true)
    public DBUser getUserById(Long userId) {
        return dbUserDAO.findById(userId);
    }	

	@Transactional(readOnly = true)
    public DBUser getUserByName(String userName) {
		return dbUserDAO.findByName(userName);
    }
	
	@Transactional(readOnly = true)
    public DBUser getUserByEmail(String email) {
		return dbUserDAO.findByEmail(email);
    }

	@Transactional(readOnly = true)
    public List<DBUser> getAllActiveUsers() {
		return dbUserDAO.findAllActiveUsers();			
    }
	
	@Transactional(readOnly = true)
    public ResultSet getUserByUsernamePassword(String userName, String password) {
		return dbUserDAO.findUserByUsernameAndPassword(userName, password);			
    }
	
	@Transactional(readOnly = false)
    public void saveDBUser(DBUser dbUser) {
		dbUserDAO.save(dbUser);		
    }
	
//Categories DAO
	@Transactional(readOnly = true)
    public Categories getCategoryById(Long id) {
        return categoriesDAO.findById(id);
    }	
	
	@Transactional(readOnly = true)
	public Categories getCategoryByCategoryName(String category) {   
        return categoriesDAO.findByCategory(category);
	}

	@Transactional(readOnly = true)
	public List<Categories> getAllCategories() {
		return categoriesDAO.findAllCategories();	
	}
	
//Date VALUE DAO
	@Transactional(readOnly = true)
	public DateValue getJSFunctionSeriesData(Long id, String insertDateString) {
		return dateValueDAO.findJSFunctionSeriesData(id, insertDateString);	
	}
	
//DEPARTMENT VALUE DAO		
	@Transactional(readOnly = true)
	public List <DepartmentValue> getJSFunctionDrillDownData(int id, String insertDateString) {
		return departmentValueDAO.findJSFunctionDrillDownData(id, insertDateString);	
	}

	
//NET VACANCY DAO
	@Transactional(readOnly = true)
	public List<Date> getInsertDates(String orderBy) {
		return netVacancyDAO.findInsertDates(orderBy);
	}
	
	@Transactional(readOnly = true)
	public List<String> getAllPositionIds(Date insertdate) {
		return netVacancyDAO.findAllPositionIds(insertdate);
	}
	
	@Transactional(readOnly = false)
    public void updatePositions( List <NetVacancy> nvList) {
		netVacancyDAO.updatePositions( nvList);		
    }
	
	@Transactional(readOnly = false)
    public String getComparedValueByDates(int categoryid, String currentDateStr, String prevDateString) {
		return netVacancyDAO.findComparedValueByDates(categoryid, currentDateStr, prevDateString);		
    }
	
	
	
	
	@Transactional(readOnly = false)
    public void updatePosition( NetVacancy nv) {
		netVacancyDAO.updatePosition( nv);		
    }
	
	
	@Transactional(readOnly = false)
    public void insertNetVacancies( List <NetVacancy> netVacancyList) {
		netVacancyDAO.insertNVFromList(netVacancyList);		
    }
	
	
	@Transactional(readOnly = false)
    public void insertNetVacancy( NetVacancy netVacancy) {
		netVacancyDAO.insertNV(netVacancy);
//		netVacancyDAO.addNetVacancy(netVacancyList);		
    }
	
	@Transactional(readOnly = false)
    public void insertNetVacancyFromList( List <NetVacancy> netVacancyList) {
		netVacancyDAO.insertNVFromList(netVacancyList);	
    }
	
	@Transactional(readOnly = true)
	public List<NetVacancy> getAllNetVacanciesByInsertDate(Date insertdate) {
		return netVacancyDAO.findAllNetVacanciesByInsertDate(insertdate);
	}
	
	@Transactional(readOnly = true)
	public List<ReportDescription> getAllReportDescriptionsByInsertDate(Date insertdate) {		
		return reportDescriptionDAO.findAllReportDescriptions(insertdate);
	}
	
	@Transactional(readOnly = false)
	public void saveReportDescription(Date insertDate, ReportDescription rd) {		
		reportDescriptionDAO.insertReportDescription(insertDate, rd);
	}
	
	
	@Transactional(readOnly = true)
	public List<ReportDescriptionClass> getAllReportDescriptionsClasses() {
		return reportDescriptionClassDAO.findAllReportDescriptionClasses();
	}
	
	@Transactional(readOnly = true)
	public ReportDescriptionClass getReportDescriptionClassById(Long id) {
		return reportDescriptionClassDAO.findById(id);
	}
	
}