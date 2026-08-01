package com._anhtai.aistudymentor.restcontroller;

import com._anhtai.aistudymentor.dto.reponse.AnswerDTO;
import com._anhtai.aistudymentor.dto.request.AskDTO;
import com._anhtai.aistudymentor.dto.request.QuizDTO;
import com._anhtai.aistudymentor.dto.request.QuizRequestDTO;
import com._anhtai.aistudymentor.service.ChatService;
import com._anhtai.aistudymentor.utils.JWTUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/question")
public class ChatController {
    private final ChatService chatService;
    private final JWTUtils utils;
    public ChatController(ChatService chatService,JWTUtils utils) {
        this.chatService = chatService;
        this.utils = utils;
    }
    @PostMapping(value = "/ask", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AnswerDTO chat(
            @RequestPart("question") AskDTO questionDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return chatService.chat(questionDTO, file);
    }
    @PostMapping("/quiz")
    public QuizDTO quizGen(@RequestBody QuizRequestDTO quizRequestDTO) {
        String email = utils.extractUsername(quizRequestDTO.getToken().getAccessToken());
        return chatService.quizGen(quizRequestDTO.getSubject(),email);
    }

}
