package com.bb.executemytrip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bb.executemytrip.R;
import java.util.ArrayList;



public class RouteAdapter extends  RecyclerView.Adapter<RouteAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<String> arrayList = null;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTimeValue,tvPriceValue, tvfeature;
        public LinearLayout llStep;

        public ViewHolder(View v) {
            super(v);
            tvTimeValue = (TextView) v.findViewById(R.id.tv_TimeValue);
            tvPriceValue = (TextView)v.findViewById(R.id.tv_PriceValue);
            tvfeature = (TextView)v.findViewById(R.id.tv_feature);
            llStep = (LinearLayout) v.findViewById(R.id.ll_Step);

        }
    }

    public RouteAdapter(ArrayList<String> productItemArrayList, Context ctx) {
        context = ctx;
        this.arrayList = productItemArrayList;
    }

    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_route,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final RouteAdapter.ViewHolder holder, final int position) {


        holder. tvTimeValue.setText(arrayList.get(position));
        holder. tvPriceValue.setText(arrayList.get(position));
        holder.tvfeature.setText(arrayList.get(position));





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return  arrayList.size();
    }


}

