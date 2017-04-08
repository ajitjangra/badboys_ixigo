package com.bb.executemytrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ajitjangra on 4/8/17.
 */

public class A2BModel {
  @SerializedName("data")
  public Data data;

  public class Data {

    @SerializedName("routes")
    public ArrayList<Routes> routes;

    @SerializedName("directFlight")
    public boolean directFlight;

    @SerializedName("directTrain")
    public boolean directTrain;

    @SerializedName("directBus")
    public boolean directBus;

    @SerializedName("directCar")
    public boolean directCar;

    @SerializedName("fastestRoute")
    public Routes fastestRoute;

    @SerializedName("cheapestRoute")
    public Routes cheapestRoute;

    public class Routes {
      public boolean isCheapestRoute = false;
      public boolean isFastestRoute = false;

      @SerializedName("price")
      public String price;

      @SerializedName("time")
      public String time;

      @SerializedName("steps")
      public ArrayList<Steps> steps;

      @SerializedName("modes")
      public ArrayList<String> modes;

      @SerializedName("via")
      public ArrayList<Via> via;

      @SerializedName("layOverTimes")
      public ArrayList<LayOverTimes> layOverTimes;

      public class Steps {
        @SerializedName("origin")
        public String origin;

        @SerializedName("originCode")
        public String originCode;

        @SerializedName("destination")
        public String destination;

        @SerializedName("destinationCode")
        public String destinationCode;

        @SerializedName("mode")
        public String mode;

        @SerializedName("carriers")
        public ArrayList<Carriers> carriers;

        @SerializedName("minPrice")
        public int minPrice;

        @SerializedName("minTime")
        public int minTime;

        @SerializedName("timeUnits")
        public String timeUnits;

        @SerializedName("distance")
        public int distance;

        @SerializedName("carrierName")
        public String carrierName;

        public class Carriers {
          @SerializedName("code")
          public String code;

          @SerializedName("time")
          public String time;

          @SerializedName("carrierName")
          public String carrierName;

          @SerializedName("depTime")
          public String depTime;

          @SerializedName("arrTime")
          public String arrTime;
        }
      }

      public class Via {
      }

      public class LayOverTimes {
      }
    }
  }
}
