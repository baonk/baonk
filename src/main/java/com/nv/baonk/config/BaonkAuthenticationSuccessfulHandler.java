package com.nv.baonk.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.nv.baonk.security.SecurityConfigBaonk;
import com.nv.baonk.service.UserService;
import com.nv.baonk.vo.User;

@Component
public class BaonkAuthenticationSuccessfulHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final Logger logger = LoggerFactory.getLogger(BaonkAuthenticationSuccessfulHandler.class);
	public final Integer SESSION_TIMEOUT_IN_SECONDS = 60 * 60;
	
	@Autowired
	private SecurityConfigBaonk securityConfBaonk;	
	@Autowired
	private UserService userService;

	public BaonkAuthenticationSuccessfulHandler () {
		super();
		setUseReferer(true);
	}
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		logger.debug("--------Running in Authentication Successful Handler!--------");
		
		String serverName 	= request.getServerName();
		String userID 	  	= authentication.getName();
		int tenantId 		= userService.getTenantId(serverName);			
		User authUser 		= userService.findUserByUseridAndTenantid(userID, tenantId);		
		String userPassword = authUser.getPassword();	
		
		logger.debug("Check authUser: " + userID + " || TenantId : " + tenantId);
				
		//Create login cookie
		String userInfo     = serverName + "+" + userID + "+" + userPassword + "+"  + tenantId;
		String loginCookie  = "";
		
		try {
			loginCookie = securityConfBaonk.encryptAES(userInfo);
		} 
		catch (Exception e) {				
			e.printStackTrace();
		}			
		
    	Cookie cookieID = new Cookie("loginCookie", loginCookie);	    	
    	cookieID.setPath("/");	    	
    	response.addCookie(cookieID); 
    	response.setStatus(HttpServletResponse.SC_OK);   
		
		HttpSession session = request.getSession(false);
		session.setMaxInactiveInterval(SESSION_TIMEOUT_IN_SECONDS);		
			
		String previous_page = (String) session.getAttribute("prior_url");	  		
        
        if (previous_page != null && !previous_page.endsWith("/login")) {
        	response.sendRedirect(previous_page);
        }
        else {
        	response.sendRedirect("home");
        }
    	  	  	
    	logger.debug("------------Authentication Successful Handler ended!----------");
	}

}
