package com.palmap.main.utils;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import com.macrowen.macromap.draw.Shop;
import com.macrowen.macromap.utils.MapService;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

import android.os.Handler;

@Singleton
public class NearbyShopController {

  public interface NearbyShopCallback {
    public void callback(Shop shop);
  }

  public class NearbyShopTimerTask extends TimerTask {
    @Override
    public void run() {
      if (callback == null) {
        return;
      }
      mHandler.post(mNearbyShopRunnable);
    }
  }

  class NearbyShopRunnable implements Runnable {
    @Override
    public void run() {
      float x = 15202;
      float y = 7447;
      Log.w("NearbyShopRunnable", "callback");
      Shop nearbyShop = mMapService.getNearbyShop(x, y, 0);
      if (nearbyShop == null) {
        return;
      }
      callback.callback(nearbyShop);
    }
  }

  private Timer timer;
  private NearbyShopTimerTask nearbyShopTimerTask;
  private NearbyShopCallback callback;
  private Handler mHandler;
  private NearbyShopRunnable mNearbyShopRunnable;
  private MapService mMapService = MapService.getInstance();

  Injector injector;

  @Inject
  public NearbyShopController(Handler handler, Injector injector) {
    this.mHandler = handler;
    this.injector = injector;
    mNearbyShopRunnable = new NearbyShopRunnable();
  }

  public void destory() {
    if (nearbyShopTimerTask != null) {
      nearbyShopTimerTask.cancel();
      nearbyShopTimerTask = null;
    }
    if (timer != null) {
      timer.cancel();
      timer.purge();
      timer = null;
    }
    callback = null;
    mNearbyShopRunnable = null;
  }

  public void setCallback(NearbyShopCallback callback) {
    this.callback = callback;
  }

  public void start() {
    timer = injector.getInstance(Timer.class);
    nearbyShopTimerTask = new NearbyShopTimerTask();
    timer.schedule(nearbyShopTimerTask, 10000, 10000);
  }

  public void stop() {
    if (nearbyShopTimerTask != null) {
      nearbyShopTimerTask.cancel();
      nearbyShopTimerTask = null;
    }
    if (timer != null) {
      timer.cancel();
      timer.purge();
      timer = null;
    }
  }

}
