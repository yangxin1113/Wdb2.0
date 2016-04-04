package com.zyx.wdb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;

/**
 * Created by zyx on 2016/3/22.
 */
public class WelcomeActivity  extends MyBaseFragmentActivity{

    private final long SPLASH_LENGTH = 2000;
    Handler handler = new Handler();

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
        setContentView(R.layout.welcom);
        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_LENGTH);//2秒后跳转至应用主界面MainActivity
    }

    @Override
    protected void initView() {

    }

    @Override
    protected View getStatusBarView() {
        return null;
    }

    @Override
    protected View getBottomView() {
        return null;
    }

    @Override
    protected void setViewData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
