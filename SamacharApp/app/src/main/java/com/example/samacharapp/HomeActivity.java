package com.example.samacharapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener
{

    public FirebaseAuth auth;
    public FirebaseUser user;
    public ImageView userprofile;
    public TextView username,useremail;
    public FragmentManager frageMentManager;
    public FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*floating action button*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Refrence variable*/
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        userprofile=navigationView.getHeaderView(0).findViewById(R.id.userprofile);
        username=navigationView.getHeaderView(0).findViewById(R.id.username);
        useremail=navigationView.getHeaderView(0).findViewById(R.id.useremail);

        if (user.getPhotoUrl()!=null)
        {
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.news_icon).error(R.drawable.news_icon).into(userprofile);
        }
        else
        {
            Picasso.get().load(R.mipmap.ic_launcher);
        }

        username.setText(user.getDisplayName().isEmpty()?"Add you name":user.getDisplayName());
        useremail.setText(user.getEmail());


        /*Fragment*/
        frageMentManager=getSupportFragmentManager();
        transaction=frageMentManager.beginTransaction();
        transaction.add(R.id.rootFrame,new HomeFragment());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
      /*  else if(id == R.id.SignOut)
        {
            auth.signOut();
            startActivity(new Intent(this,SignIn.class));
        }
*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment=null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_profile) {
            fragment=new ProfileFragment();
        } else if (id == R.id.nav_changepass) {
//            fragment = new PasswordFragment();
        } else if (id == R.id.nav_logOut) {

        }
        if (fragment!=null)
        {
            frageMentManager=getSupportFragmentManager();
            transaction=frageMentManager.beginTransaction();
            transaction.replace(R.id.rootFrame,fragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
