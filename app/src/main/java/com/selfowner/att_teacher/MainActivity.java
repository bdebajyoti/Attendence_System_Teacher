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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button login,registration;
    EditText emailid,password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Welcome Page");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();
            Intent intent=new Intent(MainActivity.this,Dashboard.class);
            startActivity(intent);
        }
        progressDialog = new ProgressDialog(this);
        login=findViewById(R.id.Login);
        registration=findViewById(R.id.NewReg);
        emailid=findViewById(R.id.emailid);
        password=findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGIN();
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            REGIS();
            }
        });
    }

    private void LOGIN(){
    String email=emailid.getText().toString();
    String pass=password.getText().toString();
            String semail=emailid.getText().toString();
            String spassword=password.getText().toString();
            //checking if email and passwords are empty

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                return;
            }

            //if the email and password are not empty
            //displaying a progress dialog

            progressDialog.setMessage("Login In Account..Please Wait...");
            progressDialog.show();

            //logging in the user
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this,  new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Teacher Not Found Please Register Yourself", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    private void REGIS(){
        finish();
        startActivity(new Intent(MainActivity.this,NewReg.class));
    }
}
