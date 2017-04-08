package com.bb.executemytrip.api;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by ajitjangra on 4/8/17.
 */

public class EmtRestController {
  private static String autoCompleteUrl = "http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=";
  private static String aTwoBModeUrl = "http://build2.ixigo.com/api/v2/a2b/modes?apiKey=ixicode!2$";

  public static void executePost(Application context, String url, JSONObject postParams, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postParams, successListener, errorListener);
    RestPlatform.getInstance(context).addToRequestQueue(jsonObjectRequest, TAG);
  }

  public static void executeGet(Application context, String url, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, successListener, errorListener);
    RestPlatform.getInstance(context).addToRequestQueue(jsonObjectRequest, TAG);
  }

  public static void executeGetArray(Application context, String url, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) {
    JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, successListener, errorListener);
    RestPlatform.getInstance(context).addToRequestQueue(jsonObjectRequest, TAG);

  }

  public static String getAutoCompleteCityUrl(String searchQuery) {
    StringBuilder url = new StringBuilder();
    url.append(autoCompleteUrl);
    url.append(searchQuery);
    return url.toString();
  }

  public static String getA2BModesUrl(String originCityId, String destinationCityId) {
    StringBuilder url = new StringBuilder();
    url.append(aTwoBModeUrl);
    url.append("&originCityId=");
    url.append(originCityId);
    url.append("&destinationCityId=");
    url.append(destinationCityId);
    return url.toString();
  }

}
