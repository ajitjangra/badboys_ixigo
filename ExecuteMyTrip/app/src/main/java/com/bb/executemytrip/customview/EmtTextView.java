package com.bb.executemytrip.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bb.executemytrip.R;


/**
 * Created by ajitjangra on 11/10/16.
 */

public class EmtTextView extends TextView {
  public EmtTextView(final Context context) {
    super(context);
    if (!isInEditMode()) {
      init();
    }
  }

  public EmtTextView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    if (!isInEditMode()) {
      init();
    }
  }

  public EmtTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    if (!isInEditMode()) {
      init();
    }
  }

  private void init() {
    Typeface tf = null;

    if (getTag() != null) {
      if (getTag().equals(getResources().getString(R.string.font_light))) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Light.ttf");
      } else if (getTag().equals(getResources().getString(R.string.font_medium))) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Medium.ttf");
      } else if (getTag().equals(getResources().getString(R.string.font_bold))) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Bold.ttf");
      } else if (getTag().equals(getResources().getString(R.string.font_regular))) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Regular.ttf");
      }
    }

    if (tf == null) {
      tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Regular.ttf");
    }

    setTypeface(tf);
  }
}
