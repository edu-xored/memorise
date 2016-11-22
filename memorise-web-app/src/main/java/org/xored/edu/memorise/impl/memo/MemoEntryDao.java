package org.xored.edu.memorise.impl.memo;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.core.dao.Dao;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link Memo}s.
 * 
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
public interface MemoEntryDao extends Dao<Memo, Long> {

}