package com.palmap.main.service;

import java.util.Timer;
import java.util.TimerTask;

import com.macrowen.macromap.draw.Shop;
import com.macrowen.macromap.utils.MapService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class NearbyShopService extends Service {

  public interface UpdateNearbyShopData {
    public void onUpataNearbyShopData(Shop shop);
  }

  public final static String TAG = "NearbyShopService";

  @Override
  public IBinder onBind(Intent intent) {
    return binder;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    timer.cancel();
    timer.purge();
    return super.onUnbind(intent);
  }

  public class MyBinder extends Binder {

    public NearbyShopService getService() {
      return NearbyShopService.this;
    }
  }

  private Handler mHandler = new Handler();
  private MapService mMapService = MapService.getInstance();
  private NearbyShopRunnable mNearbyShopRunnable;
  private NearbyShoptTimerTask mNearbyShoptTimerTask;
  private UpdateNearbyShopData mListener;
  MyBinder binder = new MyBinder();
  Timer timer;

  private class NearbyShoptTimerTask extends TimerTask {

    @Override
    public void run() {
      Log.w(TAG, "NearbyShoptTimerTask");
      if (mListener == null) {
        return;
      }
      mHandler.post(mNearbyShopRunnable);
    }

  }

  class NearbyShopRunnable implements Runnable {

    @Override
    public void run() {
      Log.w(TAG, "NearbyShopRunnable");
      float x = 15202;
      float y = 7447;
      Shop nearbyShop = mMapService.getNearbyShop(x, y, 0);
      mListener.onUpataNearbyShopData(nearbyShop);
    }

  }

  @Override
  public void onCreate() {
    super.onCreate();
    Log.w(TAG, "onCreate");
  }

  public void startHandler() {
    timer = new Timer(true);
    mNearbyShoptTimerTask = new NearbyShoptTimerTask();
    mNearbyShopRunnable = new NearbyShopRunnable();
    timer.schedule(mNearbyShoptTimerTask, 1000, 10000);
  }

  @Override
  public void onDestroy() {
    Log.w(TAG, "onDestroy");
    mListener = null;
    timer.cancel();
    timer.purge();
    timer = null;
    mNearbyShoptTimerTask = null;
    mHandler.removeCallbacks(mNearbyShopRunnable);
    super.onDestroy();
  }

  public void setUpdataListener(UpdateNearbyShopData listener) {
    mListener = listener;
  }

}
