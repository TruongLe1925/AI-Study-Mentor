package com.bonanhtai.aistudymentor.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bonanhtai.aistudymentor.R;
import com.bonanhtai.aistudymentor.api.SubjectAPI;
import com.bonanhtai.aistudymentor.model.AnswerDTO;
import com.bonanhtai.aistudymentor.model.AskDTO;
import com.bonanhtai.aistudymentor.model.SubjectDTO;
import com.bonanhtai.aistudymentor.retrofit.RetrofitService;
import com.bonanhtai.aistudymentor.viewmodel.ChatViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etQuestionInput;
    private MaterialButton btnSend;
    private ImageButton btnUploadImage;
    private MaterialCardView cardAnswer;
    private TextView tvAnswerContent;
    private TextView tvAdditionalInfo;
    private View dividerAnswer;
    private ChipGroup chipGroupSubjects;
    private FrameLayout avatarContainer;
    private TextView tvViewAll;
    private BottomNavigationView bottomNavigation;
    private FrameLayout layoutImagePreview;
    private com.google.android.material.imageview.ShapeableImageView ivSelectedImagePreview;
    private ImageButton btnRemoveImage;
    private RetrofitService retrofitService;
    private ChatViewModel chatViewModel;

    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        retrofitService = new RetrofitService(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Register Image Picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        handleImageSelection(uri);
                    }
                }
        );

        // Initialize UI components and listeners
        initViews();
        setupViewModel();
        setupListeners();
        loadSubjects();
    }

    private void setupViewModel() {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        // Observe AI Answer
        chatViewModel.getAnswer().observe(this, this::displayAnswer);

        // Observe Loading State
        chatViewModel.isLoading().observe(this, isLoading -> {
            btnSend.setEnabled(!isLoading);
            if (isLoading) {
                Toast.makeText(this, "AI is thinking...", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe Errors
        chatViewModel.getError().observe(this, error -> {
            Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
        });

        // Observe Selected Image
        chatViewModel.getSelectedImage().observe(this, file -> {
            if (file != null) {
                ivSelectedImagePreview.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                layoutImagePreview.setVisibility(View.VISIBLE);
            } else {
                layoutImagePreview.setVisibility(View.GONE);
            }
        });
    }

    private void initViews() {
        etQuestionInput = findViewById(R.id.etQuestionInput);
        btnSend = findViewById(R.id.btnSend);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        cardAnswer = findViewById(R.id.cardAnswer);
        tvAnswerContent = findViewById(R.id.tvAnswerContent);
        tvAdditionalInfo = findViewById(R.id.tvAdditionalInfo);
        dividerAnswer = findViewById(R.id.dividerAnswer);
        chipGroupSubjects = findViewById(R.id.chipGroupSubjects);
        avatarContainer = findViewById(R.id.avatarContainer);
        tvViewAll = findViewById(R.id.tvViewAll);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        layoutImagePreview = findViewById(R.id.layoutImagePreview);
        ivSelectedImagePreview = findViewById(R.id.ivSelectedImagePreview);
        btnRemoveImage = findViewById(R.id.btnRemoveImage);
    }

    private void setupListeners() {
        btnSend.setOnClickListener(v -> handleSendAction());

        btnUploadImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        btnRemoveImage.setOnClickListener(v -> chatViewModel.setSelectedImage(null));

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

            // Home is the only public screen
            if (itemId == R.id.nav_home) return true;

            // Check auth for all other screens (Practice, Leaderboard, Profile)
            if (!isLoggedIn()) {
                redirectToLogin();
                return false;
            }

            // Handle navigation for authenticated users
            if (itemId == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
            } else if (itemId == R.id.nav_practice) {
                startActivity(new Intent(this, PracticeActivity.class));
            } else if (itemId == R.id.nav_leaderboard) {
                Toast.makeText(this, "Opening Leaderboard...", Toast.LENGTH_SHORT).show();
            }
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

    private void handleImageSelection(Uri uri) {
        try {
            File file = getFileFromUri(uri);
            chatViewModel.setSelectedImage(file);
        } catch (IOException e) {
            Log.e("IMAGE_PICKER", "Error processing selected image", e);
            Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
        }
    }

    private File getFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File file = new File(getCacheDir(), "upload_image.jpg");
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return file;
    }

    private void handleSendAction() {
        String question = etQuestionInput.getText().toString().trim();
        if (!question.isEmpty()) {
            AskDTO askDTO = new AskDTO();
            askDTO.setQuestion(question);

            int checkedChipId = chipGroupSubjects.getCheckedChipId();
            if (checkedChipId != View.NO_ID) {
                Chip selectedChip = findViewById(checkedChipId);
                if (selectedChip != null) {
                    askDTO.setSubject(selectedChip.getText().toString());
                }
            }

            // Use ViewModel to send question with image
            chatViewModel.sendQuestion(askDTO, chatViewModel.getSelectedImage().getValue());

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
}
