package com.bb.executemytrip.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bb.executemytrip.EmtApplication;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmtUtility {

  public static boolean isNullOrWhiteSpace(String value) {
    return value == null || value.trim().isEmpty();
  }

  static class ToasterUtility {

    public static void showToastL(final String msg) {
      Toast.makeText(EmtApplication.getAppContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showToastLAtCenter(final String msg) {
      final Toast toast = Toast.makeText(EmtApplication.getAppContext(), msg, Toast.LENGTH_LONG);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }

    public static void showToastS(final String msg) {
      Toast.makeText(EmtApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastSAtCenter(final String msg) {
      final Toast toast = Toast.makeText(EmtApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
  }

  static class DeviceUtility {

    public static int getDeviceWidth(final Activity activity) {
      final DisplayMetrics dm = new DisplayMetrics();
      activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
      return dm.widthPixels;
    }

    public static int getDeviceHeight(final Activity activity) {
      final DisplayMetrics dm = new DisplayMetrics();
      activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
      return dm.heightPixels;
    }

    public static int getScreenWidth(Context ctx) {
      int sWidth = 0;
      Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
          .getDefaultDisplay();
      sWidth = display.getWidth();

      return sWidth;
    }

    public static int getScreenHeight(Context ctx) {
      int sWidth = 0;
      Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
          .getDefaultDisplay();
      sWidth = display.getHeight();

      return sWidth;
    }

    public static void hideTitleBar(Activity activity) {
      activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public static int getStatusBarHeight(Activity activity) {
      int height = 0;

      int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (resourceId > 0) {
        height = activity.getResources().getDimensionPixelSize(resourceId);
      }

      return height;
    }
  }

  static class DateTimeUtility {

    public static Date getDateFromString(String dateTime, String dateFormat) {
      if (!isNullOrWhiteSpace(dateTime)) {
        if (isNullOrWhiteSpace(dateFormat)) {
          dateFormat = EmtConstant.DEFAULT_DATE_FORMAT;
        }

        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        try {
          return format.parse(dateTime);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      return null;
    }

    public static String getStringFromDate(Date tempDateTime, String dateFormat) {
      if (tempDateTime != null) {

        if (isNullOrWhiteSpace(dateFormat)) {
          dateFormat = EmtConstant.DEFAULT_DATE_FORMAT;
        }

        SimpleDateFormat outFormat = new SimpleDateFormat(dateFormat, Locale.US);

        try {
          return outFormat.format(tempDateTime);
        } catch (Exception e) {
        }
      }
      return "";
    }

    public static String changeDateFormat(String dateString, String inputDateFormat, String outputDateFormat) {
      if (!isNullOrWhiteSpace(dateString) && !isNullOrWhiteSpace(outputDateFormat)) {
        if (isNullOrWhiteSpace(inputDateFormat)) {
          inputDateFormat = outputDateFormat;
        }

        SimpleDateFormat input = new SimpleDateFormat(inputDateFormat, Locale.US);
        SimpleDateFormat output = new SimpleDateFormat(outputDateFormat, Locale.US);

        try {
          Date tempDate = input.parse(dateString);
          return output.format(tempDate);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      return dateString;
    }
  }


  static class MathUtility {

    public static String formatAmount(float value) {
      DecimalFormat df = new DecimalFormat("##.##");
      df.setRoundingMode(RoundingMode.DOWN);
      return df.format(value);
    }
  }

  static class NetworkUtility {

    public static boolean isInternetConnected(Context context) {
      final ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      boolean internetConnected = false;
      if (connectivity != null) {
        final NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null) {
          for (final NetworkInfo anInfo : info) {
            if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
              internetConnected = true;
            }
          }
        }
      }
      return internetConnected;
    }
  }

  static class ViewUtility {

    public static void changeRatingBarColor(RatingBar ratingBar) {
      LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
      stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
      stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
      stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
    }
  }
}
