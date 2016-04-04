package com.zyx.fragment.life;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.BaseFragment;
import com.zyx.utils.Parse;
import com.zyx.widget.MyTitleBar;
import com.zyx.widget.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyx on 2016/2/15.
 */
public class LifeFragment extends BaseFragment {

    /**
     * 控件相关
     */
    private MyTitleBar mtb_title;// 标题栏





    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }

    }

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_life_index);
    }

    @Override
    protected void initView(View rootview) {
        mtb_title = (MyTitleBar) rootview.findViewById(R.id.mtb_title);

    }

    @Override
    protected void setViewData() {
        mtb_title.setText(getString(R.string.life_bottom_text));
        mtb_title.setLeftVisibility(View.INVISIBLE);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void handlerMessage(Message msg) {

    }

}
