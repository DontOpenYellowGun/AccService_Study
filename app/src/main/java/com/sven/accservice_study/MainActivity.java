package com.sven.accservice_study;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button;
    private static Handler messageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button1);
//        simulate(button);


        Looper looper = Looper.myLooper();
        messageHandler = new MessageHandler(looper);
        //此处的作用是延迟1秒，然后激活点击事件
        //欢迎转载并说明转自：http://blog.csdn.net/aminfo/article/details/7887964
        new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(1000); //1秒
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                Message message = Message.obtain();
                message.what = 1;
                messageHandler.sendMessage(message);
            }
        }.start();
    }

    class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 1:
                    //模拟点击按钮
                    button.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, button.getLeft() + 5, button.getTop() + 5, 0));
                    button.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, button.getLeft() + 5, button.getTop() + 5, 0));

                    //以下代码模拟点击文本编辑框
                    //et.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, et.getLeft()+5, et.getTop()+5, 0));
                    //et.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, et.getLeft()+5, et.getTop()+5, 0));
                    break;
                default:
                    break;
            }

        }
    }

  /*  public void clickeMe() {
        Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show();
    }

    public void simulate(View view) {
        setSimulateClick(button, 50f, 50f);
    }

    private void setSimulateClick(View view, float x, float y) {
        for (int i = 0; i < 1000; i++) {
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
            clickeMe();
        }
    }
*/

}




