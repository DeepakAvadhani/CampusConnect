package com.example.campusconnect.admin.Marks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campusconnect.R;
import com.example.campusconnect.admin.Faculty.UpdateFaculty;
import com.example.campusconnect.admin.Faculty.UpdateFacultyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteStudent extends AppCompatActivity {
    private DatabaseReference reference, dbref;
    private TextView textView;
    String regno;
    private Button button;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);
        getSupportActionBar().setTitle("Delete Student");
        reference = FirebaseDatabase.getInstance().getReference().child("Marks");
        textView = findViewById(R.id.deletestudregno);

        button = findViewById(R.id.deletestudbtn);
        pd = new ProgressDialog(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regno=textView.getText().toString();
        if(regno.isEmpty()){
            textView.requestFocus();
            textView.setError("Please Enter Register Number");
        }else{
            deletedata();
        }
            }
        });
    }

    private void deletedata() {
       dbref = reference.child(regno);
       dbref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(!snapshot.exists()){
                   Toast.makeText(DeleteStudent.this, "Requested Data does not exist! Please check Register no. entered!", Toast.LENGTH_SHORT).show();
               }else{
                   dbref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           pd.dismiss();
                           Toast.makeText(DeleteStudent.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(DeleteStudent.this, ManageMarks.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(DeleteStudent.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(DeleteStudent.this, error.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }
}