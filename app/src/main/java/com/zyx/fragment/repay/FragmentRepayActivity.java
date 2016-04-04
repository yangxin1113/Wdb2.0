package com.zyx.fragment.repay;

import android.app.Activity;
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
import com.zyx.ad.MyAdapter.ListOrderAdapter;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;
import com.zyx.fragment.Order.DetailOrderActivity;
import com.zyx.fragment.login.RegisterFragmentActivity;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.PostDataThread;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.wdb.MainActivity;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/27.
 */
public class FragmentRepayActivity extends MyBaseFragmentActivity {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private ImageView iv_left;// 返回按钮
    private TextView tv_right;// 记录
    private Button bt_gogo;

    private ListOrderAdapter lv_order_adapter;
    private List<OrderData> lv_OrderData;
    private ListView lv_order_view;
    private View emptyView ;
    private String customerId;


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;

            case R.id.tv_right:
                startActivity(new Intent(getApplicationContext(),
                        FragmentRecordActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.bt_gogo:
                Intent i = new Intent(FragmentRepayActivity.this, MainActivity.class);
                startActivity(i);
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
        switch (msg.what) {
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");
                String data = (String) (msg.obj);
                LogUtil.i("zyx", "data,data:" + data);
                if (data != null) {
                    lv_OrderData = ParserJsonOrderitem.JsontoOrderItem(data);
                    // LogUtil.i("zyx", "data,data:"+lv_OrderData.get(1).getProductnumber());
                    lv_order_adapter = new ListOrderAdapter(FragmentRepayActivity.this, lv_OrderData, "我要还款", mListener);
                    lv_order_adapter.notifyDataSetChanged();
                    lv_order_view.setAdapter(lv_order_adapter);

                }
        }

    }

    @Override
    protected void init(Bundle arg0) {
      setContentView(R.layout.fragmentactivity_repay);
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        bt_gogo = (Button) findViewById(R.id.bt_gogo);
        lv_order_view = (ListView) findViewById(R.id.lv_order);
        emptyView = findViewById(R.id.rl_empty);
        lv_order_view.setEmptyView(emptyView);
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


        Map<String, String> map = new HashMap<String, String>();
        map.put("customerId", ((MyApplication) getApplication()).getUser().get("CustomerId").toString());
        map.put("condition", "stage");
        startRunnable(new getJsonDataThread(Contants.OrderList, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

        lv_OrderData = new ArrayList<OrderData>();
    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        bt_gogo.setOnClickListener(this);
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

    /**
     * 实现类，响应按钮点击事件
     */
    private ListOrderAdapter.MyClickListener mListener = new ListOrderAdapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            //Toast.makeText(getContext(), "ordernumber" + lv_OrderData.get(position).getProductname(),Toast.LENGTH_LONG);
            Intent i = new Intent(FragmentRepayActivity.this, FragmentDetailRepay.class);
            i.putExtra("OrderNumber", lv_OrderData.get(position).getOrdernumber());
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }
    };
}
