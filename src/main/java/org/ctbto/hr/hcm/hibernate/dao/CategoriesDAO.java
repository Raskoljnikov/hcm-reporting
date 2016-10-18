package org.ctbto.hr.hcm.hibernate.dao;

import java.util.List;
import org.ctbto.hr.hcm.hibernate.Categories;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class CategoriesDAO extends BaseHibernateDAO {
	 
	public Categories findById(Long id) {
	        Criteria crit = this.getSession().createCriteria(Categories.class);
	        crit.add(Restrictions.eq("id", id));	        
            return (Categories) crit.uniqueResult();
	}
	
	public Categories findByCategory(final String category) {
        Criteria crit = this.getSession().createCriteria(Categories.class);
        crit.add(Restrictions.eq("category", category.trim()).ignoreCase());        
        return (Categories) crit.uniqueResult();
	}

	
	@SuppressWarnings("unchecked")
	public List<Categories> findAllCategories() {
		Criteria crit = this.getSession().createCriteria(Categories.class);
        return crit.list();
	}
}