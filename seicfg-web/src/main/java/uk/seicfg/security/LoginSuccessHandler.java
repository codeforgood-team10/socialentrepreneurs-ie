package uk.seicfg.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	protected static final Logger LOG = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    	    Authentication authentication) throws IOException, ServletException {

    	//TODO Harish
    	LOG.info("LoginSuccessHandler <- onAuthenticationSuccess() -> for user: TODO Harish");
        setDefaultTargetUrl("/static/user.html");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
