package com.taskmanager.service;

import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taskmanager.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.repository.TaskRepository;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(User user){
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email " + user.getEmail() + " уже зарегистрирован");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUser(Long userId, User user){
        User updateUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Пользователь с ID " + userId + " не найден"));

        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        return userRepository.save(updateUser);
    }

    public void deleteUser(Long userId){
        userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Пользователь с ID " + userId + " не найден"));
        userRepository.deleteById(userId);
    }

    public Optional<User> findByEmail(String email){
        userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email " + email + " не найден"));
        return userRepository.findByEmail(email);
    }

    public List<Task> getUserTasks(Long userId){
        User user = getUserById(userId);
        return taskRepository.findByAssignee(user);
    }
}
