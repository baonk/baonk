package com.nv.baonk.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class BaonkLogoutSuccessfulHandler implements LogoutSuccessHandler{
	//private final Logger logger = LoggerFactory.getLogger(BaonkLogoutSuccessfulHandler.class);
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		request.getSession().setAttribute("previous_page", "");
		response.sendRedirect("login");  		
	}

}
