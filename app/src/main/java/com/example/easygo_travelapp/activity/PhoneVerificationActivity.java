package com.example.easygo_travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.customView.CTGroupInputOTP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class PhoneVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvYourPhone;
    CTGroupInputOTP groupCode;
    String mPhoneNumber;
    static String idVerification;
    ImageButton btnSend;
    private FirebaseAuth mAuth;

    private void initUi() {
        tvYourPhone = findViewById(R.id.tvYourPhone);
        groupCode = findViewById(R.id.grCode);
        btnSend = findViewById(R.id.btnSend);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        initUi();
        getDataIntent();
        initAction();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initAction() {
        tvYourPhone.setText(mPhoneNumber);
        btnSend.setOnClickListener(this);
    }

    private void getDataIntent() {
        mPhoneNumber = getIntent().getStringExtra(LoginPhoneNumberActivity.PHONE_NUMBER);
        idVerification = getIntent().getStringExtra(LoginPhoneNumberActivity.VERIFICATION_ID);
    }

    @Override
    public void onClick(View view) {
        sendOTPVerification(groupCode.getResultInput());
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToOnboardActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(PhoneVerificationActivity.this, R.string.code_was_invalid, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToOnboardActivity(String phoneNumber) {
        Intent intent = new Intent(PhoneVerificationActivity.this, OnboardActivity.class);
        intent.putExtra(LoginPhoneNumberActivity.PHONE_NUMBER, phoneNumber);
        startActivity(intent);
        finishAffinity();
    }

    public void sendOTPVerification(String codeInput) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(idVerification, codeInput);
        signInWithPhoneAuthCredential(credential);
    }
}