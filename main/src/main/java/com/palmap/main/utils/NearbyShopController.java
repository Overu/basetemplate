package com.palmap.main.utils;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.macrowen.macromap.draw.Shop;
import com.macrowen.macromap.utils.MapService;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

import android.graphics.PointF;
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
      PointF positionPoint = mMapService.getPostition();
      if (positionPoint == null) {
        return;
      }
      mHandler.post(mNearbyShopRunnable);
    }
  }

  class NearbyShopRunnable implements Runnable {
    @Override
    public void run() {
      // float x = 159;
      // float y = 66;
      // Log.w("NearbyShopRunnable", "callback");
      PointF postition = mMapService.getPostition();
      Log.w("NearbyShopRunnable", postition.x + "callback" + postition.y);
      Shop nearbyShop = mMapService.getNearbyShop(postition.x, postition.y, 0);
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

  private boolean isStart = false;

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

  public void setPointF() {

  }

  public void start() {
    if (isStart) {
      return;
    }
    isStart = true;
    timer = injector.getInstance(Timer.class);
    nearbyShopTimerTask = new NearbyShopTimerTask();
    timer.schedule(nearbyShopTimerTask, 5000, 5000);
  }

  public void stop() {
    if (!isStart) {
      return;
    }
    isStart = false;
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
