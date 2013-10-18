package com.palmap.main.activity;

import com.google.inject.Inject;

import android.os.Bundle;

import com.macrowen.macromap.utils.MapService;
import com.palmap.main.utils.Constant;
import com.palmap.main.utils.SimpleProgressDialog;
import com.palmap.main.utils.WifiPositionController;

import javax.annotation.Nullable;

import android.util.DisplayMetrics;

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
  @InjectView(tag = "activity_home_layout_button_location_tag")
  @Nullable
  ImageButton mLoactionButton;

  private Intent mHomeIntent;

  @Inject
  WifiPositionController wifiPositionController;

  private MapService mMapService = MapService.getInstance();

  @Override
  public void onClick(View v) {
    int id = v.getId();
    mHomeIntent = null;

    if (id == R.id.activity_home_layout_stop_button) {
      // float x = 15202;
      // float y = 7447;
      // Rect rect = new Rect(15, 15, 140, 70);
      // Region region = new Region(rect);
      // Region region1 = new Region(new Rect(15, 15, 260, 120));
      // Log.w("region", region.toString());
      // Log.w("region1", region1.toString());
      // if (region.quickReject(region1)) {
      // Log.w("aaaaaaaaaaa", "aa");
      // return;
      // }
      // Log.w("bbbbbbbbbbb", "bbb");
      // mMapService.getShopsByScope(x, y, 100);
      // mHomeIntent = new Intent(this, ParkingActivity.class);
      mHomeIntent = new Intent(this, BrandWallActivity.class);
    } else if (id == R.id.activity_home_layout_map_button) {
      mHomeIntent = new Intent(this, MapActivity.class);
    } else if (id == R.id.activity_home_layout_button_location) {
      mHomeIntent = new Intent(this, MapActivity.class);
      mHomeIntent.putExtra(Constant.LOCATION, Constant.LOCATION);
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
    setTitle(this.getResources().getString(R.string.string_activity_home_title_layout_textview));

    setDetailButtonListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
        startActivity(intent);
      }
    });

    if (mMapService.getMap() != null) {
      this.parseMapData();
    }

    mStopButton.setOnClickListener(this);
    mMessageButton.setOnClickListener(this);
    mMoreButton.setOnClickListener(this);
    mMapButton.setOnClickListener(this);
    mLoactionButton.setOnClickListener(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    wifiPositionController.destory();
    mMapService.destory();
    mMapService = null;
    android.os.Process.killProcess(android.os.Process.myPid());
  }

  private void parseMapData() {
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    mMapService.setDelegateMeasure(displayMetrics.widthPixels, displayMetrics.heightPixels);
    mMapService.parseMapData(null);
  }

}
