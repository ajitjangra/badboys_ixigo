package com.bb.executemytrip;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bb.executemytrip.util.AjSharedPreference;

public class EmtApplication extends Application {
  private static Context context;

  // Shared Preference
  private static SharedPreferences sharedPreferences;

  @Override
  public void onCreate() {
    super.onCreate();
    EmtApplication.context = getApplicationContext();

    // Shared Preference
    sharedPreferences = new AjSharedPreference(EmtApplication.getAppContext());
  }

  public static Context getAppContext() {
    return EmtApplication.context;
  }

  // *******************************************************
  // Shared Preference
  // *******************************************************

  public static void setValue(String key, int value) {
    final SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(key, value);
    editor.apply();
  }

  public static synchronized int getValue(String key, int defaultValue) {
    return sharedPreferences.getInt(key, defaultValue);
  }

  public static void setValue(String key, String value) {
    final SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(key, value);
    editor.apply();
  }

  public static synchronized String getValue(String key, String defaultValue) {
    return sharedPreferences.getString(key, defaultValue);
  }

  public static void setValue(String key, boolean value) {
    final SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  public static synchronized boolean getValue(String key, boolean defaultValue) {
    return sharedPreferences.getBoolean(key, defaultValue);
  }

  public static void setValue(String key, long value) {
    final SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putLong(key, value);
    editor.apply();
  }

  public static synchronized long getValue(String key, long defaultValue) {
    return sharedPreferences.getLong(key, defaultValue);
  }
}
