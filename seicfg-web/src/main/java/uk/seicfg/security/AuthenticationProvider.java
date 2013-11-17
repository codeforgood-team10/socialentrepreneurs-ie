package uk.seicfg.security;

import uk.seicfg.service.SecurityService;
import uk.seicfg.to.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{

	@Autowired
    private SecurityService securityService;
	
	protected static final Logger LOG = LoggerFactory.getLogger(AuthenticationProvider.class);
	
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException
    {
        String login;
        String password;
        login = (String)authentication.getPrincipal();
        password = (String)authentication.getCredentials();
        User user;
        LOG.info("AuthenticationProvider <- authenticate() -> Logging in user:" + login + " getCredentials" + password);
		try {
			user = securityService.authenticateUser(login, password);
	        if(user != null){
	        	LOG.info("AuthenticationProvider <- authenticate() -> Logged in successfully for user:" + login);
	            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), null);
	        }else{
	        	LOG.error("AuthenticationProvider <- authenticate() -> Issue with Authentication : Login failed for user:" + login);
	        	//TODO handle Authentication Failures through Spring
	            throw new BadCredentialsException("You shall not pass!");
	        }
		} catch (Exception e1) {
			//TODO handle proper messaging
			e1.printStackTrace();
			 throw new BadCredentialsException("You shall not pass!");
		}
    }

    public boolean supports(Class authentication)
    {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    protected void additionalAuthenticationChecks(UserDetails userdetails, UsernamePasswordAuthenticationToken usernamepasswordauthenticationtoken)
        throws AuthenticationException
    {}

    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException
    {
    	LOG.error("AuthenticationProvider <- retrieveUser() -> user:" + username + " - ATTENTION! This method should not be called !");
        throw new UnsupportedOperationException("This method should not be called");
    }
}
