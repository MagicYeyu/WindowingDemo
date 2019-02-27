package com.magicyeyu.windowingdemo;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

public class MyApplication extends Application {

    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }

    /**
     * 创建全局变量
     * 全局变量一般都比较倾向于创建一个单独的数据类文件，并使用static静态变量
     * <p>
     * 这里使用了在Application中添加数据的方法实现全局变量
     * 注意在AndroidManifest.xml中的Application节点添加android:name=".MyApplication"属性
     */
    private static WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

    public static WindowManager.LayoutParams getMywmParams() {
        return wmParams;
    }
}
