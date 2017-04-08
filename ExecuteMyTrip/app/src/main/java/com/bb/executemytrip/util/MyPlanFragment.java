package com.bb.executemytrip.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bb.executemytrip.R;


public class MyPlanFragment extends Fragment

{
    private View parentView;
    private Context ctx;


    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.ctx = ctx;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_my_plan, container, false);
        findViews();
        return parentView;
    }

    private void findViews() {
    }

}
