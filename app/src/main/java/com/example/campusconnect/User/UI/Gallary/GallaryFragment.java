package com.example.campusconnect.User.UI.Gallary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campusconnect.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GallaryFragment extends Fragment {
    RecyclerView recyclerView1, recyclerView2,recyclerView3;
    GalleryAdapter adapter;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallary, container, false);
        recyclerView1 = view.findViewById(R.id.galleryrv1);
        recyclerView2 = view.findViewById(R.id.galleryrv2);
        recyclerView3 = view.findViewById(R.id.galleryrv3);

        reference = FirebaseDatabase.getInstance().getReference().child("Gallery");
        getConvoImage();
        getIndImage();
        getOtherImage();
        return view;
    }

    private void getOtherImage() {
        reference.child("Other Events").addValueEventListener(new ValueEventListener() {
            List<String> imagelist = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imagelist.add(data);
                }
                adapter = new GalleryAdapter(getContext(), imagelist);
                recyclerView3.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerView3.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getIndImage() {
        reference.child("Independence Day").addValueEventListener(new ValueEventListener() {
            List<String> imagelist = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imagelist.add(data);
                }
                adapter = new GalleryAdapter(getContext(), imagelist);
                recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerView2.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getConvoImage() {
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {
            List<String> imagelist = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imagelist.add(data);
                }
                adapter = new GalleryAdapter(getContext(), imagelist);
                recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerView1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}