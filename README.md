# AI Study Mentor

An intelligent learning app powered by AI, designed to help users study more effectively with Q&A chat, practice exercises, and gamification.

## 📋 Project Overview

AI Study Mentor is a complete learning solution consisting of:
- **Backend API**: Spring Boot REST API with AI integration (OpenAI)
- **Mobile App**: Android application for end users

## 🏗️ System Architecture

```
AI-Study-Mentor/
├── Spring-Boot/          # Backend API
│   ├── aistudymentor/    # Spring Boot application
│   └── SQLScript.txt     # Database schema
├── Android/              # Mobile application
└── figma/                # UI/UX designs
```

## 🚀 Technologies Used

### Backend (Spring Boot)
- **Java 17**
- **Spring Boot 4.1.0**
- **Spring AI 2.0.0** - OpenAI GPT integration
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - ORM
- **MySQL** - Database
- **JWT (jjwt)** - Token-based authentication
- **Lombok** - Boilerplate reduction
- **SpringDoc OpenAPI** - API documentation

### Mobile (Android)
- Android SDK
- Gradle build system

## 📁 Database Structure

The system uses MySQL with the main tables:

### Core Tables
- `tbl_Users` - User information
- `tbl_Subjects` - Subject catalogue
- `tbl_GamificationBadges` - Gamification badges
- `tbl_Avatars` - User avatars
- `tbl_Themes` - UI themes

### Functional Tables
- `tbl_UserSubjects` - Subjects enrolled by users
- `tbl_UserBadges` - Badges earned by users
- `tbl_Friendships` - Friend relationships
- `tbl_Notifications` - System notifications
- `tbl_Questions` - User questions
- `tbl_AIAnswers` - AI answers
- `tbl_PracticeQuizzes` - Practice quizzes
- `tbl_UserProgress` - Learning progress records

## 🔧 Setup and Run

### System Requirements
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Android Studio (for mobile app)

### Backend Setup

1. **Clone the repository**
```bash
git clone https://github.com/TruongLe1925/AI-Study-Mentor.git
cd AI-Study-Mentor/Spring-Boot/aistudymentor
```

2. **Configure the database**
```bash
# Create the database from the script
mysql -u root -p < SQLScript.txt
```

3. **Update application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ai_study_mentor
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.ai.openai.api-key=your_openai_api_key
spring.ai.openai.chat.options.model=gpt-4
```

4. **Run the application**
```bash
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080`

API documentation: `http://localhost:8080/swagger-ui.html`

### Mobile App Setup

1. **Open the project in Android Studio**
```bash
cd AI-Study-Mentor/Android
```

2. **Import the Android project**
- File → Open → Select the `Android` folder

3. **Configure the API endpoint**
- Update the Android code to point the app to the backend base URL

4. **Build and run**
- Select a device/emulator
- Click Run

## 🎯 Main Features

### 🤖 AI Chat Assistant
- Ask questions about subjects
- Receive detailed explanations and simplifications
- Get alternative solution suggestions
- Summarize key concepts
- Detect common mistakes

### 📝 Practice Quizzes
- Generate practice quizzes automatically
- Evaluate answers
- Track accuracy

### 🏆 Gamification
- XP and level system
- Unlock avatars by level
- Earn gamification badges
- Customize themes

### 👥 Social Features
- Connect with other users
- Friend leaderboard
- Share progress

### 📊 Analytics Dashboard
- Track number of questions asked
- Monitor quiz accuracy
- Review study time
- Track learning progress

## 🔐 Authentication

The system uses JWT (JSON Web Token) for authentication:
- Register a new account
- Login with email/password
- Use JWT for protected API requests

## 📝 API Endpoints

### Authentication
- `POST /api/auth/register` - Register
- `POST /api/auth/login` - Login

### Chat AI
- `POST /api/chat/ask` - Ask AI a question
- `POST /api/chat/quiz` - Create a quiz

### Subjects
- `GET /api/subjects` - Get subject list
- `POST /api/subjects/{id}/enroll` - Enroll in a subject

### User Progress
- `GET /api/progress` - Get learning progress

See the full API documentation at `/swagger-ui.html`

## 🤝 Contribution

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push your branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**TruongLe1925**

## 📞 Contact

If you have questions or feedback, please open an issue on the repository.

---

Built with ❤️ using Spring Boot & Android
