package com.example.easygo_travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.customView.CTEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CTEditText ctUsername, ctPassword;
    private TextView tvForgot, btnLogin, tvSignUp;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private ImageView btnBack;

    private void init() {
        ctUsername = findViewById(R.id.userName);
        ctPassword = findViewById(R.id.password);
        tvForgot = findViewById(R.id.forgot);
        tvSignUp = findViewById(R.id.signUp);
        btnLogin = findViewById(R.id.btnLogin);
        dialog = new ProgressDialog(this);
        btnBack = findViewById(R.id.btnBack);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initAction();
        mAuth = FirebaseAuth.getInstance();

    }

    private void initAction() {
        ctUsername.updateAction(getResources().getString(R.string.username));
        ctPassword.updateAction(getResources().getString(R.string.password));
        tvForgot.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnBack.getId()) {
            onBackPressed();
        }
        if (view.getId() == tvForgot.getId()) {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
        if (view.getId() == tvSignUp.getId()) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
        if (view.getId() == btnLogin.getId()) {
            dialog.show();
            if (ctUsername.getContent().isEmpty()
                    || ctPassword.getContent().isEmpty()) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, R.string.please_enter_full, Toast.LENGTH_SHORT).show();
            } else {
                SignInWithFireBase(this.ctUsername.getContent(), this.ctPassword.getContent());
            }
        }

    }

    private void SignInWithFireBase(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, OnboardActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.account_incorrect,
                                    Toast.LENGTH_SHORT).show();
                            clearAllContent();
                        }
                    }
                });
    }

    private void clearAllContent() {
        ctUsername.setContent(null);
        ctPassword.setContent(null);
    }
}