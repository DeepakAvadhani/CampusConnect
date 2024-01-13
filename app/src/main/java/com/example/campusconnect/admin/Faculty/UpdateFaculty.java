package com.example.campusconnect.admin.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView csdepartment, dbmsdepartment, langdepartment;
    private LinearLayout csNodata, dbmsNodata, langNodata;
    private List<LecturerData> list1, list2, list3;
    private LecturerAdapter adapter;
    private DatabaseReference reference, dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
        getSupportActionBar().setTitle("Update Faculty");
        fab = findViewById(R.id.flab);
        csdepartment = findViewById(R.id.csdepartment);
        dbmsdepartment = findViewById(R.id.dbmsdepartment);
        langdepartment = findViewById(R.id.langdepartment);

        csNodata = findViewById(R.id.csnodata);
        dbmsNodata = findViewById(R.id.dbmsnodata);
        langNodata = findViewById(R.id.langnodata);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        csDepartment();
        dbmsDepartment();
        langDepartment();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFaculty.this, AddLecturer.class));
            }
        });
    }

    private void langDepartment() {
        dbref = reference.child("Language");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if (!snapshot.exists()){
                    langNodata.setVisibility(View.VISIBLE);
                    langdepartment.setVisibility(View.GONE);//if data doesnot exists no data will be shown
                }else{
                    langNodata.setVisibility(View.GONE);
                    langdepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        LecturerData data = snapshot1.getValue(LecturerData.class);
                        list3.add(data);
                    }
                    langdepartment.setHasFixedSize(true);
                    langdepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new LecturerAdapter(list3,UpdateFaculty.this,"Language");
                    langdepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dbmsDepartment() {
        dbref = reference.child("DBMS");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2=new ArrayList<>();
                if (!snapshot.exists()){
                    dbmsNodata.setVisibility(View.VISIBLE);
                    dbmsdepartment.setVisibility(View.GONE);//if data doesnot exists no data will be shown
                }else{
                    dbmsNodata.setVisibility(View.GONE);
                    dbmsdepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        LecturerData data = snapshot1.getValue(LecturerData.class);
                        list2.add(data);
                    }
                    dbmsdepartment.setHasFixedSize(true);
                    dbmsdepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new LecturerAdapter(list2,UpdateFaculty.this,"DBMS");
                    dbmsdepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void csDepartment() {
        dbref = reference.child("Computer Science");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if(!snapshot.exists()){
                    csNodata.setVisibility(View.VISIBLE);
                    csdepartment.setVisibility(View.GONE);
                }else{
                    csNodata.setVisibility(View.GONE);
                    csdepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        LecturerData data = snapshot1.getValue(LecturerData.class);
                        list1.add(data);
                    }
                    csdepartment.setHasFixedSize(true);
                    csdepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new LecturerAdapter(list1,UpdateFaculty.this,"Computer Science");
                    csdepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}