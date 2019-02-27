package com.magicyeyu.windowingdemo.Utils;

import android.util.Log;

public class LogUtils {

    private static String TAG = "LogUtils";

    public static void LogInDetail(String content) {
        String dividingLine = "╔===========================================================================================\n";
        String dividingLine2 = "╚===========================================================================================\n";
        Log.e(TAG, dividingLine);
        setUpContent(content);
        Log.e(TAG, dividingLine2);
    }

    public static void LogInfo(String content) {
        setUpContent(content);
    }

    public static void Loge(String content) {
        Log.e(TAG, content);
    }

    private static void setUpContent(String content) {
        StackTraceElement targetStackTraceElement = getStackTraceElement();
        Log.e(TAG, "\n位置-> (" + targetStackTraceElement.getFileName() + ":" + targetStackTraceElement.getLineNumber() + ")" + " -> " + targetStackTraceElement.getMethodName());
        Log.e(TAG, content);
    }

    private static StackTraceElement getStackTraceElement() {
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(LogUtils.class.getName());
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }


}
