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

  public class MyBinder extends Binder {

    public NearbyShopService getService() {
      return NearbyShopService.this;
    }
  }

  public interface UpdateNearbyShopData {
    public void onUpataNearbyShopData(Shop shop);
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

  public final static String TAG = "NearbyShopService";

  private Handler mHandler = new Handler();
  private MapService mMapService = MapService.getInstance();
  private NearbyShopRunnable mNearbyShopRunnable;
  private NearbyShoptTimerTask mNearbyShoptTimerTask;
  private UpdateNearbyShopData mListener;
  MyBinder binder = new MyBinder();
  Timer timer;

  @Override
  public IBinder onBind(Intent intent) {
    return binder;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Log.w(TAG, "onCreate");
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

  @Override
  public boolean onUnbind(Intent intent) {
    timer.cancel();
    timer.purge();
    return super.onUnbind(intent);
  }

  public void setUpdataListener(UpdateNearbyShopData listener) {
    mListener = listener;
  }

  public void startHandler() {
    timer = new Timer(true);
    mNearbyShoptTimerTask = new NearbyShoptTimerTask();
    mNearbyShopRunnable = new NearbyShopRunnable();
    timer.schedule(mNearbyShoptTimerTask, 10000, 10000);
  }

}
