package com.eduxored.memorise.impl.journal;

import com.eduxored.core.dao.Dao;
import com.eduxored.memorise.api.journal.Journal;

import java.io.InputStream;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link Journal}s.
 * 
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
public interface JournalEntryDao extends Dao<Journal, Long> {

 	void uploadFile(Journal journal, InputStream bstream);

 	InputStream readFile(Journal journal) ;

}