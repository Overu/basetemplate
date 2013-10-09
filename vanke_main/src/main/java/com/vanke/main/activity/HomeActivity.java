package com.vanke.main.activity;

import android.os.Bundle;
import com.vanke.main.activity.R;

import javax.annotation.Nullable;

import android.view.View.OnClickListener;

import android.widget.ImageButton;

import roboguice.inject.InjectView;

import android.content.Intent;

import android.view.View;

public class HomeActivity extends PublicActivity implements OnClickListener {

  @InjectView(tag = "stop_button_tag")
  @Nullable
  ImageButton mStopButton;

  private Intent mHomeIntent;

  @Override
  public void onClick(View v) {
    int id = v.getId();

    if (id == R.id.stop_button) {
      mHomeIntent = new Intent(this, ParkingActivity.class);
    }

    if (mHomeIntent == null) {
      return;
    }
    startActivity(mHomeIntent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    setTitle("万科展厅");

    setDetailButtonListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
        startActivity(intent);
      }
    });

    mStopButton.setOnClickListener(this);
  }

}
