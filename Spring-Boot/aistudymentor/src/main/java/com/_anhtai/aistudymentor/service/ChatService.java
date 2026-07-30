package com._anhtai.aistudymentor.service;

import com._anhtai.aistudymentor.dto.reponse.AnswerDTO;
import com._anhtai.aistudymentor.dto.request.QuizDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class ChatService {
    private final ChatClient chatClient;
    public ChatService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }
    public AnswerDTO chat(String message) {
        if(message==null){
            throw new RuntimeException("Messege not found");
        }
        String userText = String.format("\"Câu hỏi / Chủ đề cần giải đáp: %s\"", message);
        String systemMessage = """
            Bạn là "AI Study Mentor" - trợ lý học tập AI thông minh, thân thiện dành cho học sinh, sinh viên (từ cấp 2, cấp 3 đến đại học).
            
            Nhiệm vụ của bạn:
            1. Phân tích câu hỏi của học sinh để xác định môn học và độ khó.
            2. Cung cấp lời giải thích chính xác, rõ ràng, chi tiết từng bước (step-by-step) bằng ngôn ngữ tiếng Việt dễ hiểu, phù hợp với trình độ học sinh.
            3. Trình bày rõ ràng các bước logic, công thức và lý thuyết liên quan đối với các bài tập tính toán / giải đề.
            4. Chỉ ra các lỗi sai thường gặp, các phương pháp giải thay thế và tóm tắt kiến thức trọng tâm.
            5. Gợi ý thêm các câu hỏi luyện tập / bài tập tương tự để học sinh củng cố kiến thức.
            6. Kiến thức không liên quan thì cứ trả lời là không hỗ trợ và không đưa ra các thông tin không liên quan.
            Luôn giữ văn phong khuyến khích, tích cực, mang tính giáo dục cao. Tuyệt đối không trả lời các nội dung không liên quan đến học tập hoặc vi phạm chuẩn mực.
            """;
            return chatClient.prompt()
                    .user(promptUserSpec -> promptUserSpec.text(userText))
                    .system(systemMessage)
                    .call()
                    .entity(AnswerDTO.class);
    }
    public QuizDTO quizGen(String subject){
        if(subject == null){
            throw new RuntimeException("Subject not found");
        }
        String userText = String.format("""
            Bạn là "AI Study Mentor" - trợ lý học tập AI thông minh, thân thiện dành cho học sinh, sinh viên (từ cấp 2, cấp 3 đến đại học).
            Nhiệm vụ của bạn là tạo ra một đề kiểm tra trắc nghiệm gồm 10 câu hỏi về môn học %s.
            Mỗi câu hỏi có 4 phương án trả lời (A, B, C, D) và chỉ có một phương án đúng.
            Trả lời dưới dạng JSON với cấu trúc:
            {
                "questions": [
                    {
                        "question": "Câu hỏi 1",
                        "options": ["A", "B", "C", "D"],
                        "answer": "A"
                    },
                    ...
                ]
            }
            """, subject);
        String systemMessage = """
        Luôn giữ văn phong khuyến khích, tích cực, mang tính giáo dục cao. 
        Tuyệt đối không trả lời các nội dung không liên quan đến học tập hoặc vi phạm chuẩn mực.
        """;

        return chatClient.prompt()
                .system(systemMessage)
                .user(userText)
                .call()
                .entity(QuizDTO.class);
    }
}
