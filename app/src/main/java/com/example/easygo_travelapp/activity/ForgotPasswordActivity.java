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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private CTEditText ctEmail;
    private TextView btnSend;
    private ProgressDialog dialog;
    private ImageView btnBack;

    private void init() {
        dialog = new ProgressDialog(this);
        ctEmail = findViewById(R.id.email);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

        ctEmail.updateAction(getResources().getString(R.string.email));
        btnSend.setOnClickListener(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (ctEmail.getContent().isEmpty()) {
            Toast.makeText(this, R.string.please_enter_full, Toast.LENGTH_SHORT);
        } else {
            dialog.show();
            resetPasswordByEmail(ctEmail.getContent());
        }
    }

    private void resetPasswordByEmail(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, R.string.email_not_exist,
                                    Toast.LENGTH_SHORT).show();
                            ctEmail.setTitle(null);
                            getCurrentFocus().clearFocus();
                        }
                    }
                });
    }
}