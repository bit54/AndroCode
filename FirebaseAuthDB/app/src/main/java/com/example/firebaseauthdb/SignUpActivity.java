package com.example.firebaseauthdb;

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

public class SignUpActivity extends AppCompatActivity {

    public FirebaseAuth auth;
    public EditText emaibox;
    public TextInputEditText passBox;
    public Button signupBtn,openLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth=FirebaseAuth.getInstance();

        emaibox=findViewById(R.id.emailBox);
        passBox=findViewById(R.id.passbox);
        signupBtn=findViewById(R.id.signupBtn);
        openLogin=findViewById(R.id.openSignIn);



        openLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emaibox.getText().toString();
                String password = passBox.getText().toString();
                if (email.isEmpty())
                {
                    emaibox.setError("Enter email");
                    emaibox.requestFocus();
                }
                else
                {
                    final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                    progressDialog.setMessage("signing up...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful())
                            {
                                startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }
}
