package com._anhtai.aistudymentor.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityErrorResponse {
    private String message;
    private int status;
    private long timestamp;
}
