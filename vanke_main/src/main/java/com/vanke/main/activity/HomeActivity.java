package com.vanke.main.activity;

import android.os.Bundle;
import com.vanke.main.activity.R;

import android.widget.ImageView;

import roboguice.inject.InjectView;

public class HomeActivity extends PublicActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
  }

}
