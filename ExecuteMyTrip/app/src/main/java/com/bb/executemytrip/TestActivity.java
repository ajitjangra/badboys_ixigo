package com.bb.executemytrip;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bb.executemytrip.api.EmtRestController;
import com.bb.executemytrip.model.A2BModel;
import com.bb.executemytrip.model.AutoCompleteCityModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);

    Button btnATwoB = (Button) findViewById(R.id.btn_atwob);
    Button btnAuto = (Button) findViewById(R.id.btn_auto);
    Button btnPromocode = (Button) findViewById(R.id.btn_promocode);

    btnATwoB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        EmtRestController.executeGetArray((Application) getApplicationContext(), EmtRestController.getAutoCompleteCityUrl("Delhi"), new Response.Listener<JSONArray>() {
          @Override
          public void onResponse(final JSONArray response) {
            Gson gson = new Gson();

            ArrayList<AutoCompleteCityModel> alAutoCompleteCityModel = gson.fromJson(response.toString(), new TypeToken<ArrayList<AutoCompleteCityModel>>() {
            }.getType());

          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(final VolleyError error) {
          }
        });
      }
    });

    btnAuto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        EmtRestController.executeGet((Application) getApplicationContext(), EmtRestController.getA2BModesUrl("1065223", "1071423"), new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(final JSONObject response) {


            Gson gson = new Gson();

            A2BModel a2BModel = gson.fromJson(response.toString(), A2BModel.class);
            System.out.println("ajit3 " + response);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(final VolleyError error) {
            System.out.println("ajit4 " + error);
          }
        });
      }
    });

    btnPromocode.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        startActivity(new Intent(TestActivity.this, PromoCodeFragment.class));
      }
    });
  }
}
