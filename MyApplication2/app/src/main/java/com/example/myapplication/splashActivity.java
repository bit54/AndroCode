package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class splashActivity extends AppCompatActivity implements View.OnClickListener,Runnable {


    public Button startButton;
    public ActionBar aBar;
    public Handler handler;
    public TextView splashLG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //hide action bar
        aBar=getSupportActionBar();
        aBar.hide();

        splashLG=findViewById(R.id.splashLogo);

        //start button
        startButton=findViewById(R.id.startbtn);
        startButton.setOnClickListener(this);

        //handler
        handler=new Handler();
        handler.postDelayed(this,5000);

        //binding animation.
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.rt_splash);
        splashLG.startAnimation(animation);

    }


    //on pressing back button sign in screen will not automatically start

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Back button pressed", Toast.LENGTH_SHORT).show();
        handler.removeCallbacks(this);
    }

    @Override
    public void onClick(View v) {
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void run() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}

