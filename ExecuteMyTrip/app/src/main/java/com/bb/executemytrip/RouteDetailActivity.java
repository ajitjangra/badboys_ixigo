package com.bb.executemytrip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.bb.executemytrip.customview.EmtEditText;
import com.bb.executemytrip.customview.EmtTextView;
import com.bb.executemytrip.model.A2BModel;
import com.google.gson.Gson;

/**
 * Created by zakir on 4/9/2017.
 */

public class RouteDetailActivity extends AppCompatActivity {

    private  EmtTextView tvSource, tvDestination;
    private EmtEditText etPromo;
    private Button btnLetsExecutePlan;
    private Gson gson;
    private A2BModel.Data.Routes routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);
        findViews();
        getDataFromIntent();
    }

    private void findViews() {

        tvSource = (EmtTextView) findViewById(R.id.tv_source);
        tvDestination = (EmtTextView) findViewById(R.id.tv_destination);
        etPromo = (EmtEditText) findViewById(R.id.et_promo);
        btnLetsExecutePlan = (Button) findViewById(R.id.btn_lets_execute_pan);
    }


    private void getDataFromIntent() {

        if(getIntent() != null)
        {
            String strRoute = getIntent().getStringExtra("route");
            gson = new Gson();
            routes = gson.fromJson(strRoute, A2BModel.Data.Routes.class);

        }
    }
}
