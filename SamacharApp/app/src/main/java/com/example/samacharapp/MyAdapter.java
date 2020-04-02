package com.example.samacharapp;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private JSONArray mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public android.support.v7.widget.CardView CardView;
        public MyViewHolder(CardView v) {
            super(v);
            CardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, JSONArray myDataset) {
        this.context=context;
        mDataset = myDataset;
        
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        try
        {
            TextView sourceName=holder.CardView.findViewById(R.id.sourceName);
            ImageView popupBtn= holder.CardView.findViewById(R.id.popupBtn);
            JSONObject sourceInfo =  mDataset.getJSONObject(position);   /*sourceInfo contains information of news sources*/
            sourceName.setText(sourceInfo.getString("name"));

            holder.CardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Toast.makeText(context, "clicked on new source", Toast.LENGTH_SHORT).show();*/
                    /*create fragment*/

                    FragmentManager fragmentManager= ((HomeActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.rootFrame,new ArticleFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });


        }catch (Exception ex)
        {

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}
