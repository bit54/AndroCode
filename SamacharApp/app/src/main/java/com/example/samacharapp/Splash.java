package com.example.samacharapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity  implements Runnable{

    public Handler handler;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        auth= FirebaseAuth.getInstance();


        handler = new Handler();
        handler.postDelayed(this,3000);
    }

    @Override
    public void run() {
    if (auth.getCurrentUser()!=null)
    {
        startActivity(new Intent(this,SignUp.class));
        finish();
    }
    else
    {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    }
}
