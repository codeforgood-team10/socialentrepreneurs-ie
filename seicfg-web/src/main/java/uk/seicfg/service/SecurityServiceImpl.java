package uk.seicfg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.seicfg.orm.entities.SEIUser;
import uk.seicfg.orm.repositories.SEIUserRepository;
import uk.seicfg.util.converter.UserConverter;

@Component
public class SecurityServiceImpl implements SecurityService
{

	protected static final Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	private SEIUserRepository userRepository;

	private UserConverter userConverter = new UserConverter();
	private UserService userService = new UserServiceImpl();

	@Override
	public uk.seicfg.to.User authenticateUser(String emailid, String password) throws Exception 
	{
		LOG.info("SecurityServiceImpl -> authenticateUser() -> for user:" + emailid + " and password : " + password);
		uk.seicfg.to.User user = null;
		if(emailid == null)
			return null;
		user = getActiveUser(emailid);
		if(user == null)
			return null;
		LOG.info("and user.getpassword : " + user.getPassword());
		if(!(password != null &&  password.equals(user.getPassword())))
		{			
			user = null;
		}
		return user;
	}

	@Override
	public uk.seicfg.to.User getActiveUser(String emailid)
	{
		if(emailid == null || emailid == "")
			return null;
		String queryStr = "FROM SEIUser as U where LOWER(U.emailId) = :emailid and U.isActive = :isActive";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("emailid", emailid.toLowerCase());
		queryParams.put("isActive", "1");
		LOG.info("SecurityServiceImpl <- getActiveUser(" + emailid +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIUser> userList = userRepository.executeQuery(queryStr, queryParams);
		if(userList.size() > 0)
			return userConverter.convertFrom((SEIUser)userList.get(0));
		else
			return null;
	}

	

	@Override
	public boolean updatePassword(String emailid, String newPassword)
	{
		String queryStr = "update User u set u.password = :password , u.modificationdatetime = :time where LOWER(u.emailId) = :emailid";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("emailid", emailid.toLowerCase());
		queryParams.put("password", newPassword);
		LOG.info("SecurityServiceImpl <- updatePassword(" + emailid +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIUser> userList = userRepository.executeQuery(queryStr, queryParams);
		return userList.size() > 0;
	}

	@Override
	public boolean logout(SEIUser user)
	{
		LOG.info("SecurityServiceImpl -> logout(" + user.getEmailId() + ")");
		//we can do more
		return true;
	}

	@Override
	public boolean lockUser(String emailid)
	{
		String queryStr = "update User u set u.isBlocked = :isBlocked where LOWER(u.emailId) = :emailid";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isActive", false);
		queryParams.put("emailid", emailid.toLowerCase());
		LOG.info("SecurityServiceImpl <- lockUser(" + emailid +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIUser> userList = userRepository.executeQuery(queryStr, queryParams);
		return userList.size() > 0;
	}

	@Override
	public boolean unLockUser(String emailid)
	{
		String queryStr = "update User u set u.isActive = :isActive where LOWER(u.emailId) = :emailid";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isActive", true);
		queryParams.put("emailid", emailid.toLowerCase());
		LOG.info("SecurityServiceImpl <- unLockUser(" + emailid +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIUser> userList = userRepository.executeQuery(queryStr, queryParams);
		return userList.size() > 0;
	}

	public boolean changePassword(String emailid, String newPassword) throws Exception
	{
		LOG.info("SecurityServiceImpl -> changePassword(" + emailid + ")");
		uk.seicfg.to.User user2 = userService.getUser(emailid);
		user2.setPassword(newPassword);

		try
		{
			SEIUser ormUser = userConverter.convertTo(user2);
		}
		catch(Exception ex)
		{
			LOG.error("SecurityServiceImpl -> changePassword(" + emailid + ") = failed during addPasswordToHistory() with error:" + ex.getMessage());
			ex.printStackTrace();
		}
		boolean result = updatePassword(user2.getEmailId(), user2.getPassword());
		try
		{
			unLockUser(user2.getEmailId());
		}
		catch(Exception ex)
		{
			LOG.error("SecurityServiceImpl -> changePassword(" + emailid + ") = failed during unLockUser() with error:" + ex.getMessage());
			ex.printStackTrace();
		}
		if(result);
		return result;
	}
}
