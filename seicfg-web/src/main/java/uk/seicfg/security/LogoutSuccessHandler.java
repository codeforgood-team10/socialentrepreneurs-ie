package uk.seicfg.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
{

	protected static final Logger LOG = LoggerFactory.getLogger(LogoutSuccessHandler.class);

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException
    {
    	//TODO Harish
        LOG.info("LogoutSuccessHandler <- onLogoutSuccess() -> for user: TODO Harish");
        if(authentication != null)
            if(request.getQueryString() != null && (request.getQueryString().contains("changePwdSuccess") || request.getQueryString().contains("error=connection_closed") || request.getQueryString().contains("disconnected")))
                setDefaultTargetUrl((new StringBuilder()).append("/#login?").append(request.getQueryString()).toString());
            else
                setDefaultTargetUrl("/");
        super.onLogoutSuccess(request, response, authentication);
        setDefaultTargetUrl("/");
        LOG.info("LogoutSuccessHandler <- onLogoutSuccess() -> Logged out user: TODO Harish Successfully");
    }
}
