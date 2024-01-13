package com.example.campusconnect.User.Notes.CourseNotes.BCA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.example.campusconnect.User.Notes.CourseNotes.BCA.Sems.bcasem3;
import com.example.campusconnect.User.Notes.CourseNotes.BCA.Sems.bcasemone;
import com.example.campusconnect.User.Notes.CourseNotes.BCA.Sems.bcasemtwo;

public class BCA extends AppCompatActivity {
    private TextView semone,semtwo,semthree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bca);
        semone = findViewById(R.id.semone);
        semtwo = findViewById(R.id.semtwo);
        semthree=findViewById(R.id.semthree);
        semone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BCA.this, bcasemone.class);
                startActivity(intent);
            }
        });
        semtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BCA.this, bcasemtwo.class);
                startActivity(intent);
            }
        });
        semthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BCA.this, bcasem3.class);
                startActivity(intent);
            }
        });
    }
}