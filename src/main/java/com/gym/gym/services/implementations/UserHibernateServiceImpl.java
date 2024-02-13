package com.gym.gym.services.implementations;

import com.gym.gym.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHibernateServiceImpl {

    @Autowired
    UserRepository userRepository;


}
