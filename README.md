# AI Study Mentor

Ứng dụng học tập thông minh được hỗ trợ bởi AI, giúp người dùng học tập hiệu quả hơn với các tính năng chat hỏi đáp, ôn luyện, và gamification.

## 📋 Tổng quan dự án

AI Study Mentor là một hệ thống học tập toàn diện bao gồm:
- **Backend API**: Spring Boot REST API với tích hợp AI (OpenAI)
- **Mobile App**: Ứng dụng Android cho người dùng cuối

## 🏗️ Kiến trúc hệ thống

```
AI-Study-Mentor/
├── Spring-Boot/          # Backend API
│   ├── aistudymentor/    # Spring Boot application
│   └── SQLScript.txt     # Database schema
├── Android/              # Mobile application
└── figma/                # UI/UX designs
```

## 🚀 Công nghệ sử dụng

### Backend (Spring Boot)
- **Java 17**
- **Spring Boot 4.1.0**
- **Spring AI 2.0.0** - Tích hợp OpenAI GPT
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - ORM
- **MySQL** - Database
- **JWT (jjwt)** - Token authentication
- **Lombok** - Reduce boilerplate code
- **SpringDoc OpenAPI** - API documentation

### Mobile (Android)
- Android SDK
- Gradle build system

## 📁 Cấu trúc Database

Hệ thống sử dụng MySQL với các bảng chính:

### Bảng cơ bản
- `tbl_Users` - Thông tin người dùng
- `tbl_Subjects` - Danh mục môn học
- `tbl_GamificationBadges` - Huy hiệu gamification
- `tbl_Avatars` - Avatar người dùng
- `tbl_Themes` - Giao diện theme

### Bảng chức năng
- `tbl_UserSubjects` - Môn học người dùng đăng ký
- `tbl_UserBadges` - Huy hiệu người dùng đạt được
- `tbl_Friendships` - Quan hệ bạn bè
- `tbl_Notifications` - Thông báo hệ thống
- `tbl_Questions` - Câu hỏi người dùng
- `tbl_AIAnswers` - Câu trả lời từ AI
- `tbl_PracticeQuizzes` - Bài tập ôn luyện
- `tbl_UserProgress` - Thống kê tiến độ học tập

## 🔧 Cài đặt và chạy

### Yêu cầu hệ thống
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Android Studio (cho mobile app)

### Cài đặt Backend

1. **Clone repository**
```bash
git clone https://github.com/TruongLe1925/AI-Study-Mentor.git
cd AI-Study-Mentor/Spring-Boot/aistudymentor
```

2. **Cấu hình Database**
```bash
# Tạo database từ script
mysql -u root -p < SQLScript.txt
```

3. **Cấu hình application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ai_study_mentor
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.ai.openai.api-key=your_openai_api_key
spring.ai.openai.chat.options.model=gpt-4
```

4. **Chạy ứng dụng**
```bash
mvn spring-boot:run
```

Backend sẽ chạy tại `http://localhost:8080`

API Documentation: `http://localhost:8080/swagger-ui.html`

### Cài đặt Mobile App

1. **Mở project với Android Studio**
```bash
cd AI-Study-Mentor/Android
```

2. **Mở file trong Android Studio**
- File → Open → Chọn thư mục Android

3. **Cấu hình API endpoint**
- Update API base URL trong code Android để trỏ đến backend

4. **Build và chạy**
- Chọn device/emulator
- Click Run button

## 🎯 Tính năng chính

### 🤖 AI Chat Assistant
- Hỏi đáp về các môn học
- Giải thích chi tiết và đơn giản hóa
- Gợi ý phương pháp giải pháp thay thế
- Tóm tắt các khái niệm chính
- Cảnh báo lỗi thường gặp

### 📝 Practice Quizzes
- Tạo bài tập ôn luyện tự động
- Đánh giá câu trả lời
- Theo dõi độ chính xác

### 🏆 Gamification
- Hệ thống XP và Level
- Unlock avatar theo level
- Thu th huy hiệu (badges)
- Theme tùy chỉnh

### 👥 Social Features
- Kết bạn với người dùng khác
- Leaderboard bạn bè
- Chia sẻ tiến độ

### 📊 Analytics Dashboard
- Thống kê số câu hỏi đã hỏi
- Độ chính xác quiz
- Thời gian ôn tập
- Theo dõi tiến độ học tập

## 🔐 Authentication

Hệ thống sử dụng JWT (JSON Web Token) cho authentication:
- Đăng ký tài khoản mới
- Đăng nhập với email/password
- JWT token cho các API request

## 📝 API Endpoints

### Authentication
- `POST /api/auth/register` - Đăng ký
- `POST /api/auth/login` - Đăng nhập

### Chat AI
- `POST /api/chat/ask` - Hỏi câu hỏi cho AI
- `POST /api/chat/quiz` - Tạo quiz

### Subjects
- `GET /api/subjects` - Lấy danh sách môn học
- `POST /api/subjects/{id}/enroll` - Đăng ký môn học

### User Progress
- `GET /api/progress` - Lấy tiến độ học tập

Xem đầy đủ API documentation tại `/swagger-ui.html`

## 🤝 Đóng góp

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**TruongLe1925**

## 📞 Liên hệ

Nếu có câu hỏi hoặc góp ý, vui lòng tạo issue trên repository.

---

Built with ❤️ using Spring Boot & Android
