package com.bb.executemytrip.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bb.executemytrip.R;
import com.bb.executemytrip.RouteDetailActivity;
import com.bb.executemytrip.customview.EmtTextView;
import com.bb.executemytrip.model.A2BModel;
import com.bb.executemytrip.util.EmtUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class RouteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public interface RouteAdapterInterface {

    AdapterView.OnItemClickListener sourceItemClick();

    AdapterView.OnItemClickListener destinationItemClick();

    TextWatcher textWatcher(AutoCompleteTextView actSource);
  }

  private RouteAdapterInterface routeAdapterInterface;
  private final LayoutInflater mLayoutInflator;
  private Context context;
  private ArrayList<A2BModel.Data.Routes> arrayList = null;

  private static final int TYPE_HEADER = 0;
  private static final int TYPE_ITEM = 1;
  private Gson gson;

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

  public static class ViewHolderRoute extends RecyclerView.ViewHolder {
    public TextView tvTimeValue, tvPriceValue, tvRouteType;
    public LinearLayout llStep;

    public ViewHolderRoute(View v) {
      super(v);
      tvTimeValue = (TextView) v.findViewById(R.id.tv_time_value);
      tvPriceValue = (TextView) v.findViewById(R.id.tv_price_value);
      tvRouteType = (TextView) v.findViewById(R.id.tv_route_type);
      llStep = (LinearLayout) v.findViewById(R.id.ll_Step);

    }
  }

  public static class ViewHolderHeader extends RecyclerView.ViewHolder {
    private AutoCompleteTextView actSource, actDestination;

    public ViewHolderHeader(View v) {
      super(v);
      actSource = (AutoCompleteTextView) v.findViewById(R.id.act_source);
      actDestination = (AutoCompleteTextView) v.findViewById(R.id.act_destination);
    }
  }

  public RouteAdapter(ArrayList<A2BModel.Data.Routes> productItemArrayList, Context ctx) {
    context = ctx;
    this.arrayList = productItemArrayList;
    mLayoutInflator = LayoutInflater.from(ctx);
    routeAdapterInterface = (RouteAdapterInterface) ctx;
    gson = new Gson();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == TYPE_ITEM) {
      View v = mLayoutInflator.inflate(R.layout.row_route, parent, false);
      return new ViewHolderRoute(v);

    } else if (viewType == TYPE_HEADER) {
      View v = mLayoutInflator.inflate(R.layout.lay_source_destination, parent, false);
      return new ViewHolderHeader(v);
    }

    throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

  }

  @Override
  public void onBindViewHolder(final RecyclerView.ViewHolder holderMain, final int position) {
    if (holderMain instanceof ViewHolderRoute) {

      ViewHolderRoute holder = (ViewHolderRoute) holderMain;

      final A2BModel.Data.Routes route = arrayList.get(position - 1);

      holder.tvTimeValue.setText(route.time + " " + route.timeUnit);
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

      holder.llStep.removeAllViews();
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
            Intent intent = new Intent(context, RouteDetailActivity.class);

          String selectedRoute = gson.toJson(route);
          intent.putExtra("route", selectedRoute);
          context.startActivity(intent);
        }
      });
    } else if (holderMain instanceof ViewHolderHeader) {
      ViewHolderHeader holder = (ViewHolderHeader) holderMain;
      holder.actSource.setOnItemClickListener(routeAdapterInterface.sourceItemClick());
      holder.actDestination.setOnItemClickListener(routeAdapterInterface.destinationItemClick());
      holder.actSource.addTextChangedListener(routeAdapterInterface.textWatcher(holder.actSource));
      holder.actDestination.addTextChangedListener(routeAdapterInterface.textWatcher(holder.actDestination));
    }

  }


  @Override
  public int getItemCount() {
    return arrayList.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0)
      return TYPE_HEADER;

    return TYPE_ITEM;
  }

}

