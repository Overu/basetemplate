package com.palmap.main.activity;

import com.google.inject.Inject;

import com.macrowen.macromap.MacroMap;
import com.macrowen.macromap.MacroMap.MapLoadStatus;
import com.macrowen.macromap.MacroMap.OnMapEventListener;
import com.macrowen.macromap.MacroMap.OnMapEventType;
import com.macrowen.macromap.MacroMap.OnMapFloorChangedListener;
import com.macrowen.macromap.MacroMap.OnMapLoadStatusChangeListener;
import com.palmap.main.utils.Constant;
import com.palmap.main.utils.WifiPositionController;
import com.siemens.wifiposition.Position;
import com.siemens.wifiposition.PositionListener;
import com.siemens.wifiposition.ServiceStateListener;
import com.siemens.wifiposition.WifiPositionManager;

import java.util.List;

import javax.annotation.Nullable;

import android.util.Log;

import android.net.wifi.ScanResult;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.content.Intent;
import android.view.MotionEvent;
import roboguice.inject.InjectView;
import android.os.Bundle;

public class MapActivity extends PublicActivity implements OnMapLoadStatusChangeListener, PositionListener {

  @InjectView(tag = "acntivity_map_layout_macromap_tag")
  @Nullable
  MacroMap mMacroMap;
  @InjectView(tag = "acntivity_map_button_zoomout_tag")
  @Nullable
  ImageButton mZoomout;
  @InjectView(tag = "acntivity_map_button_zoomin_tag")
  @Nullable
  ImageButton mZoomin;
  @InjectView(tag = "acntivity_map_button_position_tag")
  @Nullable
  ImageButton mPosition;

  // @Inject
  // WifiPositionManager wifiPosManager;

  @Inject
  WifiPositionController wifiController;

  @Override
  public void onAlarm(String arg0) {
    Log.w("onAlarm", arg0);
  }

  @Override
  public void onAPInfo(List<ScanResult> arg0) {
  }

  @Override
  public void onError(int arg0) {
    Log.w("onError", String.valueOf(arg0));
  }

  @Override
  public void onMapLoadStatusChange(MapLoadStatus arg0) {
  }

  @Override
  public void onNotification(int arg0) {
    Log.w("onNotification", String.valueOf(arg0));
  }

  @Override
  public void onPositionReceived(Position position, String arg1) {
    if (position == null) {
      return;
    }
    Log.w("onPositionReceived", position.x + "_" + position.y);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    mMacroMap.dispatchTouchEvent(event);
    return super.onTouchEvent(event);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    setTitle("地图");
    enableBack(true);

    mMacroMap.setMall("1", "商场");
    mMacroMap.setMall("1");

    // String serverIp = getResources().getString(R.string.position_server_adress);
    // int serverProt = Integer.parseInt(getResources().getString(R.string.position_server_port));
    // wifiPosManager.init(serverIp, serverProt, this);
    // wifiPosManager.setPositionInterval(1000);
    //
    // wifiPosManager.setServiceStateListener(new ServiceStateListener() {
    //
    // @Override
    // public void onStateChanged(int arg0) {
    // Log.w("onStateChanged", arg0 + "");
    // }
    // });

    mMacroMap.setOnMapLoadStatusChangeListener(this);

    mMacroMap.setOnMapEventListener(new OnMapEventListener() {
      @Override
      public void OnMapEvent(int id, OnMapEventType type) {
        if (type == OnMapEventType.MapClickedLeft) {
          Intent intent = new Intent(getBaseContext(), DetailActivity.class);
          intent.putExtra("shopid", "" + id);
          startActivity(intent);
        } else if (type == OnMapEventType.MapClickedRight) {
          Intent intent = new Intent(getBaseContext(), DetailActivity.class);
          intent.putExtra("shopid", "" + id);
          startActivity(intent);
        }
      }
    });

    mMacroMap.setOnMapFloorChangedListener(new OnMapFloorChangedListener() {
      @Override
      public void OnMapFloorChanged(String fromFloorid, String toFloorid) {
      }
    });

    mPosition.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        location();
      }
    });
    mZoomin.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mMacroMap.zoomin();
      }
    });
    mZoomout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mMacroMap.zoomout();
      }
    });
  }

  @Override
  protected void onDestroy() {
    if (wifiController.isConnection()) {
      wifiController.stop();
    }
    super.onDestroy();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    mMacroMap.setOnMapLoadStatusChangeListener(this);
  }

  private void location() {
    if (wifiController.isConnection()) {
      return;
    }
    wifiController.start();
    // String floorid = "18";
    // float x = 15202;
    // float y = 7447;
    // mMacroMap.setPosition(floorid, x, y);
  }

  private void processLoaction(Intent intent) {
    String location = intent.getStringExtra(Constant.LOCATION);
    if (location == null || location.equals("")) {
      return;
    }
    location();
  }
}