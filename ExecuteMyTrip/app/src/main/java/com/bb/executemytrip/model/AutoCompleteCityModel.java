package com.bb.executemytrip.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajitjangra on 4/8/17.
 */

public class AutoCompleteCityModel {

  @SerializedName("text")
  public String text;

  @SerializedName("lat")
  public float lat;

  @SerializedName("lon")
  public float lon;

  @SerializedName("xid")
  public String xid;

}
