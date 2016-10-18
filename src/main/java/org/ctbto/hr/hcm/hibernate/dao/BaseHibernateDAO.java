package org.ctbto.hr.hcm.hibernate.dao;

import java.util.Iterator;
import java.util.List;


import org.ctbto.hr.hcm.hibernate.UEntity;
//import org.ctbto.hibernate.ULogicalEntity;
//import org.ctbto.hibernate.filter.UHibernateFilter;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseHibernateDAO implements IBaseHibernateDAO {

    /**
     * Session factory.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Obtains the current session.
     * @return The current session.
     */
    public final Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Adds joins.
     * @param crit Query criteria.
     * @param joins Joins to add
     */
    protected final void addJoins(final Criteria crit, final List<String> joins) {
        if (joins != null) {
            for (Iterator<String> iterator = joins.iterator(); iterator.hasNext();) {
                String join = iterator.next();
                crit.setFetchMode(join, FetchMode.JOIN);
            }
        }
    }

    /**
     * Saves entity.
     * @param obj Entity to save.
     */
    public void save(final UEntity obj) {
        /*Activity.ACTIVITIES activity = Activity.ACTIVITIES.UPDATE;
        if (obj.getId() == null) {
            activity = Activity.ACTIVITIES.CREATE;
        }*/
        saveWithoutLoging(obj);
        //logActivity(obj, activity.toString());
    }

    /**
     * Saves entity.
     * @param obj Entity to save.
     * @param refresh Force to refresh.
     */
    public void save(final UEntity obj, final Boolean refresh) {
        /*Activity.ACTIVITIES activity = Activity.ACTIVITIES.UPDATE;
        if (obj.getId() == null) {
            activity = Activity.ACTIVITIES.CREATE;
        }*/
        saveWithoutLoging(obj, refresh);
        //logActivity(obj, activity.toString());
    }

    /**
     * Saves Entity.
     * @param obj Entity to save.
     */
    public final void saveWithoutLoging(final Object obj) {
        getSession().saveOrUpdate(obj);
    }

    /**
     * Saves Entity.
     * @param obj Entity to save.
     * @param refresh Force to refresh.
     */
    public final void saveWithoutLoging(final Object obj, final Boolean refresh) {
        getSession().saveOrUpdate(obj);
        if (refresh) {
            getSession().flush();
            getSession().refresh(obj);
        }
    }

    /**
     * Saves entities.
     * @param objs Entities tyo save.
     */
    public final void save(final List<UEntity> objs) {
        for (Iterator<UEntity> iterator = objs.iterator(); iterator.hasNext();) {
            save(iterator.next());
        }
    }

    /**
     * Deletes entity.
     * @param obj Entity to delete.
     */
    public final void delete(final UEntity obj) {
        //logActivity(obj, Activity.ACTIVITIES.DETETE.toString());
        getSession().delete(obj);
    }

    /**
     * @param entity Entity to delete.
     * @param ids Entities ids.
     */
    public final void deleteWithouLogging(final Class entity, final List<Integer> ids) {
        String hql = "delete from " + entity.getName() + " where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.executeUpdate();
    }

    /**
     * @param entity Entity to delete.
     * @param ids Entities ids.
     */
    public final void delete(final Class entity, final List<Integer> ids) {
        String hql = "delete from " + entity.getName() + " where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.executeUpdate();
//        for (Iterator<Integer> iterator = ids.iterator(); iterator.hasNext();) {
//            Integer id = iterator.next();
//            //logActivity(id, entity, Activity.ACTIVITIES.DETETE.toString());
//        }
    }


    /**
     * @param clazz Entity class.
     * @param ids Entities ids to delete.
     */
    public <T> void  deleteForEnvers(final Class<T> clazz, final List<Integer> ids) {
        List<T> entities = findAllByClassAndIds(clazz, ids, false);
        for (Iterator<T> iterator = entities.iterator(); iterator.hasNext();) {
            T entity = iterator.next();
            getSession().delete(entity);
        }
    }

    /**
     * @param entity Entity to find.
     * @param ids Entities ids.
     * @param useCache To cache.
     * @return List of entities.
     */
    public final <T> List<T> findAllByClassAndIds(final Class<T> entity, final List<Integer> ids, final Boolean useCache) {
        Criteria crit = getSession().createCriteria(entity);
        crit.add(Restrictions.in("id", ids));
        if (useCache) {
            crit.setCacheable(true);
            crit.setCacheRegion("org.hibernate.cache.StandardQueryCache");
        }
        return crit.list();
    }

    /**
     * Logs activity.
     * @param object Entity that was modified.
     * @param activityDescr Activity description.
     */
   /* protected final void logActivity(final UEntity object, final String activityDescr) {
        logActivity(object.getId(), object.getClass(), activityDescr);
    }*/

    /**
     * Logs activity.
     * @param id Entity id.
     * @param clazz Entity class.
     * @param activityDescr Activity description.
     */
/*    protected final void logActivity(final Integer id, final Class<?> clazz, final String activityDescr) {
        Table table = (Table) clazz.getAnnotation(Table.class);
        Activity activity = new Activity();
        activity.setEntityId(id);
        activity.setEntityType(table.name());
        activity.setActivity(activityDescr);
        activity.setUserlogin(SecurityContextHolder.getContext().getAuthentication().getName());
        getSession().saveOrUpdate(activity);
    }
*/
    /**
     * @param clazz Entity class to find.
     * @param useCache Use cache first.
     * @return List of entities.
     */
    @SuppressWarnings("unchecked")
    public final List findAllByClass(final Class clazz, final Boolean useCache) {
        Criteria crit = getSession().createCriteria(clazz);
        if (useCache) {
            crit.setCacheable(true);
            crit.setCacheRegion("org.hibernate.cache.StandardQueryCache");
        }
        return crit.list();
    }

    /**
     * @param clazz Entity class to find.
     * @param order Result set order.
     * @param useCache Use cache first.
     * @return List of entities.
     */
    @SuppressWarnings("unchecked")
    public final List findAllByClass(final Class clazz, final List<Order> orders, final Boolean useCache) {
        Criteria crit = getSession().createCriteria(clazz);
        if (orders != null) {
            for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
                crit.addOrder((Order) iterator.next());
            }
        }
        if (useCache) {
            crit.setCacheable(true);
            crit.setCacheRegion("org.hibernate.cache.StandardQueryCache");
        }
        return crit.list();
    }

    /**
     * @param clazz Entity class
     * @param sqlFilter Hibernate filter.
     * @return List of entities.
     */
    /*
    @SuppressWarnings("unchecked")
    public final List findAllByClassAndCriterias(final Class clazz, final UHibernateFilter sqlFilter) {
        Criteria crit = getSession().createCriteria(clazz);
        sqlFilter.addOrder(crit);
        sqlFilter.addCriterias(crit);
        return crit.list();
    }*/

    /**
     * @param sql Native query.
     * @return List query result.
     */
    @SuppressWarnings("unchecked")
    public final List excecuteQuery(final String sql) {
        return (List) getSession().createSQLQuery(sql).list();
    }

    /**
     * @param sql Native query.
     */
    @SuppressWarnings("unchecked")
    public final void excecuteUpdate(final String sql) {
        getSession().createSQLQuery(sql).executeUpdate();
    }

    /**
     * @param clazz Entity class
     * @param id Entity id.
     * @param joins Relations to join.
     * @return Entity object.
     */
    @SuppressWarnings("unchecked")
    public final Object getByClassAndId(final Class clazz, final Integer id, final List<String> joins) {
        Criteria crit = getSession().createCriteria(clazz);
        addJoins(crit, joins);
        crit.add(Restrictions.idEq(id));
        return crit.uniqueResult();
    }

    /**
     * @param obj - Database object.
     */
    public final void refresh(final Object obj) {
        getSession().refresh(obj);
    }
    
    
/*    public List findAllByCriterias(Class clazz, UHibernateFilter sqlFilter, List<String> joins) {
        Criteria crit = getSession().createCriteria(clazz);
        try {
            clazz.getDeclaredField("deleted");
            crit.add(Restrictions.eq("deleted", false));
        } catch (Exception e) {};
        addJoins(crit, joins);
        sqlFilter.addCriterias(crit);
        sqlFilter.addOrderByGroupBy(crit);
        sqlFilter.addOrder(crit);
        if (sqlFilter.getGridState().getStart() != null) {
            crit.setFirstResult(sqlFilter.getGridState().getStart());
        }
        if (sqlFilter.getGridState().getLimit() != null) {
            crit.setMaxResults(sqlFilter.getGridState().getLimit());
        }
        return crit.list();
    }
    
    public Number findAllByCriteriasCount(Class clazz, UHibernateFilter sqlFilter, List<String> joins) {
        Criteria crit = getSession().createCriteria(clazz);
        try {
            clazz.getField("deleted");
            crit.add(Restrictions.eq("deleted", false));
        } catch (Exception e) {};
        addJoins(crit, joins);
        sqlFilter.addCriterias(crit);
        crit.setProjection(Projections.rowCount());
        return (Number) crit.uniqueResult();
    }

    public List findAllByCriteriasSummary(Class clazz, ProjectionList summaryPl, UHibernateFilter sqlFilter, List<String> joins) {
        Criteria crit = getSession().createCriteria(clazz);
        try {
            clazz.getField("deleted");
            crit.add(Restrictions.eq("deleted", false));
        } catch (Exception e) {};
        addJoins(crit, joins);
        sqlFilter.addOrderByGroupBy(crit);
        sqlFilter.addCriterias(crit);
        ProjectionList pl = summaryPl;
        if (sqlFilter.getGridState().getGroup() != null  && !sqlFilter.getGridState().getGroup().isEmpty()) {
            String groupAlias = sqlFilter.getGridState().getGroup().get(0).getProperty();
            Map<String, String> grouping = sqlFilter.getGroupAssociatedColumnsByKey(groupAlias);
            for (Iterator<String> iterator = grouping.keySet().iterator(); iterator.hasNext();) {
                String key = iterator.next();
                pl.add(Projections.groupProperty(grouping.get(key)), key);
            }
        }
        crit.setProjection(pl);
        crit.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return crit.list();
    }
*/       
}
