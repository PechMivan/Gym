package com.gym.gym.repositories;

import com.gym.gym.entities.User;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findByUsername(String username);
}
