package com.example.firebasedb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public EditText emailbox;
    public TextInputEditText passBox;
    public Button signInBtn,openSignUp,forgotBtn;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*refrnece of firebase auth*/
        auth=FirebaseAuth.getInstance();


        emailbox=findViewById(R.id.emailBox);
        passBox=findViewById(R.id.passbox);
        signInBtn=findViewById(R.id.loginBtn);
        openSignUp=findViewById(R.id.openSignUp);
        forgotBtn=findViewById(R.id.forgotPassword);


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= emailbox.getText().toString();
                String password = passBox.getText().toString();
                if (email.isEmpty())
                {
                    emailbox.setError("Emter email");
                    emailbox.requestFocus();
                }
                else
                {
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("validating .....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        /*when we click on create account*/
        openSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });



        /*code to recover password*/
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailbox.getText().toString();
                if (email.isEmpty())
                {
                    emailbox.setError("Enter email first");
                    emailbox.requestFocus();
                }
                else
                {
                    /*send email to reset*/
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("sending password reset email");
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, "email sended", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }


}


