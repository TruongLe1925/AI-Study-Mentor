package com.bonanhtai.aistudymentor.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bonanhtai.aistudymentor.R;
import com.bonanhtai.aistudymentor.model.QuestionDTO;
import com.bonanhtai.aistudymentor.model.QuizDTO;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.List;

import com.bonanhtai.aistudymentor.ui.view.MathView;

public class QuizResultActivity extends AppCompatActivity {

    private TextView tvScore;
    private LinearLayout layoutResultList;
    private MaterialButton btnHome, btnRetry;

    private QuizDTO quiz;
    private String[] userAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        String quizJson = getIntent().getStringExtra("QUIZ_DATA");
        userAnswers = getIntent().getStringArrayExtra("USER_ANSWERS");

        if (quizJson != null) {
            quiz = new Gson().fromJson(quizJson, QuizDTO.class);
        }

        initViews();
        displayResults();
    }

    private void initViews() {
        tvScore = findViewById(R.id.tvScore);
        layoutResultList = findViewById(R.id.layoutResultList);
        btnHome = findViewById(R.id.btnHome);
        btnRetry = findViewById(R.id.btnRetry);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        btnRetry.setOnClickListener(v -> {
            startActivity(new Intent(this, PracticeActivity.class));
            finish();
        });
    }

    private void displayResults() {
        if (quiz == null || userAnswers == null) return;

        List<QuestionDTO> questions = quiz.getQuestions();
        int score = 0;
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO q = questions.get(i);
            String uAns = userAnswers[i];
            
            View itemView = inflater.inflate(R.layout.item_quiz_result, layoutResultList, false);
            MathView mathTitle = itemView.findViewById(R.id.mathQuestionTitle);
            TextView tvUAns = itemView.findViewById(R.id.tvUserAnswer);
            TextView tvCAns = itemView.findViewById(R.id.tvCorrectAnswer);
            MathView mathExpl = itemView.findViewById(R.id.mathExplanation);

            mathTitle.setText((i + 1) + ". " + q.getQuestion());
            tvUAns.setText("Your Answer: " + (uAns != null ? uAns : "Not answered"));
            tvCAns.setText("Correct Answer: " + q.getAnswer());
            mathExpl.setText(q.getExplain());

            if (q.getAnswer().equals(uAns)) {
                score++;
                tvUAns.setTextColor(Color.parseColor("#10B981")); // Green
            } else {
                tvUAns.setTextColor(Color.parseColor("#EF4444")); // Red
            }

            layoutResultList.addView(itemView);
        }

        tvScore.setText("Score: " + score + "/" + questions.size());
    }
}
