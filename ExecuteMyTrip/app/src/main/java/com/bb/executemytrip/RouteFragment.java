package com.bb.executemytrip;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bb.executemytrip.adapter.RouteAdapter;
import com.bb.executemytrip.api.EmtRestController;
import com.bb.executemytrip.model.AutoCompleteCityModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;


public class RouteFragment extends Fragment

{
    private View parentView;
    private Context ctx;
    private AutoCompleteTextView actSource, actDestination;
    private RecyclerView rvRoute;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private android.support.v7.app.ActionBar toolbar;

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.ctx = ctx;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.content_home, container, false);
        findViews();
        initActionBar();
        callAutoCompleteAPI();
        createRouteView();
        return parentView;
    }


    private void findViews() {
        actSource = (AutoCompleteTextView) parentView.findViewById(R.id.act_Source);
        actDestination = (AutoCompleteTextView) parentView.findViewById(R.id.act_Destination);
        rvRoute =  (RecyclerView) parentView.findViewById(R.id.rv_Route);
    }


    private void initActionBar() {
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle(getString(R.string.menu_execute_a_plan));
        toolbar.setDisplayHomeAsUpEnabled(false);
        toolbar.setHomeButtonEnabled(true);
    }

    public void callAutoCompleteAPI(){

        actSource.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                if(count >= 0)
                {
                    query = query.toString().toLowerCase();

                    EmtRestController.executeGetArray((Application) getActivity().getApplicationContext(), EmtRestController.getAutoCompleteCityUrl((String) query), new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(final JSONArray response) {
                            Gson gson = new Gson();

                            ArrayList<AutoCompleteCityModel> alAutoCompleteCityModel = gson.fromJson(response.toString(), new TypeToken<ArrayList<AutoCompleteCityModel>>()
                            {
                            }.getType());


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(final VolleyError error) {
                        }
                    });

                }
            }
        });



        actSource.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                if(count >= 0)
                {
                    query = query.toString().toLowerCase();

                }
            }
        });
    }


    private void createRouteView() {
        rvRoute.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvRoute.setLayoutManager(mLayoutManager);
        mAdapter = new RouteAdapter(new ArrayList<String>(), getActivity());
        rvRoute.setAdapter(mAdapter);
    }
}
