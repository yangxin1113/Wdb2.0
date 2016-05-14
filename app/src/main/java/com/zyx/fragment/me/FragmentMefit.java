package com.zyx.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;

/**
 * Created by zyx on 2016/2/23.
 */
public class FragmentMefit extends MyBaseFragmentActivity {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private ImageView iv_left;// 返回按钮
    private TextView tv_right;//意见反馈

    private Boolean istake = false;
    private LinearLayout ll_user_safe;
    private LinearLayout ll_take;
    private ImageView iv_take;
    private LinearLayout ll_update;






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.tv_right:
                utils.showToast(getApplicationContext(),"意见反馈");
                break;
            case R.id.ll_user_safe:
                Intent i = new Intent(FragmentMefit.this, FragmentMeUserSafe.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.iv_take:
                if(istake){
                    iv_take.setImageDrawable(getResources().getDrawable(R.mipmap.butn_close));
                    istake =false;
                }else{
                    iv_take.setImageDrawable(getResources().getDrawable(R.mipmap.butn_open));
                    istake =true;
                }
                break;
            case R.id.ll_update:
                utils.showToast(getApplicationContext(),"已是最新版本");
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
        setContentView(R.layout.fragment_me_fit);

    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);

        iv_take = (ImageView) findViewById(R.id.iv_take);
        ll_user_safe = (LinearLayout) findViewById(R.id.ll_user_safe);
        ll_update = (LinearLayout) findViewById(R.id.ll_update);


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
        tv_right.setOnClickListener(this);
        iv_take.setOnClickListener(this);
        ll_user_safe.setOnClickListener(this);
        ll_update.setOnClickListener(this);

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
