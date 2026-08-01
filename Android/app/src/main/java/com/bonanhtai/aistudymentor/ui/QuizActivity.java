package com.bonanhtai.aistudymentor.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bonanhtai.aistudymentor.R;
import com.bonanhtai.aistudymentor.model.QuestionDTO;
import com.bonanhtai.aistudymentor.model.QuizDTO;
import com.bonanhtai.aistudymentor.ui.view.MathView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private TextView tvQuestionNumber;
    private MathView mathQuestion;
    private LinearLayout layoutOptions;
    private MaterialButton btnNext, btnPrevious;
    private android.widget.ImageButton btnBackToPractice, btnGoHome;

    private List<QuestionDTO> questions;
    private String[] userAnswers;
    private int currentIndex = 0;
    
    private final List<MaterialCardView> optionCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String quizJson = getIntent().getStringExtra("QUIZ_DATA");
        if (quizJson != null) {
            QuizDTO quiz = new Gson().fromJson(quizJson, QuizDTO.class);
            questions = quiz.getQuestions();
            userAnswers = new String[questions.size()];
        }

        initViews();
        displayQuestion();
    }

    private void initViews() {
        progressIndicator = findViewById(R.id.quizProgress);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        mathQuestion = findViewById(R.id.mathQuestion);
        layoutOptions = findViewById(R.id.layoutOptions);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnBackToPractice = findViewById(R.id.btnBackToPractice);
        btnGoHome = findViewById(R.id.btnGoHome);

        btnNext.setOnClickListener(v -> handleNext());
        btnPrevious.setOnClickListener(v -> handlePrevious());

        btnBackToPractice.setOnClickListener(v -> finish());
        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void displayQuestion() {
        if (questions == null || currentIndex >= questions.size()) return;

        QuestionDTO q = questions.get(currentIndex);
        
        progressIndicator.setProgress((int) (((float) (currentIndex + 1) / questions.size()) * 100));
        tvQuestionNumber.setText("Question " + (currentIndex + 1) + "/" + questions.size());
        
        mathQuestion.setText(q.getQuestion());
        
        setupOptions(q.getOptions());

        btnPrevious.setVisibility(currentIndex == 0 ? View.INVISIBLE : View.VISIBLE);
        btnNext.setText(currentIndex == questions.size() - 1 ? "Finish" : "Next Question");
    }

    private void setupOptions(List<String> options) {
        layoutOptions.removeAllViews();
        optionCards.clear();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < options.size(); i++) {
            String optionText = options.get(i);
            View optionView = inflater.inflate(R.layout.item_quiz_option, layoutOptions, false);
            
            MaterialCardView card = optionView.findViewById(R.id.cardOption);
            MaterialRadioButton rb = optionView.findViewById(R.id.rbIndicator);
            MathView mathOption = optionView.findViewById(R.id.mathOptionText);
            
            mathOption.setText(optionText);
            optionCards.add(card);

            // Restore selection
            if (userAnswers[currentIndex] != null && userAnswers[currentIndex].equals(optionText)) {
                rb.setChecked(true);
                card.setStrokeColor(Color.parseColor("#0256C2")); // Theme color
            } else {
                card.setStrokeColor(Color.parseColor("#E2E8F0")); // Default color
            }

            card.setOnClickListener(v -> {
                // Clear all selections visually
                for (int j = 0; j < layoutOptions.getChildCount(); j++) {
                    View vChild = layoutOptions.getChildAt(j);
                    MaterialCardView c = vChild.findViewById(R.id.cardOption);
                    MaterialRadioButton r = vChild.findViewById(R.id.rbIndicator);
                    c.setStrokeColor(Color.parseColor("#E2E8F0"));
                    r.setChecked(false);
                }
                // Select this one
                rb.setChecked(true);
                userAnswers[currentIndex] = optionText;
                card.setStrokeColor(Color.parseColor("#0256C2"));
            });

            layoutOptions.addView(optionView);
        }
    }

    private void handlePrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            displayQuestion();
        }
    }

    private void handleNext() {
        if (userAnswers[currentIndex] != null) {
            if (currentIndex < questions.size() - 1) {
                currentIndex++;
                displayQuestion();
            } else {
                showResult();
            }
        } else {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        }
    }

    private void showResult() {
        Intent intent = new Intent(this, QuizResultActivity.class);
        intent.putExtra("QUIZ_DATA", new Gson().toJson(new QuizDTO(questions)));
        intent.putExtra("USER_ANSWERS", userAnswers);
        startActivity(intent);
        finish();
    }
}
