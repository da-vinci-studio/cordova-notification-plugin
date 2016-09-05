var exec = cordova.require('cordova/exec');

var Notification = function () {};

Notification.prototype.setTitle = function (title) {
  exec(
    null,
    null,
    'NotificationPlugin',
    'setTitle',
    [title]
  );

  return this;
};

Notification.prototype.setText = function (text) {
  exec(
    null,
    null,
    'NotificationPlugin',
    'setText',
    [text]
  );

  return this;
};

Notification.prototype.setTicker = function (ticker) {
  exec(
    null,
    null,
    'NotificationPlugin',
    'setTicker',
    [ticker]
  );

  return this;
};

Notification.prototype.setInfinityProgress = function () {
  exec(
    null,
    null,
    'NotificationPlugin',
    'setInfinityProgress',
    []
  );

  return this;
};

Notification.prototype.setProgress = function (percent) {
  exec(
    null,
    null,
    'NotificationPlugin',
    'setProgress',
    [percent]
  );

  return this;
};

Notification.prototype.setOngoing = function () {
  exec(
    null,
    null,
    'NotificationPlugin',
    'setOngoing',
    []
  );

  return this;
};

Notification.prototype.setAutoCancel = function () {
  exec(
    null,
    null,
    'NotificationPlugin',
    'setAutoCancel',
    []
  );

  return this;
};

Notification.prototype.show = function () {
  exec(
    null,
    null,
    'NotificationPlugin',
    'show',
    []
  );
};

Notification.prototype.dismiss = function () {
  exec(
    null,
    null,
    'NotificationPlugin',
    'dismiss',
    []
  );
};

var notification = new Notification();
module.exports = notification;
