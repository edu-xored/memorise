package org.xored.edu.memorise.impl.user;

import org.xored.edu.memorise.core.dao.Dao;
import org.xored.edu.memorise.api.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService {

	User findByName(String name);

}