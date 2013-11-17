package uk.seicfg.orm.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uk.seicfg.orm.entities.SEIMetric;

@Repository
public class SEIMetricRepositoryImpl extends PersistableRepositoryImpl<SEIMetric> implements SEIMetricRepository {

	protected static final Logger LOG = LoggerFactory.getLogger(SEIMetricRepositoryImpl.class);
	
	@Override
	public List<SEIMetric> findAll() {
		LOG.info("SEIMetricRepositoryImpl <- findAll()");
		return this.persistentEntityDao.findAll(SEIMetric.class);
	}

	@Override
	public SEIMetric findById(Class<SEIMetric> clz, Long id) {
		LOG.info("SEIMetricRepositoryImpl <- findById(" + id + ")");
		return this.persistentEntityDao.findOne(SEIMetric.class, id);
	}
	
}
