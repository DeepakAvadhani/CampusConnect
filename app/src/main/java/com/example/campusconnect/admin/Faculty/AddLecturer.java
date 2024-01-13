package com.example.campusconnect.admin.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddLecturer extends AppCompatActivity {
    private ImageView lecturerimageView;
    private EditText LecturerName;
    private EditText LecturerEmail;
    private EditText Lecturerpost;
    private TextView clickhere;
    private AutoCompleteTextView autoCompleteTextView;
    private Button Lecturerbtn;
    private final int REQ  = 1;
    private Bitmap bitmap = null;
    private String icategory;
    private String name,email,post,downloadUrl="";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference,dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecturer);
        clickhere = findViewById(R.id.clickhere);
        lecturerimageView = findViewById(R.id.addlecturerprofileimg);
        LecturerName = findViewById(R.id.lecturername);
        LecturerEmail = findViewById(R.id.lectureremail);
        Lecturerpost = findViewById(R.id.lecturerpost);
        Lecturerbtn = findViewById(R.id.addlecturerbtn);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference = FirebaseStorage.getInstance().getReference();
        String[] items = new String[]{"Select Category", "Computer Science", "DBMS", "Language"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinnerdropdown,items);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.spinnerpost);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                icategory = autoCompleteTextView.getText().toString();
            }
        });
        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openGallery();
            }
        });
        Lecturerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkvalidation();
            }
        });
    }

    private void checkvalidation() {
        name = LecturerName.getText().toString();
        email = LecturerEmail.getText().toString();
        post = Lecturerpost.getText().toString();
        if (name.isEmpty()) {
            LecturerName.setError("Empty");
            LecturerName.requestFocus();
        } else if (email.isEmpty()) {
            LecturerEmail.setError("Empty");
            LecturerEmail.requestFocus();
        } else if (post.isEmpty()) {
            Lecturerpost.setError("Empty");
            Lecturerpost.requestFocus();
        } else if (icategory.equals("Select Category")) {
            Toast.makeText(AddLecturer.this, "Please Select Lecturer Category", Toast.LENGTH_SHORT).show();
        } else if (bitmap == null) {
            pd.setMessage("Uploading...");
            pd.show();
            insertdata();
        } else {
            pd.setMessage("Uploading...");
            pd.show();
            uploadImage();
        }
        Lecturerpost.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("Faculty").child(finalimg + "jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddLecturer.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    insertdata();
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(AddLecturer.this, "Oops,Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }
    private void insertdata() {
        try {
            dbRef = reference.child(icategory);
            final String uniquekey = dbRef.push().getKey();
            LecturerData lecturerData = new LecturerData(name,email,post,downloadUrl,uniquekey);
            dbRef.child(uniquekey).setValue(lecturerData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    pd.dismiss();
                    Toast.makeText(AddLecturer.this, "Lecturer Added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(AddLecturer.this, "Oops,Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            lecturerimageView.setImageBitmap(bitmap);
        }
    }
}