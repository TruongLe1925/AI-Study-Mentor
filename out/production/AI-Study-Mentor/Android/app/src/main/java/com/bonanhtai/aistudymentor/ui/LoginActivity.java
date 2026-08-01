package com.bonanhtai.aistudymentor.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.bonanhtai.aistudymentor.model.AuthRequest;
import com.bonanhtai.aistudymentor.viewmodel.LoginViewModel;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private MaterialButton btnSignIn;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupViewModel();
        setupListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        viewModel.getToken().observe(this, token -> {
            if (token != null) {
                saveToken(token);
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                finish(); // Return to MainActivity
            }
        });

        viewModel.getErrorMessage().observe(this, error -> {
            Toast.makeText(this, "Login Failed: " + error, Toast.LENGTH_LONG).show();
        });

        viewModel.isLoading().observe(this, loading -> {
            btnSignIn.setEnabled(!loading);
            btnSignIn.setText(loading ? "Signing In..." : "Sign In");
        });
    }

    private void setupListeners() {
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        TextView tvSignUpLink = findViewById(R.id.tvSignUpLink);
        tvSignUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(v -> handleSignIn());
    }

    private void handleSignIn() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthRequest request = new AuthRequest();
        request.setEmail(email);
        request.setPassword(password);

        viewModel.login(request);
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);
        sharedPreferences.edit().putString("jwt_token", token).apply();
    }
}