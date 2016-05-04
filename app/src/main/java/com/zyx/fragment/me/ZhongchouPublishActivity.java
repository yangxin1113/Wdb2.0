package com.zyx.fragment.me;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zyx.R;
import com.zyx.ad.MyAdapter.DatePopWindow;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.widget.MyTitleBar;

/**
 * Created by zyx on 2016/3/23.
 */
public class ZhongchouPublishActivity extends MyBaseFragmentActivity{

    private  final String TYPE1 = "adte1";
    private  final String TYPE2 = "adte2";

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏

    private LinearLayout iv_enddate1;
    private EditText et_date1;
    private LinearLayout iv_enddate2;
    private EditText et_date2;
    private DatePopWindow datePopWindow1;
    private DatePopWindow datePopWindow2;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_enddate1:
                datePopWindow1 = new DatePopWindow(ZhongchouPublishActivity.this, handler, TYPE1);
                datePopWindow1.showPopupWindow(iv_enddate1);
                break;
            case R.id.iv_enddate2:
                datePopWindow2 = new DatePopWindow(ZhongchouPublishActivity.this, handler, TYPE2);
                datePopWindow2.showPopupWindow(iv_enddate2);
                break;

        }

    }


    @Override
    protected boolean isUserMapNull() {
        return false;
    }

    @Override
    protected boolean isToken() {
        return false;
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case 0x666:
                String data = (String) (msg.obj);
                if (data!=null){
                    String[] getData = data.split(",");
                    if(getData[1].equals(TYPE1)) {
                        datePopWindow1.showPopupWindow(iv_enddate1);
                        //et_date1.setText(getData[0]);
                    }else{
                        datePopWindow2.showPopupWindow(iv_enddate1);
                        //et_date2.setText(getData[0]);
                    }
                }
                break;
        }

    }

    @Override
    protected void init(Bundle arg0) {
     setContentView(R.layout.activity_zhongchou);
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        iv_enddate1 = (LinearLayout) findViewById(R.id.iv_enddate1);
        //et_date1 = (EditText) findViewById(R.id.et_date1);
        iv_enddate2 = (LinearLayout) findViewById(R.id.iv_enddate2);
        //et_date2 = (EditText) findViewById(R.id.et_date2);

    }

    @Override
    protected View getStatusBarView() {
        return view_status_bar;
    }

    @Override
    protected View getBottomView() {
        return view_navigation_bar;
    }

    @Override
    protected void setViewData() {
        view_status_bar.setBackgroundColor(getResources().getColor(
                R.color.main_color));
        mtb_title.setText(getString(R.string.zhongchou_money_zh));


    }

    @Override
    protected void initEvent() {
        mtb_title.setLeftImageOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });

        iv_enddate1.setOnClickListener(this);
        iv_enddate2.setOnClickListener(this);

    }

    @Override
    protected void getData() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
