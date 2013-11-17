package uk.seicfg.rest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.seicfg.service.UpdateService;
import uk.seicfg.service.UserService;
import uk.seicfg.to.Message;
import uk.seicfg.to.Update;
import uk.seicfg.util.converter.UpdateConverter;

@Controller
public class UpdateResource extends AbstractResource{

	@Autowired
	private UpdateService updateService;

	@Autowired
	private UserService userService;

	private UpdateConverter updateConverter = new UpdateConverter();
	protected static final Logger LOG = LoggerFactory.getLogger(UpdateResource.class);
	
	@RequestMapping(value="updates")
    @ResponseBody
	    public List findAll() {
	    	LOG.info("UpdateResource <- findAll()");
	        return updateService.getAllUpdates();
	    }
	    
	 @RequestMapping(value="updates/{uid}", produces = "application/json")
	    @ResponseBody
		public Update findById(@PathVariable Long uid) {
			LOG.info("UpdateResource <- findById(" + uid +")");
			return updateService.getUpdate(uid.toString());
		}
		
	 @RequestMapping(value ="updates/search", method = RequestMethod.GET, produces = "application/json")
	    @ResponseBody
		public List<Update> findByType(@RequestParam("type") String type) {
	    	LOG.info("UpdateResource <- findByType(" + type +")");
			return updateService.getUpdatesByType(type);
		}
	    
		@RequestMapping(value ="updates/del/{uid}", method = RequestMethod.DELETE)
		@ResponseBody
		public void remove(@PathVariable String uid) {
			LOG.info("UpdateResource <- remove(" + uid + ")");
			updateService.deleteUpdate(updateService.getUpdate(uid));
		}
		
		@RequestMapping(value="updates/add", method = RequestMethod.POST, produces = "application/json")
		public @ResponseBody Update create(@RequestBody String updatedata) {
			LOG.info("UpdateResource <- create()");
			HashMap<String, Object> result;
			try {
				result = new ObjectMapper().readValue(updatedata, HashMap.class);
				String type = result.get("type").toString();
				String value = result.get("value").toString();
				String status = result.get("status").toString();
				String remarks = result.get("remarks").toString();
				
				Update update = new Update();
				update.setType(type);
				update.setValue(value);
				update.setRemarks(remarks);
				update.setStatus(status);
				prepareUpdateObject(update);
				return updateService.createUpdate(updateConverter.convertTo(update));
				
			} catch (JsonParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}catch (Exception e) {
				//TODO proper error message display
				e.printStackTrace();
			}
			return null;
		}

		@RequestMapping(value ="updates/mod" ,method = RequestMethod.PUT, produces = "application/json")
		@ResponseBody
		public Update update(@RequestBody Update update) {
			LOG.info("UpdateResource <- Update(" + update.getId() + ")");
			return updateService.updateUpdate(updateConverter.convertTo(update));
		}
		
		private Update prepareUpdateObject(Update update){
			update.setCreatedby("1");
			update.setCreationDateTime((new Date()).toString());
			update.setLastModifiedDate((new Date()).toString());
			update.setModifiedby("1");
			update.setUser(userService.getUser("1"));
			return update;
		}
		
		public Boolean isUpdateExists(String updateID){
			LOG.info("UpdateResource <- isUpdateExists()");
			if(updateService.getUpdates(updateID) != null){
				return updateService.getUpdates(updateID).size() > 0 ? true : false;
			}
			return false;
		}
}
