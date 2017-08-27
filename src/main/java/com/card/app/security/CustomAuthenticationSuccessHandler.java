package com.card.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler  implements AuthenticationSuccessHandler{

	private static final Logger log = Logger.getLogger(CustomAuthenticationSuccessHandler.class);
	
	public CustomAuthenticationSuccessHandler() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication arg2)
			throws IOException, ServletException {
         if(log.isDebugEnabled())
         {
        	 log.info("CustomAuthenticationSuccessHandler nvoked ");
         }
		
		res.sendRedirect(req.getContextPath() +"/home/index");
	}
	
	
}
