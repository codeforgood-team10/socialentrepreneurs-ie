package uk.seicfg.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import uk.seicfg.service.SecurityService;
import uk.seicfg.util.converter.UserConverter;

public class SessionListener implements HttpSessionListener
{

	protected static final Logger LOG = LoggerFactory.getLogger(SessionListener.class);
	
	@Autowired
	private SecurityService securityService;
	
	private UserConverter userConverter = new UserConverter();
	
	@Override
    public void sessionCreated(HttpSessionEvent event)
    {
    	LOG.info("SessionListener <- sessionCreated() -> HttpSessionEvent sessionID=" + event.getSession().getId());
    }

	@Override
    public void sessionDestroyed(HttpSessionEvent event)
    {
        Session session = (Session)event.getSession().getAttribute("ubsession");
        if(session == null)
            LOG.warn("sessionDestroyed - can not logout user as there is no UB session available");
        else
        	securityService.logout(userConverter.convertTo(session.getUser()));
        
        LOG.info("SessionListener <- sessionDestroyed() -> sessionID=" + event.getSession().getId());
    }
}
