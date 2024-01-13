package com.example.campusconnect.User.Notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.campusconnect.R;
import com.example.campusconnect.User.Notes.CourseNotes.BBA.BBA;
import com.example.campusconnect.User.Notes.CourseNotes.BCA.BCA;

public class NotesActivity extends AppCompatActivity {
    private TextView bca, bba, bcom, ba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        bca = findViewById(R.id.coursebca);
        bba = findViewById(R.id.coursebba);
        bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, BCA.class);
                startActivity(intent);
            }
        });
        bba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, BBA.class);
                startActivity(intent);
            }
        });
    }
}