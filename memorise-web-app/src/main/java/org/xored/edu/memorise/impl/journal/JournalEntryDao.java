package org.xored.edu.memorise.impl.journal;

import org.xored.edu.memorise.api.meme.Meme;
import org.xored.edu.memorise.core.dao.Dao;

import java.io.InputStream;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link Meme}s.
 * 
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
public interface JournalEntryDao extends Dao<Meme, Long> {

 	void uploadFile(Meme meme, InputStream bstream);

 	InputStream readFile(Meme meme) ;

}