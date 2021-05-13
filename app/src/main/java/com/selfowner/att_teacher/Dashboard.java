package com.selfowner.att_teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbreference;
    Button  LogOut,selfInfo,autoAttend,manualAttend,viewAttend,uploadNotice;
    TextView userName,userDepartment;
    String emailid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("Teacher Dashboard");
        Toast.makeText(this, "Wait To Retrieve The Respected Teacher Name", Toast.LENGTH_LONG).show();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        emailid=user.getEmail();
        emailid=emailid.replace(".","");
        LOAD();
        userName=findViewById(R.id.userName);
        userDepartment=findViewById(R.id.userDepartment);
        LogOut=findViewById(R.id.LogOut);
        selfInfo=findViewById(R.id.selfInfo);
        autoAttend=findViewById(R.id.autoAttend);
        manualAttend=findViewById(R.id.manualAttend);
        viewAttend=findViewById(R.id.viewAttend);
        uploadNotice=findViewById(R.id.uploadNotice);
        selfInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(Dashboard.this,Personal_Info.class);
                intent.putExtra("Email",emailid);
                startActivity( intent);
            }
        });
        autoAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(Dashboard.this,Auto_attend.class);
                intent.putExtra("Email",emailid);
                startActivity( intent);
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
            }
        });
    }
    private void LOAD(){
        dbreference= FirebaseDatabase.getInstance().getReference("TEACHERS");
        dbreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(emailid)) {
                    String value=dataSnapshot.child(emailid).child("teacherName").getValue().toString();
                    userName.setText(value);
                    String value1=dataSnapshot.child(emailid).child("teacherDept").getValue().toString();
                    userDepartment.setText(value1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
