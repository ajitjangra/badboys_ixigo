package com.bb.executemytrip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bb.executemytrip.adapter1.PromoCodeAdapter;
import com.bb.executemytrip.customview.EmtTextView;
import com.bb.executemytrip.model.PromoCode;
import com.bb.executemytrip.util.EmtUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class PromoCodeActivity extends AppCompatActivity {

  private RecyclerView rvPromoCode;
  private EmtTextView tvFirstMsg;
  private EmtTextView tvSecondMsg;
  private LinearLayoutManager mLayoutManager;
  private PromoCodeAdapter promoCodeAdapter;
  private ArrayList<PromoCode> alPromoCode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_promo_code);
    findViews();

    initRecyclerView();
    checkForPromoCode();
  }

  private void checkForPromoCode() {
    String promoCodeArray = EmtApplication.getValue("promocode", "");
    if (!EmtUtility.isNullOrWhiteSpace(promoCodeArray)) {
      Gson gson = new Gson();
      alPromoCode.clear();
      final ArrayList<PromoCode> alPromoCodeTemp = gson.fromJson(promoCodeArray, new TypeToken<ArrayList<PromoCode>>() {
      }.getType());

      alPromoCode.addAll(alPromoCodeTemp);


      promoCodeAdapter.notifyDataSetChanged();
    } else {
      // No Promo code available
      rvPromoCode.setVisibility(View.GONE);
      tvFirstMsg.setVisibility(View.VISIBLE);
      tvSecondMsg.setVisibility(View.VISIBLE);
    }

  }

  private void initRecyclerView() {
    rvPromoCode.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(this);
    rvPromoCode.setLayoutManager(mLayoutManager);

    alPromoCode = new ArrayList<>();
    promoCodeAdapter = new PromoCodeAdapter(PromoCodeActivity.this, alPromoCode);
    rvPromoCode.setAdapter(promoCodeAdapter);
  }

  private void findViews() {
    rvPromoCode = (RecyclerView) findViewById(R.id.rv_promo_code);
    tvFirstMsg = (EmtTextView) findViewById(R.id.tv_first_msg);
    tvSecondMsg = (EmtTextView) findViewById(R.id.tv_second_msg);
  }
}
