package com.zyx.fragment.product;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;

/**
 * Created by zyx on 2016/2/24.
 */
public class TravelFragmentActivity extends MyBaseFragmentActivity{
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
        setContentView(R.layout.product_fragment_travel);

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
