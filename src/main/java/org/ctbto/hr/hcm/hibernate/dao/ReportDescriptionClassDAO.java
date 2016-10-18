package org.ctbto.hr.hcm.hibernate.dao;

import java.util.List;
import org.ctbto.hr.hcm.hibernate.ReportDescriptionClass;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ReportDescriptionClassDAO extends BaseHibernateDAO {
	 
	public ReportDescriptionClass findById(Long id) {
	        Criteria crit = this.getSession().createCriteria(ReportDescriptionClass.class);
	        crit.add(Restrictions.eq("id", id));	        
            return (ReportDescriptionClass) crit.uniqueResult();
	}
	
	public ReportDescriptionClass findByReportDescriptionClass(final String description_class) {
        Criteria crit = this.getSession().createCriteria(ReportDescriptionClass.class);
        crit.add(Restrictions.eq("description_class", description_class.trim()).ignoreCase());        
        return (ReportDescriptionClass) crit.uniqueResult();
	}

	
	@SuppressWarnings("unchecked")
	public List<ReportDescriptionClass> findAllReportDescriptionClasses() {
		Criteria crit = this.getSession().createCriteria(ReportDescriptionClass.class);
        return crit.list();
	}
}