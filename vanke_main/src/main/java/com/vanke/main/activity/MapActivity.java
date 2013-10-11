package com.vanke.main.activity;

import android.os.Bundle;

public class MapActivity extends PublicActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    setTitle("地图");
    enableBack(true);
  }

}
