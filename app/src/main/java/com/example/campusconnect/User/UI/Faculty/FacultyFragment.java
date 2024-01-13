package com.example.campusconnect.User.UI.Faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.example.campusconnect.admin.Faculty.LecturerAdapter;
import com.example.campusconnect.admin.Faculty.LecturerData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {
    private RecyclerView csdepartment, dbmsdepartment, langdepartment;
    private LinearLayout csNodata, dbmsNodata, langNodata;
    private List<LecturerData> list1, list2, list3;
    private FacultyAdapter adapter;
    private DatabaseReference reference, dbref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);
        csdepartment = view.findViewById(R.id.csdepartment);
        dbmsdepartment = view.findViewById(R.id.dbmsdepartment);
        langdepartment = view.findViewById(R.id.langdepartment);

        csNodata = view.findViewById(R.id.csnodata);
        dbmsNodata = view.findViewById(R.id.dbmsnodata);
        langNodata = view.findViewById(R.id.langnodata);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        csDepartment();
        dbmsDepartment();
        langDepartment();
        return view;
    }

    private void langDepartment() {
        dbref = reference.child("Language");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()) {
                    langNodata.setVisibility(View.VISIBLE);
                    langdepartment.setVisibility(View.GONE);
                } else {
                    langNodata.setVisibility(View.GONE);
                    langdepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        LecturerData data = snapshot1.getValue(LecturerData.class);
                        list3.add(data);
                    }
                    langdepartment.setHasFixedSize(true);
                    langdepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new FacultyAdapter(list3, getContext(), "Language");
                    langdepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dbmsDepartment() {
        dbref = reference.child("DBMS");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()) {
                    dbmsNodata.setVisibility(View.VISIBLE);
                    dbmsdepartment.setVisibility(View.GONE);
                } else {
                    dbmsNodata.setVisibility(View.GONE);
                    dbmsdepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        LecturerData data = snapshot1.getValue(LecturerData.class);
                        list2.add(data);
                    }
                    dbmsdepartment.setHasFixedSize(true);
                    dbmsdepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new FacultyAdapter(list2, getContext(), "DBMS");
                    dbmsdepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void csDepartment() {
        dbref = reference.child("Computer Science");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()) {
                    csNodata.setVisibility(View.VISIBLE);
                    csdepartment.setVisibility(View.GONE);
                } else {
                    csNodata.setVisibility(View.GONE);
                    csdepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        LecturerData data = snapshot1.getValue(LecturerData.class);
                        list1.add(data);
                    }
                    csdepartment.setHasFixedSize(true);
                    csdepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new FacultyAdapter(list1, getContext(), "Computer Science");
                    csdepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}