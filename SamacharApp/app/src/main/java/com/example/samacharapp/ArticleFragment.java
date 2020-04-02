package com.example.samacharapp;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.manager.RequestManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("ValidFragment")
public class ArticleFragment extends Fragment implements RequestManager.RequestListener {

    public JSONObject sourceInfo;
    public RecyclerView articleList;
    public SwipeRefreshLayout srl;
    public RelativeLayout errorView;
    public RequestManager requestManager;
    public ProgressDialog progressDialog;


    @SuppressLint("ValidFragment")
    public ArticleFragment(JSONObject sourceInfo) {
        this.sourceInfo = sourceInfo;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*getting references*/
        try {
            srl = view.findViewById(R.id.srl);
            articleList = view.findViewById(R.id.articleList);
            errorView = view.findViewById(R.id.errorView);

            /*configuaration for recyclerview*/
            articleList.setLayoutManager(new LinearLayoutManager(getContext()));
            articleList.setHasFixedSize(true);


            /*request manager*/
            requestManager = RequestManager.getInstance();
            requestManager.get(ApiInfo.articlesFrom(sourceInfo.getString("id")), this, 1002);
        }
        catch (Exception ex)
        {}
    }

    @Override
    public void onRequest() {
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Articles Loading.......");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onSuccess(String response, Map<String, String> headers, String url, int actionId) {
        progressDialog.dismiss();
        srl.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        try {
            JSONObject articleRes = new JSONObject(response);
            JSONArray articles = articleRes.getJSONArray("articles");
            ArticleAdapter articleAdapter = new ArticleAdapter(articles,getContext());
            articleList.setAdapter(articleAdapter);

        }catch (Exception ex)
        {

        }


    }

    @Override
    public void onError(String errorMsg, String url, int actionId) {
        progressDialog.dismiss();
        errorView.setVisibility(View.VISIBLE);
        srl.setVisibility(View.GONE);
        TextView errMsg=errorView.findViewById(R.id.errMsg);
        errMsg.setText(errorMsg);


        Button retryBtn =errorView.findViewById(R.id.retryBtn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                requestManager.get(ApiInfo.articlesFrom(sourceInfo.getString("id")),ArticleFragment.this,1002);
            }catch (Exception ex){}
            }
        });


    }

}
