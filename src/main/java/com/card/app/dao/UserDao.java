package com.card.app.dao;

import com.card.app.model.User;
import com.card.app.model.UserAttempts;

public interface UserDao extends GenericDao<User> {

	User findByName( String name );
	void updateFailAttempts(String username);
	void resetFailAttempts(String username);
	UserAttempts getUserAttempts(String username);
}
