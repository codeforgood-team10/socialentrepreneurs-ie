package uk.seicfg.orm.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uk.seicfg.orm.entities.SEIUpdate;

@Repository
public class SEIUpdateRepositoryImpl extends PersistableRepositoryImpl<SEIUpdate> implements SEIUpdateRepository {

	protected static final Logger LOG = LoggerFactory.getLogger(SEIUpdateRepositoryImpl.class);
	
	@Override
	public List<SEIUpdate> findAll() {
		LOG.info("SEIUpdateRepositoryImpl <- findAll()");
		return this.persistentEntityDao.findAll(SEIUpdate.class);
	}

	@Override
	public SEIUpdate findById(Class<SEIUpdate> clz, Long id) {
		LOG.info("SEIUpdateRepositoryImpl <- findById(" + id + ")");
		return this.persistentEntityDao.findOne(SEIUpdate.class, id);
	}
	
}
