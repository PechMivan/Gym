package com.gym.gym;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.gym.gym.entities.User;
import com.gym.gym.exceptions.InvalidPasswordException;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.exceptions.UnauthorizedAccessException;
import com.gym.gym.repositories.UserRepository;
import com.gym.gym.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceImplTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    User user;
    User newUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        this.user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .username("John.Doe")
                .password("passtest")
                .build();

        this.newUser = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .username("John.Doe")
                .password("passtest")
                .isActive(true)
                .build();
    }

    @Test
    public void getAllUsers_withUsers_succesful() {
        // Arrange
        List<User> users = Arrays.asList(new User(), new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(users.size(), result.size());
    }

    @Test
    public void getAllUsers_withoutUsers_returnsEmptyList() {
        // Arrange
        List<User> users = Collections.emptyList();
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getUser_validId_successful() {
        // Arrange
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    public void getUser_invalidId_ThrowsNotFoundException() {
        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.getUserById(100L));
    }

    @Test
    public void getUser_validUsername_successful() {
        // Arrange
        String username = "John.Doe";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void getUser_invalidUsername_throwsNotFoundException() {
        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.getUserByUsername("invalidUsername"));
    }

    @Test
    public void createUser(){
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("John.Doe", result.getUsername());
        assertEquals(10, result.getPassword().length());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void updateUser(){
        // Arrange
        String username = "John.Doe";

        User updateUser = User.builder()
                            .id(1L)
                            .firstname("newName")
                            .lastname("newLastname")
                            .username(username)
                            .password("passtest")
                            .isActive(false)
                            .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        // Act
        User result = userService.updateUser(username, updateUser);
        // Assert
        assertEquals(1L, result.getId());
        assertEquals("newName", result.getFirstname());
        assertEquals("newLastname", result.getLastname());
        assertEquals("John.Doe", result.getUsername());
        assertEquals("passtest", result.getPassword());        // Password shouldn't be updated here.
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void saveUser(){
        // Act
        userService.saveUser(user);
        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void createPassword(){
        // Act
        String result = userService.createPassword();
        // Assert
        assertEquals(10, result.length());
    }


    @Test
    public void createUsername(){
        // Arrange
        User userTest1 = User.builder().id(1L).username("test.user").build();
        User userTest2 = User.builder().id(2L).username("test.user1").build();
        List<User> users = new ArrayList<>();
        users.add(userTest1);
        users.add(userTest2);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        String username = userService.createUsername("test", "user");

        // Assert
        assertNotNull(username);
        assertEquals("test.user2", username);
    }

    @Test
    public void authenticateUser_validData_succesful(){
        // Arrange
        when(userRepository.findByUsername("John.Doe")).thenReturn(Optional.of(user));

        // Act and Assert
        assertDoesNotThrow(()-> userService.authenticateUser("John.Doe", "passtest"));
    }

    @Test
    public void authenticateUser_invalidUsername_throwsNotFoundException(){
        // Arrange
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(NotFoundException.class,
            () -> userService.authenticateUser("wrongUser", "passtest"));
    }

    @Test
    public void authenticateUser_invalidPassword_throwsUnauthorizedAccessException(){
        // Arrange
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(UnauthorizedAccessException.class,
            () -> userService.authenticateUser("John", "Wrongpass"));
    }

    @Test
    public void changePassword_validData_successful(){
        // Arrange
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        String newpass = "newpass10"; // Greather than 8 and lower than 10 to pass validation.
        // Act
        boolean passwordChangedSuccessfully = userService.changePassword("John", "passtest", newpass);
        // Assert
        assertTrue(passwordChangedSuccessfully);
    }

    @Test
    public void changePassword_invalidUser_throwsNotFoundException(){
        // Arrange
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        // Act and Assert
        assertThrows(NotFoundException.class,
            ()-> userService.changePassword("wrongUser", "passtest", "validPass"));
    }

    @Test
    public void changePassword_wrongPass_throwsUnauthorizedAccessException(){
        // Assert
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(UnauthorizedAccessException.class,
            ()-> userService.changePassword("John", "wrongPass", "validPass"));
    }

    @Test
    public void changePassword_invalidPass_throwsInvalidPasswordException(){
        // Assert
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        String invalidPass = "123";

        // Act and Assert
        assertThrows(UnauthorizedAccessException.class,
                ()-> userService.changePassword("John", "wrongPass", invalidPass));
    }

    @Test
    public void validatePassword_validPassword_successful(){
        // Arrange
        String betweenEightAndTen = "newpass10";
        // Act and Assert
        assertDoesNotThrow(() -> userService.validatePassword(betweenEightAndTen));
    }

    @Test
    public void validatePassword_nullPassword_throwsInvalidPasswordException(){
        // Act and Assert
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(null));
    }

    @Test
    public void validatePassword_emptyPassword_throwsInvalidPasswordException(){
        // Act and Assert
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(""));
    }

    @Test
    public void validatePassword_outOfBoundsPassword_throwsInvalidPasswordException(){
        // Arrange
        String lessThanEight = "1234567"; // < 8 characters
        // Act and Assert
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(lessThanEight));
    }

}
