package org.ctbto.hr.hcm.hibernate.dao;

import org.hibernate.Session;

/**
 * Data access interface.
 */
public interface IBaseHibernateDAO {
    public Session getSession();
}
