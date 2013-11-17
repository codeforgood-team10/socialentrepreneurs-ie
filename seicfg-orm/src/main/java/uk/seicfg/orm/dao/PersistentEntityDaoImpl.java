package uk.seicfg.orm.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PersistentEntityDaoImpl<T> implements PersistentEntityDao<T> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public T findOne(Class<T> clz, Long id) {
		return entityManager.find(clz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> clz) {
		return entityManager.createQuery("from " + clz.getName()).getResultList();
	}

	@Override
	public T save(final T entity) {
		return entityManager.merge(entity);
	}

	@Override
	public T saveOrUpdate(final T entity) {
		return entityManager.merge(entity);
	}

	@Override
	public void delete(final T entity) {
		entityManager.remove(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> executeQuery(String queryString, Map<String, Object> queryParams) {
		Query query = this.entityManager.createQuery(queryString);
		if(queryParams != null){
			Iterator<String> it = queryParams.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				Object value = queryParams.get(key);
				query.setParameter(key, value);
			}
		}
		List<T> results = query.getResultList();
		return results;
	}

}
