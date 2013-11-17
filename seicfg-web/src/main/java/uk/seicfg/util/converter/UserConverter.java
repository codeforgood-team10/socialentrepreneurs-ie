package uk.seicfg.util.converter;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.seicfg.orm.entities.SEIUser;
import uk.seicfg.util.SEIDateUtils;


public final class UserConverter implements Converter<SEIUser, uk.seicfg.to.User> {

	protected static final Logger LOG = LoggerFactory.getLogger(UserConverter.class);

	@Override
	public uk.seicfg.to.User convertFrom(SEIUser from) {
		LOG.info("UserConverter <- convertFrom()");
		uk.seicfg.to.User user = new uk.seicfg.to.User();
		user.setId(from.getId());
		user.setEmailId(from.getEmailId());
		user.setIsActive(from.getIsActive());
		if(from.getLastLogin() != null)
			user.setLastLogin(from.getLastLogin().toString());
		user.setPassword(from.getPassword());
		user.setType(from.getType());
		user.setCreatedby(from.getCreatedby().toString());
		user.setRemarks(from.getRemarks());
		if(from.getCreationDateTime() != null)
			user.setCreationDateTime(from.getCreationDateTime().toString());
		user.setModifiedby(from.getModifiedby().toString());
		if(from.getLastModifiedDate() != null)
			user.setLastModifiedDate(from.getLastModifiedDate().toString());		
		return user;
	}

	@Override
	public SEIUser convertTo(uk.seicfg.to.User to) {
		LOG.info("UserConverter <- convertTo()");
		SEIUser user = new SEIUser();
		if(to != null){
			user.setId(to.getId());
			user.setEmailId(to.getEmailId());
			user.setIsActive(to.getIsActive());
			user.setPassword(to.getPassword());
			user.setType(to.getType());
			user.setCreatedby(BigInteger.valueOf(Long.parseLong(to.getCreatedby())));
			user.setRemarks(to.getRemarks());
			user.setModifiedby(BigInteger.valueOf(Long.parseLong(to.getModifiedby())));
		}
		/*try {
			user.setLastLogin(SEIDateUtils.formatter.parse(to.getLastLogin()));
			user.setCreationDateTime(SEIDateUtils.formatter.parse(to.getCreationDateTime()));
			user.setLastModifiedDate(new Timestamp(SEIDateUtils.formatter.parse(to.getLastModifiedDate()).getTime()));

		} catch (ParseException e) {
			// TODO handle date parsing exceptions
			e.printStackTrace();
		}*/
		return user;
	}

}
