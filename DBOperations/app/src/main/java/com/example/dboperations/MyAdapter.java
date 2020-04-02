package com.example.dboperations;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public Context context;
    public  LayoutInflater layoutInflater;
    public ArrayList<User> userList;
    public RecyclerView recList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout lView;
        public MyViewHolder(LinearLayout v) {
            super(v);
            lView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, RecyclerView recList,LayoutInflater layoutInflater,ArrayList<User> userList) {
        this.context=context;
        this.layoutInflater=layoutInflater;
        this.recList=recList;
        this.userList=userList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rc_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        TextView nameView = holder.lView.findViewById(R.id.username);
        TextView mobileView=holder.lView.findViewById(R.id.usermobile);
        ImageButton callBtn= holder.lView.findViewById(R.id.userCall);
        ImageButton deleteBtn = holder.lView.findViewById(R.id.userDelete);

        final User userData = userList.get(position);
        nameView.setText(userData.getName());
        mobileView.setText(userData.getMobilenumber());


        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Call Button", Toast.LENGTH_SHORT).show();

                //add permission for calling in manifest
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.CALL_PHONE )== PackageManager.PERMISSION_GRANTED)
                    {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("Tel: "+userData.getMobilenumber()));
                        context.startActivity(intent);
                    }
                }

            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Delete Button", Toast.LENGTH_SHORT).show();

                DBHelper helper = new DBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();
                String whereArgs[] ={String.valueOf(userData.getSid())};
                int res=db.delete("StudentsInfo","sid=?",whereArgs);
                if (res>0)
                {
                    Toast.makeText(context, "Record Deleted", Toast.LENGTH_SHORT).show();
                    userList.remove(position);
                    recList.swapAdapter(new MyAdapter(context,recList,layoutInflater,userList),true);
                }
                else
                {
                    Toast.makeText(context, "Record NOT Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.lView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeT3ext(context, "Box Clicked", Toast.LENGTH_SHORT).show();

                View view = layoutInflater.inflate(R.layout.user_info,null);

                ((TextView)view.findViewById(R.id.nameView)).setText(userData.getName());
                ((TextView)view.findViewById(R.id.emailView)).setText(userData.getEmail());
                ((TextView)view.findViewById(R.id.mobileView)).setText(userData.getMobilenumber());
                ((TextView)view.findViewById(R.id.genderView)).setText(userData.getGender());

                AlertDialog alert = new AlertDialog.Builder(context).create();
            //    alert.setTitle("User Info");
//                alert.setMessage("Information");
                alert.setView(view);
                alert.show();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return userList.size();
    }
}
