package com.davinci.medica.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

public class NotificationPlugin extends CordovaPlugin {
  private static final int NOTIFICATION_ID = 1;

  private Activity mActivity;
	private NotificationManager mNotifyManager;
	private Notification.Builder mBuilder;
  private JSONObject mData = new JSONObject();
  private int mNumber = 1;
  
  public NotificationPlugin() {}

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    mActivity = cordova.getActivity();
    mNotifyManager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
    mBuilder = new Notification.Builder(mActivity);
  }

  public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("setTitle")) {
      String title = args.getString(0);

      if (title != "null") {
        this.setTitle(title);

        return true;
      }

      this.setTitle();

      return true;
    }

    if (action.equals("setText")) {
      String text = args.getString(0);

      if (text != "null") {
        this.setText(text);

        return true;
      }

      this.setText();

      return true;
    }

    if (action.equals("setTicker")) {
      if (args.getString(0) != "null") {
        this.setTicker(args.getString(0));

        return true;
      }

      this.setTicker();

      return true;
    }

    if (action.equals("setInfinityProgress")) {
      this.setInfinityProgress();

      return true;
    }

    if (action.equals("setProgress")) {
      try {
        this.setProgress(args.getInt(0));
      } catch (JSONException e) {
        this.setProgress();
      }

      return true;
    }

    if (action.equals("setOngoing")) {
      this.setOngoing(args.getBoolean(0));

      return true;
    }

    if (action.equals("setAutoCancel")) {
      this.setAutoCancel(args.getBoolean(0));

      return true;
    }

    if (action.equals("show")) {
      this.show();

      return true;
    }

    if (action.equals("dismiss")) {
      this.dismiss();

      return true;
    }

    return false;
  }

  private void setTitle() {
    mData.remove("title");
  }

  private void setTitle(String title) throws JSONException {
    mData.put("title", title);
  }

  private void setText() {
    mData.remove("text");
  }

  private void setText(String text) throws JSONException {
    mData.put("text", text);
  }

  private void setTicker() {
    mData.remove("ticker");
  }

  private void setTicker(String ticker) throws JSONException {
    mData.put("ticker", ticker);
  }

  private void setInfinityProgress() throws JSONException {
    mData.put("infinityProgress", true);

    return;
  }

  private void setProgress() throws JSONException {
    mData.remove("progress");
    mData.remove("infinityProgress");
  }

  private void setProgress(int progress) throws JSONException {
    mData.put("progress", progress);
    mData.put("infinityProgress", false);
  }

  private void setOngoing(boolean ongoing) throws JSONException {
    mData.put("ongoing", ongoing);
  }

  private void setAutoCancel(boolean autoCancel) throws JSONException {
    mData.put("autoCancel", autoCancel);
  }

  private void show() throws JSONException {
    String title = mData.optString("title", "");

    mBuilder
      .setContentTitle(title)
      .setContentText(mData.optString("text", ""))
      .setTicker(mData.optString("ticker", title))
      .setOngoing(mData.optBoolean("ongoing", false))
      .setAutoCancel(mData.optBoolean("autoCancel", true));

    Boolean infinityProgress = mData.optBoolean("infinityProgress", false);
    int progress = mData.optInt("progress", -1);

    if (progress == -1) {
      mBuilder.setProgress(0, 0, false);
    }
    if (progress >= 0) {
      mBuilder.setProgress(100, progress, infinityProgress);
    }

    mBuilder.setSmallIcon(mActivity.getApplicationInfo().icon);

    String packageName = mActivity.getPackageName();
    Intent intent = mActivity.getPackageManager()
      .getLaunchIntentForPackage(packageName);
    if (intent != null) {
      PendingIntent contentIntent = PendingIntent.getActivity(
        mActivity,
        NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
      );

      mBuilder.setContentIntent(contentIntent);
    }

    mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
  }

  private void dismiss() {
    mNotifyManager.cancel(NOTIFICATION_ID);
  }
}
