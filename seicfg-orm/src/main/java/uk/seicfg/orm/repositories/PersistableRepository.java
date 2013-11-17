package uk.seicfg.orm.repositories;

import java.util.List;
import java.util.Map;

public interface PersistableRepository<T> {

    T create(T persistable);

    T update(T persistable);

    void remove(T persistable);

	T saveOrUpdate(T persistable);

	List<T> findAll();
	
	T findById(Class<T> clz, Long id);
	
	List<T> executeQuery(String query, Map<String, Object> queryParams);
}
