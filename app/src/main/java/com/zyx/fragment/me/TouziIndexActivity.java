package com.zyx.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.ad.MyAdapter.ListProjectAdapter;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.ProjectData;
import com.zyx.contants.Contants;
import com.zyx.json.ParserJsonProjectitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.MyTitleBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/23.
 */
public class TouziIndexActivity extends MyBaseFragmentActivity{


    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏


    private ListView lv_project;
    private List<ProjectData> listdata;
    private ListProjectAdapter listProjectAdapter;


    @Override
    public void onClick(View v) {
        switch (v.getId()){


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
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");
                String data = (String) (msg.obj);
                LogUtil.i("zyx", "data,data:"+data);
                if(data != null ){
                    LogUtil.i("zyx", "data,data:"+data.toString());
                    listdata = ParserJsonProjectitem.JsontoProjectItem(data);
                    listProjectAdapter = new ListProjectAdapter(getApplicationContext(),listdata);
                    listProjectAdapter.notifyDataSetChanged();
                    lv_project.setAdapter(listProjectAdapter);
                }
                break;
        }

    }

    @Override
    protected void init(Bundle arg0) {
     setContentView(R.layout.activity_touzi_index);
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        lv_project = (ListView) findViewById(R.id.lv_zhong);
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
        mtb_title.setText(getString(R.string.touzi_money_zh));

        Map<String, String > map = new HashMap<String, String>();
        map.put("customerId", ((MyApplication) getApplication()).getUser().get("CustomerId").toString());
        map.put("condition", "all");
        startRunnable(new getJsonDataThread(Contants.ZhongchouList, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}