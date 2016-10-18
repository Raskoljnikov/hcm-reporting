package org.ctbto.hr.hcm.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.ctbto.hr.hcm.hibernate.DBUser;
import org.ctbto.hr.hcm.hibernate.UEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;

public class DBUserDAO extends BaseHibernateDAO {
	 
	public DBUser findById(Long id) {
	        Criteria crit = this.getSession().createCriteria(DBUser.class);
	        crit.add(Restrictions.eq("id", id));	        
            return (DBUser) crit.uniqueResult();
	}
	
	public DBUser findByName(final String userName) {		
        Criteria crit = this.getSession().createCriteria(DBUser.class);
        crit.add(Restrictions.eq("username", userName.trim()).ignoreCase());        
        return (DBUser) crit.uniqueResult();
	}

	public DBUser findByEmail(final String email) {
        Criteria crit = this.getSession().createCriteria(DBUser.class);
        crit.add(Restrictions.eq("email", email.trim()).ignoreCase());
        return (DBUser) crit.uniqueResult();
	}
	
	public List<DBUser> findAllActiveUsers() {
		Criteria crit = this.getSession().createCriteria(DBUser.class);
        crit.add(Restrictions.eq("active", "Y"));        
        return crit.list();
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
		
/*		ProcedureCall call = this.getSession().createStoredProcedureCall("getjsfunctiondata");
		
		//call.registerParameter(1, void.class, ParameterMode.REF_CURSOR);
		call.registerParameter(1, void.class, ParameterMode.OUT);
		//call.registerParameter(2, String.class, ParameterMode.IN).bindValue(username);
		//call.registerParameter(3, String.class, ParameterMode.IN).bindValue(password);
		call.registerParameter(2, Integer.class, ParameterMode.IN).bindValue(1);
		call.registerParameter(3, Integer.class, ParameterMode.IN).bindValue(2);
		
		ProcedureOutputs procedureOutputs = call.getOutputs();
		ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

		//List results = resultSetOutput.getResultList();
		
		return resultSetOutput.getResultList();
		*/
	}
	
	public void saveDBUser(final UEntity obj) {
	        super.saveWithoutLoging(obj);
	}

	
}