package com.bb.executemytrip;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.bb.executemytrip.util.MyPlanFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


  private Toolbar toolbar;
  private DrawerLayout drawer;
  private NavigationView navigationView;

  private boolean shouldLoadHomeFragOnBackPress = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    findViews();

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    setUpDrawerLayout();
    initFirebase();
    replaceFrag(new RouteFragment(), "");
    navigationView.getMenu().getItem(0).setChecked(true);
  }

  private void initFirebase() {
    final FirebaseDatabase myFirebaseRef = FirebaseDatabase.getInstance();
    DatabaseReference myRef = myFirebaseRef.getReference("promocode");

    myRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(final DataSnapshot snapshot) {
        Iterable<DataSnapshot> snapshotChildren = snapshot.getChildren();

        JSONArray promoCodeArray = new JSONArray();

        if (snapshotChildren != null) {
          for (DataSnapshot dsPromoCode : snapshotChildren) {
            DataSnapshot key = dsPromoCode.child("key");
            DataSnapshot value = dsPromoCode.child("value");
            JSONObject promocodeObj = new JSONObject();
            try {
              promocodeObj.put("key", key.getValue().toString());
              promocodeObj.put("value", value.getValue().toString());
              promoCodeArray.put(promocodeObj);
            } catch (JSONException e) {
              e.printStackTrace();
            }
            EmtApplication.setValue("promocode", promoCodeArray.toString());
            System.out.println("promo " + EmtApplication.getValue("promocode", promoCodeArray.toString()));
          }
        }
      }

      @Override
      public void onCancelled(final DatabaseError databaseError) {
        System.out.println("ajit " + databaseError);
      }
    });
  }

  private void setUpDrawerLayout() {
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    navigationView.setNavigationItemSelectedListener(this);
  }

  private void findViews() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    navigationView = (NavigationView) findViewById(R.id.nav_view);
  }


  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else if (shouldLoadHomeFragOnBackPress) {
      shouldLoadHomeFragOnBackPress = false;
      replaceFrag(new RouteFragment(), "");
      navigationView.getMenu().getItem(0).setChecked(true);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home: {
        return true;
      }
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.nav_executePlan) {
      toolbar.setTitle(getString(R.string.menu_execute_a_plan));
      replaceFrag(new RouteFragment(), "");
      shouldLoadHomeFragOnBackPress = false;
    } else if (id == R.id.nav_myPlan) {
      toolbar.setTitle(getString(R.string.menu_my_plan));
      replaceFrag(new MyPlanFragment(), "");
      shouldLoadHomeFragOnBackPress = true;
    } else if (id == R.id.nav_promoGiftCode) {
      toolbar.setTitle(getString(R.string.menu_promo));
      replaceFrag(new PromoCodeFragment(), "");
      shouldLoadHomeFragOnBackPress = true;
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private void replaceFrag(Fragment frag, String addToBackStack) {
    if (TextUtils.isEmpty(addToBackStack.trim())) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.fl_frag_main, frag).commit();

    } else {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.fl_frag_main, frag)
          .addToBackStack(addToBackStack).commit();
    }
  }
}
