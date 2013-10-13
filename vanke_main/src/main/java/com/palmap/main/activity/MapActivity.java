package com.vanke.main.activity;

import com.macrowen.macromap.MacroMap;
import com.macrowen.macromap.MacroMap.MapLoadStatus;
import com.macrowen.macromap.MacroMap.OnMapEventListener;
import com.macrowen.macromap.MacroMap.OnMapEventType;
import com.macrowen.macromap.MacroMap.OnMapFloorChangedListener;
import com.macrowen.macromap.MacroMap.OnMapLoadStatusChangeListener;
import com.vanke.main.utils.Constant;
import javax.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.content.Intent;
import android.view.MotionEvent;
import roboguice.inject.InjectView;
import android.os.Bundle;

public class MapActivity extends PublicActivity implements OnMapLoadStatusChangeListener {

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

  @Override
  public void onMapLoadStatusChange(MapLoadStatus arg0) {
    processLoaction(getIntent());
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

    mMacroMap.setMall("3", "商场");
    mMacroMap.setMall("3");

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
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    mMacroMap.setOnMapLoadStatusChangeListener(this);
  }

  private void location() {
    String floorid = "18";
    float x = 15202;
    float y = 7447;
    mMacroMap.setPosition(floorid, x, y);
  }

  private void processLoaction(Intent intent) {
    String location = intent.getStringExtra(Constant.LOCATION);
    if (location == null || location.equals("")) {
      return;
    }
    location();
  }
}
