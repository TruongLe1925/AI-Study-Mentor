package com._anhtai.aistudymentor.dto.request;

import com._anhtai.aistudymentor.dto.reponse.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuizRequestDTO {
    private String subject;
    private Token token;
}
