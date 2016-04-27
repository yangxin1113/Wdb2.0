package com.zyx.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.ad.MyAdapter.DatePopWindow;
import com.zyx.ad.MyAdapter.GridViewAdapterProduct;
import com.zyx.ad.MyAdapter.ListProjectAdapter;
import com.zyx.ad.MyAdapter.PropertyAdapter;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.ProjectData;
import com.zyx.contants.Contants;
import com.zyx.json.ParserJsonProductitem;
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
public class ZhongchouIndexActivity extends MyBaseFragmentActivity{


    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private ImageView iv_left;// 返回按钮
    private TextView tv_right;// 历史记录
    private Button bt_public;

    private ListView lv_project;
    private List<ProjectData> listdata;
    private ListProjectAdapter listProjectAdapter;



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_public:
                Intent i= new Intent(ZhongchouIndexActivity.this, ZhongchouPublishActivity.class);
                startActivity(i);
                break;
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
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
     setContentView(R.layout.activity_zhongchou_index);
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        bt_public = (Button) findViewById(R.id.bt_public);
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

        Map<String, String > map = new HashMap<String, String>();
        map.put("customerId", ((MyApplication) getApplication()).getUser().get("CustomerId").toString());
        startRunnable(new getJsonDataThread(Contants.ZhongchouList, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

    }

    @Override
    protected void initEvent() {
        bt_public.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
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
