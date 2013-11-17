package uk.seicfg.service;

import java.util.List;

import uk.seicfg.to.User;


public interface UserService {
	
	List<User> getAllUsers();
	
	List<User> getUsers(String emailid);
	
	User getUser(String userId);
	
	User createUser(uk.seicfg.orm.entities.SEIUser user);
	
	User updateUser(uk.seicfg.orm.entities.SEIUser user);
	
	void deleteUser(User user);
	
}
