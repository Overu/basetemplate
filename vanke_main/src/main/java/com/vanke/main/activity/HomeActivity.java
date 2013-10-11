package com.vanke.main.activity;

import android.os.Bundle;
import com.vanke.main.activity.R;
import com.vanke.main.utils.SimpleProgressDialog;
import javax.annotation.Nullable;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.view.View;

public class HomeActivity extends PublicActivity implements OnClickListener {

  @InjectView(tag = "activity_home_layout_stop_button_tag")
  @Nullable
  ImageButton mStopButton;
  @InjectView(tag = "activity_home_layout_message_button_tag")
  @Nullable
  ImageButton mMessageButton;
  @InjectView(tag = "activity_home_layout_more_button_tag")
  @Nullable
  ImageButton mMoreButton;
  @InjectView(tag = "activity_home_layout_map_button_tag")
  @Nullable
  ImageButton mMapButton;

  private Intent mHomeIntent;

  @Override
  public void onClick(View v) {
    int id = v.getId();
    mHomeIntent = null;

    if (id == R.id.activity_home_layout_stop_button) {
      mHomeIntent = new Intent(this, ParkingActivity.class);
    } else if (id == R.id.activity_home_layout_map_button) {
      mHomeIntent = new Intent(this, MapActivity.class);
    } else if (id == R.id.activity_home_layout_message_button) {
      SimpleProgressDialog.show(this, new DialogInterface.OnCancelListener() {

        @Override
        public void onCancel(DialogInterface arg0) {

        }
      });
    } else if (id == R.id.activity_home_layout_more_button) {
      mHomeIntent = new Intent(this, DetailActivity.class);
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
    mMessageButton.setOnClickListener(this);
    mMoreButton.setOnClickListener(this);
    mMapButton.setOnClickListener(this);
  }

}
