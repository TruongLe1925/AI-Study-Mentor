package com._anhtai.aistudymentor.restcontroller;

import com._anhtai.aistudymentor.dto.reponse.SubjectDTO;
import com._anhtai.aistudymentor.service.SubjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @GetMapping("/allsubjects")
    public List<SubjectDTO> findAll() {
        return subjectService.findAll();
    }
}
