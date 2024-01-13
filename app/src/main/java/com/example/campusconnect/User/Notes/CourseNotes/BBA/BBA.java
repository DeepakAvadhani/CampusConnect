package com.example.campusconnect.User.Notes.CourseNotes.BBA;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.example.campusconnect.User.Notes.CourseNotes.BBA.Sems.bbasemone;
import com.example.campusconnect.User.Notes.CourseNotes.BBA.Sems.bbasemthree;
import com.example.campusconnect.User.Notes.CourseNotes.BBA.Sems.bbasemtwo;

public class BBA extends AppCompatActivity {
    private TextView semone, semtwo, semthree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bba);
        semone = findViewById(R.id.bbasemone);
        semtwo = findViewById(R.id.bbasemtwo);
        semthree=findViewById(R.id.bbasemthree);
        semone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BBA.this, bbasemone.class);
                startActivity(intent);
            }
        });
        semtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BBA.this, bbasemtwo.class);
                startActivity(intent);
            }
        });
        semthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BBA.this, bbasemthree.class);
                startActivity(intent);
            }
        });
    }
}