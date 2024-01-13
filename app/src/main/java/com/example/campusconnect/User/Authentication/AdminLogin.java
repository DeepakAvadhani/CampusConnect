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

import com.example.campusconnect.R;
import com.example.campusconnect.admin.AdminMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {
    private EditText adminemail, adminpass;
    private Button button;
    private TextView textView;
    private String email,password;
    private FirebaseAuth auth;
    private ProgressDialog pd;
    private SharedPreferences sharedPreferences1;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        adminemail = findViewById(R.id.adminEmail);
        adminpass = findViewById(R.id.adminPassword);
        button = findViewById(R.id.adminLoginbtn);
        textView = findViewById(R.id.adminforgotpassword);
        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this,ForgotPassword.class));
                finish();
            }
        });
        sharedPreferences1 = getSharedPreferences("AdminLogin", MODE_PRIVATE);
        editor = sharedPreferences1.edit();
        if (sharedPreferences1.getString("AdminLogin", "false").equals("true")) {
            openadminmain();
        }else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(adminpass.getWindowToken(), 0);
                    validateData();
                }
            });
        }
    }

    private void validateData() {
        email = adminemail.getText().toString();
        password = adminpass.getText().toString();
        if(email.isEmpty()){
            adminemail.setError("Required");
            adminemail.requestFocus();
        }else if(password.isEmpty()){
            adminpass.setError("Required");
            adminpass.requestFocus();
        }else{
            adminlogin();
        }
    }

    private void adminlogin() {
        pd.setMessage("Logging in...");
        pd.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    editor.putString("AdminLogin","true");
                    editor.commit();
                    pd.hide();
                    Toast.makeText(AdminLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    openadminmain();
                } else {
                    pd.hide();
                    Toast.makeText(AdminLogin.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.hide();
                Toast.makeText(AdminLogin.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openadminmain() {
        startActivity(new Intent(AdminLogin.this, AdminMainActivity.class));
        finish();
    }
}