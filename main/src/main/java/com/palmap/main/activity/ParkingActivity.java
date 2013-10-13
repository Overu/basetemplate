package com.palmap.main.activity;

import android.util.Log;
import android.os.Bundle;

public class ParkingActivity extends PublicActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parking);
    setTitle("找车位");
    enableBack(true);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    Log.w("ParkingActivity", "onPostCreate");
  }

}
