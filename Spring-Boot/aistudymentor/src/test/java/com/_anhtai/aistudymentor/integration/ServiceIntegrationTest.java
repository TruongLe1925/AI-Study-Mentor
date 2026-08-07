package com._anhtai.aistudymentor.integration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com._anhtai.aistudymentor.dto.reponse.SubjectDTO;
import com._anhtai.aistudymentor.dto.reponse.UserDTO;
import com._anhtai.aistudymentor.entity.Subject;
import com._anhtai.aistudymentor.entity.User;
import com._anhtai.aistudymentor.repositoy.SubjectRepository;
import com._anhtai.aistudymentor.repositoy.UserDetailsRepository;
import com._anhtai.aistudymentor.service.SubjectService;
import com._anhtai.aistudymentor.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
class ServiceIntegrationTest {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userDetailsRepository.deleteAll();
        subjectRepository.deleteAll();

        Subject math = new Subject();
        math.setSubjectName("Toán");
        subjectRepository.save(math);

        Subject english = new Subject();
        english.setSubjectName("Tiếng Anh");
        subjectRepository.save(english);
    }

    @Test
    void subjectServiceAndUserService_shouldWorkTogetherWithDatabase() {
        UserDTO userDTO = UserDTO.builder()
                .email("integration@example.com")
                .password("123456")
                .educationLevel("Đại học")
                .build();

        userService.save(userDTO);

        List<SubjectDTO> subjects = subjectService.findAll();
        User savedUser = userDetailsRepository.findByEmail("integration@example.com").orElseThrow();

        assertThat(subjects).extracting(SubjectDTO::getName).contains("Toán", "Tiếng Anh");
        assertThat(savedUser.getEmail()).isEqualTo("integration@example.com");
        assertThat(passwordEncoder.matches("123456", savedUser.getPasswordHash())).isTrue();
    }
}
