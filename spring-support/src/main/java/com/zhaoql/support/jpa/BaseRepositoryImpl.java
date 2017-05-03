package com.zhaoql.support.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.internal.QueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Lists;

public class BaseRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {
    private final Class<T> entityClass;
    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ?> entityInformation;

    public BaseRepositoryImpl(Class<T> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        entityInformation = new JpaMetamodelEntityInformation<T, ID>(entityClass, entityManager.getMetamodel());
    }

    public static void main(String[] args) {
        Customer target = new Customer();
        target.setId("1");

        Customer des = new Customer();
        Field[] fields = Customer.class.getDeclaredFields();
        // ReflectionUtils.getAllDeclaredMethods(leafClass)
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
            Object v = ReflectionUtils.getField(field, target);
            System.err.println(field.getName() + "++ " + v);
            ReflectionUtils.setField(field, des, v);
        }
        System.err.println(des.getId());
        // ReflectionUtils.getAllDeclaredMethods(entityClass);

    }

    @Override
    public T getOneByUpdate(ID id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        Predicate pre = builder.equal(root.get(getPrimaryKey()).as(Integer.class), id);
        criteriaQuery.where(pre);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setLockMode(LockModeType.READ);
        List<T> list = query.getResultList();
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public int update(T entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = builder.createCriteriaUpdate(entityClass);
        Root<T> root = criteriaUpdate.from(entityClass);
        Field[] fields = entityClass.getDeclaredFields();
        Object primaryV = null;
        String primaryKey = this.getPrimaryKey();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
            Object fieldV = ReflectionUtils.getField(field, entity);
            if (fieldV == null)
                continue;
            if (primaryKey.equals(field.getName())) {// 主键不参与修改
                primaryV = fieldV;
            } else {
                criteriaUpdate.set(root.get(field.getName()), fieldV);
            }
        }
        criteriaUpdate.where(builder.equal(root.get(primaryKey), primaryV));
        Query query = entityManager.createQuery(criteriaUpdate);
        return query.executeUpdate();

    }

    @Override
    public <X> Page<X> find(String hql, String countHql, Map<String, ?> params, Pageable pageable, Class<X> clazz) {
        long total = count(countHql, params);
        List<X> content = total > pageable.getOffset() ? find(hql, params, pageable, clazz) : Lists.newArrayList();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public <X> List<X> find(String hql, Map<String, ?> params, Class<X> clazz) {
        Assert.hasText(hql, "hql can not been null or blank");
        Query query = entityManager.createQuery(hql);
        if (query != null) {
            query.unwrap(QueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz));
            if (params != null) {
                params.entrySet().forEach(param -> query.setParameter(param.getKey(), param.getValue()));
            }
            return query.getResultList();
        }
        return Lists.newArrayList();
    }

    @Override
    public <X> List<X> find(String hql, Map<String, ?> params, Pageable pageable, Class<X> clazz) {
        Assert.hasText(hql, "hql can not been null or blank");
        Query query = entityManager.createQuery(hql);
        if (query != null) {
            query.unwrap(QueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz));
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            if (params != null) {
                params.entrySet().forEach(param -> query.setParameter(param.getKey(), param.getValue()));
            }
            return query.getResultList();
        }
        return Lists.newArrayList();
    }

    @Override
    public long count(String hql, Map<String, ?> params) {
        Assert.hasText(hql, "hql can not been null or blank");
        Query countQuery = entityManager.createQuery(hql);
        if (countQuery != null) {
            if (params != null) {
                params.entrySet().forEach(param -> countQuery.setParameter(param.getKey(), param.getValue()));
            }
            return CollectionUtils.isNotEmpty(countQuery.getResultList()) ? ((Long) countQuery.getResultList().get(0)).longValue() : 0;
        }
        return 0;
    }

    @Override
    public <X> Page<X> findByNative(String sql, String countSql, List<?> params, Pageable pageable, Class<X> clazz) {
        long total = countByNative(countSql, params);
        List<X> content = total > pageable.getOffset() ? findByNative(sql, params, pageable, clazz) : Lists.newArrayList();
        return new PageImpl<>(content, pageable, total);
    }
 
    @Override
    public <X> List<X> findByNative(String sql, List<?> params, Class<X> clazz) {
        Assert.hasText(sql, "native sql can not been null or blank");
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
            if (CollectionUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.size(); i++) {
                    query.setParameter(i + 1, params.get(i));
                }
            }
            return query.getResultList();
        }
        return Lists.newArrayList();
    }

    @Override
    public <X> List<X> findByNative(String sql, List<?> params, Pageable pageable, Class<X> clazz) {
        Assert.hasText(sql, "native sql can not been null or blank");
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            if (CollectionUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.size(); i++) {
                    query.setParameter(i + 1, params.get(i));
                }
            }
            return query.getResultList();
        }
        return Lists.newArrayList();
    }

    @Override
    public long countByNative(String sql, List<?> params) {
        Assert.hasText(sql, "native sql can not been null or blank");
        Query countQuery = entityManager.createNativeQuery(sql);
        if (countQuery != null) {
            if (CollectionUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.size(); i++) {
                    countQuery.setParameter(i + 1, params.get(i));
                }
            }
            return CollectionUtils.isNotEmpty(countQuery.getResultList()) ? ((BigInteger) countQuery.getResultList().get(0)).longValue() : 0;
        }
        return 0;
    }

    private final String getPrimaryKey() {
        Iterable<String> ids = entityInformation.getIdAttributeNames();
        return ids.iterator().next();
    }

    public static class Customer {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
}
