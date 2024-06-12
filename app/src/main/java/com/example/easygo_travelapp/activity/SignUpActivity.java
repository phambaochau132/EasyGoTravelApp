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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private CTEditText ctUsername, ctPassword, ctConFirmPass;
    private TextView btnSignup;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private ImageView btnBack;

    private void init() {
        dialog = new ProgressDialog(this);
        ctUsername = findViewById(R.id.userName);
        ctPassword = findViewById(R.id.password);
        ctConFirmPass = findViewById(R.id.confirmPassword);
        btnSignup = findViewById(R.id.btnSignUp);
        btnBack = findViewById(R.id.btnBack);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        initAction();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initAction() {
        ctUsername.updateAction(getResources().getString(R.string.username));
        ctPassword.updateAction(getResources().getString(R.string.password));
        ctConFirmPass.updateAction(getResources().getString(R.string.confirm_password));
        btnSignup.setOnClickListener(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (ctUsername.getContent().isEmpty()
                || ctPassword.getContent().isEmpty()
                || ctConFirmPass.getContent().isEmpty()) {
            Toast.makeText(this, R.string.please_enter_full, Toast.LENGTH_SHORT).show();
        } else {
            if (!this.ctPassword.getContent().equals(this.ctConFirmPass.getContent())) {
                Toast.makeText(this, R.string.confirm_password_not_match, Toast.LENGTH_SHORT).show();
            } else {
                dialog.show();
                createAccountWithFireBase(this.ctUsername.getContent(), this.ctPassword.getContent());
            }
        }
    }

//    private boolean checkContentFull() {
//        if (ctUsername.getContent().isEmpty()
//                && ctPassword.getContent().isEmpty()
//                && ctConFirmPass.getContent().isEmpty()){
//            return false;
//        }
//        return true;
//    }

    private void createAccountWithFireBase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(SignUpActivity.this, R.string.email_not_exist,
                                    Toast.LENGTH_SHORT).show();
                            clearAllContent();
                        }
                    }
                });
    }

    private void clearAllContent() {
        ctUsername.setContent(null);
        ctPassword.setContent(null);
        ctConFirmPass.setContent(null);}
}