package com.example.dboperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    //creates database
    public DBHelper(@Nullable Context context)
    {
        super(context,"Student's DB",null,1);
    }



    //creating table in database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmd="create table StudentsInfo(sid integer primary key autoincrement,name varchar(40),email varchar(40) unique,mobile varchar(10) unique,dob datetime,gender varchar(10))";
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
