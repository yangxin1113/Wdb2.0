package com.zyx.ad.ScaleView;

import android.annotation.TargetApi;
import android.view.View;

@TargetApi(16)
/**
 * Created by zyx on 2015/10/15.
 */
public class SDK16 {

    public static void postOnAnimation(View view, Runnable r) {
        view.postOnAnimation(r);
    }
}
