package com.bb.executemytrip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bb.executemytrip.customview.EmtEditText;
import com.bb.executemytrip.customview.EmtTextView;
import com.bb.executemytrip.model.A2BModel;
import com.bb.executemytrip.util.EmtUtility;
import com.google.gson.Gson;


/**
 * Created by zakir on 4/9/2017.
 */

public class RouteDetailActivity extends AppCompatActivity {

  private EmtTextView tvSource, tvDestination;
  private EmtEditText etPromo;
  private Button btnLetsExecutePlan;
  private Gson gson;
  private A2BModel.Data.Routes routes;
  private Toolbar toolbar;
  private LinearLayout llStep;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_route_detail);
    findViews();
    initToolBar();
    getDataFromIntent();
    updateUI();
  }

  private void updateUI() {
    for (int i = 0; i < routes.steps.size(); i++) {
      A2BModel.Data.Routes.Steps step = routes.steps.get(i);

      View vStep = LayoutInflater.from(this).inflate(R.layout.layout_detail_step, null, false);

      View vLine = (View) vStep.findViewById(R.id.v_line);
      EmtTextView tvSource = (EmtTextView) vStep.findViewById(R.id.tv_source);
      EmtTextView tvDestination = (EmtTextView) vStep.findViewById(R.id.tv_destination);
      EmtTextView tvFastestCarrierValue = (EmtTextView) vStep.findViewById(R.id.tv_fastest_carrier_value);
      EmtTextView tvPossibleCarrierValue = (EmtTextView) vStep.findViewById(R.id.tv_possible_carrier_value);
      EmtTextView tvMinPriceValue = (EmtTextView) vStep.findViewById(R.id.tv_min_price_value);
      EmtTextView tvMinTimeValue = (EmtTextView) vStep.findViewById(R.id.tv_min_time_value);
      EmtTextView tvDistanceValue = (EmtTextView) vStep.findViewById(R.id.tv_distance_value);
      EmtTextView tvFastestCarrierDurationValue = (EmtTextView) vStep.findViewById(R.id.tv_fastest_carrie_duration_value);

      if (i == routes.steps.size() - 1) {
        vLine.setVisibility(View.GONE);
      }
      tvSource.setText(step.origin);
      tvDestination.setText(step.destination);
      tvFastestCarrierValue.setText(step.fastestCarrier);
      tvPossibleCarrierValue.setText(step.carrierName);
      tvMinPriceValue.setText("" + step.minPrice);
      tvMinTimeValue.setText(step.minTime + " " + step.timeUnits);
      tvDistanceValue.setText("" + step.distance);
      tvFastestCarrierDurationValue.setText(step.fastestCarrierDuration);

      llStep.addView(vStep);
    }
  }

  private void initToolBar() {
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(getString(R.string.detail_root_details));
  }

  private void findViews() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    tvSource = (EmtTextView) findViewById(R.id.tv_source);
    tvDestination = (EmtTextView) findViewById(R.id.tv_destination);
    etPromo = (EmtEditText) findViewById(R.id.et_promo);
    btnLetsExecutePlan = (Button) findViewById(R.id.btn_lets_execute_pan);
    llStep = (LinearLayout) findViewById(R.id.ll_step);
  }


  private void getDataFromIntent() {

    if (getIntent() != null) {
      String strRoute = getIntent().getStringExtra("route");
      gson = new Gson();
      routes = gson.fromJson(strRoute, A2BModel.Data.Routes.class);

      if (routes == null) {
        EmtUtility.ToasterUtility.showToastS(getString(R.string.something_went_wrong));
        finish();
      }

    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }
}
