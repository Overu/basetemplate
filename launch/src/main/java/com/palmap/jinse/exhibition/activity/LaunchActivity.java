package com.palmap.jinse.exhibition.activity;

import com.palmap.main.activity.HomeActivity;

import android.content.Intent;

import android.os.Handler;

import roboguice.inject.ContentView;

import android.os.Bundle;

import roboguice.activity.RoboActivity;

@ContentView(R.layout.activity_launch)
public class LaunchActivity extends RoboActivity {

  private Handler mHandler = new Handler() {
    @Override
    public void handleMessage(android.os.Message msg) {
      goHome();
    };
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    new Thread() {
      @Override
      public void run() {
        mHandler.sendEmptyMessageDelayed(1, 1000);
      };
    }.start();
  }

  private void goHome() {
    Intent intent = new Intent();
    intent.setClass(this, HomeActivity.class);
    startActivity(intent);
    this.finish();
  }
}
