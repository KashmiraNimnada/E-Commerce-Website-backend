package com.ijse.springintro.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ijse.springintro.entity.User;
import com.ijse.springintro.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Test
    void createUserShouldCreateUser() {

        User user = new User();
        user.setId(1L);
        user.setUsername("kashmira98");
        user.setPassword("kashmira12");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("Encoded password");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        
        User createdUser = userServiceImpl.createUser(user);

        Assertions.assertTrue(createdUser.getPassword()=="Encoded password");
        Assertions.assertTrue(createdUser.getUsername()==user.getUsername());
    }
    
}
