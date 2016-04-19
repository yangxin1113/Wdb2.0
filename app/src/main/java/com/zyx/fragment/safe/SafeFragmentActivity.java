package com.zyx.fragment.safe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.fragment.help.DetailFragmentActivity;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/4/18.
 */
public class SafeFragmentActivity extends MyBaseFragmentActivity {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private ListView lv_list;
    String[] safeTab;
    private List<Map<String, Object>> listdata = new ArrayList<Map<String,Object>>();
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
        setContentView(R.layout.fragment_activity_help);
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        lv_list = (ListView) findViewById(R.id.lv_help);
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
        listdata = getlistData();
        SimpleAdapter     adapter = new SimpleAdapter(this, listdata,
                R.layout.item_help,
                new String[] { "safeTab"},
                new int[] { R.id.tv_help});
        lv_list.setAdapter(adapter);
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

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //utils.showToast(getApplicationContext(), listdata.get(position).get("safeTab").toString());
                Intent i = new Intent(getApplicationContext(), com.zyx.fragment.safe.DetailFragmentActivity.class);
                i.putExtra("safetab", listdata.get(position).get("safeTab").toString());
                startActivity(i);
            }
        });

    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View v) {

    }

    public List<Map<String,Object>> getlistData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < safeTab.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("safeTab", safeTab[i]);
            map.put("img", R.mipmap.img_to_right_true1);
            list.add(map);
        }
        return list;
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
