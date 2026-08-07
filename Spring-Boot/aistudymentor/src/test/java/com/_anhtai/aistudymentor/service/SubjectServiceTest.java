package com._anhtai.aistudymentor.service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com._anhtai.aistudymentor.dto.reponse.SubjectDTO;
import com._anhtai.aistudymentor.entity.Subject;
import com._anhtai.aistudymentor.repositoy.SubjectRepository;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void findAll_shouldMapEntitiesToDtos() {
        Subject math = new Subject();
        math.setSubjectId(1);
        math.setSubjectName("Toán");

        Subject physics = new Subject();
        physics.setSubjectId(2);
        physics.setSubjectName("Vật lý");

        when(subjectRepository.findAll()).thenReturn(List.of(math, physics));

        List<SubjectDTO> result = subjectService.findAll();

        assertThat(result)
                .extracting(SubjectDTO::getName)
                .containsExactly("Toán", "Vật lý");
    }
}
