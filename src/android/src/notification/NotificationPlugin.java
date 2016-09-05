package com.davinci_studio.notification;

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
import org.json.JSONException;

import java.util.ArrayList;

public class NotificationPlugin extends CordovaPlugin {
  private static final int NOTIFICATION_ID = 1;

  private Activity mActivity;
  private NotificationManager mNotifyManager;
  private Notification.Builder mBuilder;
  private NotificationConfig mNotificationConfig;
  
  public NotificationPlugin() {}

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    mActivity = cordova.getActivity();
    mNotifyManager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
    mBuilder = new Notification.Builder(mActivity);
    mNotificationConfig = new NotificationConfig();
  }

  public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) {
    if (action.equals("setTitle")) {
      String title = "";

      try {
        title = args.getString(0);
      } catch (JSONException e) {}

      this.setTitle(title);

      return true;
    }

    if (action.equals("setText")) {
      String text = "";
      
      try {
        text = args.getString(0);
      } catch (JSONException e) {}

      this.setText(text);

      return true;
    }

    if (action.equals("setTicker")) {
      String ticker = "";
      
      try {
        ticker = args.getString(0);
      } catch (JSONException e) {}

      this.setTicker(ticker);

      return true;
    }

    if (action.equals("setInfinityProgress")) {
      this.setInfinityProgress();

      return true;
    }

    if (action.equals("setProgress")) {
      int progress = 0;

      try {
        progress = args.getInt(0);
      } catch (JSONException e) {
        this.hideProgress();

        return;
      }

      this.setProgress(progress);

      return true;
    }

    if (action.equals("setOngoing")) {
      boolean ongoing = false;

      try {
        ongoing = args.getBoolean(0);
      } catch (JSONException e) {}

      this.setOngoing(ongoing);

      return true;
    }

    if (action.equals("setAutoCancel")) {
      boolean autoCancel = false;

      try {
        autoCancel = args.getBoolean(0);
      } catch (JSONException e) {}

      this.setOngoing(autoCancel);

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

  private void setTitle(String title) {
    if ("null" == title) {
      mNotificationConfig.title = "";

      return;
    }

    mNotificationConfig.title = title;
  }

  private void setText(String text) {
    if ("null" == text) {
      mNotificationConfig.text = "";

      return;
    }

    mNotificationConfig.text = text;
  }

  private void setTicker(String ticker) {
    if ("null" == ticker) {
      mNotificationConfig.ticker = "";

      return;
    }

    mNotificationConfig.ticker = ticker;
  }

  private void setInfinityProgress() {
    mNotificationConfig.progressVisibility = true;
    mNotificationConfig.infinityProgress = true;

    return;
  }

  private void hideProgress() {
    mNotificationConfig.progressVisibility = false;
  }

  private void setProgress(int progress) {
    mNotificationConfig.progressVisibility = true;
    mNotificationConfig.progressValue = progress;
    mNotificationConfig.infinityProgress = false;
  }

  private void setOngoing(boolean ongoing) {
    mNotificationConfig.ongoing = ongoing;
  }

  private void setAutoCancel(boolean autoCancel) {
    mNotificationConfig.autoCancel = autoCancel;
  }

  private void show() {
    String ticker = mNotificationConfig.ticker;
    if ("" == mNotificationConfig.ticker) {
      ticker = mNotificationConfig.title;
    }

    mBuilder
      .setContentTitle(mNotificationConfig.title)
      .setContentText(mNotificationConfig.text)
      .setTicker(ticker)
      .setOngoing(mNotificationConfig.ongoing)
      .setAutoCancel(mNotificationConfig.autoCancel);

    if (!mNotificationConfig.progressVisibility) {
      mBuilder.setProgress(0, 0, false);
    }

    if (mNotificationConfig.progressVisibility && mNotificationConfig.progressValue >= 0) {
      mBuilder.setProgress(100, mNotificationConfig.progressValue, mNotificationConfig.infinityProgress);
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
