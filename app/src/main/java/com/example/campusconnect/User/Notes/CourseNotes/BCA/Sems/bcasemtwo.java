package com.example.campusconnect.User.Notes.CourseNotes.BCA.Sems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.example.campusconnect.User.Notes.EbookAdapter;
import com.example.campusconnect.User.Notes.EbookData;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class bcasemtwo extends AppCompatActivity {
    private RecyclerView pdfRecyclerView;
    private DatabaseReference reference,dbref;
    private List<EbookData> list;
    private EbookAdapter adapter;
    private LinearLayout bcanodata;
    LinearLayout linearLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcasemtwo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BCA 2nd SEM Notes");
        pdfRecyclerView = findViewById(R.id.bcatworecycler);
        reference = FirebaseDatabase.getInstance().getReference().child("Notes");
        shimmerFrameLayout = findViewById(R.id.shimmeranimation);
        linearLayout = findViewById(R.id.bcatwoshimmer);
        bcanodata = findViewById(R.id.bcanodata);
        getData();
    }

    private void getData() {
        dbref = reference.child("BCA").child("2nd SEM");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>(); //If its initialised in onCreate method new data may get added and will be shown along with the old data
                if (!snapshot.exists()) {
                    bcanodata.setVisibility(View.VISIBLE);
                    pdfRecyclerView.setVisibility(View.INVISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        EbookData data = snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    adapter = new EbookAdapter(bcasemtwo.this, list);
                    pdfRecyclerView.setLayoutManager(new LinearLayoutManager(bcasemtwo.this));
                    pdfRecyclerView.setAdapter(adapter);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(bcasemtwo.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}