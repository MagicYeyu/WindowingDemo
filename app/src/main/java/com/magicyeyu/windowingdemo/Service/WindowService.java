package com.magicyeyu.windowingdemo.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.magicyeyu.windowingdemo.Utils.LogUtils;

public class WindowService extends Service implements View.OnClickListener {

    private WindowManager wManager;   //窗口管理者
    private WindowManager.LayoutParams mParams;   //窗口属性
    private boolean flag = true;
    private ImageView smallWindowView;   //悬浮窗的 控件

    @Override
    public IBinder onBind(Intent intent) {   // startservice 运行  所以 不走onband
        return null;
    }

    @Override
    public void onCreate() {
        //初始化
        wManager = (WindowManager) getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0+
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

//        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
        mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        //mParams.format = PixelFormat.RGBA_8888;
        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点
        mParams.width = 800;//窗口的宽和高
        mParams.height = 400;
        mParams.x = 0;//窗口位置的偏移量
        mParams.y = 0;
        //mParams.alpha = 0.1f;//窗口的透明度
        smallWindowView = new ImageView(this);
        Glide.with(this).load("https://b-ssl.duitang.com/uploads/item/201109/18/20110918212819_KmxMa.thumb.1900_0.jpg").into(smallWindowView);

        smallWindowView.setOnClickListener(this);
        smallWindowView.setOnTouchListener(new ImageViewOnTouchListener());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (flag) {
            flag = false;
            wManager.addView(smallWindowView, mParams);//添加窗口
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (smallWindowView.getParent() != null)
            wManager.removeView(smallWindowView);//移除窗口
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(smallWindowView)) {
            flag = true;
            if (smallWindowView.getParent() != null)
                wManager.removeView(smallWindowView);//移除窗口
        }
    }

    private class ImageViewOnTouchListener implements View.OnTouchListener {
        private int x;//每次移动后 x坐标
        private int y;//每次移动后Y坐标
        private int firstX;//按下时x坐标
        private int firstY;//按下时y坐标

        private boolean isRemove = false;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    firstX = (int) event.getRawX();
                    firstY = (int) event.getRawY();

                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    mParams.x = mParams.x + movedX;
                    mParams.y = mParams.y + movedY;
                    wManager.updateViewLayout(view, mParams);
                    break;
                case MotionEvent.ACTION_UP:
                    //判断  是点击还是移动,  如果返回false  则不会触发onclick事件
                    LogUtils.Loge((firstX - (int) event.getRawX()) + "   " + (firstY - (int) event.getRawY()));
                    if (Math.abs(firstX - (int) event.getRawX()) < 5 && Math.abs(firstY - (int) event.getRawY()) < 5)
                        isRemove = false;
                    else
                        isRemove = true;
                default:
                    break;
            }
            return isRemove;
        }
    }

}
