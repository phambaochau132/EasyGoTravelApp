package com.example.easygo_travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easygo_travelapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginPhoneNumberActivity extends AppCompatActivity{
    public static final String PHONE_NUMBER = "phone_number";
    public static final String VERIFICATION_ID = "verification_id";
    EditText edtYourPhone;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);

        mAuth = FirebaseAuth.getInstance();

        edtYourPhone = findViewById(R.id.edtYourPhone);
        edtYourPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String phoneNumber = edtYourPhone.getText().toString().trim();
                if (i != 0 && EditorInfo.IME_MASK_ACTION != 0) {
                    getOtpVerification("+84" + phoneNumber);
                    edtYourPhone.clearFocus();
                }
                return false;
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToMainActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(LoginPhoneNumberActivity.this, R.string.code_was_invalid, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void getOtpVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(LoginPhoneNumberActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String idVerification, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(idVerification, forceResendingToken);
                                goToPhoneNumberActivity(phoneNumber, idVerification);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void goToMainActivity(String phoneNumber) {
        Intent intent = new Intent(LoginPhoneNumberActivity.this, MainActivity.class);
        intent.putExtra(PHONE_NUMBER, phoneNumber);
        startActivity(intent);
    }

    private void goToPhoneNumberActivity(String phoneNumber, String idVerification) {
        Intent intent = new Intent(LoginPhoneNumberActivity.this, PhoneVerificationActivity.class);
        intent.putExtra(PHONE_NUMBER, phoneNumber);
        intent.putExtra(VERIFICATION_ID, idVerification);
        startActivity(intent);
    }
}