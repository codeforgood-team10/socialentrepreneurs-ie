package uk.seicfg.util.converter;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.seicfg.orm.entities.SEIUpdate;


public final class UpdateConverter implements Converter<SEIUpdate, uk.seicfg.to.Update> {

	protected static final Logger LOG = LoggerFactory.getLogger(UpdateConverter.class);
	private static final UserConverter userConverter = new UserConverter();

	@Override
	public uk.seicfg.to.Update convertFrom(SEIUpdate from) {
		LOG.info("UpdateConverter <- convertFrom()");
		uk.seicfg.to.Update update = new uk.seicfg.to.Update();
		update.setCreatedby(from.getCreatedby().toString());
		update.setCreationDateTime(from.getCreationDateTime().toString());
		update.setId(from.getId());
		update.setLastModifiedDate(from.getLastModifiedDate().toString());
		update.setModifiedby(from.getModifiedby().toString());
		update.setRemarks(from.getRemarks());
		update.setStatus(from.getStatus());
		update.setType(from.getType());
		update.setUser(userConverter.convertFrom(from.getUser()));
		update.setValue(from.getValue());
		return update;
	}

	@Override
	public SEIUpdate convertTo(uk.seicfg.to.Update to) {
		LOG.info("UpdateConverter <- convertTo()");
		SEIUpdate update = new SEIUpdate();
		update.setCreatedby(BigInteger.valueOf(Long.parseLong(to.getCreatedby())));
		update.setCreationDateTime(new Date(to.getCreationDateTime()));
		update.setId(to.getId());
		update.setLastModifiedDate(new Timestamp(new Date(to.getLastModifiedDate()).getTime()));
		update.setModifiedby(BigInteger.valueOf(Long.parseLong(to.getModifiedby())));
		update.setRemarks(to.getRemarks());
		update.setStatus(to.getStatus());
		update.setType(to.getType());
		update.setUser(userConverter.convertTo(to.getUser()));
		update.setValue(to.getValue());
		return update;
	}

}
