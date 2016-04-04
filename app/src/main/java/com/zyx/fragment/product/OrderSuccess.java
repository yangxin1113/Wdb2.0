package com.zyx.fragment.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.fragment.Order.OrderFragmentActivity;
import com.zyx.wdb.MainActivity;
import com.zyx.widget.MyTitleBar;

/**
 * Created by zyx on 2016/3/23.
 */
public class OrderSuccess extends MyBaseFragmentActivity{


    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private Button bt_order;
    private Button bt_gogo;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_order:
                Intent i = new Intent(OrderSuccess.this, OrderFragmentActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.bt_gogo:
                i = new Intent(OrderSuccess.this, MainActivity.class);
                startActivity(i);
                finish();
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

    }

    @Override
    protected void init(Bundle arg0) {
     setContentView(R.layout.ordersucc);
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        bt_order = (Button) findViewById(R.id.bt_order);
        bt_gogo = (Button) findViewById(R.id.bt_gogo);
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
        mtb_title.setText(getString(R.string.title_ordersucc_zh));
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
        bt_order.setOnClickListener(this);
        bt_gogo.setOnClickListener(this);
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
