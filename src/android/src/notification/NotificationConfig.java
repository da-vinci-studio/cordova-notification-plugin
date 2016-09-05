package com.davinci_studio.notification;

public class NotificationConfig {
  public String title = "";
  public String text = "";
  public String ticker = "";

  public boolean progressVisibility = false;
  public boolean infinityProgress = false;
  public int progressValue = 0;
  
  public boolean ongoing = false;
  public boolean autoCancel = true;

  public NotificationConfig() {}
}
