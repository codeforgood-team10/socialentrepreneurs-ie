package uk.seicfg.service;

import java.util.List;

import uk.seicfg.to.Metric;
import uk.seicfg.to.Update;


public interface UpdateService {
	
	List<Update> getAllUpdates();
	
	List<Update> getUpdates(String emailid);
	
	Update getUpdate(String id);
	
	List<Update> getUpdatesByType(String type);
	
	Update createUpdate(uk.seicfg.orm.entities.SEIUpdate update);
	
	Update updateUpdate(uk.seicfg.orm.entities.SEIUpdate update);
	
	void deleteUpdate(Update update);
	
}
