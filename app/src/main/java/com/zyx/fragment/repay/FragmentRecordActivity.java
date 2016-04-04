package com.zyx.fragment.repay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.zyx.R;
import com.zyx.ad.MyAdapter.ListOrderAdapter;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;
import com.zyx.fragment.login.RegisterFragmentActivity;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/27.
 */
public class FragmentRecordActivity extends MyBaseFragmentActivity {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏

    private ListOrderAdapter lv_order_adapter;
    private List<OrderData> lv_OrderData;
    private ListView lv_order_view;
    private String customerId;

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
        switch (msg.what) {
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");
                String data = (String) (msg.obj);
                LogUtil.i("zyx", "data,data:" + data);
                if (data != null) {
                    lv_OrderData = ParserJsonOrderitem.JsontoOrderItem(data);
                    // LogUtil.i("zyx", "data,data:"+lv_OrderData.get(1).getProductnumber());
                    lv_order_adapter = new ListOrderAdapter(FragmentRecordActivity.this, lv_OrderData, "还款详情", mListener);
                    lv_order_adapter.notifyDataSetChanged();
                    lv_order_view.setAdapter(lv_order_adapter);

                }
        }

    }

    @Override
    protected void init(Bundle arg0) {
      setContentView(R.layout.fragmentactivity_repay_record);
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);

        lv_order_view = (ListView) findViewById(R.id.lv_order);
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

        mtb_title.setText(getString(R.string.history_my_bill));
        mtb_title.setLeftImageOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });

        Map<String, String> map = new HashMap<String, String>();
        map.put("customerId", ((MyApplication) getApplication()).getUser().get("CustomerId").toString());
        map.put("condition", "all");
        startRunnable(new getJsonDataThread(Contants.OrderList, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

        lv_OrderData = new ArrayList<OrderData>();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
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

    /**
     * 实现类，响应按钮点击事件
     */
    private ListOrderAdapter.MyClickListener mListener = new ListOrderAdapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            //Toast.makeText(getContext(), "ordernumber" + lv_OrderData.get(position).getProductname(),Toast.LENGTH_LONG);
            Intent i = new Intent(FragmentRecordActivity.this, FragmentDetailActivity.class);
            i.putExtra("OrderNumber", lv_OrderData.get(position).getOrdernumber());
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }
    };
}
