package com._anhtai.aistudymentor.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AskDTO {
    private String question;
}
