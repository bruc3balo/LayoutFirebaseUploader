package com.example.videoimageupload;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login Activity" ;
    private FirebaseAuth firebaseAuth;
    private EditText passwordField, emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        passwordField = findViewById(R.id.passwordField);
        emailField = findViewById(R.id.emailField);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent goToMenu = new Intent(Login.this, MainActivity.class);
            startActivity(goToMenu);
            finish();
        } /*else if (currentUser == null) {
            Intent goToMenu = new Intent(Login.this, Menu.class);
            startActivity(goToMenu);
            finish();
        }*/ else {
            Toast.makeText(this, "Log in to continue", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccount(String email,String password){
       try {
           firebaseAuth.createUserWithEmailAndPassword(email, password)
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               Log.d(TAG, "createUserWithEmail:success");
                               FirebaseUser user = firebaseAuth.getCurrentUser();
                               updateUI(user);
                           } else {
                               // If sign in fails, display a message to the user.
                               Log.w(TAG, "createUserWithEmail:failure", task.getException());
                               Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                               updateUI(null);
                           }

                           // ...
                       }
                   });
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    private void signInUser(String email, String password) {
       try {
           firebaseAuth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               Log.d(TAG, "signInWithEmail:success");
                               FirebaseUser user = firebaseAuth.getCurrentUser();
                               updateUI(user);
                           } else {
                               // If sign in fails, display a message to the user.
                               Log.w(TAG, "signInWithEmail:failure", task.getException());
                               Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                               updateUI(null);
                           }

                           // ...
                       }
                   });
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    public void validateCreateForm(View view) {
        if (emailField.getText().toString().isEmpty()) {
            emailField.setError("Email empty");
            emailField.requestFocus();
        } else if (passwordField.getText().toString().isEmpty()) {
            passwordField.setError("Password empty");
            passwordField.requestFocus();
        } else if (passwordField.getText().toString().length() < 6) {
            passwordField.setError("Too short");
            passwordField.requestFocus();
        } else {
            createAccount(emailField.getText().toString(),passwordField.getText().toString());
        }
    }

    public void validateLoginForm(View view) {
        if (emailField.getText().toString().isEmpty()) {
            emailField.setError("Email empty");
            emailField.requestFocus();
        } else if (passwordField.getText().toString().isEmpty()) {
            passwordField.setError("Password empty");
            passwordField.requestFocus();
        } else if (passwordField.getText().toString().length() < 6) {
            passwordField.setError("Too short");
            passwordField.requestFocus();
        } else {
            signInUser(emailField.getText().toString(),passwordField.getText().toString());
        }
    }
}