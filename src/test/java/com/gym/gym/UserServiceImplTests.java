package com.gym.gym;

import com.gym.gym.entities.Token;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.InvalidPasswordException;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.UserRepository;
import com.gym.gym.services.TokenService;
import com.gym.gym.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserServiceImpl userService;
    User user;
    User newUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .username("John.Doe")
                .password("validPass10Chars")
                .build();

        this.newUser = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .username("John.Doe")
                .password("validPass10Chars")
                .isActive(true)
                .build();
    }

    @Test
    void getAllUsers_withUsers_successful() {
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
    void getAllUsers_withoutUsers_returnsEmptyList() {
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
    void getUser_validId_successful() {
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
    void getUser_invalidId_ThrowsNotFoundException() {
        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.getUserById(100L));
    }

    @Test
    void getUser_validUsername_successful() {
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
    void getUser_invalidUsername_throwsNotFoundException() {
        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.getUserByUsername("invalidUsername"));
    }

    @Test
    void createUser(){
        // Arrange
        String accessToken = "jwtToken";

        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(tokenService.generateToken(newUser.getId(), newUser.getUsername(), List.of("USER")))
                .thenReturn(accessToken);
        when(tokenService.createToken(newUser, accessToken)).thenReturn(new Token());

        // Act
        User result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getPassword());
        assertEquals(user.getFirstname(), result.getFirstname());
        assertEquals(user.getLastname(), result.getLastname());
        assertEquals(user.getUsername().toLowerCase(), result.getUsername());
        assertEquals("hashedPassword", result.getHashedPassword());
        assertEquals(10, result.getPassword().length());
        assertFalse(result.getTokens().isEmpty());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser(){
        // Arrange
        String username = "John.Doe";

        User updateUser = User.builder()
                            .id(1L)
                            .firstname("newName")
                            .lastname("newLastname")
                            .username(username)
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
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void saveUser(){
        // Act
        userService.saveUser(user);
        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createPassword(){
        // Act
        String result = userService.createPassword();
        // Assert
        assertEquals(10, result.length());
    }


    @Test
    void createUsername(){
        // Arrange
        User userTest1 = User.builder().id(1L).firstname("test").lastname("user").username("test.user").build();
        User userTest2 = User.builder().id(2L).firstname("test").lastname("user").username("test.user1").build();
        List<User> users = new ArrayList<>();
        users.add(userTest1);
        users.add(userTest2);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        String username = userService.createUsername("Test", "User"); // Notice capital letters

        // Assert
        assertNotNull(username);
        assertEquals("test.user2", username);
    }

    @Test
    void changePassword_validData_successful(){
        // Arrange
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        String newpass = "validPass10Chars"; // Greater or equal to 10 to pass validation.
        // Act
        boolean passwordChangedSuccessfully = userService.changePassword("John", "validPass10Chars", newpass);
        // Assert
        assertTrue(passwordChangedSuccessfully);
    }

    @Test
    void changePassword_invalidUser_throwsNotFoundException(){
        // Arrange
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        // Act and Assert
        assertThrows(NotFoundException.class,
            ()-> userService.changePassword("wrongUser", "moreThanTenCharacters", "validPass10Chars"));
    }

    @Test
    void changePassword_invalidPass_throwsInvalidPasswordException(){
        // Assert
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        String invalidPass = "123";

        // Act and Assert
        assertThrows(InvalidPasswordException.class,
                ()-> userService.changePassword("John", "validPass10Chars", invalidPass));
    }

    @Test
    void validatePassword_validPassword_successful(){
        // Arrange
        String greaterOrEqualThan10 = "validPass10Chars";
        // Act and Assert
        assertDoesNotThrow(() -> userService.validatePassword(greaterOrEqualThan10));
    }

    @Test
    void validatePassword_nullPassword_throwsInvalidPasswordException(){
        // Act and Assert
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(null));
    }

    @Test
    void validatePassword_emptyPassword_throwsInvalidPasswordException(){
        // Act and Assert
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(""));
    }

    @Test
    void validatePassword_outOfBoundsPassword_throwsInvalidPasswordException(){
        // Arrange
        String lessThanEight = "1234567"; // < 8 characters
        // Act and Assert
        assertThrows(InvalidPasswordException.class, () -> userService.validatePassword(lessThanEight));
    }

}
