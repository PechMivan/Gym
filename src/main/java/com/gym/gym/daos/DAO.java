package com.gym.gym.daos;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface DAO<T> {

    Optional<T> findById(long id);

    List<T> findAll();

    void save(T t);

    void delete(long id);
}
