package uk.seicfg.util.converter;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.seicfg.orm.entities.SEIMetric;


public final class MetricConverter implements Converter<SEIMetric, uk.seicfg.to.Metric> {

	protected static final Logger LOG = LoggerFactory.getLogger(MetricConverter.class);
	private static final UserConverter userConverter = new UserConverter();

	@Override
	public uk.seicfg.to.Metric convertFrom(SEIMetric from) {
		LOG.info("MetricConverter <- convertFrom()");
		uk.seicfg.to.Metric update = new uk.seicfg.to.Metric();
		update.setCreatedby(from.getCreatedby().toString());
		update.setCreationDateTime(from.getCreationDateTime().toString());
		update.setId(from.getId());
		update.setLastModifiedDate(from.getLastModifiedDate().toString());
		update.setModifiedby(from.getModifiedby().toString());
		update.setRemarks(from.getRemarks());
		update.setType(from.getType());
		update.setValue(from.getValue().toString());
		update.setUser(userConverter.convertFrom(from.getUser()));
		return update;
	}

	@Override
	public SEIMetric convertTo(uk.seicfg.to.Metric to) {
		LOG.info("MetricConverter <- convertTo()");
		SEIMetric update = new SEIMetric();
		update.setCreatedby(BigInteger.valueOf(Long.parseLong(to.getCreatedby())));
		update.setCreationDateTime(new Date(to.getCreationDateTime()));
		update.setId(to.getId());
		update.setLastModifiedDate(new Timestamp(new Date(to.getLastModifiedDate()).getTime()));
		update.setModifiedby(BigInteger.valueOf(Long.parseLong(to.getModifiedby())));
		update.setRemarks(to.getRemarks());
		update.setType(to.getType());
		update.setUser(userConverter.convertTo(to.getUser()));
		update.setValue(BigInteger.valueOf(Long.parseLong(to.getValue())));
		return update;
	}

}
