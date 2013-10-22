package com.palmap.main.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.palmap.main.activity.R;
import com.siemens.wifiposition.Position;
import com.siemens.wifiposition.PositionListener;
import com.siemens.wifiposition.WifiPositionManager;

import java.util.List;

import android.util.Log;

import android.net.wifi.ScanResult;

import android.content.res.Resources;

@Singleton
public class WifiPositionController implements PositionListener {

  public interface PositionListenerDelegate {
    public void onPositionReceived(Position postion, String str);
  }

  private WifiPositionManager wifiPosMananger;

  private boolean isConnection = false;

  private PositionListenerDelegate listener;

  private String serverIp;
  private int serverProt;

  @Inject
  public WifiPositionController(WifiPositionManager wifiPosMananger, Resources resource) {
    this.wifiPosMananger = wifiPosMananger;
    serverIp = resource.getString(R.string.position_server_adress);
    serverProt = Integer.parseInt(resource.getString(R.string.position_server_port));
  }

  public void destory() {
    stop();
    wifiPosMananger.destroy();
  }

  public boolean isConnection() {
    return this.isConnection;
  }

  @Override
  public void onAlarm(String arg0) {
    Log.w("onAlarm", arg0 + "");
  }

  @Override
  public void onAPInfo(List<ScanResult> arg0) {

  }

  @Override
  public void onError(int arg0) {
    Log.w("onError", arg0 + "");
  }

  @Override
  public void onNotification(int arg0) {
    Log.w("onNotification", arg0 + "");
  }

  @Override
  public void onPositionReceived(Position arg0, String arg1) {
    this.listener.onPositionReceived(arg0, arg1);
  }

  public void setOnPositionListener(PositionListenerDelegate listener) {
    this.listener = listener;
  }

  public void start() {
    if (isConnection) {
      return;
    }
    isConnection = true;
    wifiPosMananger.init(serverIp, serverProt, this);
    wifiPosMananger.setPositionInterval(1500);
    wifiPosMananger.start();
  }

  public void stop() {
    if (!isConnection) {
      return;
    }
    isConnection = false;
    wifiPosMananger.stop();
  }
}
