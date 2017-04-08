package com.bb.executemytrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ajitjangra on 4/8/17.
 */

public class A2BModel {
  @SerializedName("data")
  private Data data;

  private class Data {

    @SerializedName("routes")
    private ArrayList<Routes> routes;

    @SerializedName("directFlight")
    private boolean directFlight;

    @SerializedName("directTrain")
    private boolean directTrain;

    @SerializedName("directBus")
    private boolean directBus;

    @SerializedName("directCar")
    private boolean directCar;

    @SerializedName("fastestRoute")
    private Routes fastestRoute;

    @SerializedName("cheapestRoute")
    private Routes cheapestRoute;

    private class Routes {
      @SerializedName("price")
      private String price;

      @SerializedName("time")
      private String time;

      @SerializedName("steps")
      private ArrayList<Steps> steps;

      @SerializedName("modes")
      private ArrayList<String> modes;

      @SerializedName("via")
      private ArrayList<Via> via;

      @SerializedName("layOverTimes")
      private ArrayList<LayOverTimes> layOverTimes;

      private class Steps {
        @SerializedName("origin")
        private String origin;

        @SerializedName("originCode")
        private String originCode;

        @SerializedName("destination")
        private String destination;

        @SerializedName("destinationCode")
        private String destinationCode;

        @SerializedName("mode")
        private String mode;

        @SerializedName("carriers")
        private ArrayList<Carriers> carriers;

        @SerializedName("minPrice")
        private int minPrice;

        @SerializedName("minTime")
        private int minTime;

        @SerializedName("timeUnits")
        private String timeUnits;

        @SerializedName("distance")
        private int distance;

        @SerializedName("carrierName")
        private String carrierName;

        private class Carriers {
          @SerializedName("code")
          private String code;

          @SerializedName("time")
          private String time;

          @SerializedName("carrierName")
          private String carrierName;

          @SerializedName("depTime")
          private String depTime;

          @SerializedName("arrTime")
          private String arrTime;
        }
      }

      private class Via {
      }

      private class LayOverTimes {
      }
    }
  }
}
