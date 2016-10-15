package com.eduxored.memorise.impl.user;

import com.daniilefremov.core.dao.Dao;
import com.eduxored.memorise.api.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService {

	User findByName(String name);

}