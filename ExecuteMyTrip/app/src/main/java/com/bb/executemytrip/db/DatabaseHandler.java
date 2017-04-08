package com.bb.executemytrip.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ajitjangra on 4/8/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

  // Database Version
  private static final int DATABASE_VERSION = 1;

  // Database Name
  private static final String DATABASE_NAME = "emt_db";

  // My plan table
  private static final String TABLE_MY_PLAN = "my_plan";
  private static final String KEY_ID = "id";
  private static final String KEY_ORIGIN = "origin";
  private static final String KEY_DESTINATION = "destination";
  private static final String KEY_ROUTE = "route";

  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    String CREATE_MY_PLAN = "CREATE TABLE " + TABLE_MY_PLAN + "("
        + KEY_ID + " INTEGER PRIMARY KEY,"
        + KEY_ORIGIN + " TEXT,"
        + KEY_DESTINATION + " TEXT,"
        + KEY_ROUTE + " TEXT"
        + ")";
    db.execSQL(CREATE_MY_PLAN);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_PLAN);

    // Create tables again
    onCreate(db);
  }

}
