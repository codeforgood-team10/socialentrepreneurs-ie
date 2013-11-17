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
import uk.seicfg.orm.repositories.SEIMetricRepository;
import uk.seicfg.to.Metric;
import uk.seicfg.util.converter.MetricConverter;

@Component
public class MetricServiceImpl implements MetricService {

	protected static final Logger LOG = LoggerFactory.getLogger(MetricServiceImpl.class);

	@Autowired
	private SEIMetricRepository metricRepository;

	private MetricConverter metricConverter = new MetricConverter();

	@Override
	public List<Metric> getAllMetrics() {
		LOG.info("MetricServiceImpl -> getAllMetrics()");
		List<SEIMetric> metricsList = this.metricRepository.findAll();
		List<uk.seicfg.to.Metric> metricsList2 = new ArrayList<uk.seicfg.to.Metric>();
		for(SEIMetric metric : metricsList) {
			metricsList2.add(metricConverter.convertFrom(metric));
		}
		return metricsList2;

	}

	@Override
	public List<Metric> getMetrics(String uid) {
		String queryStr = "FROM SEIMetric as U WHERE Lower(U.uid) LIKE :uid order by id";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("uid", uid);
		LOG.info("UserServiceImpl <- getMetrics(" + uid +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIMetric> metricList = this.metricRepository.executeQuery(queryStr, queryParams);
		List<uk.seicfg.to.Metric> metricList2 = new ArrayList<uk.seicfg.to.Metric>();
		for(SEIMetric metric : metricList) {
			metricList2.add(metricConverter.convertFrom(metric));
		}
		return metricList2;
	}

	@Override
	public Metric getMetric(String id) {
		LOG.info("MetricServiceImpl <- getMetric(" + id + ")");
		SEIMetric metric = this.metricRepository.findById(SEIMetric.class, Long.valueOf(id));
		if(metric != null)
			return metricConverter.convertFrom(metric);
		else
			return null;
	}
	
	@Override
	public List<Metric> getMetricsByType(String type) {
		LOG.info("MetricServiceImpl <- getMetric(" + type + ")");
		if(type != null) 
			type = type.toLowerCase();
		String queryStr = "FROM SEIMetric as U WHERE Lower(U.type) = Lower(:type) order by id";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("type", type);
		System.out.println("MetricServiceImpl <- getMetrics(" + type +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIMetric> metricList = this.metricRepository.executeQuery(queryStr, queryParams);
		List<uk.seicfg.to.Metric> metricList2 = new ArrayList<uk.seicfg.to.Metric>();
		for(SEIMetric metric : metricList) {
			metricList2.add(metricConverter.convertFrom(metric));
		}
		System.out.println("\n metricList2 " + metricList2);
		return metricList2;
	}

	@Override
	public Metric createMetric(SEIMetric metric) {
		LOG.info("MetricServiceImpl <- createMetric(" + metric + ")");
		return metricConverter.convertFrom(this.metricRepository.create(metric));
	}

	@Override
	public Metric updateMetric(SEIMetric metric) {
		LOG.info("MetricServiceImpl <- metricMetric(" + metric + ")");
		return metricConverter.convertFrom(this.metricRepository.update(metric));
	}

	@Override
	public void deleteMetric(Metric metric) {
		LOG.info("UserServiceImpl <- deleteUser(" + metric + ")");
		this.metricRepository.remove(metricConverter.convertTo(metric));		
	}
}
