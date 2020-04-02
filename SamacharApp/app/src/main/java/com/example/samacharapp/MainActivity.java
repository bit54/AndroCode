package com.example.samacharapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

 public    FirebaseAuth auth;
 public FirebaseUser user;
 public ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar=getSupportActionBar();
        actionBar.setSubtitle(user.getEmail()); /*Display user's emial*/

 /*       public boolean onCreateOptionMenu(Menu menu)
        {
            getMenuInflater().inflate(R.menu.mainmenu,menu);
            return super.onCreateOptionsMenu();
        }

        public boolean onOptionItemSelected(Menu menu)
        {

        }*/
    }


}
