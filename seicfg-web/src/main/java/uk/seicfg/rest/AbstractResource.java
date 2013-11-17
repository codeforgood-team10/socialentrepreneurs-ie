package uk.seicfg.rest;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uk.seicfg.security.Session;
import uk.seicfg.to.User;

@Controller
public class AbstractResource {
	
	protected static final Logger LOG = LoggerFactory.getLogger(AbstractResource.class);
	public Session getSession() {
		LOG.info("AbstractResource <- getSession()");
		Session session = (Session) RequestContextHolder.currentRequestAttributes().getAttribute(Session.SESSIONKEY, RequestAttributes.SCOPE_SESSION);
		if (session == null) {
			/* if session is not in HttpSession just create it here */
			session = initSession();
		}
		return session;
	}

	private static Session initSession() {
		LOG.info("AbstractResource <- initSession()");
		Session session = new Session();
		session.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		//TODO check if we can set the default something for a user
		
		HttpSession httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
		//check if the user can use WebSockets
		Boolean isHtml5 = (Boolean) httpSession.getAttribute("isHtml5");
		session.setIsHtml5(isHtml5);
		//check what is the screen resolution of the client
		if(httpSession.getAttribute("screenWidth") != null){
			Integer width = Integer.parseInt((String) httpSession.getAttribute("screenWidth"));
			session.setScreenWidth(width);
		}
		
		RequestContextHolder.currentRequestAttributes().setAttribute(Session.SESSIONKEY, session, RequestAttributes.SCOPE_SESSION);
		return session;
	}
}
