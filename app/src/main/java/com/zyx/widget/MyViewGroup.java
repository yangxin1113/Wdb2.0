package com.zyx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zyx on 2016/3/1.
 *
 * 商品属性选择
 */
public class MyViewGroup extends ViewGroup {

    private final static int VIEW_MARGIN = 15;

    public MyViewGroup(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public MyViewGroup(Context context){
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int stages = 1;
        int stagesHeight = 0;
        int stagesWidth = 0;

        int wholeWidth = MeasureSpec.getSize(widthMeasureSpec);

        for(int i=0; i<getChildCount(); i++){
            final View child= getChildAt(i);
            //measure
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            stagesWidth += (child.getMeasuredWidth() + VIEW_MARGIN);
            stagesHeight = child.getMeasuredHeight();
            if(stagesWidth > wholeWidth){
                stages++;
                stagesWidth = child.getMeasuredWidth();
            }
        }

        int wholeHeight = (stagesHeight + VIEW_MARGIN)*stages;

        setMeasuredDimension(resolveSize(wholeWidth, widthMeasureSpec),resolveSize(wholeHeight, heightMeasureSpec));
    }

    private int jiange = 10;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int row = 0;
        int lengthX = l;
        int lengthY = t;

        for(int i=0; i<count; i++){
            final View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();

            if(i ==0){
                lengthX +=width+VIEW_MARGIN;
            }else{
                lengthX +=width+VIEW_MARGIN+jiange;
            }

            lengthY = row*(height + VIEW_MARGIN) + VIEW_MARGIN +height +t;
            if(lengthX > r){
                lengthX = width + VIEW_MARGIN + l;
                row++;
                lengthY = row*(height + VIEW_MARGIN) + VIEW_MARGIN +height +t;
            }
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
        }
    }
}
