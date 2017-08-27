package com.card.app.dao.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;

import com.card.app.controller.HomeController;
import com.card.app.dao.UserDao;
import com.card.app.model.User;
import com.card.app.model.UserAttempts;

@Repository

public class UserDaoImpl extends AbstractGenericDao<User> implements UserDao{

	private static final Logger log = Logger.getLogger(UserDaoImpl.class);
	//@Autowired
	//private SessionFactory sessionFactory;
	private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE USER SET USER_LOCKED = :P1 WHERE USER_NAME = :P2";
	private static final String SQL_USERS_COUNT = "SELECT count(*) FROM USER WHERE USER_NAME = :P1";

	private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM USER_ATTEMPTS WHERE USER_NAME = :P1";
	private static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO USER_ATTEMPTS (USER_NAME, LOGIN_ATTEMPTS, LST_UPDT_DTM) VALUES(:P1,:P2,:P3)";
	private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE USER_ATTEMPTS SET LOGIN_ATTEMPTS = LOGIN_ATTEMPTS + 1, LST_UPDT_DTM = :P1 WHERE USER_NAME = :P2";
	private static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE USER_ATTEMPTS SET LOGIN_ATTEMPTS = 0, LST_UPDT_DTM = null WHERE USER_NAME = :P1";
	private static final int MAX_ATTEMPTS = 3;
	
	
	
	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		User user=(User) getSession().createCriteria(User.class).add(Restrictions.ilike("userName", name)).list().get(0);
		return user;
	}


	
	@Override
	@Transactional
	public void updateFailAttempts(String username) {
		
		UserAttempts user = getUserAttempts(username);
		if (user == null) {
			if (isUserExists(username)) {
				// if no record, insert a new
			//	user=new UserAttempts();
			//	user.setAttempts(1);
			//	user.setUsername(username);
			//	user.setLastModified(new Date());
			//	getSession().save(user);
				int insert=	getSession().createSQLQuery(SQL_USER_ATTEMPTS_INSERT).setParameter("P1", username)
				.setParameter("P2", 1).setParameter("P3",  new Date() ).executeUpdate();
				flush();
				log.info("isUserExists insert "+insert);
			}
		  }
		else
		{
			if (isUserExists(username)) {
				// update attempts count, +1
			int update=	getSession().createSQLQuery(SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS).setParameter("P1", new Date())
				.setParameter("P2", username).executeUpdate();
			flush();
			
			log.info("isUserExists update "+update);
			}

			if (user.getAttempts() + 1 >= MAX_ATTEMPTS) {
				// locked user
				getSession().createSQLQuery(SQL_USERS_UPDATE_LOCKED).setParameter("P1", true)
				.setParameter("P2", username).executeUpdate();
				flush();
				// throw exception
				throw new LockedException("User Account is locked!");
			}
		}
	}


	@Override
	@Transactional
	public void resetFailAttempts(String username) {
		// TODO Auto-generated method stub
		getSession().createSQLQuery(SQL_USER_ATTEMPTS_RESET_ATTEMPTS).setParameter("P1", username).executeUpdate();
	}


	@Override
	public UserAttempts getUserAttempts(String username) {
		// TODO Auto-generated method stub
		
		int list= getSession().createSQLQuery(SQL_USER_ATTEMPTS_GET).addEntity(UserAttempts.class)
				.setParameter("P1", username)
				.list().size();
		if(list>0)
		{
		return 	(UserAttempts)getSession().createSQLQuery(SQL_USER_ATTEMPTS_GET).addEntity(UserAttempts.class)
			.setParameter("P1", username)
			.list().get(0);
		}
		else
		{
			return null;
		}
		
		
	}
	
	private boolean isUserExists(String username) {
	
		int count= ((Number) getSession().createSQLQuery(SQL_USERS_COUNT).setParameter("P1", username).uniqueResult()).intValue();
	//	int count=  getSession().createCriteria(UserAttempts.class).setProjection(Projections.rowCount()).list().size();
		log.info("isUserExists count "+count);
		
		if(count==0)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
}
