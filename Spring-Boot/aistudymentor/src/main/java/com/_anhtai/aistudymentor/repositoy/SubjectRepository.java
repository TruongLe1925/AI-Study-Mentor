package com._anhtai.aistudymentor.repositoy;

import com._anhtai.aistudymentor.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    List<Subject> findAll();
    Subject findBySubjectName(String subjectName);
}
