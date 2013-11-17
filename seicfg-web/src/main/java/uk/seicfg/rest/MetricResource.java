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

import uk.seicfg.service.MetricService;
import uk.seicfg.service.UserService;
import uk.seicfg.to.Metric;
import uk.seicfg.util.converter.MetricConverter;

@Controller
public class MetricResource extends AbstractResource{

	@Autowired
	private MetricService metricService;

	@Autowired
	private UserService userService;

	private MetricConverter metricConverter = new MetricConverter();
	protected static final Logger LOG = LoggerFactory.getLogger(MetricResource.class);
	
    @RequestMapping(value="metrics")
    @ResponseBody
	public List findAll() {
	    	LOG.info("MetricResource <- findAll()");
	        return metricService.getAllMetrics();
	    }
	    
	    
    @RequestMapping(value="metrics/{uid}", produces = "application/json")
    @ResponseBody
	public Metric findById(@PathVariable Long uid) {
		LOG.info("MetricResource <- findById(" + uid +")");
		return metricService.getMetric(uid.toString());
	}
    
    @RequestMapping(value ="metrics/search", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
	public List<Metric> findByType(@RequestParam("type") String type) {
    	LOG.info("MetricResource <- findByName(" + type +")");
		return metricService.getMetricsByType(type);
	}
    
	@RequestMapping(value ="metrics/del/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public void remove(@PathVariable String uid) {
		LOG.info("MetricResource <- remove(" + uid + ")");
		metricService.deleteMetric(metricService.getMetric(uid));
	}
	
	@RequestMapping(value="metrics/add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Metric create(@RequestBody String metricdata) {
			LOG.info("MetricResource <- create()");
			HashMap<String, Object> result;
			try {
				Metric metric = new Metric();
				
				result = new ObjectMapper().readValue(metricdata, HashMap.class);
				String type = result.get("type").toString();
				String value = result.get("value").toString();
				String remarks = result.get("remarks").toString();
				
				metric.setType(type);
				metric.setValue(value);
				metric.setRemarks(remarks);
				prepareMetricObject(metric);
				return metricService.createMetric(metricConverter.convertTo(metric));
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

	@RequestMapping(value ="metrics/mod" ,method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
		public Metric update(@RequestBody Metric metric) {
			LOG.info("MetricResource <- Update(" + metric.getId() + ")");
			return metricService.updateMetric(metricConverter.convertTo(metric));
		}
		
		private Metric prepareMetricObject(Metric metric){
			metric.setCreatedby("1");
			metric.setCreationDateTime((new Date()).toString());
			metric.setLastModifiedDate((new Date()).toString());
			metric.setModifiedby("1");
			metric.setUser(userService.getUser("1"));
			return metric;
		}
		
		public Boolean isMetricExists(String metricID){
			LOG.info("MetricResource <- isMetricExists()");
			if(metricService.getMetrics(metricID) != null){
				return metricService.getMetrics(metricID).size() > 0 ? true : false;
			}
			return false;
		}
}
