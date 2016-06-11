package com.zyx.fragment.life;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zyx.R;
import com.zyx.ad.MyAdapter.ListJobAdapter;
import com.zyx.ad.MyAdapter.ListProductAdapter;
import com.zyx.ad.MyAdapter.ListViewAdapterProduct;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.JobItem;
import com.zyx.bean.ProductData;
import com.zyx.contants.Contants;
import com.zyx.fragment.product.ProductFragmentActivity;
import com.zyx.fragment.product.TravelFragmentActivity;
import com.zyx.json.ParserJsonJobitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.MyListView;
import com.zyx.widget.MyTitleBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/22.
 */

public class JobFragmentActivity extends MyBaseFragmentActivity{

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏

    private ListView job_listview;
    private List<JobItem> lv_JobData;
    private ListJobAdapter lv_job_adapter;

    @Override
    public void onClick(View v) {
        switch(v.getId()){

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
    protected void init(Bundle arg0) {

        setContentView(R.layout.fragment_job_index);

    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        job_listview = (ListView) findViewById(R.id.lv_job);

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
        mtb_title.setText(getString(R.string.title_job_zh));

        Map<String, String > map = new HashMap<String, String>();
        map.put("cursor", "0");
        map.put("catId", "0");
        map.put("areaId", "0");
        //map.put("domain","");

        startRunnable(new getJsonDataThread(Contants.PartJobs, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

    }

    @Override
    protected void initEvent() {
//
        //intemOnClick();
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
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");
                String data = (String) (msg.obj);
                LogUtil.i("zyx", "data,data:"+data);
                if(data != null ){
                    LogUtil.i("zyx", "data,data:" + data.toString());
                    lv_JobData = ParserJsonJobitem.JsontoProductItem(data);
                    //Log.w("zzzz+zzz",String.valueOf(lv_JobData.get(15).getId()));
                    lv_job_adapter = new ListJobAdapter(getApplicationContext(), lv_JobData, mListener);
                    lv_job_adapter.notifyDataSetChanged();
                    job_listview.setAdapter(lv_job_adapter);
                }
                break;
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

/*

    private void intemOnClick() {
        job_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utils.showToast(getApplicationContext(), String.valueOf(lv_JobData.get(position).getAddress()) + "zzzzz");
                */
/*Intent i = new Intent(JobFragmentActivity.this, ProductFragmentActivity.class);
                i.putExtra("categoryId", lv_ProductData.get(position).getCategoryId());
                startActivity(i);*//*

                //overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }
*/


    /**
     *  实现类，响应按钮点击事件
     *
     */
    private ListJobAdapter.MyClickListener mListener = new ListJobAdapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            utils.showToast(getApplicationContext(), String.valueOf(lv_JobData.get(position).getAddress()) + "zzzzz");
                Intent i = new Intent(JobFragmentActivity.this, JobDetailFragmentActivity.class);
                i.putExtra("link", lv_JobData.get(position).getLink());
                startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }
    };


}
