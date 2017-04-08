package com.bb.executemytrip;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bb.executemytrip.adapter.AutoCompleteArrayAdapter;
import com.bb.executemytrip.adapter.RouteAdapter;
import com.bb.executemytrip.api.EmtRestController;
import com.bb.executemytrip.customview.EmtTextView;
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
  private EmtTextView tvPlanATrip;
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
    parentView = inflater.inflate(R.layout.fragment_route, container, false);
    findViews();
    initActionBar();

    registerListener();
    createRouteView();
    return parentView;
  }

  private void registerListener() {
    actSource.addTextChangedListener(sourceListener);
    actSource.setOnItemClickListener(sourceItemClickListener);
    actDestination.addTextChangedListener(destinationListener);
    actDestination.setOnItemClickListener(destinationItemClickListener);
  }


  private void findViews() {
    actSource = (AutoCompleteTextView) parentView.findViewById(R.id.act_source);
    actDestination = (AutoCompleteTextView) parentView.findViewById(R.id.act_destination);
    rvRoute = (RecyclerView) parentView.findViewById(R.id.rv_route);
    tvPlanATrip = (EmtTextView) parentView.findViewById(R.id.tv_plan_a_trip);
  }


  private void initActionBar() {
    toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    toolbar.setTitle(getString(R.string.menu_execute_a_plan));
    toolbar.setDisplayHomeAsUpEnabled(true);
    toolbar.setHomeButtonEnabled(true);
  }


  private ArrayList<AutoCompleteCityModel> alAutoCompleteCityModel;
  final TextWatcher sourceListener = new TextWatcher() {
    @Override
    public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

    }

    @Override
    public void onTextChanged(CharSequence query, int start, int before, int count) {
      if (query.toString().trim().length() > 3) {

        EmtRestController.executeGetArray((Application) getActivity().getApplicationContext(), EmtRestController.getAutoCompleteCityUrl(query.toString()), new Response.Listener<JSONArray>() {
          @Override
          public void onResponse(final JSONArray response) {
            Gson gson = new Gson();

            alAutoCompleteCityModel = gson.fromJson(response.toString(), new TypeToken<ArrayList<AutoCompleteCityModel>>() {
            }.getType());

            AutoCompleteArrayAdapter adapter = new AutoCompleteArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, alAutoCompleteCityModel);

            actSource.setAdapter(adapter);
            actSource.setThreshold(1);
            actSource.setTextColor(Color.BLACK);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(final VolleyError error) {
            // No need to do anything
          }
        });

      } else {
        if (alAutoCompleteCityModel != null) {
          alAutoCompleteCityModel.clear();
        }
      }
    }

    @Override
    public void afterTextChanged(final Editable editable) {

    }
  };

  final AdapterView.OnItemClickListener sourceItemClickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int pos, final long id) {
      String xid = alAutoCompleteCityModel.get(pos).xid;
      EmtApplication.setValue("sourcexid", xid);
    }
  };

  final TextWatcher destinationListener = new TextWatcher() {
    @Override
    public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

    }

    @Override
    public void onTextChanged(CharSequence query, int start, int before, int count) {
      if (query.toString().trim().length() > 3) {

        EmtRestController.executeGetArray((Application) getActivity().getApplicationContext(), EmtRestController.getAutoCompleteCityUrl(query.toString()), new Response.Listener<JSONArray>() {
          @Override
          public void onResponse(final JSONArray response) {
            Gson gson = new Gson();

            alAutoCompleteCityModel = gson.fromJson(response.toString(), new TypeToken<ArrayList<AutoCompleteCityModel>>() {
            }.getType());

            AutoCompleteArrayAdapter adapter = new AutoCompleteArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, alAutoCompleteCityModel);

            actDestination.setAdapter(adapter);
            actDestination.setThreshold(1);
            actDestination.setTextColor(Color.BLACK);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(final VolleyError error) {
            // No need to do anything
          }
        });

      } else {
        if (alAutoCompleteCityModel != null) {
          alAutoCompleteCityModel.clear();
        }
      }
    }

    @Override
    public void afterTextChanged(final Editable editable) {

    }
  };

  final AdapterView.OnItemClickListener destinationItemClickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int pos, final long id) {
      String xid = alAutoCompleteCityModel.get(pos).xid;
      EmtApplication.setValue("destinationxid", xid);
    }
  };

  private void createRouteView() {
    rvRoute.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getActivity());
    rvRoute.setLayoutManager(mLayoutManager);
    mAdapter = new RouteAdapter(new ArrayList<String>(), getActivity());
    rvRoute.setAdapter(mAdapter);
  }
}
