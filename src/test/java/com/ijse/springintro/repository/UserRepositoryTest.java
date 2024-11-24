package com.ijse.springintro.repository;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ijse.springintro.entity.User;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void saveShouldsave() {

        User user = new User();
        user.setId(1L);
        user.setUsername("kashmira98");
        String pw = "kashmira12";
        user.setPassword(passwordEncoder.encode(pw));
        
        when(userRepository.save(user)).thenReturn(user);
        
        User createdUser = userRepository.save(user);
        Assertions.assertEquals(user, createdUser);
        Assertions.assertTrue(user.getUsername()=="kashmira98");
    }

    @Test
    void findByUsernameShouldFindByUsername() {

        User user = new User();
        user.setId(1L);
        user.setUsername("kashmira98");
        String pw = "kashmira12";
        user.setPassword(passwordEncoder.encode(pw));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(user, foundUser.get());
        Assertions.assertTrue(foundUser.get().getUsername()=="kashmira98");
    }

}
