package com.gym.gym.repositories;

import com.gym.gym.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Long, User> {
}
