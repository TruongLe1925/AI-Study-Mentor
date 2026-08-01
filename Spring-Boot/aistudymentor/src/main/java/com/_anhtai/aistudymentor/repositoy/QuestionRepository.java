package com._anhtai.aistudymentor.repositoy;

import com._anhtai.aistudymentor.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByUserEmail(String email);
}
