package org.ctbto.hr.hcm.hibernate.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ctbto.hr.hcm.hibernate.DepartmentValue;
import org.hibernate.internal.SessionImpl;


public class DepartmentValueDAO extends BaseHibernateDAO {
	 
	public List<DepartmentValue> findJSFunctionDrillDownData(int categoryId, String dateString) {
		List<DepartmentValue> retVal = new ArrayList<DepartmentValue>();
		
		SessionImpl sessionImpl = (SessionImpl) this.getSession();
		Connection conn = sessionImpl.connection();
		CallableStatement  cs = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			cs = conn.prepareCall ("{ ? = call get_js_function_drilldown (?,?)}");
			cs.registerOutParameter (1, Types.OTHER);
			cs.setInt(2, categoryId);
			cs.setString(3, dateString); 
            cs.execute ();
            
            rs = (ResultSet) cs.getObject (1); 
            
            if (rs != null){				
				while (rs.next()){
					String department = rs.getString(1);
					Integer value = rs.getInt(2);					
					retVal.add(new DepartmentValue(department, value));
				}
			}
			
		} catch( SQLException e ){
            e.printStackTrace();
		}
		
		return retVal;
	}


	
}