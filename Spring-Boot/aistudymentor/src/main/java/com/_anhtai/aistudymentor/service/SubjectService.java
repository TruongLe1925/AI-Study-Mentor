package com._anhtai.aistudymentor.service;

import com._anhtai.aistudymentor.dto.reponse.SubjectDTO;
import com._anhtai.aistudymentor.entity.Subject;
import com._anhtai.aistudymentor.repositoy.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    public List<SubjectDTO> findAll() {
        List<Subject> subject = subjectRepository.findAll();
        return subject.stream().map(s -> SubjectDTO.builder()
                .name(s.getSubjectName())
                .build()).toList();
    }
}
