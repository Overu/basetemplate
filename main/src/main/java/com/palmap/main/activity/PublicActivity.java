package com.palmap.main.activity;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.ImageButton;

import android.widget.TextView;
import roboguice.inject.InjectView;

import android.view.KeyEvent;

import android.view.Window;

import android.os.Bundle;

import roboguice.activity.RoboActivity;

public class PublicActivity extends RoboActivity {

  @InjectView(tag = "activity_home_title_layout_textview_tag")
  TextView mTitleView;
  @InjectView(tag = "acitivity_home_title_layout_btn_about_tag")
  ImageButton mDetailButton;
  @InjectView(tag = "activity_home_title_layout_btn_back_tag")
  ImageButton mBackButton;

  public void enableBack(boolean enable) {
    mBackButton.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    if (enable) {
      mBackButton.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          finish();
        }
      });
    }
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
      finish();
    }
    return super.onKeyDown(keyCode, event);
  }

  public void setDetailButtonListener(OnClickListener listener) {
    mDetailButton.setVisibility(View.VISIBLE);
    mDetailButton.setOnClickListener(listener);
  }

  @Override
  public void setTitle(CharSequence title) {
    mTitleView.setText(title);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.title_layout);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);
  }
}
