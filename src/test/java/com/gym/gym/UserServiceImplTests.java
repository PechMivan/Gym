package com.gym.gym;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.gym.gym.dtos.UserDTO;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.InvalidPasswordException;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.exceptions.UnauthorizedAccessException;
import com.gym.gym.repositories.UserRepository;
import com.gym.gym.services.implementations.UserServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceImplTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    Validator validator;

    @InjectMocks
    private UserServiceImpl userService;
    User user;
    UserDTO userdto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("John.Doe")
                .password("passtest")
                .isActive(true)
                .build();

        this.userdto = UserDTO.builder()
                .firstname("John")
                .lastname("Doe")
                .build();
    }

    @Test
    public void testGetAllUsers() {
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
    public void testGetUserById() {
        // Arrange
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertThrows(NotFoundException.class, () -> userService.getUserById(100L));
    }

    @Test
    public void testGetUserByUsername() {
        // Arrange
        String username = "John.Doe";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertThrows(NotFoundException.class, () -> userService.getUserByUsername("invalidUsername"));
    }

    @Test
    public void testCreateUser(){
        // Arrange
        when(validator.validate(userdto)).thenReturn(Collections.emptySet());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.createUser(userdto);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("John.Doe", result.getUsername());
        assertEquals(10, result.getPassword().length());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser(){
        // Arrange
        UserDTO userdto = UserDTO.builder()
                .firstname("newName")
                .lastname("newLastname")
                .username("John.Doe")
                .password("passtest")
                .build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        // Act
        User result = userService.updateUser(userdto);
        // Assert
        assertEquals(1L, result.getId());
        assertEquals("newName", result.getFirstName());
        assertEquals("newLastname", result.getLastName());
        // Username and password are used as credentials and shouldn't be updated here.
        assertEquals("John.Doe", result.getUsername());
        assertEquals("passtest", result.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSaveUser(){
        // Act
        userService.saveUser(user);
        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testCreatePassword(){
        // Act
        String result = userService.createPassword();
        // Assert
        assertEquals(10, result.length());
    }


    @Test
    public void testCreateUsername(){
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
    public void testAuthenticateUser_With_Valid_Data(){
        // Arrange
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));

        // Act and Assert
        assertDoesNotThrow(()-> userService.authenticateUser("John", "passtest"));
    }

    @Test
    public void testAuthenticateUser_With_Invalid_Data(){
        // Arrange
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.authenticateUser("wrongUser", "passtest"));
        assertThrows(UnauthorizedAccessException.class, () -> userService.authenticateUser("John", "Wrongpass"));
    }

    @Test
    public void testChangePassword(){
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        String newpass = "newpass10"; // Greather than 8 and lower than 10 to pass validation.
        // Act
        boolean passwordChangedSuccessfully = userService.changePassword("John", "passtest", newpass);
        // Assert
        assertTrue(passwordChangedSuccessfully);
    }

    @Test
    public void testValidatePassword_With_Valid_Data(){
        // Arrange
        String betweenEightAndThen = "newpass10";
        // Act and Assert
        assertDoesNotThrow(() -> userService.validatePassword(betweenEightAndThen));
    }

    @Test
    public void testValidatePassword_With_Invalid_Data(){
        // Arrange
        String lessThanEight = "1234567"; // < 8 characters
        String moreThanTen = "12345678910"; // > 10 characters
        // Act and Assert
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(null));
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(""));
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(lessThanEight));
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(moreThanTen));
    }

    @Test
    public void testToggleActive_With_True_Status(){
        // Arrange
        user.setActive(true); // Making sure isActive is true.
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        // Act
        boolean activeStatus = userService.toggleActive(1L);
        // Assert
        assertFalse(activeStatus);
    }

    @Test
    public void testToggleActive_With_False_Status(){
        // Arrange
        user.setActive(false); // Making sure isActive is false.
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        // Act
        boolean activeStatus = userService.toggleActive(1L);
        // Assert
        assertTrue(activeStatus);
    }
}
