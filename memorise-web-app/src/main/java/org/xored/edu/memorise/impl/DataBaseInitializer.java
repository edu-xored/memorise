package org.xored.edu.memorise.impl;

import org.xored.edu.memorise.api.memo.MemoStatus;
import org.xored.edu.memorise.impl.memo.MemoEntryDao;
import org.xored.edu.memorise.impl.user.UserDao;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.user.Role;
import org.xored.edu.memorise.api.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

/**
 * Initialize the database with some test entries.
 *
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
public class DataBaseInitializer {

	private MemoEntryDao memoEntryDao;

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;


	protected DataBaseInitializer() {
		/* Default constructor for reflection instantiation */
	}

	public DataBaseInitializer(
			UserDao userDao,
			MemoEntryDao memoEntryDao,
			PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.memoEntryDao = memoEntryDao;
		this.passwordEncoder = passwordEncoder;
	}

	//todo fill in with demo data
	public void initDataBase() {
		User userUser = new User("user", this.passwordEncoder.encode("user"));
		userUser.addRole(Role.USER);
		this.userDao.save(userUser);

		User publisher = new User("publisher", this.passwordEncoder.encode("publisher"));
		publisher.addRole(Role.USER);
		publisher.addRole(Role.PUBLISHER);
		this.userDao.save(publisher);

		long timestamp = System.currentTimeMillis() - (1000 * 60 * 60 * 24);
		for (int i = 0; i < 5; i++) {
			Memo memo = new Memo();
			memo.setTitle("This is Memo title " + i);
			memo.setDescription("This is Memo detailed info " + i);
			memo.setDate(new Date(timestamp));
			memo.setStatus(MemoStatus.ACTUAL);
			memo.setCounter(10L);
			this.memoEntryDao.save(memo);
			timestamp += 1000 * 60 * 60;
		}

		for (int i = 0; i < 5; i++) {
			Memo memo = new Memo();
			memo.setTitle("This is Memo title " + (i + 5));
			memo.setDescription("This is Memo detailed info " + (i + 5));
			memo.setDate(new Date(timestamp));
			memo.setStatus(MemoStatus.ARCHIVED);
			memo.setCounter(1L);
			this.memoEntryDao.save(memo);
			timestamp += 1000 * 60 * 60;
		}

		for (int i = 0; i < 5; i++) {
			Memo memo = new Memo();
			memo.setTitle("This is Memo title " + (i + 10));
			memo.setDescription("This is Memo detailed info " + (i + 10));
			memo.setDate(new Date(timestamp));
			memo.setStatus(MemoStatus.CANDIDATE);
			memo.setCounter(5L);
			this.memoEntryDao.save(memo);
			timestamp += 1000 * 60 * 60;
		}
	}

}