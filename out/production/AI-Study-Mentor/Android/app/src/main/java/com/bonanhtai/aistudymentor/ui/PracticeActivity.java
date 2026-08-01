package com.bonanhtai.aistudymentor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bonanhtai.aistudymentor.R;
import com.bonanhtai.aistudymentor.model.SubjectDTO;
import com.bonanhtai.aistudymentor.viewmodel.QuizViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class PracticeActivity extends AppCompatActivity {

    private QuizViewModel viewModel;
    private ChipGroup chipGroupSubjects;
    private MaterialButton btnStartQuiz;
    private View layoutLoading;
    private String selectedSubject = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupViewModel();
        setupListeners();
        
        viewModel.loadSubjects();
    }

    private void initViews() {
        chipGroupSubjects = findViewById(R.id.chipGroupPracticeSubjects);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);
        layoutLoading = findViewById(R.id.layoutLoading);
        
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        viewModel.getSubjects().observe(this, this::populateSubjects);

        viewModel.isLoading().observe(this, loading -> {
            layoutLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
            btnStartQuiz.setEnabled(!loading);
        });

        viewModel.getError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });

        viewModel.getQuiz().observe(this, quiz -> {
            if (quiz != null && quiz.getQuestions() != null && !quiz.getQuestions().isEmpty()) {
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("QUIZ_DATA", new com.google.gson.Gson().toJson(quiz));
                startActivity(intent);
            }
        });
    }

    private void setupListeners() {
        btnStartQuiz.setOnClickListener(v -> {
            if (selectedSubject.isEmpty()) {
                Toast.makeText(this, "Please select a subject first", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.generateQuiz(selectedSubject);
            }
        });
    }

    private void populateSubjects(List<SubjectDTO> subjects) {
        chipGroupSubjects.removeAllViews();
        for (SubjectDTO subject : subjects) {
            Chip chip = new Chip(this);
            chip.setText(subject.getName());
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedSubject = subject.getName();
                }
            });
            chipGroupSubjects.addView(chip);
        }
    }
}
