package com.palmap.main.activity;

import com.google.inject.Inject;

import android.os.Bundle;

import com.macrowen.macromap.draw.Shop;
import com.macrowen.macromap.utils.MapService;
import com.palmap.main.utils.Constant;
import com.palmap.main.utils.NearbyShopController;
import com.palmap.main.utils.WifiPositionController;
import com.palmap.main.utils.WifiPositionController.PositionListenerDelegate;
import com.siemens.wifiposition.Position;

import javax.annotation.Nullable;

import android.widget.TextView;

import android.os.Handler;

import android.util.Log;

import android.util.DisplayMetrics;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.view.View;

public class HomeActivity extends PublicActivity implements OnClickListener, PositionListenerDelegate {

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
  @InjectView(tag = "activity_home_layout_message_text_tag")
  @Nullable
  TextView mMessageText;

  @Inject
  NearbyShopController nearbyShopController;

  private Handler mHandler = new Handler();

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
      mHomeIntent = new Intent(this, ParkingActivity.class);
      // mHomeIntent = new Intent(this, BrandWallActivity.class);
    } else if (id == R.id.activity_home_layout_map_button) {
      mHomeIntent = new Intent(this, MapActivity.class);
    } else if (id == R.id.activity_home_layout_button_location) {
      mHomeIntent = new Intent(this, MapActivity.class);
      mHomeIntent.putExtra(Constant.LOCATION, Constant.LOCATION);
    } else if (id == R.id.activity_home_layout_message_button) {
      // SimpleProgressDialog.show(this, new DialogInterface.OnCancelListener()
      // {
      //
      // @Override
      // public void onCancel(DialogInterface arg0) {
      //
      // }
      // });
      // float x = 15202;
      // float y = 7447;
      // Shop nearbyShop = mMapService.getNearbyShop(x, y, 0);
      // Log.w("nearbyShop:", nearbyShop == null ? "null" :
      // nearbyShop.getName());
      nearbyShopController.start();
      wifiPositionController.setOnPositionListener(this);
      wifiPositionController.start();
    } else if (id == R.id.activity_home_layout_more_button) {
      // mHomeIntent = new Intent(this, DetailActivity.class);
      mHomeIntent = new Intent(this, BrandWallActivity.class);
    }

    if (mHomeIntent == null) {
      return;
    }
    startActivity(mHomeIntent);
  }

  @Override
  public void onPositionReceived(final Position postion, String str) {
    Log.w("HomeActivity", "HomeActivity onPositionReceived:" + postion.x + "--" + postion.y);
    Log.w("HomeActivity", "HomeActivity onPositionReceived");
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        mMapService.setPositionLazy("114", postion.x, postion.y);
      }
    });
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
      mMapService.setMapScale(10);
      mMapService.setMapOffset(-160, -100);
    }

    mStopButton.setOnClickListener(this);
    mMessageButton.setOnClickListener(this);
    mMoreButton.setOnClickListener(this);
    mMapButton.setOnClickListener(this);
    mLoactionButton.setOnClickListener(this);

    nearbyShopController.setCallback(new NearbyShopController.NearbyShopCallback() {

      @Override
      public void callback(Shop shop) {
        Log.w("nearbyShopController", shop.getName());
        mMessageText.setText(shop.getName());
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    wifiPositionController.destory();
    mMapService.destory();
    mMapService = null;
    nearbyShopController.destory();
    android.os.Process.killProcess(android.os.Process.myPid());
  }

  @Override
  protected void onPause() {
    super.onPause();
    nearbyShopController.stop();
    wifiPositionController.stop();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private void parseMapData() {
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    mMapService.setDelegateMeasure(displayMetrics.widthPixels, displayMetrics.heightPixels);
    mMapService.parseMapData(null);
  }

}
