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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateFacultyActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText name, email, post;
    private Button update, delete;
    private final int REQ = 1;
    private String name1, email1, image1, post1;
    private Bitmap bitmap = null;
    private DatabaseReference reference, dref;
    private StorageReference storageReference;
    private String downloadUrl, category, uniqueKey;
    private ProgressDialog pd;
    private TextView clickhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_facultydetails);
        pd = new ProgressDialog(this);
        name1 = getIntent().getStringExtra("name");
        email1 = getIntent().getStringExtra("email");
        post1 = getIntent().getStringExtra("post");
        image1 = getIntent().getStringExtra("image");
        uniqueKey = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");
        imageView = findViewById(R.id.updatelecturerimage);
        name = findViewById(R.id.updatelecturename);
        email = findViewById(R.id.updatelectureemail);
        post = findViewById(R.id.updatelecturepost);
        update = findViewById(R.id.updatebutton);
        delete = findViewById(R.id.deletebutton);
        clickhere = findViewById(R.id.clickhere);
        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference = FirebaseStorage.getInstance().getReference();
        try {
            Picasso.get().load(image1).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        email.setText(email1);
        name.setText(name1);
        post.setText(post1);
        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallary();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Updating...");
                pd.show();
                name1 = name.getText().toString();
                email1 = email.getText().toString();
                post1 = post.getText().toString();
                checkValidation();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting...");
                pd.show();
                deleteData();
            }
        });
    }

    private void openGallary() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    private void deleteData() {
        reference.child(category).child(uniqueKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UpdateFacultyActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateFacultyActivity.this, UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateFacultyActivity.this, "Oops...Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {
        if (name1.isEmpty()) {
            name.setError("Empty");
            name.requestFocus();
        } else if (post1.isEmpty()) {
            post.setError("Empty");
            post.requestFocus();
        } else if (email1.isEmpty()) {
            email.setError("Empty");
            email.requestFocus();
        } else if (bitmap == null) {
            updatedata(image1);
        } else {
            uploadimage();
        }
    }

    private void uploadimage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("Faculty").child(finalimg + "jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UpdateFacultyActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pd.dismiss();
                                    downloadUrl = String.valueOf(uri);
                                    updatedata(downloadUrl);
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(UpdateFacultyActivity.this, "Oops,Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatedata(String s) {
        HashMap hp = new HashMap();
        hp.put("name", name1);
        hp.put("email", email1);
        hp.put("post", post1);
        hp.put("image", s);

        reference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateFacultyActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateFacultyActivity.this, UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateFacultyActivity.this, "Oops!..Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
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
            imageView.setImageBitmap(bitmap);
        }
    }
}