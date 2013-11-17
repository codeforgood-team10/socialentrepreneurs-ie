package uk.seicfg.orm.dao;

import java.util.List;
import java.util.Map;

public interface PersistentEntityDao<T> {

	T save(T entity);

	T saveOrUpdate(T entity);

	void delete(T entity);

	T findOne(Class<T> clz, Long id);

	List<T> findAll(Class<T> clz);
	
	List<T> executeQuery(String query, Map<String, Object> queryParams);
	
}
