package com.example.campusconnect.admin.Marks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.campusconnect.R;

public class ManageMarks extends AppCompatActivity {
    private CardView cardView1, cardView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_marks);
        getSupportActionBar().setTitle("Manage Marks");
        cardView1 = findViewById(R.id.AddStudent);
        cardView2 = findViewById(R.id.DeleteStudent);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMarks.this, AddStudent.class));
                finish();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMarks.this, DeleteStudent.class));
                finish();
            }
        });
    }
}