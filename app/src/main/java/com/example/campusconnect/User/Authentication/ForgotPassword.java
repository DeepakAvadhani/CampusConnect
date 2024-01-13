package com.example.campusconnect.User.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText uemail;
    private String email;
    private Button button;
    private TextView textView;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth = FirebaseAuth.getInstance();
        uemail = findViewById(R.id.userforgotEmail);
        button = findViewById(R.id.forgotbutton);
        textView = findViewById(R.id.backtologin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
    }

    private void validatedata() {
        email = uemail.getText().toString();
        if (email.isEmpty()) {
            uemail.setError("Required");
            uemail.requestFocus();
        } else {
            forgotpassword();
        }
    }
    private void forgotpassword() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Please Check your Email Inbox and Spam Folder", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPassword.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}