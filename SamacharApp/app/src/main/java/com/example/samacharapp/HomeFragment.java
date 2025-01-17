package com.example.samacharapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.manager.RequestManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RequestManager.RequestListener {



    /*create a request manager*/
    public RequestManager requestManager;
    public RecyclerView sourceList;
    public RelativeLayout errorView;
    public Button retryBtn;
    public ProgressDialog progressDialog;








    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sourceList=view.findViewById(R.id.sourceList);
        errorView=view.findViewById(R.id.errMsg);

        /*confriguration for recyclerview*/
        sourceList.setLayoutManager(new LinearLayoutManager(getContext()));
        sourceList.setHasFixedSize(true);


        /*Will send request*/
        requestManager=RequestManager.getInstance();
        requestManager.get(ApiInfo.allSources(),this,getId());







    }







    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRequest() {
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Sources Loading.......");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onSuccess(String response, Map<String, String> headers, String url, int actionId) {
        progressDialog.dismiss();
        sourceList.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        try {
            JSONObject res = new JSONObject(response);
            JSONArray sources = res.getJSONArray("sources");
            MyAdapter myAdapter = new MyAdapter(getContext(),sources);
            sourceList.setAdapter(myAdapter);

        }catch (Exception ex)
        {

        }


    }

    @Override
    public void onError(String errorMsg, String url, int actionId) {
        progressDialog.dismiss();
        errorView.setVisibility(View.VISIBLE);
        sourceList.setVisibility(View.GONE);
        TextView errMsg=errorView.findViewById(R.id.errMsg);
        errMsg.setText(errorMsg);


        retryBtn =errorView.findViewById(R.id.retryBtn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestManager.get(ApiInfo.allSources(),HomeFragment.this,getId());
            }
        });


    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
