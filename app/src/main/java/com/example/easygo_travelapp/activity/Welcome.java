package com.example.easygo_travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easygo_travelapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class Welcome extends AppCompatActivity implements View.OnClickListener {

    private TextView btnPhone, btnLogin, tvSignUp;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private LoginButton loginButton;

    private void init() {
        btnPhone = findViewById(R.id.btnLoginPhone);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.signUp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        mAuth = FirebaseAuth.getInstance();
        btnPhone.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        loginFacebook();
        if (mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnPhone.getId()) {
            Intent intent = new Intent(Welcome.this, LoginPhoneNumberActivity.class);
            startActivity(intent);
        }
        if (view.getId() == btnLogin.getId()) {
            Intent intent = new Intent(Welcome.this, LoginActivity.class);
            startActivity(intent);
        }
        if (view.getId() == tvSignUp.getId()) {
            Intent intent = new Intent(Welcome.this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Welcome.this, OnboardActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Welcome.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.btnLoginFB);
        loginButton.setReadPermissions(Arrays.asList("email"));
//        loginButton.setReadPermissions("email");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("onError: ", error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}