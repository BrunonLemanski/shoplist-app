package com.pjatk.brunolemanski.shoplist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends Activity {

    private TextInputLayout emailInputSign;
    private TextInputLayout passInputSign;
    private TextInputLayout confirmInputSign;
    private Button signUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);

        emailInputSign = findViewById(R.id.userInputSign);
        passInputSign = findViewById(R.id.passInputSign);
        confirmInputSign = findViewById(R.id.confirmInputSign);
        signUp = findViewById(R.id.signupButton);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();

                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private void startSignUp() {
        String email = emailInputSign.getEditText().getText().toString().trim();
        String pass = passInputSign.getEditText().getText().toString().trim();
        String confirm = confirmInputSign.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(pass) && TextUtils.isEmpty(confirm)) {
            passInputSign.setError("Password is required");
            confirmInputSign.setError("Confirm password is required");
        } else if(TextUtils.isEmpty(pass)) {
            passInputSign.setError("Password is required");
        } else if(TextUtils.isEmpty(confirm)) {
            confirmInputSign.setError("Confirm password is required");
        }else if(TextUtils.isEmpty(email)) {
            emailInputSign.setError("Email is required");
        } else {

            if(!TextUtils.equals(pass, confirm)) {
                confirmInputSign.setError("Passwords is not match");

            } else {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this, "You have been registered", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }



    public void logIn(View view) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
    }
}
