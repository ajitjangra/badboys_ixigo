package com.bb.executemytrip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bb.executemytrip.R;
import com.bb.executemytrip.customview.EmtTextView;
import com.bb.executemytrip.model.A2BModel;
import com.bb.executemytrip.util.EmtUtility;

import java.util.ArrayList;


public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
  private final LayoutInflater mLayoutInflator;
  private Context context;
  private ArrayList<A2BModel.Data.Routes> arrayList = null;


  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTimeValue, tvPriceValue, tvRouteType;
    public LinearLayout llStep;

    public ViewHolder(View v) {
      super(v);
      tvTimeValue = (TextView) v.findViewById(R.id.tv_time_value);
      tvPriceValue = (TextView) v.findViewById(R.id.tv_price_value);
      tvRouteType = (TextView) v.findViewById(R.id.tv_route_type);
      llStep = (LinearLayout) v.findViewById(R.id.ll_Step);

    }
  }

  public RouteAdapter(ArrayList<A2BModel.Data.Routes> productItemArrayList, Context ctx) {
    context = ctx;
    this.arrayList = productItemArrayList;
    mLayoutInflator = LayoutInflater.from(ctx);
  }

  @Override
  public RouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = mLayoutInflator.inflate(R.layout.row_route, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }


  @Override
  public void onBindViewHolder(final RouteAdapter.ViewHolder holder, final int position) {
    A2BModel.Data.Routes route = arrayList.get(position);

    holder.tvTimeValue.setText(route.time);
    holder.tvPriceValue.setText(route.price);

    if (route.isFastestRoute) {
      holder.tvRouteType.setVisibility(View.VISIBLE);
      holder.tvRouteType.setText("Fastest");
    } else if (route.isCheapestRoute) {
      holder.tvRouteType.setVisibility(View.VISIBLE);
      holder.tvRouteType.setText("Cheapest");
    } else {
      holder.tvRouteType.setVisibility(View.GONE);
    }

    for (int i = 0; i < route.steps.size(); i++) {
      A2BModel.Data.Routes.Steps step = route.steps.get(i);

      View vStep = mLayoutInflator.inflate(R.layout.row_route_step, null, false);

      EmtTextView tvStepNo = (EmtTextView) vStep.findViewById(R.id.tv_step);
      EmtTextView tvSource = (EmtTextView) vStep.findViewById(R.id.tv_source);
      EmtTextView tvCarrier = (EmtTextView) vStep.findViewById(R.id.tv_carrier);
      EmtTextView tvDestination = (EmtTextView) vStep.findViewById(R.id.tv_destination);

      tvStepNo.setText("Step " + i);
      tvSource.setText(step.origin);
      if (step.carriers != null && step.carriers.get(0) != null && !EmtUtility.isNullOrWhiteSpace(step.carriers.get(0).carrierName)) {
        tvCarrier.setText(step.carriers.get(0).carrierName);
      }
      tvDestination.setText(step.destination);

      holder.llStep.addView(vStep);
    }


    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

  }

  @Override
  public int getItemCount() {
    return arrayList.size();
  }


}

