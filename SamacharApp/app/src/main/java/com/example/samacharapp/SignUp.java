package com.example.samacharapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    public Button openSignUp,openSignIn;
    public EditText passBox, emailBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        openSignIn=findViewById(R.id.openSignIn);
        openSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.openSignUp)
        {
            String email = emailBox.getText().toString();
            String password = passBox.getText().toString();
            if (email.isEmpty())
            {
                emailBox.setError("Empty email");
                emailBox.requestFocus();
            }
            else if (password.isEmpty())
            {
                passBox.setError("Empty password");
                passBox.requestFocus();
            }
            else {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Creating account .....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(SignUp.this,MainActivity.class));
                        }
                        else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        if (viewId == R.id.openSignIn)
        {
            startActivity(new Intent(this,SignIn.class));
        }
    }
}

