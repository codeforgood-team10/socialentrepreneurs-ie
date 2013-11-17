package uk.seicfg.service;

import java.util.ArrayList;
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
public class UserServiceImpl implements UserService {

	protected static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private SEIUserRepository userRepository;

	private UserConverter userConverter = new UserConverter();

	@Override
	public List<uk.seicfg.to.User> getAllUsers() {
		LOG.info("UserServiceImpl -> getAllUsers()");
		List<SEIUser> userList = this.userRepository.findAll();
		List<uk.seicfg.to.User> userList2 = new ArrayList<uk.seicfg.to.User>();
		for(SEIUser user : userList) {
			userList2.add(userConverter.convertFrom(user));
		}
		return userList2;
	}

	@Override
	public List<uk.seicfg.to.User> getUsers(String emailId) {
		String queryStr = "FROM SEIUser as U WHERE Lower(U.emailId) LIKE :emailid order by id";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("emailid", emailId);
		LOG.info("UserServiceImpl <- getUsers(" + emailId +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIUser> userList = this.userRepository.executeQuery(queryStr, queryParams);
		List<uk.seicfg.to.User> userList2 = new ArrayList<uk.seicfg.to.User>();
		for(SEIUser user : userList) {
			userList2.add(userConverter.convertFrom(user));
		}
		return userList2;
	}

	@Override
	public uk.seicfg.to.User getUser(String userId) {
		LOG.info("UserServiceImpl <- getUser(" + userId + ")");
		SEIUser user = this.userRepository.findById(SEIUser.class, Long.valueOf(userId));
		if(user != null)
			return userConverter.convertFrom(user);
		else
			return null;
	}

	@Override
	public uk.seicfg.to.User createUser(SEIUser user) {
		LOG.info("UserServiceImpl <- createUser(" + user + ")");
		return userConverter.convertFrom(this.userRepository.create(user));
	}

	@Override
	public uk.seicfg.to.User updateUser(SEIUser user) {
		LOG.info("UserServiceImpl <- updateUser(" + user + ")");
		return userConverter.convertFrom(this.userRepository.update(user));
	}

	@Override
	public void deleteUser(uk.seicfg.to.User user) {
		LOG.info("UserServiceImpl <- deleteUser(" + user + ")");
		this.userRepository.remove(userConverter.convertTo(user));
	}
}
