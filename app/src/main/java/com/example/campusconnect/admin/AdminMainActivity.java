package com.example.campusconnect.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.campusconnect.R;
import com.example.campusconnect.User.Authentication.AdminLogin;
import com.example.campusconnect.admin.Faculty.UpdateFaculty;
import com.example.campusconnect.admin.Marks.ManageMarks;
import com.example.campusconnect.admin.Notice.DeleteNotice;
import com.example.campusconnect.admin.Notice.UploadNotice;
import com.google.firebase.auth.FirebaseAuth;

public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView UploadNotice, UploadImage, UploadNotes, UpdateFaculty, DeleteNotice, UpdateMarks;
    private long backPressed;
    private Button button1,button2;
    private SharedPreferences sharedPreferences1;
    private SharedPreferences.Editor editor;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        button1 = findViewById(R.id.adminlogout);
        button2=findViewById(R.id.addnewadmin);
        auth = FirebaseAuth.getInstance();
        sharedPreferences1 = this.getSharedPreferences("AdminLogin", MODE_PRIVATE);
        editor = sharedPreferences1.edit();
//        if (sharedPreferences1.getString("AdminLogin", "false").equals("true")) {
//            openlogin();
//        }
        UploadNotice = findViewById(R.id.UploadNotice);
        UploadNotice.setOnClickListener(this);
        UploadImage = findViewById(R.id.UploadImage);
        UploadImage.setOnClickListener(this);
        UploadNotes = findViewById(R.id.UploadNotes);
        UploadNotes.setOnClickListener(this);
        UpdateFaculty = findViewById(R.id.UpdateFaculty);
        UpdateFaculty.setOnClickListener(this);
        DeleteNotice = findViewById(R.id.DeleteNotice);
        DeleteNotice.setOnClickListener(this);
        UpdateMarks = findViewById(R.id.UpdateMarks);
        UpdateMarks.setOnClickListener(this);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("AdminLogin", "false");
                editor.commit();
                auth.signOut();
                openlogin();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,AddAdmin.class));
                finish();
            }
        });
    }

    private void openlogin() {
        startActivity(new Intent(AdminMainActivity.this, AdminLogin.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.UploadNotice:
                startActivity(new Intent(AdminMainActivity.this, UploadNotice.class));
                break;
            case R.id.UploadNotes:
                startActivity(new Intent(AdminMainActivity.this, UploadNotes.class));
                break;
            case R.id.UpdateFaculty:
                startActivity(new Intent(AdminMainActivity.this, UpdateFaculty.class));
                break;
            case R.id.UploadImage:
                startActivity(new Intent(AdminMainActivity.this, UploadImage.class));
                break;
            case R.id.DeleteNotice:
                startActivity(new Intent(AdminMainActivity.this, DeleteNotice.class));
                break;
            case R.id.UpdateMarks:
                startActivity(new Intent(AdminMainActivity.this, ManageMarks.class));
                break;
            default:
                Toast.makeText(this, "Project Developed By Deepak And Goutam", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressed + 3000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}