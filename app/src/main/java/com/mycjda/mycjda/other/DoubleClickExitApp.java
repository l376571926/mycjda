package com.mycjda.mycjda.other;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class DoubleClickExitApp implements Runnable {

    private Context mcontext;
    /**
     * 是否是再次点击back键
     */
    private boolean isDoubleClick;
    private Handler mHandler;
    /**
     * 退出提示Toast
     */
    private Toast mExitToast;
    private static DoubleClickExitApp doubleClickExitApp;

    public static DoubleClickExitApp getInstance() {
        if (doubleClickExitApp == null) {
            doubleClickExitApp = new DoubleClickExitApp();
        }
        return doubleClickExitApp;
    }

    /**
     * 所在Activity中的点击返回事件处理
     */
    public boolean onKeyDown(Context context) {
        if (mcontext == null) {
            this.mcontext = context;
        }
        if (mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        if (isDoubleClick) {
            mHandler.removeCallbacks(this);
            if (mExitToast != null) {
                mExitToast.cancel();
            }
            doubleClickExitApp = null;
            mExitToast = null;
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
            return true;
        } else {
            if (mExitToast == null) {
                mExitToast = Toast.makeText(mcontext, "再按一次退出程序", Toast.LENGTH_SHORT);
            }
            mExitToast.show();
            isDoubleClick = true;
            mHandler.postDelayed(this, 2000);
            return true;
        }
    }

    @Override
    public void run() {
        isDoubleClick = false;
        if (mExitToast != null) {
            mExitToast.cancel();
        }
    }
}