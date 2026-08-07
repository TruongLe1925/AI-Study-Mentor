package com._anhtai.aistudymentor.service;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;

import com._anhtai.aistudymentor.dao.UserDAO;
import com._anhtai.aistudymentor.dto.reponse.QuestionHistory;
import com._anhtai.aistudymentor.entity.AIAnswer;
import com._anhtai.aistudymentor.entity.Question;
import com._anhtai.aistudymentor.entity.Subject;
import com._anhtai.aistudymentor.repositoy.QuestionRepository;
import com._anhtai.aistudymentor.repositoy.SubjectRepository;
import com._anhtai.aistudymentor.repositoy.UserDetailsRepository;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserDAO userDAO;

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private ChatClient chatClient;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    private ChatService chatService;

    @BeforeEach
    void setUp() {
        when(chatClientBuilder.build()).thenReturn(chatClient);
        chatService = new ChatService(subjectRepository, questionRepository, userDAO, chatClientBuilder, userDetailsRepository);
    }

    @Test
    void chat_shouldThrowWhenAskDtoIsNull() {
        assertThatThrownBy(() -> chatService.chat(null, null, "student@example.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Messege not found");
    }

    @Test
    void quizGen_shouldThrowWhenSubjectIsNull() {
        assertThatThrownBy(() -> chatService.quizGen(null, "student@example.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Subject not found");
    }

    @Test
    void getQuestions_shouldMapQuestionEntitiesToHistory() {
        Subject subject = new Subject();
        subject.setSubjectName("Toán");

        Question question = new Question();
        question.setQuestionText("Câu hỏi về đạo hàm");
        question.setAskedAt(LocalDateTime.of(2024, 1, 2, 3, 4));
        question.setSubject(subject);

        AIAnswer aiAnswer = new AIAnswer();
        aiAnswer.setPrimaryAnswer("Đạo hàm là...");
        aiAnswer.setSimplifiedExplanation("Giải thích ngắn gọn");
        question.setAiAnswer(aiAnswer);

        when(questionRepository.findAllByUserEmail("student@example.com")).thenReturn(List.of(question));

        List<QuestionHistory> result = chatService.getQuestions("student@example.com");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getQuestionText()).isEqualTo("Câu hỏi về đạo hàm");
        assertThat(result.get(0).getSubject()).isEqualTo("Toán");
        assertThat(result.get(0).getPrimaryAnswer()).isEqualTo("Đạo hàm là...");
        assertThat(result.get(0).getSimplifiedExplanation()).isEqualTo("Giải thích ngắn gọn");
    }
}
