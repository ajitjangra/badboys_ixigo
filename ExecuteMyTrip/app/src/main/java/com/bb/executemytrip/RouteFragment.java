package com.bb.executemytrip;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.bb.executemytrip.model.A2BModel;
import com.bb.executemytrip.model.AutoCompleteCityModel;
import com.bb.executemytrip.util.EmtUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

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
  private ArrayList<AutoCompleteCityModel> alAutoCompleteCityModel;
  private ArrayList<A2BModel.Data.Routes> alA2BModel;
  private ProgressDialog progress;

  @Override
  public void onAttach(Context ctx) {
    super.onAttach(ctx);
    this.ctx = ctx;

  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    parentView = inflater.inflate(R.layout.fragment_route, container, false);
    findViews();

    EmtApplication.setValue("source_xid", "");
    EmtApplication.setValue("destination_xid", "");

    registerListener();
    initRecyclerView();
    return parentView;
  }

  private void registerListener() {
    actSource.addTextChangedListener(new TextChangeWatcher(actSource));
    actSource.setOnItemClickListener(sourceItemListener);
    actDestination.addTextChangedListener(new TextChangeWatcher(actDestination));
    actDestination.setOnItemClickListener(destinationItemListener);
  }


  private void findViews() {
    actSource = (AutoCompleteTextView) parentView.findViewById(R.id.act_source);
    actDestination = (AutoCompleteTextView) parentView.findViewById(R.id.act_destination);
    rvRoute = (RecyclerView) parentView.findViewById(R.id.rv_route);
    tvPlanATrip = (EmtTextView) parentView.findViewById(R.id.tv_plan_a_trip);
  }

  private void initRecyclerView() {
    rvRoute.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getActivity());
    rvRoute.setLayoutManager(mLayoutManager);
    alA2BModel = new ArrayList<>();
    mAdapter = new RouteAdapter(alA2BModel, getActivity());
    rvRoute.setAdapter(mAdapter);
  }

  final AdapterView.OnItemClickListener sourceItemListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int pos, final long l) {
      String xid = alAutoCompleteCityModel.get(pos).xid;
      EmtApplication.setValue("source_xid", xid);
      callA2BApi();
    }
  };

  final AdapterView.OnItemClickListener destinationItemListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int pos, final long l) {
      String xid = alAutoCompleteCityModel.get(pos).xid;
      EmtApplication.setValue("destination_xid", xid);
      callA2BApi();
    }
  };

  private void callA2BApi() {
    String sourceXid = EmtApplication.getValue("source_xid", "");
    String destinationXid = EmtApplication.getValue("destination_xid", "");

    if (!EmtUtility.isNullOrWhiteSpace(sourceXid) && !EmtUtility.isNullOrWhiteSpace(destinationXid)) {
      if (!EmtUtility.NetworkUtility.isInternetConnected(getActivity())) {
        showInternetConnectivityDialog();
        return;
      }

      showProgressDialog();
      EmtRestController.executeGet((Application) getActivity().getApplicationContext(), EmtRestController.getA2BModesUrl(sourceXid, destinationXid), new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(final JSONObject response) {
          hideProgressDialog();
          rvRoute.setVisibility(View.VISIBLE);
          tvPlanATrip.setVisibility(View.GONE);

          Gson gson = new Gson();
          A2BModel a2BModel = gson.fromJson(response.toString(), A2BModel.class);

          if (a2BModel != null && a2BModel.data != null) {
            if (a2BModel.data.cheapestRoute != null) {
              A2BModel.Data.Routes cheapestRoute = a2BModel.data.cheapestRoute;
              cheapestRoute.isCheapestRoute = true;
              alA2BModel.add(cheapestRoute);
            }

            if (a2BModel.data.fastestRoute != null) {
              A2BModel.Data.Routes fastestRoute = a2BModel.data.fastestRoute;
              fastestRoute.isFastestRoute = true;
              alA2BModel.add(fastestRoute);
            }

            if (a2BModel.data.routes != null) {
              alA2BModel.addAll(a2BModel.data.routes);
            }

            mAdapter.notifyDataSetChanged();
          }

        }
      }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(final VolleyError error) {
          hideProgressDialog();
          rvRoute.setVisibility(View.GONE);
          tvPlanATrip.setVisibility(View.VISIBLE);
        }
      });
    }
  }

  private void showInternetConnectivityDialog() {
    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
    alertDialogBuilder.setTitle(ctx.getString(R.string.internet_msg_1));
    alertDialogBuilder.setMessage(ctx.getString(R.string.internet_msg_2))
        .setCancelable(false)
        .setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
          public void onClick(final DialogInterface dialog, final int id) {
            ((Activity) ctx).finish();
            dialog.cancel();
          }
        });
    final AlertDialog alertDialog = alertDialogBuilder.create();
    if (ctx instanceof Activity) {
      Activity activity = (Activity) ctx;
      if (!activity.isFinishing()) {
        alertDialog.show();
      }
    }
  }

  private void hideProgressDialog() {
    try {
      if (isAdded() && getActivity() != null) {
        if (progress != null && progress.isShowing()) {
          progress.dismiss();
        }
      }
    } catch (Exception e) {
    }
  }

  private void showProgressDialog() {
    if (isAdded() && getActivity() != null) {
      if (progress != null && progress.isShowing()) {
        progress.dismiss();
      }
      progress = new ProgressDialog(ctx);
      progress.setMessage(ctx.getString(R.string.loading));
      progress.show();
      progress.setCanceledOnTouchOutside(false);
      progress.setCancelable(false);
    }
  }

  class TextChangeWatcher implements TextWatcher {
    View view;

    public TextChangeWatcher(View view) {
      this.view = view;
    }


    @Override
    public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

    }

    @Override
    public void onTextChanged(CharSequence query, int start, int before, int count) {
      if (query.toString().trim().length() > 3) {

        if (!EmtUtility.NetworkUtility.isInternetConnected(getActivity())) {
          showInternetConnectivityDialog();
          return;
        }

        EmtRestController.executeGetArray((Application) getActivity().getApplicationContext(), EmtRestController.getAutoCompleteCityUrl(query.toString()), new Response.Listener<JSONArray>() {
          @Override
          public void onResponse(final JSONArray response) {
            Gson gson = new Gson();

            alAutoCompleteCityModel = gson.fromJson(response.toString(), new TypeToken<ArrayList<AutoCompleteCityModel>>() {
            }.getType());

            AutoCompleteArrayAdapter adapter = new AutoCompleteArrayAdapter(getActivity(), R.layout.row_auto_complete, alAutoCompleteCityModel);

            if (view.getId() == R.id.act_source) {
              actSource.setAdapter(adapter);
              actSource.setThreshold(1);
              actSource.setTextColor(Color.BLACK);
            } else if (view.getId() == R.id.act_destination) {
              actDestination.setAdapter(adapter);
              actDestination.setThreshold(1);
              actDestination.setTextColor(Color.BLACK);
            }
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
  }

}
