package com.card.app.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.card.app.dao.UserDao;
import com.card.app.model.Permission;
import com.card.app.model.User;
import com.card.app.model.UserAttempts;
import com.card.app.service.UserService;


public class CustomAuthenticationManager implements AuthenticationManager{
	
	private Md5PasswordEncoder encoder;
	private SessionFactory sessionFactory;
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	
	  public CustomAuthenticationManager() {
	        encoder = new Md5PasswordEncoder();
	    }
 
	  @Transactional	
public Authentication authenticate(Authentication auth) throws AuthenticationException {
	// TODO Auto-generated method stub
	 try {

	 String username = auth.getName();
     String password = (String) auth.getCredentials();
   
     //User user = null;
     
    // Session session=sessionFactory.getCurrentSession();
    // session.beginTransaction();
     User user=userService.findByName(username);//.findByName(username);
     if(!user.getPasscode().equals(password))
     {
    	 throw new BadCredentialsException("Wrong Password");
     }
     
     Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
     for(Permission permission : user.getRole().getPermissions()) {
         authorities.add(new GrantedAuthorityImpl( permission.getName()));
     }
	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password,authorities);
   // token.setDetails();
	userDao.resetFailAttempts(username);

    
    return token;
	 }catch (BadCredentialsException e) {

			//invalid login, update to user_attempts
		 userDao.updateFailAttempts(auth.getName());
			throw e;

		  } catch (LockedException e){

			//this user is locked!
			String error = "";
			UserAttempts userAttempts =
					userDao.getUserAttempts(auth.getName());

	               if(userAttempts!=null){
				Date lastAttempts = userAttempts.getLastModified();
				error = "User account is locked! <br><br>Username : "
	                           + auth.getName() + "<br>Last Attempts : " + lastAttempts;
			}else{
				error = e.getMessage();
			}

		  throw new LockedException(error);
		}
}
}
