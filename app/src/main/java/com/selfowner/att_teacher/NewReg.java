package com.selfowner.att_teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewReg extends AppCompatActivity {
    Spinner SelectCollege,SelectDept;
    EditText TeacherName,TeacherEmail,Password1,Password2;
    Button Regme,ViewRules;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reg);
        setTitle("Teacher Self Registration");
        SelectDept=findViewById(R.id.SelectDept);
        SelectCollege=findViewById(R.id.SelectCollege);
        TeacherName=findViewById(R.id.TeacherName);
        TeacherEmail=findViewById(R.id.TeacherEmail);
        Password1=findViewById(R.id.Password1);
        Password2=findViewById(R.id.Password2);
        Regme=findViewById(R.id.RegMe);
        ViewRules=findViewById(R.id.ViewRule);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        ViewRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewRule();
            }
        });
        Regme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }
    private void Register() {
       final String tname = TeacherName.getText().toString();
        final String temail = TeacherEmail.getText().toString();
        final String pass1 = Password1.getText().toString();
        final String pass2 = Password2.getText().toString();
        final String college=SelectCollege.getSelectedItem().toString();
        final String dept=SelectDept.getSelectedItem().toString();
        final String emailid=temail.replace(".","");
        if (!TextUtils.isEmpty(tname) && !TextUtils.isEmpty(temail) && !TextUtils.isEmpty(pass1) && !TextUtils.isEmpty(pass2) && !college.equals("SELECT COLLEGE") && !dept.equals("SELECT DEPARTMENT")) {
            if (pass1.equals(pass2)) {

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(temail, pass1)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //checking if success
                                if (task.isSuccessful()) {
                                    Toast.makeText(NewReg.this,"Please Wait While The Process Complete Do No Press Back Button",Toast.LENGTH_LONG).show();
                                    firebaseDatabase= FirebaseDatabase.getInstance().getReference().child(""+emailid);
                                    firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChild("DETAILS")) {
                                                Toast.makeText(NewReg.this,"Candidate Already Exist",Toast.LENGTH_LONG).show();
                                            }
                                            else
                                            {
                                                //Toast.makeText(NewReg.this,"Please Wait While The Process Complete Do No Press Back Button",Toast.LENGTH_LONG).show();
                                                firebaseDatabase = FirebaseDatabase.getInstance().getReference("TEACHERS").child(emailid);
                                                Teacher_Upload_Helper_Class stud=new Teacher_Upload_Helper_Class(tname,temail,college,dept,pass1);
                                                firebaseDatabase.setValue(stud);
                                                finish();
                                                Intent intent=new Intent(NewReg.this,MainActivity.class);
                                                intent.putExtra("TNAME",temail);
                                                startActivity(intent);
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                } else {
                                    //display some message here
                                    Toast.makeText(NewReg.this, "Registration Error", Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });


            } else {
                Toast.makeText(this, "Password Mismatched...!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else{
            Toast.makeText(this, "Please Fill The Form Correctly", Toast.LENGTH_LONG).show();
            return;
        }
        }

    private void ViewRule(){

    }
}
