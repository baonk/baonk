package com.nv.baonk.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class BaonkLogoutSuccessfulHandler implements LogoutSuccessHandler{
	private final Logger logger = LoggerFactory.getLogger(BaonkLogoutSuccessfulHandler.class);
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String referrer = request.getHeader("Referer");
		String currentURL = request.getRequestURL().toString();
		logger.debug("Referer: " + referrer + " || Current URL: " + currentURL);
		
	   /* if(referrer!=null) {
	        request.getSession().setAttribute("previous_page", referrer);
	    }*/
		response.sendRedirect("login");  		
	}

}
