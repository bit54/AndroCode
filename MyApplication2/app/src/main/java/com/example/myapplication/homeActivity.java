package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class homeActivity extends AppCompatActivity {

    public Intent intent;
    public String name,email;
    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //receive data

        intent=getIntent();
        name=intent.getStringExtra("name");
        email=intent.getStringExtra("email");

        /*String info="Name: "+name+" Email: "+email;
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();*/


        /*Action bar*/

        actionBar=getSupportActionBar(); //will return refrence of action bar

        // actionBar.hide() will hide action bar

        actionBar.setTitle(name);
        actionBar.setSubtitle(email);

        actionBar.setDisplayHomeAsUpEnabled(true);  //will enable back button

        //actionBar.setDisplayUseLogoEnabled(true);

        actionBar.setLogo(R.drawable.logo);


    }


    //adding menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();  //will return menuinflator
        menuInflater.inflate(R.menu.primary_menu,menu); //will create menu
        return super.onCreateOptionsMenu(menu);
    }

    //perform operations on clicking on menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuID= item.getItemId();

        if (menuID == R.id.showProfile)
        {
            Toast.makeText(this, "show profile", Toast.LENGTH_SHORT).show();
        }

        else if (menuID==R.id.changePass)
        {
            Toast.makeText(this, "change Password", Toast.LENGTH_SHORT).show();
        }

        else if (menuID == R.id.signOut)
        {
            Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
