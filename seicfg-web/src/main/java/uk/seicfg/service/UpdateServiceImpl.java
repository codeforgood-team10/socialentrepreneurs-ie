package uk.seicfg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.seicfg.orm.entities.SEIMetric;
import uk.seicfg.orm.entities.SEIUpdate;
import uk.seicfg.orm.entities.SEIUser;
import uk.seicfg.orm.repositories.SEIUpdateRepository;
import uk.seicfg.to.Metric;
import uk.seicfg.to.Update;
import uk.seicfg.util.converter.UpdateConverter;

@Component
public class UpdateServiceImpl implements UpdateService {

	protected static final Logger LOG = LoggerFactory.getLogger(UpdateServiceImpl.class);

	@Autowired
	private SEIUpdateRepository updateRepository;

	private UpdateConverter updateConverter = new UpdateConverter();

	@Override
	public List<Update> getAllUpdates() {
		LOG.info("UpdateServiceImpl -> getAllUpdates()");
		List<SEIUpdate> updatesList = this.updateRepository.findAll();
		List<uk.seicfg.to.Update> updatesList2 = new ArrayList<uk.seicfg.to.Update>();
		for(SEIUpdate update : updatesList) {
			updatesList2.add(updateConverter.convertFrom(update));
		}
		return updatesList2;

	}

	@Override
	public List<Update> getUpdates(String uid) {
		String queryStr = "FROM SEIUpdate as U WHERE Lower(U.uid) LIKE :uid order by id";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("uid", uid);
		LOG.info("UserServiceImpl <- getUpdates(" + uid +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIUpdate> updateList = this.updateRepository.executeQuery(queryStr, queryParams);
		List<uk.seicfg.to.Update> updateList2 = new ArrayList<uk.seicfg.to.Update>();
		for(SEIUpdate update : updateList) {
			updateList2.add(updateConverter.convertFrom(update));
		}
		return updateList2;
	}

	@Override
	public Update getUpdate(String id) {
		LOG.info("UpdateServiceImpl <- getUpdate(" + id + ")");
		SEIUpdate update = this.updateRepository.findById(SEIUpdate.class, Long.valueOf(id));
		if(update != null)
			return updateConverter.convertFrom(update);
		else
			return null;
	}

	@Override
	public List<Update> getUpdatesByType(String type){
		LOG.info("UpdateServiceImpl <- getMetric(" + type + ")");
		if(type != null) 
			type = type.toLowerCase();
		String queryStr = "FROM SEIUpdate as U WHERE Lower(U.type) = Lower(:type) order by id";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("type", type);
		System.out.println("UpdateServiceImpl <- getUpdate(" + type +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIUpdate> updateList = this.updateRepository.executeQuery(queryStr, queryParams);
		List<uk.seicfg.to.Update> updateList2 = new ArrayList<uk.seicfg.to.Update>();
		for(SEIUpdate update : updateList) {
			updateList2.add(updateConverter.convertFrom(update));
		}
		System.out.println("\n updateList2 " + updateList2);
		return updateList2;
	}

	@Override
	public Update createUpdate(SEIUpdate update) {
		LOG.info("UpdateServiceImpl <- createUpdate(" + update + ")");
		return updateConverter.convertFrom(this.updateRepository.create(update));
	}

	@Override
	public Update updateUpdate(SEIUpdate update) {
		LOG.info("UpdateServiceImpl <- updateUpdate(" + update + ")");
		return updateConverter.convertFrom(this.updateRepository.update(update));
	}

	@Override
	public void deleteUpdate(Update update) {
		LOG.info("UserServiceImpl <- deleteUser(" + update + ")");
		this.updateRepository.remove(updateConverter.convertTo(update));		
	}
}
