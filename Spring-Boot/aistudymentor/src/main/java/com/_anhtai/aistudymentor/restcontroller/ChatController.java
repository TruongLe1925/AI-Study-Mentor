package com._anhtai.aistudymentor.restcontroller;

import com._anhtai.aistudymentor.dto.reponse.AnswerDTO;
import com._anhtai.aistudymentor.dto.request.AskDTO;
import com._anhtai.aistudymentor.dto.reponse.SubjectDTO;
import com._anhtai.aistudymentor.dto.request.QuizDTO;
import com._anhtai.aistudymentor.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
public class ChatController {
    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    @PostMapping("/ask")
    public AnswerDTO chat(@RequestBody AskDTO questionDTO) {

        return chatService.chat(questionDTO.getQuestion());
    }
    @PostMapping("/quiz")
    public QuizDTO quizGen(@RequestBody SubjectDTO subject) {
        return chatService.quizGen(subject.getName());
    }

}
