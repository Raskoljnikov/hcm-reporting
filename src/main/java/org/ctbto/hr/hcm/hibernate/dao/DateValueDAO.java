package org.ctbto.hr.hcm.hibernate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.ctbto.hr.hcm.hibernate.DateValue;
import org.hibernate.internal.SessionImpl;


public class DateValueDAO extends BaseHibernateDAO {
	 
	
	public DateValue findJSFunctionSeriesData(Long categoryId, java.sql.Date insertDate) {
		DateValue retVal = new DateValue();

		SessionImpl sessionImpl = (SessionImpl) this.getSession();
		Connection conn = sessionImpl.connection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM get_js_function_data_from_date(?,?)");
			
			stmt.setInt(1, categoryId.intValue());
			stmt.setDate(2, insertDate);
						
			rs = stmt.executeQuery();
			
			while (rs.next()) {		
				retVal = new DateValue (rs.getDate(1), rs.getInt(2));
			}
		} catch( SQLException e ){
            e.printStackTrace();
		}
		
		return retVal;
	}		
	
	public DateValue findJSFunctionSeriesData(Long categoryId, String insertDateString) {
		DateValue retVal = new DateValue();

		SessionImpl sessionImpl = (SessionImpl) this.getSession();
		Connection conn = sessionImpl.connection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM get_js_function_data_from_date_string(?,?)");
			
			stmt.setInt(1, categoryId.intValue());
			stmt.setString(2, insertDateString);
						
			rs = stmt.executeQuery();
			
			while (rs.next()) {		
				retVal = new DateValue (rs.getDate(1), rs.getInt(2));
			}
		} catch( SQLException e ){
            e.printStackTrace();
		}
		
		return retVal;
	}		
}