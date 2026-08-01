package com.bonanhtai.aistudymentor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bonanhtai.aistudymentor.R;
import com.bonanhtai.aistudymentor.model.QuestionDTO;
import com.bonanhtai.aistudymentor.model.QuizDTO;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.gson.Gson;

import java.util.List;

import com.bonanhtai.aistudymentor.ui.view.MathView;

public class QuizActivity extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private TextView tvQuestionNumber;
    private MathView mathQuestion;
    private RadioGroup rgOptions;
    private MaterialRadioButton rbA, rbB, rbC, rbD;
    private MaterialButton btnNext, btnPrevious;
    private android.widget.ImageButton btnBackToPractice, btnGoHome;

    private List<QuestionDTO> questions;
    private String[] userAnswers;
    private int currentIndex = 0;

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
        rgOptions = findViewById(R.id.rgOptions);
        rbA = findViewById(R.id.rbOptionA);
        rbB = findViewById(R.id.rbOptionB);
        rbC = findViewById(R.id.rbOptionC);
        rbD = findViewById(R.id.rbOptionD);
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
        
        // Wrap with MathView requirements (e.g. if string doesn't contain $ or \( \))
        String questionText = q.getQuestion();
        mathQuestion.setText(questionText);
        
        List<String> opts = q.getOptions();
        rbA.setText(opts.size() > 0 ? opts.get(0) : "");
        rbB.setText(opts.size() > 1 ? opts.get(1) : "");
        rbC.setText(opts.size() > 2 ? opts.get(2) : "");
        rbD.setText(opts.size() > 3 ? opts.get(3) : "");

        // Restore previous answer if any
        rgOptions.clearCheck();
        String savedAnswer = userAnswers[currentIndex];
        if (savedAnswer != null) {
            if (savedAnswer.equals(rbA.getText().toString())) rbA.setChecked(true);
            else if (savedAnswer.equals(rbB.getText().toString())) rbB.setChecked(true);
            else if (savedAnswer.equals(rbC.getText().toString())) rbC.setChecked(true);
            else if (savedAnswer.equals(rbD.getText().toString())) rbD.setChecked(true);
        }

        btnPrevious.setVisibility(currentIndex == 0 ? View.INVISIBLE : View.VISIBLE);
        btnNext.setText(currentIndex == questions.size() - 1 ? "Finish" : "Next Question");
    }

    private void handlePrevious() {
        saveCurrentAnswer();
        if (currentIndex > 0) {
            currentIndex--;
            displayQuestion();
        }
    }

    private void handleNext() {
        if (saveCurrentAnswer()) {
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

    private boolean saveCurrentAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId != -1) {
            MaterialRadioButton selectedRb = findViewById(selectedId);
            userAnswers[currentIndex] = selectedRb.getText().toString();
            return true;
        }
        return false;
    }

    private void showResult() {
        Intent intent = new Intent(this, QuizResultActivity.class);
        intent.putExtra("QUIZ_DATA", new Gson().toJson(new QuizDTO(questions)));
        intent.putExtra("USER_ANSWERS", userAnswers);
        startActivity(intent);
        finish();
    }
}
