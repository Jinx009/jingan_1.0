package com.protops.gateway.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 数据访问对象基类。
 */
@SuppressWarnings("unchecked")
public abstract class HibernateBaseDao<T, PK extends Serializable> {

    protected static final Logger logger = Logger.getLogger(HibernateBaseDao.class);

    protected Class<T> entityClass;

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void updateBySql(String sql, Object ... params){
        jdbcTemplate.update(sql, params);
    }

    public void batchUpdate(String sql, final List<Object[]> dataSet) {

        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                int cnt = 1;

                Object[] rowParamsLsit = dataSet.get(i);
                for (Object rowParams : rowParamsLsit) {
                    ps.setObject(cnt++, rowParams);
                }

            }

            public int getBatchSize() {
                return dataSet.size();

            }
        };

        jdbcTemplate.batchUpdate(sql, setter);

    }


    /**
     * 用于扩展的DAO子类使用的构造函数.
     * <p/>
     * 通过子类的范型定义取得对象类型Class. eg. public class UserDao extends
     * SimpleHibernateDao<User, Long>
     */
    public HibernateBaseDao() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    private Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    /**
     * 保存新增或修改的对象.
     */
    public void save(final T entity) {

        Assert.notNull(entity);

        hibernateTemplate.saveOrUpdate(entity);
    }


    /**
     * 修改对象2017-03-07新增
     */
    public void update(final T entity) {

        Assert.notNull(entity);

        hibernateTemplate.merge(entity);
    }


    /**
     * 删除对象.
     *
     * @param entity 对象必须是session中的对象或含id属性的transient对象.
     */
    public void delete(final T entity) {
        if (entity == null) {
            return;
        }

        hibernateTemplate.delete(entity);
    }

    /**
     * 按id删除对象.
     */
    public void delete(final PK id) {
        if (id == null) {
            return;
        }
        delete(get(id));
    }

    /**
     * 按id获取对象.
     */
    public T get(final PK id) {
        Assert.notNull(id);
        return (T) hibernateTemplate.get(entityClass, id);
    }

    /**
     * 获取全部对象.
     */
    public List<T> getAll() {
        return findByCriteria();
    }

    /**
     * 按属性查找对象列表,匹配方式为相等.
     */
    public List<T> findByProperty(final String propertyName, final Object value) {
        Assert.hasText(propertyName);
        Criterion criterion = Restrictions.eq(propertyName, value);
        return findByCriteria(criterion);
    }

    /**
     * 按属性查找唯一对象,匹配方式为相等.
     */
    public T findUniqueByProperty(final String propertyName, final Object value) {
        Assert.hasText(propertyName);
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 数量可变的参数
     */
    public int update(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 数量可变的参数
     */
    public List<T> find(final String hql, final Object... values) {
        return createQuery(hql, values).list();
    }

    public List<T> findLatest(final String hql, final Integer maxResults, final Object... values) {
        return createQuery(hql, values).setMaxResults(maxResults).list();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 数量可变的参数
     */
    public List<Object> findList(final String hql, final Object... values) {
        return createQuery(hql, values).list();
    }

    public List<T> findIn(final String hql, final String placeHolder, final List values, final Object ... params) {
        return createQueryIn(hql, placeHolder, values, params).list();
    }


    /**
     * 按HQL查询唯一对象.
     */
    public T findUnique(final String hql, final Object... values) {
        return (T) createQuery(hql, values).uniqueResult();
    }

    public T findUniqueIn(final String hql, final String placeHolder, final List values) {
        return (T) createQueryIn(hql, placeHolder, values).uniqueResult();
    }

    /**
     * 按HQL查询Integer类型结果.
     */
    public Integer findInt(final String hql, final Object... values) {
        return (Integer) findUnique(hql, values);
    }

    public Integer findIntIn(final String hql, final String placeHolder, final List inParams, final Object... values) {
        return (Integer) findUnique(hql, placeHolder, inParams, values);
    }

    /**
     * 按HQL查询Long类型结果.
     */
    public Long findLong(final String hql, final Object... values) {
        return (Long) findUnique(hql, values);
    }

    public Long findLongIn(final String hql, String placeHolder, final List values) {
        return (Long) findUniqueIn(hql, placeHolder, values);
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * <p/>
     * 返回对象类型不是Entity时可用此函数灵活查询.
     *
     * @param values 数量可变的参数
     */
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString);
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    public Query createQueryIn(final String queryString, final String placeHolder, final List values, final Object ... params) {
        Assert.hasText(queryString);
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setParameterList(placeHolder, values);
        }
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
        return query;
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> findByCriteria(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 根据Criterion条件创建Criteria.
     * <p/>
     * 返回对象类型不是Entity时可用此函数灵活查询.
     *
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * <p/>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(final String propertyName,
                                    final Object newValue, final Object orgValue) {
        if (newValue == null || newValue.equals(orgValue)) return true;
        Object object = findUniqueByProperty(propertyName, newValue);
        return (object == null);
    }

    /**
     * 取得对象的主键名.
     */
    public String getIdName() {
        ClassMetadata meta = hibernateTemplate.getSessionFactory().getClassMetadata(entityClass);
        Assert.notNull(meta, "Class " + entityClass.getSimpleName()
                + " not define in HibernateSessionFactory.");
        return meta.getIdentifierPropertyName();
    }

    ////////////////////
    ///  分页查询函数
    ////////////////////

    /**
     * 分页获取全部对象.
     */
    public Page<T> getAll(final Page<T> page) {
        return findByCriteria(page);
    }

    /**
     * 按HQL分页查询.
     * 不支持自动获取总结果数,需用户另行执行查询.
     *
     * @param page   分页参数.仅支持pageSize 和firstResult,忽略其他参数.
     * @param hql    hql语句.
     * @param values 数量可变的查询参数.
     * @return 分页查询结果, 附带结果列表及所有查询时的参数.
     */
    public Page<T> find(final Page<T> page, final String hql, final Object... values) {
        Assert.notNull(page);

        String newHql = setOrderByParamter(hql, page);

        Query q = createQuery(newHql, values);
        setPageParameter(q, page);
        List<T> result = q.list();
        page.setResult(result);
        return page;
    }


    public Page<T> findInPage(Page<T> page, final String hql, final String placeHolder, final List values, final Object ... params) {
        Query q = createQueryIn(hql, placeHolder, values, params);
        setPageParameter(q, page);
        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    /**
     * 设置排序参数到hql, 辅助函数.
     */
    private String setOrderByParamter(final String hql, final Page<T> page) {

        if (!page.isOrderBySetted()) return hql;


        String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
        String[] orderArray = StringUtils.split(page.getOrder(), ',');

        Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

        if (orderByArray.length == 0) return hql;

        StringBuilder newHql = new StringBuilder(hql).append(" order by ");

        newHql.append(orderByArray[0]).append(" ").append(orderArray[0]);

        for (int i = 1; i < orderByArray.length; i++) {
            newHql.append(", ").append(orderByArray[i]).append(" ").append(orderArray[i]);
        }

        return newHql.toString();
    }

    /**
     * 按Criteria分页查询.
     *
     * @param page       分页参数.支持pageSize、firstResult和orderBy、order、autoCount参数.
     *                   其中autoCount指定是否动态获取总结果数.
     * @param criterions 数量可变的Criterion.
     * @return 分页查询结果.附带结果列表及所有查询时的参数.
     */
    public Page<T> findByCriteria(final Page<T> page, final Criterion... criterions) {
        Assert.notNull(page);

        Criteria c = createCriteria(criterions);

        if (page.isAutoCount()) {
            int totalCount = countCriteriaResult(c, page);
            page.setTotalCount(totalCount);
        }

        setPageParameter(c, page);
        List<T> result = c.list();
        page.setResult(result);
        return page;
    }

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    protected Query setPageParameter(final Query q, final Page<T> page) {
        q.setFirstResult(page.getFirst());
        q.setMaxResults(page.getPageSize());
        return q;
    }

    /**
     * 设置分页参数到Criteria对象,辅助函数.
     */
    protected Criteria setPageParameter(final Criteria c, final Page<T> page) {
        c.setFirstResult(page.getFirst());
        c.setMaxResults(page.getPageSize());

        if (page.isOrderBySetted()) {
            String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
            String[] orderArray = StringUtils.split(page.getOrder(), ',');

            Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

            for (int i = 0; i < orderByArray.length; i++) {
                if (Page.ASC.equals(orderArray[i])) {
                    c.addOrder(Order.asc(orderByArray[i]));
                } else {
                    c.addOrder(Order.desc(orderByArray[i]));
                }
            }
        }
        return c;
    }

    /**
     * 执行count查询获得本次Criteria查询所能获得的对象总数.
     */
    protected int countCriteriaResult(final Criteria c, final Page<T> page) {
        CriteriaImpl impl = (CriteriaImpl) c;

        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List<CriteriaImpl.OrderEntry>) ReflectionUtils.getFieldValue(impl, "orderEntries");
            ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList<CriteriaImpl.OrderEntry>());
        } catch (Exception e) {
            logger.error("Impossible Exception throwed: ", e);
        }

        // 执行Count查询
        int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();

        // 将之前的Projection, ResultTransformer 和 OrderBy 条件重新设回去
        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            logger.error("Impossible Exception throwed: ", e);
        }

        return totalCount;
    }

    ////////////////////
    ///  属性条件查询函数
    ////////////////////

    /**
     * 按属性查找对象列表,支持多种匹配方式.
     *
     * @param propertyName
     * @param value
     * @param matchTypeStr 目前支持的取值为"EQUAL"与"LIKE".
     */
    public List<T> findByProperty(final String propertyName, final Object value, String matchTypeStr) {
        PropertyFilter.MatchType matchType = Enum.valueOf(PropertyFilter.MatchType.class, matchTypeStr);
        Criterion criterion = buildPropertyCriterion(propertyName, value, matchType);
        return findByCriteria(criterion);
    }

    /**
     * 按属性过滤条件列表查找对象列表.
     */
    public List<T> findByFilters(final List<PropertyFilter> filters) {
        Criterion[] criterions = buildPropertyFilterCriterions(filters);
        return findByCriteria(criterions);
    }

    /**
     * 按属性过滤条件列表分页查找对象.
     */
    public Page<T> findByFilters(final Page<T> page, final List<PropertyFilter> filters) {
        Criterion[] criterions = buildPropertyFilterCriterions(filters);
        return findByCriteria(page, criterions);
    }

    /**
     * 按属性条件列表创建Criterion数组,辅助函数.
     */
    protected Criterion[] buildPropertyFilterCriterions(List<PropertyFilter> filters) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        for (PropertyFilter filter : filters) {
            String propertyName = filter.getPropertyName();

            boolean multiProperty = StringUtils.contains(propertyName, "|");
            if (!multiProperty) { //properNameName中只有一个属性的情况.
                Criterion criterion = buildPropertyCriterion(propertyName, filter.getValue(), filter.getMatchType());
                criterionList.add(criterion);
            } else {//properName中包含多个属性的情况,进行or处理.
                Disjunction disjunction = Restrictions.disjunction();
                String[] params = StringUtils.split(propertyName, '|');

                for (String param : params) {
                    Criterion criterion = buildPropertyCriterion(param, filter.getValue(), filter.getMatchType());
                    disjunction.add(criterion);
                }
                criterionList.add(disjunction);
            }
        }
        return criterionList.toArray(new Criterion[criterionList.size()]);
    }

    /**
     * 按属性条件参数创建Criterion,辅助函数.
     */
    protected Criterion buildPropertyCriterion(final String propertyName, final Object value, PropertyFilter.MatchType matchType) {
        Assert.hasText(propertyName);
        Criterion criterion = null;

        if (PropertyFilter.MatchType.EQUAL.equals(matchType)) {
            criterion = Restrictions.eq(propertyName, value);
        }
        if (PropertyFilter.MatchType.LIKE.equals(matchType)) {
            criterion = Restrictions.like(propertyName, (String) value, MatchMode.ANYWHERE);
        }

        return criterion;
    }

    /**
     * 通用的sql查询语句返回列表实现 values中的值代替sql中的？
     */
    public List<T> findBySql(final String sql, final Object... values) {
        if (sql != null) {
            List<T> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.ALIAS_TO_ENTITY_MAP);
                            setParameters(values, q);

                            return q.list();
                        }
                    });
            return list;
        } else {
            return null;
        }
    }

    /**
     * 通用的sql查询语句返回列表实现 values中的值代替sql中的？
     */
    public List<T> findBySql(final String sql, final Class clazz, final Object... values) {
        if (sql != null) {
            List<T> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.aliasToBean(clazz));
                            setParameters(values, q);

                            return q.list();
                        }
                    });
            return list;
        } else {
            return null;
        }
    }

    /**
     * 通用的sql查询语句返回列表实现 values中的值代替sql中的？
     */
    public List<Object> findBySqlWithParams(final String sql, final Object... values) {
        if (sql != null) {
            List<Object> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.ALIAS_TO_ENTITY_MAP);
                            setParameters(values, q);

                            return q.list();
                        }
                    });
            return list;
        } else {
            return null;
        }
    }

    public List<T> findBySqlWithListAndParams(final String sql, final Class clazz, final String placeHolder, final List paramsList, final Object... values) {
        if (sql != null) {
            List<T> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.aliasToBean(clazz));
                            q.setParameterList(placeHolder, paramsList);
                            setParameters(values, q);
                            return q.list();
                        }
                    });
            return list;
        } else {
            return null;
        }
    }


    public List<Object> findBySqlWithList(final String sql, final Class clazz, final String placeHolder, final List paramsList) {
        if (sql != null) {
            List<Object> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.aliasToBean(clazz));
                            q.setParameterList(placeHolder, paramsList);
                            return q.list();
                        }
                    });
            return list;
        } else {
            return null;
        }
    }

    public List<T> findObjectsBySqlWithList(final String sql, final Class clazz, final String placeHolder, final List paramsList) {
        if (sql != null) {
            List<T> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.aliasToBean(clazz));
                            q.setParameterList(placeHolder, paramsList);
                            return q.list();
                        }
                    });
            return list;
        } else {
            return null;
        }
    }

    /**
     * 通用的sql查询语句返回列表实现 values中的值代替sql中的？
     */
    public List<Object> findBySql(final String sql) {
        if (sql != null) {
            List<Object> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            return session.createSQLQuery(sql.toString()).list();
                        }
                    });
            return list;
        } else {
            return null;
        }
    }

    /**
     * 通用的sql查询语句返回部分列表实现 values中的值代替sql中的？
     */
    public List<T> findBySql(final Page<T> page, final String sql,
                             final Object[] values) {
        if (page.getFirst() >= 0 && page.getPageSize() > 0 && sql != null) {
            List<T> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.ALIAS_TO_ENTITY_MAP);
                            setParameters(values, q);
                            setPageParameter(q, page);

                            return q.list();
                        }
                    });
            return list;
        } else {
            return null;
        }
    }

    public Page<Object> findBySqlWithObject(final Page page, final String sql,
                             final Object[] values) {
        if (page.getFirst() >= 0 && page.getPageSize() > 0 && sql != null) {
            List<Object> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.ALIAS_TO_ENTITY_MAP);
                            setParameters(values, q);
                            setPageParameter(q, page);

                            return q.list();
                        }
                    });
            page.setResult(list);
            return page;
        } else {
            return null;
        }
    }

    public Page<T> findBySqlWithObject(final Page page, final String sql, final Class clazz,
                                       final Object[] values) {
        if (page.getFirst() >= 0 && page.getPageSize() > 0 && sql != null) {
            List<T> list = hibernateTemplate.executeFind(
                    new HibernateCallback<Object>() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Query q = session.createSQLQuery(sql.toString())
                                    .setResultTransformer(
                                            Transformers.aliasToBean(clazz));
                            setParameters(values, q);
                            setPageParameter(q, page);

                            return q.list();
                        }
                    });
            page.setResult(list);
            return page;
        } else {
            return null;
        }
    }

    private void setParameters(final Object[] values, Query q) {
        if (null != values && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                q.setParameter(i, values[i]);
            }
        }
    }

    /**
     * 通用的sql查询语句返回结果数的实现,sql以from开头 values中的值代替sql中的？
     */
    @SuppressWarnings("rawtypes")
    public Integer findCountBySql(final String sql, final Object[] values) {
        if (sql != null) {
            Object count = hibernateTemplate.execute(new HibernateCallback() {
                public Object doInHibernate(Session session)
                        throws HibernateException {
                    SQLQuery q = session.createSQLQuery(sql);
                    setParameters(values, q);

                    Object object = q.uniqueResult();

                    if(object == null){
                        return 0;
                    }

                    if(q.uniqueResult() instanceof BigDecimal){
                        return ((BigDecimal) q.uniqueResult()).intValue();
                    }
                    Integer count = ((BigInteger) q.uniqueResult()).intValue();

                    return count;

                }
            });
            return (Integer)count;
        } else {
            return 0;
        }
    }
}
