package com.zyx.fragment.me;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.widget.MyTitleBar;

/**
 * Created by zyx on 2016/2/23.
 */
public class FragmentMeInfo extends MyBaseFragmentActivity {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private ImageView iv_left;// 返回按钮
    private Button bt_login_out;
    private TextView tv_more;
    private TextView tv_modify_info;




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.bt_login_out:
                ((MyApplication) getApplication()).setUser(null);
                startHomeActivity();
                break;
            case R.id.tv_more:
                break;
            case R.id.tv_modify_info:
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
        setContentView(R.layout.fragment_me_info);

    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        bt_login_out = (Button) findViewById(R.id.bt_login_out);
        tv_more = (TextView)findViewById(R.id.tv_more);
        tv_modify_info = (TextView)findViewById(R.id.tv_modify_info);

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


    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        bt_login_out.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        tv_modify_info.setOnClickListener(this);

    }

    @Override
    protected void getData() {

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
           // overridePendingTransition(R.anim.no_animation, R.anim.bottom_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
