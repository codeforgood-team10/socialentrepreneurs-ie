package uk.seicfg.rest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.seicfg.service.UserService;
import uk.seicfg.to.User;
import uk.seicfg.util.converter.UserConverter;

@Controller
public class UserResource extends AbstractResource{

	@Autowired
	private UserService userService;

	private UserConverter userConverter = new UserConverter();
	protected static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

	@RequestMapping(value="users")
	@ResponseBody
	public List findAll() {
		LOG.info("UserResource <- findAll()");
		return userService.getAllUsers();
	}


	@RequestMapping(value="users/{uid}", produces = "application/json")
	@ResponseBody
	public User findById(@PathVariable Long uid) {
		LOG.info("UserResource <- findById(" + uid +")");
		return userService.getUser(uid.toString());
	}

	@RequestMapping(value ="users/search}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<User> findByType(@RequestParam("type") String type) {
		LOG.info("UserResource <- findByName(" + type +")");
		return userService.getUsers(type);
	}

	@RequestMapping(value ="users/del/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public void remove(@PathVariable String uid) {
		LOG.info("UserResource <- remove(" + uid + ")");
		userService.deleteUser(userService.getUser(uid));
	}

	@RequestMapping(value="users/add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User create(@RequestBody String userdata) {

		LOG.info("UserResource <- User add()" + userdata);
		HashMap<String, Object> result;
		try {
			result = new ObjectMapper().readValue(userdata, HashMap.class);
			String emailid = result.get("emailid").toString();
			String password = result.get("password").toString();
			String type = result.get("type").toString();
			if(isUserExists(emailid)){
				//TODO handle proper message
				return null;
			}
			if(password == null || password == ""){
				return null;
			}
			User user = new User();
			user.setEmailId(emailid);
			user.setPassword(password);
			user.setType(type);
			prepareUserObject(user);
			return userService.createUser(userConverter.convertTo(user));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {
			//TODO proper error message display
			e.printStackTrace();
		}
		return null;
}

@RequestMapping(value ="users/mod" ,method = RequestMethod.PUT, produces = "application/json")
@ResponseBody
public User update(@RequestBody User user) {
	LOG.info("UserResource <- Update(" + user.getId() + ")");
	return userService.updateUser(userConverter.convertTo(user));
}

private User prepareUserObject(User user){
	user.setCreatedby("0");
	user.setCreationDateTime((new Date()).toString());
	user.setIsActive("1");
	user.setLastLogin((new Date()).toString());
	user.setLastModifiedDate((new Date()).toString());
	user.setModifiedby("1");
	user.setRemarks("");
	return user;
}

public Boolean isUserExists(String userID){
	LOG.info("UserResource <- isUserExists()");
	if(userService.getUsers(userID) != null){
		return userService.getUsers(userID).size() > 0 ? true : false;
	}
	return false;
}

/*@RequestMapping(value = "users/login", method = RequestMethod.GET)
		public String login(Model model, HttpServletRequest request) {
			LOG.info("UserResource <- login()");
			if (request.getSession(false).getAttribute("SPRING_SECURITY_CONTEXT") != null) { 
    			//The user is logged in 
    			//Build user session object
				return "redirect:/"; 
			}
			model.addAttribute("error", "");
			//check the validity of client browser
			return "login" ;
		}

		@RequestMapping(value = "users/unsupportedbrowser", method = RequestMethod.GET)
		public final String unsupportedbrowser(Model model) {
			LOG.info("UserResource <- unsupportedbrowser()");
			return "unsupportedbrowser";
		}

		@RequestMapping(value = "users/{error}", method = RequestMethod.GET)
		public final String displayLoginform(Model model, @PathVariable final String error, HttpServletRequest request) {
			LOG.info("UserResource <- displayLoginform() -> error" + error);
			return "/" + error; 
		}

		@RequestMapping(value = "users/check", method = RequestMethod.GET) @ResponseBody
		public User checkIfUserLoggedIn(HttpServletRequest request) {
			LOG.info("UserResource <- checkIfUserLoggedIn()");
			if (request.getSession(false) != null && request.getSession(false).getAttribute("SPRING_SECURITY_CONTEXT") != null) { 
    			//The user is logged in :) 
    			//Build user session object
				return getSession().getUser();
    		}else
    			return null;
		}*/		
}
