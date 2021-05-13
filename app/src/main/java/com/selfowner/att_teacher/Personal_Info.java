package com.selfowner.att_teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Personal_Info extends AppCompatActivity {
    DatabaseReference dbref;
    TextView teacherCollege,teacherDept,teacherName,teacherEmail,teacherPassword;
    Button  close;
    ProgressBar pbar;
    String emailid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__info);
        setTitle("Teacher Information");
        emailid = getIntent().getExtras().getString("Email");
        pbar=findViewById(R.id.pBar);
        close=findViewById(R.id.exitWindow);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Personal_Info.this,Dashboard.class));
            }
        });
        teacherCollege=findViewById(R.id.teacherCollege);
        teacherDept=findViewById(R.id.teacherDept);
        teacherName=findViewById(R.id.teacherName);
        teacherEmail=findViewById(R.id.teacherEmail);
        teacherPassword=findViewById(R.id.teacherPassword);
        LOAD();
    }
    private void LOAD(){
        dbref= FirebaseDatabase.getInstance().getReference("TEACHERS");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(emailid)) {
                    String name=dataSnapshot.child(emailid).child("teacherName").getValue().toString();
                    String college=dataSnapshot.child(emailid).child("teacherCollege").getValue().toString();
                    String email=dataSnapshot.child(emailid).child("teacherEmail").getValue().toString();
                    String dept=dataSnapshot.child(emailid).child("teacherDept").getValue().toString();
                    String pass=dataSnapshot.child(emailid).child("teacherPassword").getValue().toString();
                    loadMap(name,college,email,dept,pass);
                    pbar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void loadMap(String name,String college,String email,String dept,String pass){
        teacherName.setText("NAME:"+name);
        teacherCollege.setText("COLLEGE:"+college);
        teacherEmail.setText("EMAIL_ID:"+email);
        teacherDept.setText("DEPARTMENT:"+dept);
        teacherPassword.setText("PASSWORD:"+pass);
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(Personal_Info.this,Dashboard.class));
        finish();
    }
}
