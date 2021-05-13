package com.selfowner.att_teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Auto_attend extends AppCompatActivity {
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> namelist;
    private String emailid,dept,getdate;
    DatabaseReference dbref,databaseReference;
    private String randomnum,teachername,collegename;
    TextView textView1;
    Button randomBtn,exit,loadSubject;
    Spinner semester,courseType,subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_attend);
        setTitle("Automated Attendance Section");
        emailid = getIntent().getExtras().getString("Email");
        loadSubject=findViewById(R.id.loadSubject);
        semester=findViewById(R.id.semester);
        courseType=findViewById(R.id.courseType);
        subject=findViewById(R.id.subject);
        randomBtn=findViewById(R.id.randomNumber);
        textView1=findViewById(R.id.textView1);
        exit=findViewById(R.id.exit);
        GETDATE();
        LOAD();
        loadSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOADSUBJECT();
            }
        });
        randomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RANDOMNUMBER();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Auto_attend.this,Dashboard.class));
            }
        });
    }
private void LOADSUBJECT(){
    namelist=new ArrayList<>();
    adapter=new ArrayAdapter<String>(Auto_attend.this,android.R.layout.simple_spinner_dropdown_item,namelist);
    subject.setAdapter(adapter);
        if(!semester.getSelectedItem().toString().equalsIgnoreCase("SELECT SEMESTER") && !courseType.getSelectedItem().toString().equalsIgnoreCase("SELECT COURSE TYPE")) {
            databaseReference = FirebaseDatabase.getInstance().getReference(collegename).child(dept).child(courseType.getSelectedItem().toString()).child(semester.getSelectedItem().toString());
            listener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        namelist.add(item.child("subject").getValue().toString());
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
}
    private void RANDOMNUMBER() {
        long time= System.currentTimeMillis();
        time=time+300000;
        Random rand = new Random();
        randomnum=""+rand.nextInt(999)+"-"+getdate;
        textView1.setText(""+randomnum);
       //dbref= FirebaseDatabase.getInstance().getReference("ATTENDENCE").child(getdate).child(dept).child(subject.getSelectedItem().toString()).child(teachername);
        dbref= FirebaseDatabase.getInstance().getReference("ATTENDENCE").child(getdate).child(dept).child(textView1.getText().toString());
       dbref.child("RANDOM NUMBER").setValue(textView1.getText().toString());
       dbref.child("INITIAL_TIME").setValue(time);
       dbref.child("SUBJECT").setValue(subject.getSelectedItem().toString());
    }

    private void LOAD(){
        dbref= FirebaseDatabase.getInstance().getReference("TEACHERS");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(emailid)) {
                    collegename=dataSnapshot.child(emailid).child("teacherCollege").getValue().toString();
                    dept=dataSnapshot.child(emailid).child("teacherDept").getValue().toString();
                    teachername=dataSnapshot.child(emailid).child("teacherName").getValue().toString();
                    Toast.makeText(Auto_attend.this, "College:"+collegename+" Dept:"+dept+" Name:"+teachername, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void GETDATE(){

            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
            Date todayDate = new Date();
            getdate =""+currentDate.format(todayDate);
           // Toast.makeText(Auto_attend.this, "Today Is "+getdate, Toast.LENGTH_SHORT).show();
    }
  /*  @Override
    public void onBackPressed() {

        startActivity(new Intent(Auto_attend.this,Dashboard.class));
        finish();
    }*/
}
