package com.bonanhtai.aistudymentor.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.bonanhtai.aistudymentor.model.AnswerDTO;
import com.bonanhtai.aistudymentor.model.AskDTO;
import com.bonanhtai.aistudymentor.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;

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
    }

    private void initViews() {
        etQuestionInput = findViewById(R.id.etQuestionInput);
        btnSend = findViewById(R.id.btnSend);
        cardAnswer = findViewById(R.id.cardAnswer);
        tvAnswerContent = findViewById(R.id.tvAnswerContent);
        tvAdditionalInfo = findViewById(R.id.tvAdditionalInfo);
        dividerAnswer = findViewById(R.id.dividerAnswer);
    }

    private void setupListeners() {
        btnSend.setOnClickListener(v -> handleSendAction());
    }

    private void handleSendAction() {
        String question = etQuestionInput.getText().toString().trim();
        if (!question.isEmpty()) {
            AskDTO askDTO = new AskDTO();
            askDTO.setQuestion(question);
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
            Toast.makeText(this, "Question saved: " + askDTO.getQuestion(), Toast.LENGTH_SHORT).show();
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