package com.taskmanager.service;

import com.taskmanager.exception.DuplicateEmailException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.*;
import com.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Получить всех пользователей - должно вернуть список")
    void getAllUsers_ShouldReturnsList(){
        List<User> userList = Arrays.asList(
                new User("Иван", "ivan@mail.com", "123", UserRole.USER),
                new User("Мария", "maria@mail.com", "456", UserRole.USER)
        );
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Иван", result.get(0).getName());
        assertEquals("Мария", result.get(1).getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получить пользователя по ID - пользователь существует")
    void getUserById_UserExists_ShouldReturnUser(){
        User user = new User("Иван", "ivan@mail.com", "123", UserRole.USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Иван", result.getName());
        assertEquals("ivan@mail.com", result.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Получить пользователя по ID - пользователь не найден")
    void getUserById_UserNotFound_ShouldThrowException(){
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(999L));
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Создать пользователя - email не занят")
    void createUser_EmailNotTaken_ShouldCreateUser(){
        User user = new User("Пётр", "petr@mail.com", "789", UserRole.USER);
        when(userRepository.existsByEmail("petr@mail.com")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);
        assertNotNull(result);
        assertEquals("Пётр", result.getName());

        verify(userRepository, times(1)).existsByEmail("petr@mail.com");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Создать пользователя - email уже существует")
    void createUser_EmailExists_ShouldThrowException(){
        User newUser = new User("Иван", "ivan@mail.com", "123", UserRole.USER);
        when(userRepository.existsByEmail("ivan@mail.com")).thenReturn(true);
        assertThrows(DuplicateEmailException.class, () -> userService.createUser(newUser));

        verify(userRepository, times(1)).existsByEmail("ivan@mail.com");
        verify(userRepository, times(1)).save(any());
    }
}
