package com.example.firebasedb;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity implements Runnable{

    public FirebaseUser user;
    public FirebaseAuth auth;
    public Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*firebase authincation refrences*/
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();



        /*handler refrneces*/
        handler = new Handler();
        handler.postDelayed(this,4000);
    }

    @Override
    public void run() {

        if (user ==null)
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else
        {
            startActivity(new Intent(this,HomeActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(this);
    }
}
