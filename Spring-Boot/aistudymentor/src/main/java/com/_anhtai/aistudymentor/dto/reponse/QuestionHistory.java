package com._anhtai.aistudymentor.dto.reponse;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionHistory {
    private String questionText;
    private String subject;
    private String primaryAnswer;
    private String simplifiedExplanation;
    private LocalDateTime askedAt;
}
