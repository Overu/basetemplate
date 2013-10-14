package com.palmap.main.app;

import roboguice.RoboGuice;

import android.app.Application;

public class MainApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE);
  }

}
