package org.ctbto.hr.hcm.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ctbto.hr.hcm.hibernate.NetVacancy;
import org.ctbto.hr.hcm.utils.BusinessIntelligenceImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;


public class NetVacancyDAO extends BaseHibernateDAO {
	
	public static final Logger logger = Logger.getLogger(NetVacancyDAO.class.getName());
	 
	public NetVacancy findById(Long id) {
	        Criteria crit = this.getSession().createCriteria(NetVacancy.class);
	        crit.add(Restrictions.eq("id", id));	        
            return (NetVacancy) crit.uniqueResult();
	}
	
	public NetVacancy findByPositionId(Long positionid) {
        Criteria crit = this.getSession().createCriteria(NetVacancy.class);
        crit.add(Restrictions.eq("positionid", positionid));	        
        return (NetVacancy) crit.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<NetVacancy> findAllNetVacanciesByInsertDate(Date insertDate) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(insertDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date fromDate = calendar.getTime();

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date toDate = calendar.getTime();

		Set <String> positionEG = new HashSet<String> ();
		positionEG.add("International Staff");
		positionEG.add("Local Staff");
		
		Set <String> position_esg = new HashSet<String> ();
		position_esg.add("Director");
		position_esg.add("Professional");
		position_esg.add("General Service");
		position_esg.add("General Service Int.");
		
		Criteria crit = this.getSession().createCriteria(NetVacancy.class);
		crit.add(Restrictions.between("insertDate", fromDate, toDate));
		crit.add(Restrictions.not(Restrictions.eq("positionId", 20250115)));
		crit.add(Restrictions.in("position_eg", positionEG));
		crit.add(Restrictions.in("position_esg", position_esg));
		crit.add(Restrictions.eqOrIsNull("fund", "GF"));
		
        return crit.list();
	}
	

	
	@SuppressWarnings("unchecked")
	public List<Date> findInsertDates(String orderBy) {
		Criteria crit = this.getSession().createCriteria(NetVacancy.class);
		crit.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("insertDate"), "insertDate") ));
		if(orderBy.equals(BusinessIntelligenceImpl.sortASC)){
        	crit.addOrder(Order.asc("insertDate"));
        }else if (orderBy.equals(BusinessIntelligenceImpl.sortDESC)){
        	crit.addOrder(Order.desc("insertDate"));
        }
        return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findAllPositionIds(Date insertdate) {
		List <String> retVal = new ArrayList <String> ();
		Criteria crit = this.getSession().createCriteria(NetVacancy.class);
		crit.add(Restrictions.eq("insertDate", insertdate));
		List <NetVacancy> nvList = crit.list();
		Iterator <NetVacancy> it = nvList.iterator();
		while(it.hasNext()){
			NetVacancy nvBean = it.next();
			String positionId = nvBean.getPositionId().toString();
			retVal.add(positionId);
		}

        return retVal;
	}
	
	
	@SuppressWarnings("unchecked")
	public void updatePositions (List <NetVacancy> netVacancyList){
		for(int i=0; i<netVacancyList.size(); i++){
			NetVacancy nv = netVacancyList.get(i);
			this.getSession().saveOrUpdate(nv);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updatePosition (NetVacancy netVacancy){
		this.getSession().saveOrUpdate(netVacancy);
	}
	
	
	@SuppressWarnings("unchecked")
	public void insertNVFromList (List <NetVacancy> netVacancyList){
	
		for(int i=0; i<netVacancyList.size(); i++){
			NetVacancy nv = netVacancyList.get(i);
			this.getSession().save(nv);
			if ( i % 20 == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
				this.getSession().flush();
				this.getSession().clear();
		    }
		}
	}
	
	public void insertNV (NetVacancy netVacancy) {
		//this.getSession().getTransaction().begin();
		this.getSession().save(netVacancy);
		//this.getSession().getTransaction().commit();
	}
	
	
	public String findComparedValueByDates(int categoryid, String currentDateStr, String prevDateString){
		String retVal = " stayed the same";
//		select compare_values_by_date(1, '2016_09_27', '2016_09_20')
		
		SessionImpl sessionImpl = (SessionImpl) this.getSession();
		Connection conn = sessionImpl.connection();
		CallableStatement  cs = null;
//		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			cs = conn.prepareCall("{? = call compare_values_by_date(?,?,?)}");
			cs.registerOutParameter (1, Types.VARCHAR);
            cs.setInt(2 , categoryid );
            cs.setString(3, currentDateStr);
            cs.setString(4, prevDateString);
            cs.execute ();
            
            if(cs.getObject (1) != null){
            	retVal = (String) cs.getObject (1);  
            } 
			
		} catch( SQLException e ){
            e.printStackTrace();
		}
		return retVal;
	}
	
	public ResultSet findUserByUsernameAndPassword(String username, String password) {			
		SessionImpl sessionImpl = (SessionImpl) this.getSession();
		Connection conn = sessionImpl.connection();
		CallableStatement  cs = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			cs = conn.prepareCall("{? = call get_user(?,?)}");
			cs.registerOutParameter (1, Types.OTHER);
            cs.setString(2 , username );
            cs.setString(3, password);
            cs.execute ();
            
            rs = (ResultSet) cs.getObject (1);            
			
		} catch( SQLException e ){
            e.printStackTrace();
		}
		
		return rs;
	
	}
	
	
	
	
	

}	
