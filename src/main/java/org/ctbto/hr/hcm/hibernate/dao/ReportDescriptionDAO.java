package org.ctbto.hr.hcm.hibernate.dao;

import java.util.Date;
import java.util.List;

import org.ctbto.hr.hcm.hibernate.NetVacancy;
import org.ctbto.hr.hcm.hibernate.ReportDescription;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ReportDescriptionDAO extends BaseHibernateDAO {
	 
	public ReportDescription findById(Long id) {
	        Criteria crit = this.getSession().createCriteria(ReportDescription.class);
	        crit.add(Restrictions.eq("id", id));	        
            return (ReportDescription) crit.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ReportDescription> findAllReportDescriptions(final Date insertDate) {
		Criteria crit = this.getSession().createCriteria(ReportDescription.class);
		crit.add(Restrictions.eq("insertDate", insertDate));
        return crit.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public void insertReportDescription(Date insertDate, ReportDescription rd){
		Criteria crit = this.getSession().createCriteria(ReportDescription.class);
		crit.add(Restrictions.eq("insertDate", insertDate));
		crit.add(Restrictions.eq("description_class_id", rd.getDescription_class_id()));
		
		this.getSession().saveOrUpdate(rd);
//			this.getSession().flush();
//			this.getSession().clear();
	}
	
	
}