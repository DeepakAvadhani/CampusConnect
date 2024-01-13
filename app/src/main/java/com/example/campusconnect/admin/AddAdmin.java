package com.example.campusconnect.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.example.campusconnect.User.Authentication.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddAdmin extends AppCompatActivity {
    private EditText adminname, adminemail, adminpassword;
    private Button button;
    private String name, email, pass;
    private FirebaseAuth auth;
    private DatabaseReference reference,dbref;
    private ProgressDialog pd;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Admin");
        setContentView(R.layout.activity_add_admin);
        adminname = findViewById(R.id.Adminname);
        adminemail = findViewById(R.id.Adminemail);
        adminpassword = findViewById(R.id.Adminpassword);
        button = findViewById(R.id.addadminbtn);
        auth = FirebaseAuth.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        name = adminname.getText().toString();
        email = adminemail.getText().toString();
        pass = adminpassword.getText().toString();
        if (name.isEmpty()) {
            adminname.setError("Required");
            adminname.requestFocus();
        } else if (email.isEmpty()) {
            adminemail.setError("Required");
            adminemail.requestFocus();
        } else if (pass.isEmpty()) {
            adminpassword.setError("Required");
            adminpassword.requestFocus();
        }else{
            pd.setMessage("Creating User...");
            pd.show();
            createadmin();
        }
    }

    private void createadmin() {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pd.hide();
                    uploaddata();
                } else {
                    pd.hide();
                    Toast.makeText(AddAdmin.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.hide();
                Toast.makeText(AddAdmin.this, "Error :"+e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploaddata() {
        dbref = reference.child("Admin");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = user.getUid();
        HashMap<String, String> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        dbref.child(Uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddAdmin.this, "User Created", Toast.LENGTH_SHORT).show();
                    openMain();
                } else {
                    Toast.makeText(AddAdmin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.hide();
                Toast.makeText(AddAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMain() {
        startActivity(new Intent(AddAdmin.this,AdminMainActivity.class));
        finish();
    }
}