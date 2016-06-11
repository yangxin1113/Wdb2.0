package com.zyx.ad.MyAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zyx.R;
import com.zyx.widget.datepicker.MonthDateView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by zyx on 2016/4/22.
 */
public class ProductPopWindow extends PopupWindow{


    private View conentView;
    private Handler mhandler;
    private PropertyAdapter propertyAdapter;
    private ArrayList<LinkedHashMap<String,Object>> mList;

    public ProductPopWindow(final Activity context, Handler handler, ArrayList<LinkedHashMap<String,Object>> datalist) {
        this.mhandler = handler;
        this.mList = datalist;
        this.propertyAdapter = new PropertyAdapter(context, mList, handler);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.product_pop, null);


        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        //this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }

    private void setOnlistener(){

    }


    /**
     * 发消息
     *
     * @param emptyMessage
     * @param data
     */
    private void sendEmptyMessage(int emptyMessage, String date) {
        if (emptyMessage != -1) {
            if (mhandler != null) {
                Message msg = mhandler.obtainMessage();
                msg.what = emptyMessage;
                msg.obj = date;
                mhandler.sendMessage(msg);
            }
        }
    }
}
