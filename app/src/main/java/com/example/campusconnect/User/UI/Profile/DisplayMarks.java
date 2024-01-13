package com.example.campusconnect.User.UI.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayMarks extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference reference, dbref,reference1;
    private MarksAdapter adapter;
    private ArrayList<MarksHelper> list;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_marks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student Marks");
        recyclerView = findViewById(R.id.marksrecycler);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference1 = FirebaseDatabase.getInstance().getReference("Marks");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayMarks.this));
        list = new ArrayList<>();
        adapter = new MarksAdapter(this, list);
        recyclerView.setAdapter(adapter);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String Uid = user.getUid();
        reference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper userAdapter = snapshot.getValue(UserHelper.class);
                if (userAdapter != null) {
                    String regno = userAdapter.getRegno();
                    dbref = reference1.child(regno);
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                MarksHelper data = snapshot.getValue(MarksHelper.class);
                                list.add(data);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(DisplayMarks.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayMarks.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}