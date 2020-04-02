package com.example.dboperations;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button addNew,showAll,searchRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNew=findViewById(R.id.addnew);
        showAll=findViewById(R.id.showall);
        searchRec=findViewById(R.id.search);


        addNew.setOnClickListener(this);
        searchRec.setOnClickListener(this);
        showAll.setOnClickListener(this);

        /* adding runtime permission
         * 1.check your android version
          * 2.*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
                String appPermission[] = {Manifest.permission.CALL_PHONE};
                requestPermissions(appPermission,1001);
            }
            else
            {

            }

        }


    }

    @Override
    public void onClick(View v) {

        if (v == addNew)
        {
                startActivity(new Intent(this,AddNew.class));
        }
        else if (v == showAll)
        {
            startActivity(new Intent(this,ShowAll.class));
        }
        else if (v == searchRec)
        {
            startActivity(new Intent(this,SearchAll.class));
        }
    }
}
