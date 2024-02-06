package com.gym.gym.daos;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface DAO<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(long id, T t);

    void delete(long id);
}
