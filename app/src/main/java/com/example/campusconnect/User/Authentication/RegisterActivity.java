package com.example.campusconnect.User.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText username, useremail, userpassword, userregno;
    private Button button;
    private String name, email, pass, regno, Course, Semester;
    private FirebaseAuth auth;
    private DatabaseReference reference,dbref;
    private TextView textView;
    private AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1;
    private ProgressDialog pd;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        username = findViewById(R.id.Username);
        useremail = findViewById(R.id.Email);
        userpassword = findViewById(R.id.Password);
        userregno = findViewById(R.id.regno);
        button = findViewById(R.id.registerbtn);
        textView = findViewById(R.id.loginhere);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        String[] course = new String[]{"BCA", "BBA"};
        String[]  SEM = new String[]{"1st SEM","2nd SEM","3rd SEM","4th SEM","5th SEM","6th SEM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinnerdropdown, course);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.spinnercourse);
        AutoCompleteTextView autoCompleteTextView1 = findViewById(R.id.spinnersemester);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course = autoCompleteTextView.getText().toString();
                Semester = autoCompleteTextView1.getText().toString();
            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinnerdropdown, SEM);
        autoCompleteTextView1.setAdapter(adapter1);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course = autoCompleteTextView.getText().toString();
                Semester = autoCompleteTextView1.getText().toString();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openlogin();
            }
        });
    }

    private void validateData() {
        name = username.getText().toString();
        email = useremail.getText().toString();
        pass = userpassword.getText().toString();
        regno = userregno.getText().toString();
        if (name.isEmpty()) {
            username.setError("Required");
            username.requestFocus();
        } else if (email.isEmpty()) {
            useremail.setError("Required");
            useremail.requestFocus();
        } else if (pass.isEmpty()) {
            userpassword.setError("Required");
            userpassword.requestFocus();
        } else if (regno.isEmpty()) {
            userregno.setError("Required");
            userregno.requestFocus();
        } else if (Course.equals("Select Course")) {
            Toast.makeText(this, "Please Select Course", Toast.LENGTH_SHORT).show();
        } else if (Semester.equals("Select Course")) {
            Toast.makeText(this, "Please select Semester", Toast.LENGTH_SHORT).show();
        } else {
            pd.setMessage("Creating User...");
            pd.show();
            createuser();
        }
    }

    private void createuser() {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pd.hide();
                    uploaddata();
                } else {
                    pd.hide();
                    Toast.makeText(RegisterActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.hide();
                Toast.makeText(RegisterActivity.this, "Error :"+e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploaddata() {
        dbref = reference.child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = user.getUid();
        HashMap<String, String> user = new HashMap<>();
        user.put("name", name);
        user.put("regno", regno);
        user.put("course", Course);
        user.put("sem", Semester);
        user.put("email", email);
        dbref.child(Uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    uploadmarks();
                    openMain();
                } else {
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.hide();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMain() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    private void uploadmarks() {
        String sub1 = "0", sub2 = "0", sub3 = "0", sub4 = "0", sub5 = "0", sub6 = "0", sub7 = "0";
        dbref = reference.child("Marks");
        HashMap<String, String> marks = new HashMap<>();
        marks.put("course", Course);
        marks.put("sem", Semester);
        marks.put("sub1", sub1);
        marks.put("sub2", sub2);
        marks.put("sub3", sub3);
        marks.put("sub4", sub4);
        marks.put("sub5", sub5);
        marks.put("sub6", sub6);
        marks.put("sub7", sub7);
        dbref.child(regno).setValue(marks).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openlogin() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}