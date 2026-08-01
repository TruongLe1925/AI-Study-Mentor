package com._anhtai.aistudymentor.restcontroller;

import com._anhtai.aistudymentor.dto.reponse.AnswerDTO;
import com._anhtai.aistudymentor.dto.reponse.QuestionHistory;
import com._anhtai.aistudymentor.dto.request.AskDTO;
import com._anhtai.aistudymentor.dto.request.QuestionDTO;
import com._anhtai.aistudymentor.dto.request.QuizDTO;
import com._anhtai.aistudymentor.dto.request.QuizRequestDTO;
import com._anhtai.aistudymentor.entity.Question;
import com._anhtai.aistudymentor.service.ChatService;
import com._anhtai.aistudymentor.utils.JWTUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.security.Principal;
import java.util.List;

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
            @RequestPart(value = "file", required = false) MultipartFile file,
            Principal principal
    ) {
        return chatService.chat(questionDTO, file,principal.getName());
    }
    @PostMapping("/quiz")
    public QuizDTO quizGen(@RequestBody QuizRequestDTO quizRequestDTO) {
        String email = utils.extractUsername(quizRequestDTO.getToken().getAccessToken());
        return chatService.quizGen(quizRequestDTO.getSubject(),email);
    }
    @GetMapping("/questions")
    public List<QuestionHistory> getQuestions(Principal principal) {
        return chatService.getQuestions(principal.getName());
    }
}
