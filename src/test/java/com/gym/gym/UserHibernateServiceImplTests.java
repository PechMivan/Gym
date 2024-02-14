package com.gym.gym;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.gym.gym.dtos.UserDTO;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.UserRepository;
import com.gym.gym.services.implementations.UserHibernateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.validation.Validator;

public class UserHibernateServiceImplTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private Validator validator;
    @InjectMocks
    private UserHibernateServiceImpl userService;
    User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("testUser")
                .isActive(true)
                .build();
    }

    @Test
    public void testGetAllUsers() {
        // Mock behavior to return a list of users
        List<User> users = Arrays.asList(new User(), new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // Call getAllUsers method
        List<User> result = userService.getAllUsers();

        // Assert that the result is not null and contains the expected number of users
        assertNotNull(result);
        assertEquals(users.size(), result.size());
    }

    @Test
    public void testGetUserById() {
        // Mock behavior to return a user for a given id
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Call getUserById method with valid user id
        User result = userService.getUserById(userId);

        // Assert that the result is not null and has the expected id
        assertNotNull(result);
        assertEquals(userId, result.getId());

        // Call getUserById method with invalid user id and assert NotFoundException is thrown
        assertThrows(NotFoundException.class, () -> userService.getUserById(100L));
    }

    @Test
    public void testGetUserByUsername() {
        // Mock behavior to return a user for a given username
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Call getUserByUsername method with valid username
        User result = userService.getUserByUsername(username);

        // Assert that the result is not null and has the expected username
        assertNotNull(result);
        assertEquals(username, result.getUsername());

        // Call getUserByUsername method with invalid username and assert NotFoundException is thrown
        assertThrows(NotFoundException.class, () -> userService.getUserByUsername("invalidUsername"));
    }

    @Test
    public void testCreateUser() {
        // Prepare test data
        UserDTO userdto = UserDTO.builder()
                            .firstname("John")
                            .lastname("Doe")
                            .build();
        
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(userdto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("testUser", result.getUsername());
        assertEquals(10, result.getPassword().length());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
