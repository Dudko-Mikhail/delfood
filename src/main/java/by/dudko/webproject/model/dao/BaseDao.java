package by.dudko.webproject.model.dao;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.entity.RootEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, E extends RootEntity<K>> { // todo ? extends K
    List<E> findAll() throws DaoException;

    Optional<E> findById(K id) throws DaoException;

    boolean deleteById(K id) throws DaoException;

    boolean create(E e) throws DaoException;
}
