package com.pjatk.brunolemanski.shoplist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends Activity {

    private TextView title;
    private TextInputLayout emailInput;
    private TextInputLayout passInput;
    private Button login;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        title = findViewById(R.id.loginTitle);
        emailInput = findViewById(R.id.userInput);
        passInput = findViewById(R.id.passInput);
        login = findViewById(R.id.loginButton);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }

        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    /**
     * Login with Email and Password.
     */
    private void startLogIn() {
        String user = emailInput.getEditText().getText().toString().trim();
        String pass = passInput.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(user) && TextUtils.isEmpty(pass)){
            emailInput.setError("Email is required");
            passInput.setError("Password is required");

        } else if(TextUtils.isEmpty(user)){
            emailInput.setError("Email is required");

        } else if(TextUtils.isEmpty(pass)){
            passInput.setError("Password is required");

        } else {

            auth.signInWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Toast.makeText(LoginActivity.this, "You have been logged", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sorry, You haven't logged in :(", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public void signUp(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }
}
