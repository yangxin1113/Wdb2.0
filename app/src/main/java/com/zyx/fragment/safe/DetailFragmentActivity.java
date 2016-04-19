package com.zyx.fragment.safe;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
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
    private ListView lv_safe_detail;
    String[] safeTab;
    String[] safemima;
    String[] safetishi;
    String switchtab;
    private List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();;
    private List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();;
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
        setContentView(R.layout.fragment_safe_detail);
        switchtab = getIntent().getStringExtra("safetab");
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        lv_safe_detail = (ListView) findViewById(R.id.lv_safe_detail);
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
        mtb_title.setText(getString(R.string.title_safe_z));
        safeTab = getResources().getStringArray(R.array.safe);
        safemima = getResources().getStringArray(R.array.safe_mima);
        safetishi = getResources().getStringArray(R.array.safe_tishi);
        getlistData();
        SimpleAdapter adapter = null;
        if(switchtab.equals("保护密码")){
            adapter = new SimpleAdapter(this, list1,
                    R.layout.item_text,
                    new String[] { "safemima"},
                    new int[] { R.id.tv_text});
        }else {
            adapter = new SimpleAdapter(this, list2,
                    R.layout.item_text,
                    new String[] { "safetishi"},
                    new int[] { R.id.tv_text});
        }
        lv_safe_detail.setAdapter(adapter);
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

    public void getlistData() {

        for (int i = 0; i < safemima.length; i++) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("safemima", safemima[i]);
            list1.add(map1);
        }

        for (int i = 0; i < safetishi.length; i++) {
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("safetishi", safetishi[i]);
            list2.add(map2);
        }
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
