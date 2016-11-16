package org.xored.edu.memorise.impl;

import org.xored.edu.memorise.impl.journal.JournalEntryDao;
import org.xored.edu.memorise.impl.user.UserDao;
import org.xored.edu.memorise.api.journal.Memo;
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

	private JournalEntryDao journalEntryDao;

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;


	protected DataBaseInitializer() {
		/* Default constructor for reflection instantiation */
	}

	public DataBaseInitializer(
			UserDao userDao,
			JournalEntryDao journalEntryDao,
			PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.journalEntryDao = journalEntryDao;
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
		for (int i = 0; i < 10; i++) {
			Memo memo = new Memo();
			memo.setTitle("This is Memo title " + i);
			memo.setDescription("This is Memo detailed info " + i);
			memo.setDate(new Date(timestamp));
			memo.setStatus(0);
			this.journalEntryDao.save(memo);
			timestamp += 1000 * 60 * 60;
		}

		for (int i = 0; i < 10; i++) {
			Memo memo = new Memo();
			memo.setTitle("This is Memo title " + i + 10);
			memo.setDescription("This is Memo detailed info " + i + 10);
			memo.setDate(new Date(timestamp));
			memo.setStatus(1);
			this.journalEntryDao.save(memo);
			timestamp += 1000 * 60 * 60;
		}
	}

}