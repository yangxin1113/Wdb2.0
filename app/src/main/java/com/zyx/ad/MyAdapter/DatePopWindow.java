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
import java.util.List;


/**
 * Created by zyx on 2016/4/22.
 */
public class DatePopWindow extends PopupWindow{
    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_date;
    private TextView tv_week;
    private TextView tv_today;
    private MonthDateView monthDateView;

    private View conentView;
    private Handler mhandler;
    private String type;

    public DatePopWindow(final Activity context, Handler handler, final String type) {
        this.mhandler = handler;
        this.type = type;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.activity_date, null);

        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(12);
        list.add(15);
        list.add(16);

        iv_left = (ImageView) conentView.findViewById(R.id.iv_left);
        iv_right = (ImageView) conentView.findViewById(R.id.iv_right);
        monthDateView = (MonthDateView) conentView.findViewById(R.id.monthDateView);
        tv_date = (TextView) conentView.findViewById(R.id.date_text);
        tv_week  =(TextView) conentView.findViewById(R.id.week_text);
        tv_today = (TextView) conentView.findViewById(R.id.tv_today);
        monthDateView.setTextView(tv_date,tv_week);
        monthDateView.setDaysHasThingList(list);
        
        monthDateView.setDateClick(new MonthDateView.DateClick() {

            @Override
            public void onClickOnDate() {
                Toast.makeText(context, "点击了：" +monthDateView.getmSelYear()+"-"+monthDateView.getmSelMonth()+"-"+monthDateView.getmSelDay(), Toast.LENGTH_SHORT).show();
                String date = monthDateView.getmSelYear()+"-"+monthDateView.getmSelMonth()+"-"+monthDateView.getmSelDay();
                sendEmptyMessage(0x666, date+","+type);
            }
        });
        setOnlistener();

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
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
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
        iv_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onLeftClick();
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onRightClick();
            }
        });

        tv_today.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.setTodayToView();
            }
        });
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
