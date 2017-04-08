package com.bb.executemytrip.adapter1;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bb.executemytrip.R;
import com.bb.executemytrip.customview.EmtTextView;
import com.bb.executemytrip.model.PromoCode;
import com.bb.executemytrip.util.EmtUtility;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by ajitjangra on 4/8/17.
 */

public class PromoCodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final ArrayList<PromoCode> alPromoCode;
  private final LayoutInflater mLayoutInflater;
  private final Activity ctx;
  private ClipData myClip;
  private ClipboardManager myClipboard;

  public PromoCodeAdapter(Activity ctx, ArrayList<PromoCode> alPromoCode) {
    this.ctx = ctx;
    this.alPromoCode = alPromoCode;
    this.mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    myClipboard = (ClipboardManager) ctx.getSystemService(CLIPBOARD_SERVICE);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
    View view = mLayoutInflater.inflate(R.layout.row_promo_code, parent, false);
    RecyclerView.ViewHolder viewHolder = new ViewHolder(view);


    return viewHolder;
  }

  @Override
  public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    final PromoCode pc = alPromoCode.get(position);

    ViewHolder viewHolder = (ViewHolder) holder;
    viewHolder.tvKey.setText(pc.key);
    viewHolder.tvValue.setText(pc.value);
    viewHolder.rlMain.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        // Copy the code to clipboard
        myClip = ClipData.newPlainText("text", pc.key);
        myClipboard.setPrimaryClip(myClip);
        EmtUtility.ToasterUtility.showToastS(ctx.getString(R.string.promocode_msg_2));
      }
    });
  }

  @Override
  public int getItemCount() {
    return alPromoCode.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout rlMain;
    EmtTextView tvKey;
    EmtTextView tvValue;

    public ViewHolder(View itemView) {
      super(itemView);
      rlMain = (RelativeLayout) itemView.findViewById(R.id.rl_main);
      tvKey = (EmtTextView) itemView.findViewById(R.id.tv_key);
      tvValue = (EmtTextView) itemView.findViewById(R.id.tv_value);
    }

  }
}
