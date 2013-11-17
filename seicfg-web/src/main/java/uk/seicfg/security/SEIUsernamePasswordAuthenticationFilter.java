package uk.seicfg.security;

import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SEIUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{

	protected static final Logger LOG = LoggerFactory.getLogger(SEIUsernamePasswordAuthenticationFilter.class);
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException
    {
		//TODO Harish
		LOG.info("UBUsernamePasswordAuthenticationFilter <- attemptAuthentication() for user: TODO Harish");
        String html5 = request.getParameter("websocketSupport");
        String screenWidth = request.getParameter("screenWidth");
        if(request.getSession() != null && html5 != null && screenWidth != null)
        {
            request.getSession().setAttribute("isHtml5", Boolean.valueOf(html5.compareTo("1") == 0));
            request.getSession().setAttribute("screenWidth", screenWidth);
        }
        return super.attemptAuthentication(request, response);
    }
}
