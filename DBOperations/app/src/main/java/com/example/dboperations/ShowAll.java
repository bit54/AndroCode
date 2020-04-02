package com.example.dboperations;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.solver.widgets.Helper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ShowAll extends AppCompatActivity{

    public DBHelper helper;
    public SQLiteDatabase sql;
    public RecyclerView recList;
    public RelativeLayout errorView;
    public Button addNewBtn;
    public ArrayList<User> userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        //getting refrence
        recList=findViewById(R.id.recordList);
        errorView=findViewById(R.id.errorView);
        addNewBtn=findViewById(R.id.addNewRecBtn);



        //DB helper
        helper=new DBHelper(this);
        sql=helper.getReadableDatabase();

        // read data
        Cursor resCursor = sql.query("StudentsInfo",null,null,null,null,null,"sid");
        int totalRecords=resCursor.getCount();

        getSupportActionBar().setTitle("Total Records: "+totalRecords);

        if (totalRecords > 0)
        {
            recList.setVisibility(View.VISIBLE);

            //make linearLayoutManager and add to record list
            LinearLayoutManager llm = new LinearLayoutManager(this);
            recList.setLayoutManager(llm);

            //set grid size equal for all data
            recList.setHasFixedSize(true);

            //preparing data fro adapter
            userlist = new ArrayList<User>();
            while (resCursor.moveToNext())
            {
                User user = new User();
                user.setSid(resCursor.getInt(resCursor.getColumnIndex("sid")));
                user.setName(resCursor.getString(resCursor.getColumnIndex("name")));
                user.setEmail(resCursor.getString(resCursor.getColumnIndex("email")));
                user.setMobilenumber(resCursor.getString(resCursor.getColumnIndex("mobile")));
                user.setDob(resCursor.getString(resCursor.getColumnIndex("dob")));
                user.setGender(resCursor.getString(resCursor.getColumnIndex("gender")));

                userlist.add(user); //will add user object in arraylist
            }

            //binding adapter
            MyAdapter adapter= new MyAdapter(this,recList,getLayoutInflater(),userlist);
            recList.setAdapter(adapter);


            errorView.setVisibility(View.GONE);
        }
        else
        {
            errorView.setVisibility(View.VISIBLE);
            recList.setVisibility(View.GONE);
        }


        //anonymous listener class
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowAll.this,AddNew.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
