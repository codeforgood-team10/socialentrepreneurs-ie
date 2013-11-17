package uk.seicfg.orm.repositories;

import uk.seicfg.orm.dao.PersistentEntityDao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class PersistableRepositoryImpl<T> implements PersistableRepository<T> {

	protected static final Logger LOG = LoggerFactory.getLogger(PersistableRepositoryImpl.class);

    @Autowired
    protected PersistentEntityDao<T> persistentEntityDao;

    @Override
    public T create(T persistable) {
    	LOG.info("PersistableRepositoryImpl <- create()");
        return this.persistentEntityDao.save(persistable);
    }

    @Override
    public T update(T persistable) {
    	LOG.info("PersistableRepositoryImpl <- update()");
        return this.persistentEntityDao.saveOrUpdate(persistable);
    }

    @Override
    public void remove(T persistable) {
    	LOG.info("PersistableRepositoryImpl <- remove()");
        this.persistentEntityDao.delete(persistable);

    }
    
    @Override
	public T saveOrUpdate(T persistable) {
    	LOG.info("PersistableRepositoryImpl <- saveOrUpdate()");
        return this.persistentEntityDao.saveOrUpdate(persistable);

    }
	
    @Override
    public List<T> executeQuery(String query, Map<String, Object> queryParams){
    	LOG.info("PersistableRepositoryImpl <- executeQuery() -> [query: " + query + "; queryParams:" + queryParams + "]");
    	return this.persistentEntityDao.executeQuery(query, queryParams);
    }
}
