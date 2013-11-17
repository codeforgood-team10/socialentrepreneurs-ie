package uk.seicfg.service;

import java.util.List;

import uk.seicfg.to.Metric;


public interface MetricService {
	
	List<Metric> getAllMetrics();
	
	List<Metric> getMetrics(String uid);
	
	Metric getMetric(String id);
	
	List<Metric> getMetricsByType(String type);
	
	Metric createMetric(uk.seicfg.orm.entities.SEIMetric metric);
	
	Metric updateMetric(uk.seicfg.orm.entities.SEIMetric metric);
	
	void deleteMetric(Metric metric);
	
}
