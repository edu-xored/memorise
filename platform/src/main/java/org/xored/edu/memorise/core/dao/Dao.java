package org.xored.edu.memorise.core.dao;

import java.util.List;

import org.xored.edu.memorise.core.entity.Entity;

/**
 * Generic CRUDL DAO interface
 *
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
public interface Dao<T extends Entity, I> {

	List<T> findAll();

	T find(I id);

	T save(T entry);

	void delete(I id);

}