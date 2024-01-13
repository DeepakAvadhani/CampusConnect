package com.example.campusconnect.User.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campusconnect.MainActivity;
import com.example.campusconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.Policy;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private EditText uemail, upassword;
    private TextView textView, textView2,textView3;
    private String email, password;
    private FirebaseAuth auth;
    private ProgressDialog pd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        uemail = findViewById(R.id.userEmail);
        upassword = findViewById(R.id.userPassword);
        button = findViewById(R.id.UserLoginbtn);
        textView = findViewById(R.id.userforgotpassword);
        textView2 = findViewById(R.id.registerhere);
        textView3 = findViewById(R.id.loginasadmin);
        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,AdminLogin.class));
                finish();
            }
        });
        if (sharedPreferences.getString("Login", "false").equals("true")) {
            openmain();
        }else{
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(upassword.getWindowToken(), 0);
                validateData();
            }
        });
    }

    private void openmain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void validateData() {
        email = uemail.getText().toString();
        password = upassword.getText().toString();
        if(email.isEmpty()){
            uemail.setError("Required");
            uemail.requestFocus();
        }else if(password.isEmpty()){
            upassword.setError("Required");
            upassword.requestFocus();
        }else{
            userlogin();
        }
    }

    private void userlogin() {
        pd.setMessage("Logging in...");
        pd.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    editor.putString("Login","true");
                    editor.commit();
                    pd.hide();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    openmain();
                } else {
                    pd.hide();
                    Toast.makeText(LoginActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.hide();
                Toast.makeText(LoginActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}