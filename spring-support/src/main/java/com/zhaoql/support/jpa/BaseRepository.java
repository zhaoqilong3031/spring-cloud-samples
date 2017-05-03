package com.zhaoql.support.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	/**
	 * 加锁
	 *
	 * @param id
	 * @return
	 */
	T getOneByUpdate(ID id);

	/**
	 * 编辑
	 *
	 * @param entity
	 * @return
	 */
	int update(T entity);

	/**
	 * hibernate 分页
	 *
	 * @param hql
	 * @param countHql
	 * @param params
	 * @param pageable
	 * @param <X>
	 * @return
	 */
	<X> Page<X> find(final String hql, final String countHql, final Map<String, ?> params, Pageable pageable,
			Class<X> clazz);

	/**
	 * 根据hibernate sql查找
	 *
	 * @param hql
	 * @param params
	 * @param <X>
	 * @return
	 */
	<X> List<X> find(final String hql, final Map<String, ?> params, Class<X> clazz);

	/**
	 * hql 带分页参数
	 *
	 * @param hql
	 * @param params
	 * @param pageable
	 * @param clazz
	 * @param <X>
	 * @return
	 */

	<X> List<X> find(final String hql, final Map<String, ?> params, Pageable pageable, Class<X> clazz);

	/**
	 * 查询count记录
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	long count(final String hql, final Map<String, ?> params);

	/**
	 * 原生的分页
	 *
	 * @param sql
	 * @param countSql
	 * @param params
	 * @param pageable
	 * @param <X>
	 * @return
	 */
	<X> Page<X> findByNative(final String sql, final String countSql, final List<?> params, Pageable pageable,
			Class<X> clazz);

	/**
	 * 根据原生的sql查询
	 *
	 * @param sql
	 * @param params
	 * @param <X>
	 * @return
	 */

	<X> List<X> findByNative(final String sql, final List<?> params, Class<X> clazz);

	/**
	 * 原生sql带分页参数
	 *
	 * @param sql
	 * @param params
	 * @param pageable
	 * @param clazz
	 * @param <X>
	 * @return
	 */
	<X> List<X> findByNative(final String sql, final List<?> params, Pageable pageable, Class<X> clazz);

	/**
	 * 查询原生的记录count数
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	long countByNative(final String sql, final List<?> params);
}
