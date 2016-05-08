package com.zyx.widget;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by zyx on 2016/5/5.
 */
public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    public Context mContext;
    public MyGestureListener(Context context) {
        this.mContext = context;
    }

    @Override
    //按下触摸屏按下时立刻触发
    public boolean onDown(MotionEvent e) {
        Toast.makeText(mContext, "按下 " + e.getAction(), Toast.LENGTH_SHORT).show();
        return false;
    }
    // 短按，触摸屏按下片刻后抬起，会触发这个手势，如果迅速抬起则不会
    @Override
    public void onShowPress(MotionEvent e) {
        Toast.makeText(mContext, "短按 " + e.getAction(), Toast.LENGTH_SHORT).show();
    }
    //释放，手指离开触摸屏时触发(长按、滚动、滑动时，不会触发这个手势)
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Toast.makeText(mContext, "释放" + e.getAction(), Toast.LENGTH_SHORT).show();
        return false;
    }
    // 滑动，按下后滑动
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {
        Toast.makeText(mContext, "滑动 " + e2.getAction(), Toast.LENGTH_SHORT).show();
        return false;
    }
    // 长按，触摸屏按下后既不抬起也不移动，过一段时间后触发
    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(mContext, "长按 " + e.getAction(), Toast.LENGTH_SHORT).show();
    }
    // 滑动，触摸屏按下后快速移动并抬起，会先触发滚动手势，跟着触发一个滑动手势
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        Toast.makeText(mContext, "快速滑动并抬起 " + e2.getAction(), Toast.LENGTH_SHORT).show();
        return false;
    }
    // 双击，手指在触摸屏上迅速点击第二下时触发
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Toast.makeText(mContext, "双击 " + e.getAction(), Toast.LENGTH_SHORT).show();
        return false;
    }
    // 双击后按下跟抬起各触发一次
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Toast.makeText(mContext, "双击和抬起都触发 " + e.getAction(), Toast.LENGTH_SHORT).show();
        return false;
    }
    // 单击
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Toast.makeText(mContext, "单击 " + e.getAction(), Toast.LENGTH_SHORT).show();
        return false;
    }
}
