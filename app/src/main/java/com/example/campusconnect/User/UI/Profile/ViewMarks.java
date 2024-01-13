package com.example.campusconnect.User.UI.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.campusconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMarks<context> extends AppCompatActivity {
    private Context context;
    private ArrayList<MarksHelper> list;
    private FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference,dbref;
    private TextView subject1,subject3,subject4,subject5,subject6,subject7,subject2,marks1, marks2, marks3, marks4, marks5, marks6, marks7;
    private TextView total, totalmarks, percent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmarks);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        subject1 = findViewById(R.id.subject1);
        subject2 = findViewById(R.id.subject2);
        subject3 = findViewById(R.id.subject3);
        subject4 = findViewById(R.id.subject4);
        subject5 = findViewById(R.id.subject5);
        subject6 = findViewById(R.id.subject6);
        subject7 = findViewById(R.id.subject7);
        marks1 = findViewById(R.id.sub1marks);
        marks2 = findViewById(R.id.sub2marks);
        marks3 = findViewById(R.id.sub3marks);
        marks4 = findViewById(R.id.sub4marks);
        marks5 = findViewById(R.id.sub5marks);
        marks6 = findViewById(R.id.sub6marks);
        marks7 = findViewById(R.id.sub7marks);
        total = findViewById(R.id.total);
        totalmarks = findViewById(R.id.totalmarks);
        percent = findViewById(R.id.percentage);

        displaymarks();
    }

    private void displaymarks() {
        String Uid = user.getUid();
        dbref = reference.child(Uid);
        int position;
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper helper = snapshot.getValue(UserHelper.class);
                if (helper != null) {
                    String course = helper.getCourse();
                    String sem = helper.getSem();
                    if (course == "BBA" && sem == "1st SEM") {
                        subject1.setText("FA");
                        subject2.setText("FA");
                        subject3.setText("FA");
                        subject4.setText("FA");
                        subject5.setText("FA");
                        subject6.setText("FA");
                        subject7.setText("FA");
                        total.setText("140");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}