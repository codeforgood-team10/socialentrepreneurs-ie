package uk.seicfg.util.converter;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.seicfg.orm.entities.SEIMessage;


public final class MessageConverter implements Converter<SEIMessage, uk.seicfg.to.Message> {

	protected static final Logger LOG = LoggerFactory.getLogger(MessageConverter.class);
	private static final UserConverter userConverter = new UserConverter();

	@Override
	public uk.seicfg.to.Message convertFrom(SEIMessage from) {
		LOG.info("UpdateConverter <- convertFrom()");
		uk.seicfg.to.Message update = new uk.seicfg.to.Message();
		update.setCreatedby(from.getCreatedby().toString());
		update.setCreationDateTime(from.getCreationDateTime().toString());
		update.setId(from.getId());
		update.setLastModifiedDate(from.getLastModifiedDate().toString());
		update.setModifiedby(from.getModifiedby().toString());
		update.setRemarks(from.getRemarks());
		update.setType(from.getType());
		update.setSolved(from.getSolved());
		update.setText(from.getText());
		update.setUser(userConverter.convertFrom(from.getUser()));
		return update;
	}

	@Override
	public SEIMessage convertTo(uk.seicfg.to.Message to) {
		LOG.info("UpdateConverter <- convertTo()");
		SEIMessage update = new SEIMessage();
		update.setCreatedby(BigInteger.valueOf(Long.parseLong(to.getCreatedby())));
		update.setCreationDateTime(new Date(to.getCreationDateTime()));
		update.setId(to.getId());
		update.setLastModifiedDate(new Timestamp(new Date(to.getLastModifiedDate()).getTime()));
		update.setModifiedby(BigInteger.valueOf(Long.parseLong(to.getModifiedby())));
		update.setRemarks(to.getRemarks());
		update.setType(to.getType());
		update.setSolved(to.getSolved());
		update.setText(to.getText());
		update.setUser(userConverter.convertTo(to.getUser()));
		return update;
	}

}
