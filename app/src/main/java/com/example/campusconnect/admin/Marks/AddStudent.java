package com.example.campusconnect.admin.Marks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campusconnect.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddStudent extends AppCompatActivity {
    private EditText getname, getregno, Submarks1, Submarks2, Submarks3, Submarks4, Submarks5, Submarks6, Submarks7;
    AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2;
    private Button button;
    private String sub1 = "0", sub2 = "0", sub3 = "0", sub4 = "0", sub5 = "0", sub6 = "0", sub7 = "0";
    private String studcourse,studsem,perc;
    private String Course, Sem, name, regno, course;
    private TextView textView;
    DatabaseReference reference, dbref;
    LinearLayout linearLayout;
    private List<HelperClass> list1;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setTitle("Add Student");
        textView = findViewById(R.id.subtextview);
        Submarks1 = findViewById(R.id.sub1);
        Submarks2 = findViewById(R.id.sub2);
        Submarks3 = findViewById(R.id.sub3);
        Submarks4 = findViewById(R.id.sub4);
        Submarks5 = findViewById(R.id.sub5);
        Submarks6 = findViewById(R.id.sub6);
        Submarks7 = findViewById(R.id.sub7);
        getname = findViewById(R.id.studname);
        getregno = findViewById(R.id.studregno);
        button = findViewById(R.id.uploadmarksbtn);
        linearLayout = findViewById(R.id.subjectlinearlayout);
        autoCompleteTextView1 = findViewById(R.id.studcourse);
        autoCompleteTextView2 = findViewById(R.id.studsem);
        String[] course = new String[]{"Select Course", "BCA", "BBA", "BCOM", "BA"};
        String[] SEM = new String[]{"Select Semester", "1st SEM", "2nd SEM", "3rd SEM", "4th SEM", "5th SEM", "6th SEM"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinnerdropdown, course);
        autoCompleteTextView1.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinnerdropdown, SEM);
        autoCompleteTextView2.setAdapter(adapter2);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                studcourse = autoCompleteTextView1.getText().toString();
                studsem = autoCompleteTextView2.getText().toString();
                if (studcourse.equals("BCA") && studsem.equals("1st SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Kannada Marks");
                    Submarks2.setHint("Enter Mathematics-1 Marks");
                    Submarks3.setHint("Enter Accounting 1 Marks");
                    Submarks4.setHint("Enter Basic Electricals Marks");
                    Submarks5.setHint("Enter Computer Concepts Marks");
                    Submarks6.setHint("Enter Indian Constitution Marks");
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BCA") && studsem.equals("2nd SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Mathematics-2 marks");
                    Submarks2.setHint("Enter Accounting-2 marks");
                    Submarks3.setHint("Enter NSM marks");
                    Submarks4.setHint("Enter Data Structures marks");
                    Submarks5.setHint("Enter Human Right marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);

                } else if (studcourse.equals("BCA") && studsem.equals("3rd SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter COA Marks");
                    Submarks2.setHint("Enter OOPS Cpp Marks");
                    Submarks3.setHint("Enter DMS Marks");
                    Submarks4.setHint("Enter Visual Programming Marks");
                    Submarks5.setHint("Enter Pesonality Development Marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);

                } else if (studcourse.equals("BCA") && studsem.equals("4th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter DAA Marks");
                    Submarks2.setHint("Enter System Programming Marks");
                    Submarks3.setHint("Enter Data Communications Marks");
                    Submarks4.setHint("Enter Microprocessor Marks");
                    Submarks5.setHint("Enter SAD Marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);

                } else if (studcourse.equals("BCA") && studsem.equals("5th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Operating System Marks");
                    Submarks2.setHint("Enter Internet Programming Marks");
                    Submarks3.setHint("Enter Operational Research Marks");
                    Submarks4.setHint("Enter DBMS Marks");
                    Submarks5.setHint("Enter System Engineering Marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BCA") && studsem.equals("6th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter OOSD Marks");
                    Submarks2.setHint("Enter Unix Marks");
                    Submarks3.setHint("Enter Computer Graphics Marks");
                    Submarks4.setHint("Enter E-Commerce Marks");
                    Submarks5.setHint("Enter Computer Networks Marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);

                } else if (studcourse.equals("BBA") && studsem.equals("1st SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Financial Accounting Marks");
                    Submarks2.setHint("Enter Economics Marks");
                    Submarks3.setHint("Enter POM Marks");
                    Submarks4.setHint("Enter Principls of Market Marks");
                    Submarks5.setHint("Enter Business Commerce Marks");
                    Submarks6.setHint("Enter Modern Indian Language Marks");
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BBA") && studsem.equals("2nd SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter FA-2 Marks");
                    Submarks2.setHint("Enter Economics-2 Marks");
                    Submarks3.setHint("Enter Principles of Management-2 Marks");
                    Submarks4.setHint("Enter Principles of Market-2 Marks");
                    Submarks5.setHint("Enter Indian Business Evolution Marks");
                    Submarks6.setHint("Enter CALSP Marks");
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BBA") && studsem.equals("3rd SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Business Stats Marks");
                    Submarks2.setHint("Enter CO-operative Accounting Marks");
                    Submarks3.setHint("Enter Computer Application Marks");
                    Submarks4.setHint("Enter Small Business Management Marks");
                    Submarks5.setHint("Enter Marketing Research Marks");
                    Submarks6.setHint("Enter Costing Fundamentals Marks");
                    Submarks7.setHint("Enter Indian Constitution Marks");
                } else if (studcourse.equals("BBA") && studsem.equals("4th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Business Maths Marks");
                    Submarks2.setHint("Enter Co-operate Accounting-2 Marks");
                    Submarks3.setHint("Enter Computer Application 2 Marks");
                    Submarks4.setHint("Enter Enterprenurship Development Marks");
                    Submarks5.setHint("Enter HR Management Marks");
                    Submarks6.setHint("Enter Insurance Management Marks");
                    Submarks7.setHint("Enter Management Accounting Marks");
                } else if (studcourse.equals("BBA") && studsem.equals("5th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Retial Management Marks");
                    Submarks2.setHint("Enter Sales Management Marks");
                    Submarks3.setHint("Enter Project Management Marks");
                    Submarks4.setHint("Enter Financial Services Marks");
                    Submarks5.setHint("Enter Leadership Styles Marks");
                    Submarks6.setHint("Enter Performance Appraisal Marks");
                    Submarks7.setHint("Enter E-Commerce Marks");
                } else if (studcourse.equals("BBA") && studsem.equals("6th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Stock exchange operation Marks");
                    Submarks2.setHint("Enter Forex Management Marks");
                    Submarks3.setHint("Enter Consumer Behaviour Marks");
                    Submarks4.setHint("Enter Adverticing Management Marks");
                    Submarks5.setHint("Enter Industrial Management Marks");
                    Submarks6.setHint("Enter Income Tax Marks");
                    Submarks7.setHint("Enter Trailing and Behaviour Marks");
                }
            }
        });
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                studcourse = autoCompleteTextView1.getText().toString();
                studsem = autoCompleteTextView2.getText().toString();
                linearLayout.setVisibility(View.VISIBLE);
                if (studcourse.equals("BCA") && studsem.equals("1st SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Kannada Marks");
                    Submarks2.setHint("Enter Mathematics-1 Marks");
                    Submarks3.setHint("Enter Accounting 1 Marks");
                    Submarks4.setHint("Enter Basic Electricals Marks");
                    Submarks5.setHint("Enter Computer Concepts Marks");
                    Submarks6.setHint("Enter Indian Constitution Marks");
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BCA") && studsem.equals("2nd SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Mathematics-2 marks");
                    Submarks2.setHint("Enter Accounting-2 marks");
                    Submarks3.setHint("Enter NSM marks");
                    Submarks4.setHint("Enter Data Structures marks");
                    Submarks5.setHint("Enter Human Right marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BCA") && studsem.equals("3rd SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter COA Marks");
                    Submarks2.setHint("Enter OOPS Cpp Marks");
                    Submarks3.setHint("Enter DMS Marks");
                    Submarks4.setHint("Enter Visual Programming Marks");
                    Submarks5.setHint("Enter Pesonality Development Marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BCA") && studsem.equals("4th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter DAA Marks");
                    Submarks2.setHint("Enter System Programming Marks");
                    Submarks3.setHint("Enter Data Communications Marks");
                    Submarks4.setHint("Enter Microprocessor Marks");
                    Submarks5.setHint("Enter SAD Marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BCA") && studsem.equals("5th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Operating System Marks");
                    Submarks2.setHint("Enter Internet Programming Marks");
                    Submarks3.setHint("Enter Operational Research Marks");
                    Submarks4.setHint("Enter DBMS Marks");
                    Submarks5.setHint("Enter System Engineering Marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BCA") && studsem.equals("6th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter OOSD Marks");
                    Submarks2.setHint("Enter Unix Marks");
                    Submarks3.setHint("Enter Computer Graphics Marks");
                    Submarks4.setHint("Enter E-Commerce Marks");
                    Submarks5.setHint("Enter Computer Networks Marks");
                    Submarks6.setVisibility(View.GONE);
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BBA") && studsem.equals("1st SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Financial Accounting Marks");
                    Submarks2.setHint("Enter Economics Marks");
                    Submarks3.setHint("Enter POM Marks");
                    Submarks4.setHint("Enter Principls of Market Marks");
                    Submarks5.setHint("Enter Business Commerce Marks");
                    Submarks6.setHint("Enter Modern Indian Language Marks");
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BBA") && studsem.equals("2nd SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter FA-2 Marks");
                    Submarks2.setHint("Enter Economics-2 Marks");
                    Submarks3.setHint("Enter Principles of Management-2 Marks");
                    Submarks4.setHint("Enter Principles of Market-2 Marks");
                    Submarks5.setHint("Enter Indian Business Evolution Marks");
                    Submarks6.setHint("Enter CALSP Marks");
                    Submarks7.setVisibility(View.GONE);
                } else if (studcourse.equals("BBA") && studsem.equals("3rd SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Business Stats Marks");
                    Submarks2.setHint("Enter Co-operative Accounting Marks");
                    Submarks3.setHint("Enter Computer Application Marks");
                    Submarks4.setHint("Enter Small Business Management Marks");
                    Submarks5.setHint("Enter Marketing Research Marks");
                    Submarks6.setHint("Enter Costing Fundamentals Marks");
                    Submarks7.setHint("Enter Indian Constitution Marks");
                } else if (studcourse.equals("BBA") && studsem.equals("4th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Business Maths Marks");
                    Submarks2.setHint("Enter Co-operate Accounting-2 Marks");
                    Submarks3.setHint("Enter Computer Application 2 Marks");
                    Submarks4.setHint("Enter Enterprenurship Development Marks");
                    Submarks5.setHint("Enter HR Management Marks");
                    Submarks6.setHint("Enter Insurance Management Marks");
                    Submarks7.setHint("Enter Management Accounting Marks");
                } else if (studcourse.equals("BBA") && studsem.equals("5th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Retial Management Marks");
                    Submarks2.setHint("Enter Sales Management Marks");
                    Submarks3.setHint("Enter Project Management Marks");
                    Submarks4.setHint("Enter Financial Services Marks");
                    Submarks5.setHint("Enter Leadership Styles Marks");
                    Submarks6.setHint("Enter Performance Appraisal Marks");
                    Submarks7.setHint("Enter E-Commerce Marks");
                } else if (studcourse.equals("BBA") && studsem.equals("6th SEM")) {
                    textView.setText(studcourse + " " + studsem);
                    Submarks1.setHint("Enter Stock exchange operation Marks");
                    Submarks2.setHint("Enter Forex Management Marks");
                    Submarks3.setHint("Enter Consumer Behaviour Marks");
                    Submarks4.setHint("Enter Adverticing Management Marks");
                    Submarks5.setHint("Enter Industrial Management Marks");
                    Submarks6.setHint("Enter Income Tax Marks");
                    Submarks7.setHint("Enter Trailing and Behaviour Marks");
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(Submarks7.getWindowToken(), 0);
                name = getname.getText().toString();
                regno = getregno.getText().toString();
                sub1 = Submarks1.getText().toString();
                sub2 = Submarks2.getText().toString();
                sub3 = Submarks3.getText().toString();
                sub4 = Submarks4.getText().toString();
                sub5 = Submarks5.getText().toString();
                sub6 = Submarks6.getText().toString();
                sub7 = Submarks7.getText().toString();
                if (name.isEmpty()) {
                    getname.setError("Empty");
                    getname.requestFocus();
                } else if (regno.isEmpty()) {
                    getregno.setError("Empty");
                    getregno.requestFocus();
                } else if (studcourse.equals("Select Course")) {
                    autoCompleteTextView1.requestFocus();
                    Toast.makeText(AddStudent.this, "Please Select Course", Toast.LENGTH_SHORT).show();
                } else if (studsem.equals("Select Semester")) {
                    autoCompleteTextView2.requestFocus();
                    Toast.makeText(AddStudent.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
                } else if (sub1.isEmpty()||sub2.isEmpty()||sub3.isEmpty()||sub4.isEmpty()||sub5.isEmpty()) {
                    Toast.makeText(AddStudent.this, "Please Enter Marks", Toast.LENGTH_SHORT).show();
                }  else {
                    uploadinfo();
                }
            }
        });
    }

    private void uploadinfo() {
        name = getname.getText().toString();
        regno = getregno.getText().toString();
        sub1 = Submarks1.getText().toString();
        sub2 = Submarks2.getText().toString();
        sub3 = Submarks3.getText().toString();
        sub4 = Submarks4.getText().toString();
        sub5 = Submarks5.getText().toString();
        sub6 = Submarks6.getText().toString();
        Course = autoCompleteTextView1.getText().toString();
        Sem = autoCompleteTextView2.getText().toString();
        if(sub7.isEmpty()){
            Submarks7.setText("0");
            sub7=Submarks7.getText().toString();
        }else{
            sub7 = Submarks7.getText().toString();
        }
        pd.setMessage("Uploading...");
        pd.show();
        HelperClass helperClass = new HelperClass(Sem, Course, sub1, sub2, sub3, sub4, sub5, sub6,sub7);
        dbref = reference.child("Marks");
        dbref.child(regno).setValue(helperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddStudent.this, "Upload Successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddStudent.this, "Oops...Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}