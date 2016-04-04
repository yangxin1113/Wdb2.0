package com.zyx.ad.ScaleView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zyx on 2015/10/15.
 */
public class HackyViewPager extends ViewPager {
    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        }  catch (IllegalArgumentException e) {
            //不理会
            return false;
        }catch(ArrayIndexOutOfBoundsException e ){
            //不理会
            return false;
        }
    }
}
