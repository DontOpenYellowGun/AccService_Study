package com.sven.accservice_study;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by Sven on 2016/7/14 0014.
 *
 */

public class MyAccessibility extends AccessibilityService {

    private static final String TAG = "MyAccessibility";
    String[] PACKAGES = {"com.tencent.mm"};

    @Override
    protected void onServiceConnected() {
        Log.e(TAG, "config success!");
       /* AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.packageNames = PACKAGES;
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        accessibilityServiceInfo.notificationTimeout = 1000;
        setServiceInfo(accessibilityServiceInfo);*/


    }


    /*该服务类继承了3个方法：
     1.onServiceConnected()：绑定服务所用方法，一些初始化的操作放在这里面。
     2.onAccessibilityEvent(AccessibilityEvent event)：响应AccessibilityEvent的事件，在用户操作的过程中，系统不断的发送sendAccessibiltyEvent(AccessibilityEvent event)；然后通过onAccessibilityEvent()可以捕捉到该事件，然后分析。
     3.public void onInterrupt()：打断获取事件的过程，本例中暂不适用。
    在onServiceConnected()我们做了一些初始化的工作，通过AccessibilityServiceInfo设置了AccessibilityService的一些参数
    //ccessibilityServiceInfo.packageNames = PACKAGES：响应某个应用的时间，包名为应用的包名；可以用String[]对象传入多包。如果不设置，默认响应所有应用的事件。
    ssibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK：响应时间的类型，事件分很多种：单击、长按、滑动等，需要指定，我设置了所有事件都响应：TYPES_ALL_MASK。
    cessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN：设置回馈给用户的方式，是语音播出还是振动。这个我们稍后尝试配置一些TTS引擎，让它发音。
    cessibilityServiceInfo.notificationTimeout = 1000：看意思就能明白，响应时间的设置。*/

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "accessibilityEvent.getPackageName():" + event.getPackageName());
        Log.e(TAG, "accessibilityEvent.getClassName():" + event.getClassName());
        int eventType = event.getEventType();
        String className= (String) event.getClassName();

        String eventText = "";
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                /*获取被点击的控件信息*/
                AccessibilityNodeInfo noteInfo = event.getSource();
                if (noteInfo.getClassName()!=null&&noteInfo.getClassName().equals("android.widget.TextView")&&noteInfo.getPackageName().equals("com.tencent.mm")) {
                    openPacket();
                }
                
                
                
                
                Log.e(TAG, noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "TYPE_VIEW_FOCUSED";
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventText = "TYPE_VIEW_LONG_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                eventText = "TYPE_VIEW_SELECTED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "TYPE_VIEW_TEXT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                eventText = "TYPE_WINDOW_STATE_CHANGED";

                /*如果进入了启动页面*/
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {

                }

                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                eventText = "TYPE_ANNOUNCEMENT";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                eventText = "TYPE_VIEW_HOVER_ENTER";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                eventText = "TYPE_VIEW_HOVER_EXIT";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "TYPE_VIEW_SCROLLED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
                break;
            default:
                eventText = "default";
                break;
        }
        Log.e(TAG, eventText);
    }

    @SuppressLint("NewApi")
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText("搜索");
            for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }

    }

    @SuppressLint("NewApi")
    private void getPacket() {

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodeInfos = rootNode
                    .findAccessibilityNodeInfosByText("领取红包");
            for (AccessibilityNodeInfo nodeInfo : nodeInfos) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    public void onInterrupt() {

        /*int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        if (content.contains("[微信红包]")) {
                            // 监听到微信红包的notification，打开通知
                            if (event.getParcelableData() != null
                                    && event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event
                                        .getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                } catch (CanceledException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

                break;*/

//        }

    }

    private void setSimulateClick(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }
}
