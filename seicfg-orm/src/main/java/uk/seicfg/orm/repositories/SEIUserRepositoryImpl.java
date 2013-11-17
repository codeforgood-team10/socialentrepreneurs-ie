package uk.seicfg.orm.repositories;

import uk.seicfg.orm.entities.SEIUser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SEIUserRepositoryImpl extends PersistableRepositoryImpl<SEIUser> implements SEIUserRepository {

	protected static final Logger LOG = LoggerFactory.getLogger(SEIUserRepositoryImpl.class);
	
	@Override
	public List<SEIUser> findAll() {
		LOG.info("SEIUserRepositoryImpl <- findAll()");
		return this.persistentEntityDao.findAll(SEIUser.class);
	}

	@Override
	public SEIUser findById(Class<SEIUser> clz, Long id) {
		LOG.info("SEIUserRepositoryImpl <- findById(" + id + ")");
		return this.persistentEntityDao.findOne(SEIUser.class, id);
	}
	
}
