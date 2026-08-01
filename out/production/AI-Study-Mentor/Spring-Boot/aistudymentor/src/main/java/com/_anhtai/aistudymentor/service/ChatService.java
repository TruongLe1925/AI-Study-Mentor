package com._anhtai.aistudymentor.service;

import com._anhtai.aistudymentor.dto.reponse.AnswerDTO;
import com._anhtai.aistudymentor.dto.request.AskDTO;
import com._anhtai.aistudymentor.dto.request.QuizDTO;
import com._anhtai.aistudymentor.entity.User;
import com._anhtai.aistudymentor.repositoy.UserDetailsRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ChatService {
    private final ChatClient chatClient;
    private final UserDetailsRepository userDetailsRepository;
    public ChatService(ChatClient.Builder chatClient,UserDetailsRepository userDetailsRepository) {
        this.chatClient = chatClient.build();
        this.userDetailsRepository = userDetailsRepository;
    }
    public AnswerDTO chat(AskDTO askDTO, MultipartFile file) {
        if(askDTO==null){
            throw new RuntimeException("Messege not found");
        }
        String userText = String.format("\"Câu hỏi / Chủ đề cần giải đáp: %s \"" +
                "về môn học: %s\"", askDTO.getQuestion(), askDTO.getSubject());
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

        if (file != null && !file.isEmpty()) {
            // 1. Lấy contentType từ file gửi lên
            String contentType = file.getContentType();

            // 2. Xử lý trường hợp contentType bị null hoặc chứa dấu "*" (ví dụ: "image/*")
            if (contentType == null || contentType.contains("*")) {
                // Gán mặc định về image/jpeg hoặc tự dò lại (fallback)
                contentType = "image/jpeg";
            }

            // 3. Parse MimeType an toàn
            Media media = Media.builder()
                    .mimeType(MimeTypeUtils.parseMimeType(contentType))
                    .data(file.getResource())
                    .build();

            return chatClient.prompt()
                    .user(promptUserSpec -> promptUserSpec.text(userText).media(media))
                    .system(systemMessage)
                    .call()
                    .entity(AnswerDTO.class);
        }
            return chatClient.prompt()
                    .user(promptUserSpec -> promptUserSpec.text(userText))
                    .system(systemMessage)
                    .call()
                    .entity(AnswerDTO.class);
    }
    public QuizDTO quizGen(String subject,String email){
        if(subject == null){
            throw new RuntimeException("Subject not found");
        }
        User user = userDetailsRepository.findByEmail(email).orElse(null);
        String eduLevel = user.getEducationLevel();
        String userText = String.format("""
            Bạn là "AI Study Mentor" - trợ lý học tập AI thông minh, thân thiện dành cho học sinh, sinh viên (từ cấp 2, cấp 3 đến đại học).
            Nhiệm vụ của bạn là tạo ra một đề kiểm tra trắc nghiệm gồm 10 câu hỏi về môn học %s ở lớp %s.
            
            YÊU CẦU ĐỊNH DẠNG VÀ HIỂN THỊ (CỰC KỲ QUAN TRỌNG):
            1. Trả về ký tự tiếng Việt và ký tự đặc biệt ở dạng văn bản UTF-8 chuẩn (UTF-8 Plain Text), TUYỆT ĐỐI NÓI KHÔNG với việc encode unicode dạng escape sequence (như \\u00b0, \\u221a, \\u00eef...). Viết trực tiếp ký tự như: °, √, α, β...
            2. Tất cả công thức toán học, biểu thức, biến số, căn thức, phân số, mũ phải được bọc trong cặp dấu $...$ theo đúng cú pháp LaTeX chuẩn.
               - Ví dụ đúng: "$y = 2x^2 + 1$", "$y = \\sqrt{3}x - 2$", "80^\\circ", "$y = \\frac{3}{x} + 1$"
               - Ví dụ sai: "2x^2 + 1", "\\u221a3x - 2", "80\\u00b0"
            3. Cấu trúc JSON trả về bắt buộc phải tuân theo schema sau:
            {
                "questions": [
                    {
                        "question": "Nội dung câu hỏi 1 (chứa LaTeX nếu có toán/lý/hóa)",
                        "options": ["A. Phương án 1", "B. Phương án 2", "C. Phương án 3", "D. Phương án 4"],
                        "answer": "A"
                        "explain: "giải thích từng phương án, tại sao chọn A, tại sao B/C/D sai, có thể có công thức LaTeX nếu cần"
                    }
                ]
            }
            """, subject, eduLevel);

        String systemMessage = """
            Luôn giữ văn phong khuyến khích, tích cực, mang tính giáo dục cao. 
            Tuyệt đối không trả lời các nội dung không liên quan đến học tập hoặc vi phạm chuẩn mực.
            Đảm bảo output JSON là hợp lệ, chỉ trả về chuỗi JSON thuần (không kèm bất kỳ văn bản giải thích nào khác).
            """;

        return chatClient.prompt()
                .system(systemMessage)
                .user(userText)
                .call()
                .entity(QuizDTO.class);
    }
}
