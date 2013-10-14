package com.palmap.main.app;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.siemens.wifiposition.WifiPositionManager;

import android.app.Application;

public class MainModule extends AbstractModule {

  @Override
  protected void configure() {

  }

  @Provides
  @Singleton
  WifiPositionManager provideWifiPositionManager(Application application) {
    WifiPositionManager wifiPosMananger = new WifiPositionManager(application);
    return wifiPosMananger;
  }

}
