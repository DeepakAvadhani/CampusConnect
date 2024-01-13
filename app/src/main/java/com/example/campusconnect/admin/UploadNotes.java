package com.example.campusconnect.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class UploadNotes extends AppCompatActivity {
    private CardView selectPdf;
    private TextView textView;
    private final int REQ = 1;
    private EditText editText;
    private Uri pdfData;
    private Button button;
    private String Course,coursesem;
    private Spinner spinner,spinner2;
    private DatabaseReference reference,dbref;
    private StorageReference storageReference;
    String downloadUrl = "";
    private String pdfName, title;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);
        getSupportActionBar().setTitle("Upload Notes");
        reference = FirebaseDatabase.getInstance().getReference().child("Notes");
        storageReference = FirebaseStorage.getInstance().getReference().child("Notes");
        selectPdf = findViewById(R.id.selectPdf);
        textView = findViewById(R.id.viewpdftitle);
        editText = findViewById(R.id.pdftitle);
        button = findViewById(R.id.uploadpdfbtn);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        String[] course = new String[]{"BCA", "BBA"};
        String[]  SEM = new String[]{"1st SEM","2nd SEM","3rd SEM","4th SEM","5th SEM","6th SEM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinnerdropdown, course);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.spinnercourse);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Course = autoCompleteTextView.getText().toString();
            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinnerdropdown, SEM);
        AutoCompleteTextView autoCompleteTextView1 = findViewById(R.id.spinnersemester);
        autoCompleteTextView1.setAdapter(adapter1);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                coursesem = autoCompleteTextView1.getText().toString();
            }
        });
        selectPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallary();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editText.getText().toString();
                if (title.isEmpty()) {
                    editText.setError("Empty");
                    editText.requestFocus();
                } else if (pdfData == null) {
                    Toast.makeText(getBaseContext(), "Please Select Pdf", Toast.LENGTH_SHORT).show();
                }else{
                    uploadpdf();
                }
                editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
    }

    private void uploadpdf() {
        pd.setTitle("Please wait...");
        pd.setMessage("Uploading...");
        pd.show();
        storageReference = storageReference.child(Course + pdfName + "-" + System.currentTimeMillis() + ".pdf");
        //"pdf/"
        storageReference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotes.this, "Oops,Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData(String downloadUrl) {
        dbref = reference.child(Course);
        final String uniquekey = reference.push().getKey();
        HashMap data = new HashMap();
        data.put("pdfTitle", title);
        data.put("pdfUrl", downloadUrl);

        dbref.child(coursesem).child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            //we can add another class using dbref.child(Class).child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadNotes.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                editText.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotes.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallary() {
        Intent intent = new Intent();
        intent.setType("application/pdf/docs/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQ);
    }
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            pdfData = data.getData();
            if (pdfData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadNotes.this.getContentResolver().query(pdfData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (pdfData.toString().startsWith("file://")) {
                pdfName = new File(pdfData.toString()).getName();
            }
            textView.setText(pdfName);
        }
    }
}