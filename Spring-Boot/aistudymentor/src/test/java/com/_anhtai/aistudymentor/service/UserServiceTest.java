package com._anhtai.aistudymentor.service;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com._anhtai.aistudymentor.dto.reponse.UserDTO;
import com._anhtai.aistudymentor.entity.User;
import com._anhtai.aistudymentor.exception.EmailIsPresentException;
import com._anhtai.aistudymentor.repositoy.UserDetailsRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void save_shouldThrowWhenEmailExists() {
        UserDTO input = UserDTO.builder()
                .email("student@example.com")
                .password("123456")
                .educationLevel("Đại học")
                .build();

        when(userDetailsRepository.findByEmail(input.getEmail())).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> userService.save(input))
                .isInstanceOf(EmailIsPresentException.class)
                .hasMessage("User already exists");
    }

    @Test
    void save_shouldEncodePasswordAndPersistUser() {
        UserDTO input = UserDTO.builder()
                .email("newstudent@example.com")
                .password("123456")
                .educationLevel("THPT")
                .build();

        when(userDetailsRepository.findByEmail(input.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("encoded-password");

        userService.save(input);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDetailsRepository).save(captor.capture());

        User savedUser = captor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo(input.getEmail());
        assertThat(savedUser.getPasswordHash()).isEqualTo("encoded-password");
        assertThat(savedUser.getEducationLevel()).isEqualTo("THPT");
        assertThat(savedUser.getRole()).isEqualTo("STUDENT");
        assertThat(savedUser.getTotalXp()).isEqualTo(0);
    }
}
