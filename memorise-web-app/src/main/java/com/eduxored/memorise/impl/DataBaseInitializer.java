package com.eduxored.memorise.impl;

import com.eduxored.memorise.impl.journal.JournalEntryDao;
import com.eduxored.memorise.impl.user.UserDao;
import com.eduxored.memorise.api.journal.Journal;
import com.eduxored.memorise.api.user.Role;
import com.eduxored.memorise.api.user.User;
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
			Journal journal = new Journal();
			journal.setTitle("This is Memo title " + i);
			journal.setDescription("This is Memo detailed info " + i);
			journal.setDate(new Date(timestamp));
			this.journalEntryDao.save(journal);
			timestamp += 1000 * 60 * 60;
		}
	}

}