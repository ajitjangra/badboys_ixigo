package com.bb.executemytrip.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajitjangra on 4/8/17.
 */

public class AutoCompleteCityModel {

  @SerializedName("text")
  private String text;

  @SerializedName("lat")
  private float lat;

  @SerializedName("lon")
  private float lon;

  @SerializedName("xid")
  private String xid;

}
