package com.palmap.main.activity;

import android.os.Bundle;

public class AboutActivity extends PublicActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    setTitle("关于我们");
    enableBack(true);
  }

}
