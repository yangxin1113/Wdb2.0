package com.zyx.fragment.help;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/4/18.
 */
public class DetailFragmentActivity extends MyBaseFragmentActivity {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private TextView tv_detail;
    String[] helpTab;
    String[] helpDetail;
    String switchtab;
    private LinkedHashMap<String, String> listdata = new LinkedHashMap<String, String>();
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
        setContentView(R.layout.fragment_help_detail);
        switchtab = getIntent().getStringExtra("helptab");
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        tv_detail = (TextView)findViewById(R.id.tv_detail);
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
        mtb_title.setText(getString(R.string.title_help_z));
        helpTab = getResources().getStringArray(R.array.help);
        helpDetail = getResources().getStringArray(R.array.help_detail);
        listdata = getlistData();

        Iterator iter = listdata.entrySet().iterator();
        while (iter.hasNext()){
            LinkedHashMap.Entry entry = (LinkedHashMap.Entry)iter.next();
            if(entry.getKey().equals(switchtab))
                tv_detail.setText(entry.getValue().toString());
        }
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


    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View v) {

    }

    public LinkedHashMap<String,String> getlistData() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < helpTab.length; i++) {
            map.put(helpTab[i], helpDetail[i]);
        }
        return map;
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
