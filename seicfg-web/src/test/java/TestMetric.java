import junit.framework.TestCase;
import junit.framework.TestResult;
import uk.seicfg.orm.repositories.SEIMetricRepository;
import uk.seicfg.orm.repositories.SEIMetricRepositoryImpl;
import uk.seicfg.service.MetricService;
import uk.seicfg.service.MetricServiceImpl;


public class TestMetric{


	public static void main(String args[]){
		TestMetric tnm = new TestMetric();
		SEIMetricRepository metricRepository = new SEIMetricRepositoryImpl();
		
		//System.out.println(metricRepository.executeQuery(query, queryParams));
	}

}
