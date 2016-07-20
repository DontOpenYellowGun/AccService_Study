package com.sven.accservice_study;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by Sven on 2016/7/14 0014.
 */

public class MyAccessibility extends AccessibilityService {

    private static final String TAG = "MyAccessibility";
    //    String[] PACKAGES = {"com.tencent.mm"};
    String[] PACKAGES = {"com.sven.accservice_test"};

    @Override
    protected void onServiceConnected() {
        Log.e(TAG, "config success!");
        /*辅助服务要观察的app包名以及事件类型的配置*/
        /*AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.packageNames = PACKAGES;
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        accessibilityServiceInfo.notificationTimeout = 1000;
        setServiceInfo(accessibilityServiceInfo);*/
    }


    /*该服务类继承了3个方法：
     1.onServiceConnected()：绑定服务所用方法，一些初始化的操作放在这里面。

     2.onAccessibilityEvent(AccessibilityEvent event)：响应AccessibilityEvent的事件，在用户操作的过程
     中，系统不断的发送sendAccessibiltyEvent(AccessibilityEvent event)；然后通过onAccessibilityEvent()
     可以捕捉到该事件，然后分析。

     3.public void onInterrupt()：打断获取事件的过程，本例中暂不适用。
     在onServiceConnected()我们做了一些初始化的工作，通过AccessibilityServiceInfo设置了AccessibilityService的一些参数
     //ccessibilityServiceInfo.packageNames = PACKAGES：响应某个应用的时间，包名为应用的包名；可以用String[]
     对象传入多包。如果不设置，默认响应所有应用的事件。

     ssibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK：响应时间的类型，事件分很多种：
     单击、长按、滑动等，需要指定，我设置了所有事件都响应：TYPES_ALL_MASK。

     cessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN：设置回馈给用户的方式，
     是语音播出还是振动。这个我们稍后尝试配置一些TTS引擎，让它发音。

     cessibilityServiceInfo.notificationTimeout = 1000：看意思就能明白，响应时间的设置。*/

    /*这些设置很重要，因为AccessibilityService服务不是用户去开启的。我们无法用startService()方法启动它，
    所以我连activity都没写。因为它是通过该配置让系统自动识别的，识别成功后，就会在我们手机里面看到该服务，
    然后需要手动开启该服务*/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {


        int eventType = event.getEventType();
        String packName = (String) event.getPackageName();
        String className = (String) event.getClassName();
        Log.e(TAG, "__________start____________");
        Log.e(TAG, "accessibilityEvent.getPackageName():" + packName);
        Log.e(TAG, "accessibilityEvent.getClassName():" + className);

        String eventText = "";
        switch (eventType) {

            /*在调用这个getSource()方法时，需要事先配置一下，也就是需要android：canRetrieveWindowContent="true"，
            这个属性是API14（android4.0）给出的，它的配置方法受限于需要采用XML初始化的方式配置AccessibilityService，
            也就是我们现在不在onServiceConnected()里配置初始化信息，改在xml里实现。那么我们现在就要将上面的程序修改一下。
            首先，删除onServiceConnected()的配置(因为getSource()不是所有的操作都有该方法，所以得在特定的方法中实现该方法，
            我们选择在TYPE_VIEW_CLICKED里实现)*/

            /*view被点击*/
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "TYPE_VIEW_CLICKED";
                /*获得控件信息*/
                AccessibilityNodeInfo noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");
                break;
            /*view获取到焦点*/
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "TYPE_VIEW_FOCUSED";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");
                break;
            /*view被长按*/
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventText = "TYPE_VIEW_LONG_CLICKED";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");
                break;
            /*view被选择*/
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                eventText = "TYPE_VIEW_SELECTED";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            /*view的文本发生变化*/
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "TYPE_VIEW_TEXT_CHANGED";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            /*窗口状态改变*/
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                eventText = "TYPE_WINDOW_STATE_CHANGED";
                /*如果在首页*/
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
                    jump();
                }
                /*如果是搜索主页*/
                else if (className.equals("com.tencent.mm.plugin.search.ui.FTSMainUI")) {
                    jump2();
                }
                /*如果是二级文章搜索页面*/
                else if (className.equals("com.tencent.mm.plugin.webview.ui.tools.fts.FTSSearchTabWebViewUI")) {
                    jump3();
                    getWebVIew();
                }
               /* *//*如果是文章列表展示页面*//*
                else if (className.equals("org.chromium.content.browser.ContentViewCore")) {
                    jump3();
                }*/
                break;
            /*通知栏状态改变*/
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                eventText = "TYPE_ANNOUNCEMENT";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                eventText = "TYPE_VIEW_HOVER_ENTER";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                eventText = "TYPE_VIEW_HOVER_EXIT";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "TYPE_VIEW_SCROLLED";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");

                break;
            /*窗口的内容发生变化*/
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
                noteInfo = event.getSource();
                Log.e(TAG, noteInfo != null ? noteInfo.toString() : "为空");
                break;
        }
        Log.e(TAG, "事件类型:     " + eventText);
        Log.e(TAG, "__________end____________");
    }

    /*微信文章搜索页面*/
    /*com.tencent.mm.plugin.webview.ui.tools.fts.FTSSearchTabWebViewUI*/

    /*微信搜索主页面*/
    /*com.tencent.mm.plugin.search.ui.FTSMainUI*/

    /*微信文章列表展示的webView页面*/
    /*org.chromium.content.browser.ContentViewCore*/

    @SuppressLint("NewApi")
    private void jump() {
        AccessibilityNodeInfo noteInfo = getRootInActiveWindow();
        if (noteInfo != null) {

           /* if (noteInfo.getContentDescription().equals("搜索")) {
                Log.e(TAG, "搜索");
            }*/
            Log.e(TAG, "jump");
            Log.e(TAG, "noteInfo.getContentDescription():" + noteInfo.getContentDescription());
            Log.e(TAG, "noteInfo.getContentDescription():" + noteInfo.getClassName());

            List<AccessibilityNodeInfo> list = noteInfo.findAccessibilityNodeInfosByText("");
            Log.e(TAG, "list.size():" + list.size() + "");

            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(0).getChildCount());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(1).getChildCount());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(2).getChildCount());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(3).getChildCount());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(4).getChildCount());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(5).getChild(1).getContentDescription());

            noteInfo.getChild(5).getChild(1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    @SuppressLint("NewApi")
    private void jump2() {
        AccessibilityNodeInfo noteInfo = getRootInActiveWindow();
        if (noteInfo != null) {
            /*List<AccessibilityNodeInfo> list = noteInfo
                    .findAccessibilityNodeInfosByText("2");
            for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }*/
            Log.e(TAG, "jump2");
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChildCount());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(0).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(1).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(2).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(3).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(4).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(5).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(6).getClassName());

            noteInfo.getChild(3).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    @SuppressLint("NewApi")
    private void jump3() {
        AccessibilityNodeInfo noteInfo = getRootInActiveWindow();
        if (noteInfo != null) {

            List<AccessibilityNodeInfo> list = noteInfo.findAccessibilityNodeInfosByText("搜索文章");

            Log.e(TAG, "jump3");
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChildCount());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(0).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(1).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(2).getClassName());
            Log.e(TAG, "noteInfo.getChildCount():" + noteInfo.getChild(1).getChild(0).getChild(0).getClassName());

            Log.e(TAG, "list.size():" + list.size());

            //Log.e(TAG, "noteInfo.getChild(1).getChild(2).getClassName():" + noteInfo.getChild(1).getChild(2).getClassName());

            Bundle arguments22 = new Bundle();
            arguments22.putString(AccessibilityNodeInfo.ACTION_ARGUMENT_HTML_ELEMENT_STRING, "BUTTON");
            noteInfo.getChild(1).getChild(0).getChild(0).performAction(AccessibilityNodeInfo.ACTION_NEXT_HTML_ELEMENT, arguments22);

            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    "你好");
            Log.e(TAG, "noteInfo.isVisibleToUser():" + noteInfo.isVisibleToUser());

           /* if (noteInfo.isVisibleToUser() && noteInfo.getChild(1) != null) {
                noteInfo.getChild(1).getChild(2).performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT.getId(), arguments);
            }*/


//            noteInfo.getChild(1).getChild(2).setText("你好");
           /* for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE);
            }*/
        }
    }

    @SuppressLint("NewApi")
    private void getWebVIew() {
        AccessibilityNodeInfo noteInfo = getRootInActiveWindow();
        if (noteInfo != null) {
            /*List<AccessibilityNodeInfo> list = noteInfo
                    .findAccessibilityNodeInfosByText("2");
            for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }*/

            Log.e(TAG, "getWebVIew:");
        }
    }


    /*当服务事件被中断时*/
    @Override
    public void onInterrupt() {

    }
}
