package com.zyx.fragment.repay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.ad.MyAdapter.ListOrderAdapter;
import com.zyx.ad.MyAdapter.ListRepayAdapter;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;
import com.zyx.fragment.login.RegisterFragmentActivity;
import com.zyx.fragment.product.ProductFragmentActivity;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.DialogWidget;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/27.
 *
 * 每笔账单的还款详情
 */
public class FragmentDetailActivity extends MyBaseFragmentActivity {

    /** 相关控件*/
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private BitmapUtils bitmapUtils;
    private ImageView iv_product;
    private TextView tv_product_name;
    private LinearLayout ll_product;
    private TextView tv_stage;
    private TextView tv_Qprice;
    private ListView lv_order;
    private ListRepayAdapter repayAdapter;



    /** 相关数据 */
    private List<OrderData> lv_OrderData;
    private String ordernumber;
    private String categoryId;


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
                    lv_OrderData = ParserJsonOrderitem.JsontoOrderRecord(data);
                    // LogUtil.i("zyx", "data,data:"+lv_OrderData.get(1).getProductnumber());
                    setOrderData(lv_OrderData);

                }
        }

    }

    @Override
    protected void init(Bundle arg0) {
      setContentView(R.layout.fragment_repay_detail);
        ordernumber = getIntent().getStringExtra("OrderNumber");
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);

        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_stage = (TextView) findViewById(R.id.tv_stage);
        iv_product = (ImageView) findViewById(R.id.iv_product);
        ll_product = (LinearLayout) findViewById(R.id.ll_product);
        tv_Qprice = (TextView) findViewById(R.id.tv_qprice);
        lv_order = (ListView) findViewById(R.id.lv_repay);

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

        mtb_title.setText(getString(R.string.detail_my_bill));


        Map<String, String> map = new HashMap<String, String>();
        map.put("orderNumber", ordernumber);
        map.put("condition", "d_stage");
        startRunnable(new getJsonDataThread(Contants.OrdersOfOne, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

        lv_OrderData = new ArrayList<OrderData>();
    }

    @Override
    protected void initEvent() {
        ll_product.setOnClickListener(this);
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

        switch(v.getId()){

            case R.id.ll_product:
                Intent i = new Intent(FragmentDetailActivity.this, ProductFragmentActivity.class);
                i.putExtra("categoryId", Integer.valueOf(categoryId));
                startActivity(i);

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


    private void setOrderData(List<OrderData> lv_orderData) {
        bitmapUtils = new BitmapUtils(getApplicationContext());
        OrderData od = lv_orderData.get(lv_orderData.size()-1);
        LogUtil.w("zzzzzzz", od.getCategoryid().toString());
        categoryId = od.getCategoryid().toString();
        bitmapUtils.display(iv_product, od.getImageurls());
        tv_product_name.setText(od.getProductname().toString());
        tv_stage.setText(od.getTime().toString()+"/"+od.getTimes().toString());
        tv_Qprice.setText(od.getQuotoprice().toString());

        repayAdapter = new ListRepayAdapter(getApplicationContext(), lv_OrderData);
        lv_order.setAdapter(repayAdapter);
        repayAdapter.notifyDataSetChanged();

    }

}
