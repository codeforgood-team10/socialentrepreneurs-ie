package uk.seicfg.orm.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uk.seicfg.orm.entities.SEIMessage;

@Repository
public class SEIMessageRepositoryImpl extends PersistableRepositoryImpl<SEIMessage> implements SEIMessageRepository {

	protected static final Logger LOG = LoggerFactory.getLogger(SEIMessageRepositoryImpl.class);
	
	@Override
	public List<SEIMessage> findAll() {
		LOG.info("SEIMessageRepositoryImpl <- findAll()");
		return this.persistentEntityDao.findAll(SEIMessage.class);
	}

	@Override
	public SEIMessage findById(Class<SEIMessage> clz, Long id) {
		LOG.info("SEIMessageRepositoryImpl <- findById(" + id + ")");
		return this.persistentEntityDao.findOne(SEIMessage.class, id);
	}
	
}
