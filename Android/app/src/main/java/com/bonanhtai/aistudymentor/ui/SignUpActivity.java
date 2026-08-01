package com.bonanhtai.aistudymentor.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bonanhtai.aistudymentor.R;
import com.bonanhtai.aistudymentor.model.UserDTO;
import com.bonanhtai.aistudymentor.viewmodel.RegisterViewModel;
import com.google.android.material.button.MaterialButton;

public class SignUpActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private AutoCompleteTextView actvEducationLevel;
    private MaterialButton btnSignUp;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupViewModel();
        setupListeners();
        setupDropdown();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        actvEducationLevel = findViewById(R.id.actvEducationLevel);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        
        viewModel.getIsSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to Login
            }
        });

        viewModel.getErrorMessage().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });

        viewModel.isLoading().observe(this, loading -> {
            btnSignUp.setEnabled(!loading);
            btnSignUp.setText(loading ? "Registering..." : "Create Account");
        });
    }

    private void setupListeners() {
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        TextView tvSignInLink = findViewById(R.id.tvSignInLink);
        tvSignInLink.setOnClickListener(v -> finish());

        btnSignUp.setOnClickListener(v -> handleSignUp());
    }

    private void setupDropdown() {
        String[] educationLevels = getResources().getStringArray(R.array.education_levels);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, educationLevels);
        actvEducationLevel.setAdapter(adapter);
    }

    private void handleSignUp() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String educationLevel = actvEducationLevel.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || educationLevel.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setEducationLevel(educationLevel);

        viewModel.register(userDTO);
    }
}