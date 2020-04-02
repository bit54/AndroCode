package com.example.samacharapp;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {
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
    public ArticleAdapter(JSONArray myDataset,Context context) {
        this.mDataset = myDataset;
        this.context=context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            ImageView articleimage = holder.CardView.findViewById(R.id.articleImg);
            TextView articleText = holder.CardView.findViewById(R.id.articleTitle);

            JSONObject article = mDataset.getJSONObject(position);

            Picasso.get().load(article.getString("urlToImage")).error(R.drawable.news_icon).into(articleimage);
            articleText.setText(article.getString("title"));


            holder.CardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert = new AlertDialog()
                }
            });
        }
        catch (Exception ex){}

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}
