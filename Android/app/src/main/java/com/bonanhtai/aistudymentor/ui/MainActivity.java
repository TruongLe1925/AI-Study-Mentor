package com.bonanhtai.aistudymentor.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bonanhtai.aistudymentor.R;
import com.bonanhtai.aistudymentor.api.ApiCallback;
import com.bonanhtai.aistudymentor.api.QuestionAPI;
import com.bonanhtai.aistudymentor.api.SubjectAPI;
import com.bonanhtai.aistudymentor.model.AnswerDTO;
import com.bonanhtai.aistudymentor.model.AskDTO;
import com.bonanhtai.aistudymentor.model.SubjectDTO;
import com.bonanhtai.aistudymentor.retrofit.RetrofitService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText etQuestionInput;
    private MaterialButton btnSend;
    private MaterialCardView cardAnswer;
    private TextView tvAnswerContent;
    private TextView tvAdditionalInfo;
    private View dividerAnswer;
    private ChipGroup chipGroupSubjects;
    private FrameLayout avatarContainer;
    private TextView tvViewAll;
    private BottomNavigationView bottomNavigation;
    private RetrofitService retrofitService = new RetrofitService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components and listeners
        initViews();
        setupListeners();
        loadSubjects();
    }

    private void initViews() {
        etQuestionInput = findViewById(R.id.etQuestionInput);
        btnSend = findViewById(R.id.btnSend);
        cardAnswer = findViewById(R.id.cardAnswer);
        tvAnswerContent = findViewById(R.id.tvAnswerContent);
        tvAdditionalInfo = findViewById(R.id.tvAdditionalInfo);
        dividerAnswer = findViewById(R.id.dividerAnswer);
        chipGroupSubjects = findViewById(R.id.chipGroupSubjects);
        avatarContainer = findViewById(R.id.avatarContainer);
        tvViewAll = findViewById(R.id.tvViewAll);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupListeners() {
        btnSend.setOnClickListener(v -> handleSendAction());

        avatarContainer.setOnClickListener(v -> {
            if (isLoggedIn()) {
                // Navigate to Profile
                Toast.makeText(this, "Opening Profile...", Toast.LENGTH_SHORT).show();
            } else {
                redirectToLogin();
            }
        });

        tvViewAll.setOnClickListener(v -> {
            if (isLoggedIn()) {
                // Navigate to Recent Questions
                Toast.makeText(this, "Opening Recent Questions...", Toast.LENGTH_SHORT).show();
            } else {
                redirectToLogin();
            }
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_profile) {
                if (isLoggedIn()) {
                    // Handle Navigation to Profile Fragment/Activity
                    return true;
                } else {
                    redirectToLogin();
                    return false;
                }
            }
            // Add other navigation logic as needed
            return true;
        });
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("jwt_token", null);
        return token != null;
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void handleSendAction() {
        String question = etQuestionInput.getText().toString().trim();
        if (!question.isEmpty()) {
            AskDTO askDTO = new AskDTO();
            askDTO.setQuestion(question);

            // Get selected subject from ChipGroup
            int checkedChipId = chipGroupSubjects.getCheckedChipId();
            if (checkedChipId != View.NO_ID) {
                Chip selectedChip = findViewById(checkedChipId);
                if (selectedChip != null) {
                    askDTO.setSubject(selectedChip.getText().toString());
                }
            }

            callApi(askDTO, new ApiCallback<AnswerDTO>() {
                @Override
                public void onSuccess(AnswerDTO result) {
                    displayAnswer(result);
                }

                @Override
                public void onError(Throwable t) {
                    Log.e("API_ERROR", "Error while calling API", t);
                    Toast.makeText(MainActivity.this, "Failed to get answer: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            // For verification: Show a toast and clear input
            Toast.makeText(this, "Question sent for subject: " + askDTO.getSubject(), Toast.LENGTH_SHORT).show();
            etQuestionInput.setText("");
        } else {
            Toast.makeText(this, "Please enter a question", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayAnswer(AnswerDTO result) {
        if (result == null) return;

        tvAnswerContent.setText(result.getMainAnswer());

        if (result.getAdditionalInfo() != null && !result.getAdditionalInfo().isEmpty()) {
            tvAdditionalInfo.setText(result.getAdditionalInfo());
            tvAdditionalInfo.setVisibility(View.VISIBLE);
            dividerAnswer.setVisibility(View.VISIBLE);
        } else {
            tvAdditionalInfo.setVisibility(View.GONE);
            dividerAnswer.setVisibility(View.GONE);
        }

        cardAnswer.setVisibility(View.VISIBLE);
    }

    private void loadSubjects() {
        SubjectAPI subjectAPI = retrofitService.getRetrofit().create(SubjectAPI.class);
        subjectAPI.GetAllEmployee().enqueue(new Callback<List<SubjectDTO>>() {
            @Override
            public void onResponse(Call<List<SubjectDTO>> call, Response<List<SubjectDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addSubjectChips(response.body());
                } else {
                    Log.e("API_ERROR", "Failed to load subjects. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SubjectDTO>> call, Throwable t) {
                Log.e("API_ERROR", "Error loading subjects", t);
            }
        });
    }

    private void addSubjectChips(List<SubjectDTO> subjects) {
        for (SubjectDTO subject : subjects) {
            Chip chip = new Chip(this);
            chip.setText(subject.getName());
            chip.setCheckable(true);
            chip.setClickable(true);
            chipGroupSubjects.addView(chip);
        }
    }

    private void callApi(AskDTO askDTO, ApiCallback<AnswerDTO> callback) {
        QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);
        questionAPI.askQuestion(askDTO)
                .enqueue(new Callback<AnswerDTO>() {
                    @Override
                    public void onResponse(Call<AnswerDTO> call, Response<AnswerDTO> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            AnswerDTO answerDTO = response.body();
                            callback.onSuccess(answerDTO);
                        } else {
                            Log.e("API_ERROR", "Code: " + response.code());
                            try {
                                if (response.errorBody() != null) {
                                    Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AnswerDTO> call, Throwable throwable) {
                        callback.onError(throwable);
                    }
                });
    }
}