package com.zyx.utils;

import android.content.Context;

/**
 * Created by zyx on 2016/2/15.
 */
public class TwoQuit {

    private long time;
    private final int quitTime = 2000;

    public TwoQuit(){
        time = 0;
    }

    /**
     * 关闭所有界面
     *
     * @param  context
     */
    public void finish(Context context){
        if(System.currentTimeMillis() - time > quitTime){
            time = System.currentTimeMillis();
            MyUtils.getInstance().showToast(context,"再按一次退出");
            return;
        }
        CrashHandler.finish();

    }

    /**
     * 完全退出程序（结束java虚拟机）
     *
     * @param context
     */
    public void exit(Context context){
        if (System.currentTimeMillis() - time > quitTime) {
            time = System.currentTimeMillis();
            MyUtils.getInstance().showToast(context, "再按一次退出");
            return;
        }
        CrashHandler.exit();
    }
}
