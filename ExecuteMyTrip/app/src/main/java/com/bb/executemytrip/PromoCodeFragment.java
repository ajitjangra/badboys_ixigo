package com.bb.executemytrip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bb.executemytrip.adapter.PromoCodeAdapter;
import com.bb.executemytrip.customview.EmtTextView;
import com.bb.executemytrip.model.PromoCode;
import com.bb.executemytrip.util.EmtUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class PromoCodeFragment extends Fragment {

  private RecyclerView rvPromoCode;
  private EmtTextView tvFirstMsg;
  private EmtTextView tvSecondMsg;
  private LinearLayoutManager mLayoutManager;
  private PromoCodeAdapter promoCodeAdapter;
  private ArrayList<PromoCode> alPromoCode;
  private View parentView;
  private android.support.v7.app.ActionBar toolbar;

  @Nullable
  @Override
  public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
    parentView = inflater.inflate(R.layout.fragment_promo_code, container, false);
    findViews();
    initRecyclerView();
    checkForPromoCode();
    return parentView;
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
    mLayoutManager = new LinearLayoutManager(getActivity());
    rvPromoCode.setLayoutManager(mLayoutManager);

    alPromoCode = new ArrayList<>();
    promoCodeAdapter = new PromoCodeAdapter(getActivity(), alPromoCode);
    rvPromoCode.setAdapter(promoCodeAdapter);
  }

  private void findViews() {
    rvPromoCode = (RecyclerView) parentView.findViewById(R.id.rv_promo_code);
    tvFirstMsg = (EmtTextView) parentView.findViewById(R.id.tv_first_msg);
    tvSecondMsg = (EmtTextView) parentView.findViewById(R.id.tv_second_msg);
  }
}
