package com.bb.executemytrip;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.bb.executemytrip.adapter.RouteAdapter;
import com.bb.executemytrip.customview.EmtEditText;

import java.util.ArrayList;


public class RouteFragment extends Fragment

{
    private View parentView;
    private Context ctx;
    private AutoCompleteTextView actSource, actDestination;
    private RecyclerView rvRoute;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.ctx = ctx;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.content_home, container, false);
        findViews();
        callAutoCompleteAPI();
        createRouteView();
        return parentView;
    }

    private void findViews() {
        actSource = (AutoCompleteTextView) parentView.findViewById(R.id.act_Source);
        actDestination = (AutoCompleteTextView) parentView.findViewById(R.id.act_Destination);
        rvRoute =  (RecyclerView) parentView.findViewById(R.id.rv_Route);
    }

    public void callAutoCompleteAPI(){

        actSource.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final ArrayList<String> filteredList = new ArrayList<>();

//                for (int i = 0; i < arrayListHotelListModel.size(); i++) {
//
//                    final String text = arrayListHotelListModel.get(i).strHotelName.toLowerCase();
//                    if (text.contains(query)) {
//
//                        filteredList.add(arrayListHotelListModel.get(i));
//                    }
//                }
//
//                mAdapter = new CustomListBooking(filteredList, getActivity());
//                rvHotelList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
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
