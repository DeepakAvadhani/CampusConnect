package com.example.campusconnect.User.Notes.CourseNotes.BBA.Sems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class bbasemone extends AppCompatActivity {
    private RecyclerView pdfRecyclerView;
    private DatabaseReference reference, dbref;
    private LinearLayout bbanodata;
    private List<EbookData> list;
    private EbookAdapter adapter;
    LinearLayout linearLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbasemone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BBA 1st SEM Notes");
        pdfRecyclerView = findViewById(R.id.bbaonerecycler);
        reference = FirebaseDatabase.getInstance().getReference().child("Notes");
        shimmerFrameLayout = findViewById(R.id.shimmeranimation);
        linearLayout = findViewById(R.id.bbaoneshimmer);
        bbanodata = findViewById(R.id.bbanodata);
        getData();
    }

    private void getData() {
        dbref = reference.child("BBA").child("1st SEM");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>(); //If its initialised in onCreate method new data may get added and will be shown along with the old data
                if (!snapshot.exists()) {
                    bbanodata.setVisibility(View.VISIBLE);
                    pdfRecyclerView.setVisibility(View.INVISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        EbookData data = snapshot1.getValue(EbookData.class);
                        list.add(data);
                    }
                    adapter = new EbookAdapter(bbasemone.this, list);
                pdfRecyclerView.setLayoutManager(new LinearLayoutManager(bbasemone.this));
                pdfRecyclerView.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                }
            }
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    EbookData data = snapshot1.getValue(EbookData.class);
//                    list.add(data);
//                }
//                adapter = new EbookAdapter(bbasemone.this, list);
//                pdfRecyclerView.setLayoutManager(new LinearLayoutManager(bbasemone.this));
//                pdfRecyclerView.setAdapter(adapter);
//                shimmerFrameLayout.stopShimmer();
//                shimmerFrameLayout.setVisibility(View.GONE);
//                linearLayout.setVisibility(View.GONE);
//            }
//
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(bbasemone.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onPause() {

        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}