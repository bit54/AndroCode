package com.example.firebasedb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCaptureSession;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.security.PublicKey;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public FirebaseAuth auth;
    public FirebaseUser user;
    public ImageView userProfile;
    public TextView userName,userEmail;
    public CountryCodePicker countryCode;
    public EditText phoneBox;
    public Button verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*getting refrences*/

        countryCode=findViewById(R.id.countryCode);
        phoneBox=findViewById(R.id.phoneNumberBox);
        verifyBtn=findViewById(R.id.verify);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        if (!user.isEmailVerified())
        {

            AlertDialog.Builder alertDialog= new  AlertDialog.Builder(this);
            alertDialog.setMessage("Please Verify Your Email.... ");
            alertDialog.setPositiveButton("Verify Email", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    user.sendEmailVerification();
                }
            });

            alertDialog.setNegativeButton("cancel",null);
            alertDialog.show();

        }






        //getting refrences

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String username=user.getDisplayName().isEmpty()?"Home Screen":user.getDisplayName();
        getSupportActionBar().setTitle(username);







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



        userProfile=navigationView.getHeaderView(0).findViewById(R.id.userProfile);
        userName=navigationView.getHeaderView(0).findViewById(R.id.userName);
        userEmail=navigationView.getHeaderView(0).findViewById(R.id.userEmail);
        /*binding user data*/
        String name=user.getDisplayName().isEmpty()?"Home Screen":user.getDisplayName();
        userName.setText(name);
        userEmail.setText(user.getEmail());

        if (user.getPhotoUrl()==null)
        {
            Picasso.get().load(R.drawable.ic_user).into(userProfile);
        }
        else
        {
            Picasso.get().load(user.getPhotoUrl()).into(userProfile);
        }


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "Photo Clicked", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,1003);
            }
        });


        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String cCode= countryCode.getSelectedCountryCode();
               String phone=phoneBox.getText().toString();
               String mobile = cCode+phone;

               final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
               progressDialog.setMessage("Validation Phone Number.....");
               progressDialog.setCancelable(false);
               progressDialog.show();

                /*update phone nunber*/
               PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile, 60, TimeUnit.SECOND, HomeActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                   @Override
                   public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                       progressDialog.setMessage("Updating phone number");
                       user.updatePhoneNumber(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               progressDialog.dismiss();
                               if (task.isSuccessful())
                               {
                                   Toast.makeText(HomeActivity.this, "Phone Number", Toast.LENGTH_SHORT).show();
                               }
                               else
                               {
                                   Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       });

                       /*Reautnthicate user for updating password*/

                       user.updatePassword("New Update");

                   }

                   @Override
                   public void onVerificationFailed(FirebaseException e) {

                   }
               });

            }
        });



















    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1003 && data!=null)
        {

            /*prepairing image*/
        Bitmap imgsrc =(Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream baos  = new ByteArrayOutputStream() ;
        imgsrc.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte image[] = baos.toByteArray();


        /*image uploading*/
        /*getting refrence of firebase storage*/

            /*progress bar*/
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading profile....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            final StorageReference storage = FirebaseStorage.getInstance().getReference().child("userProfiles/"+user.getUid());
            storage.putBytes(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        /*update profile*/

//                        storage.getDownloadUrl().getResult();

                        storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                final Uri imguri = task.getResult();
                                UserProfileChangeRequest updates = new UserProfileChangeRequest.Builder().setPhotoUri(imguri).build();

                                user.updateProfile(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        if (task.isSuccessful())
                                        {
                                            Picasso.get().load(imguri).into(userProfile);
                                            Toast.makeText(HomeActivity.this, "profile picture  updated", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });



                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });




//        userProfile.setImageBitmap(imgsrc);
//        /*FileUpload*/
        }
        else
        {

        }
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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id==R.id.action_delete)
        {
            deletemyAccount();

        }

        if (id == R.id.action_logout) {
            auth.signOut();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    /*Method to update password*/
    public void myUpdatedPassword(String oldPssword, final String newPassword)
    {

        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),oldPssword);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(HomeActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "Wrong password entered :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void deletemyAccount()
    {
        Toast.makeText(this, "delete method called", Toast.LENGTH_SHORT).show();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(HomeActivity.this, "Account deleted succesfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}
