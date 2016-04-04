package com.zyx.fragment.Order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zyx.R;
import com.zyx.ad.MyAdapter.ListOrderAdapter;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;
import com.zyx.fragment.product.ProductFragmentActivity;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Parse;
import com.zyx.widget.CustomDialog;
import com.zyx.widget.DialogWidget;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/26.
 */
public class DetailOrderActivity extends MyBaseFragmentActivity {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private DialogWidget mDialogWidget; //取消订单弹框
    private BitmapUtils bitmapUtils;
    private Button bt_cancle; //取消订单
    private ImageView iv_product;
    private TextView tv_product_name;
    private TextView tv_qprice;
    private TextView tv_firstpay;
    private TextView tv_stage;
    private TextView tv_mprice;
    private TextView tv_ordernumber;
    private TextView tv_creatdate;
    private TextView tv_custname;
    private TextView tv_custphone;
    private TextView tv_address;
    private LinearLayout ll_product;

    /** 相关数据 */
    String ordernumber;
    private List<OrderData> lv_OrderData;
    private String  categoryId;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_cancle_order:
                Intent i = new Intent(DetailOrderActivity.this, CancleOrderActivity.class);
                i.putExtra("OrderNumber",ordernumber);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_product:
                i = new Intent(DetailOrderActivity.this, ProductFragmentActivity.class);
                i.putExtra("categoryId", Parse.getInstance().parseInt(categoryId));
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
                if(data != null ){
                    lv_OrderData = ParserJsonOrderitem.JsontoOrderItem(data);
                    // LogUtil.i("zyx", "data,data:"+lv_OrderData.get(1).getProductnumber());
                    setOrderData(lv_OrderData);

                }


        }
    }

    private void setOrderData(List<OrderData> lv_orderData) {
        OrderData od = lv_orderData.get(0);
        categoryId = od.getCategoryid().toString();
        LogUtil.w("zzzzzzz",od.getCategoryid().toString());
        bitmapUtils.display(iv_product, od.getImageurls());
        tv_product_name.setText(od.getProductname().toString());
        tv_qprice.setText(od.getQuotoprice().toString());
        tv_firstpay.setText(od.getHasfirstpay().toString());
        tv_stage.setText(od.getTimes().toString());
        tv_mprice.setText(od.getRepayment().toString());
        tv_ordernumber.setText(od.getOrdernumber().toString());
        tv_creatdate.setText(od.getOrderdate().toString().replace(".0",""));
        tv_custname.setText(od.getCustname().toString());
        tv_custphone.setText(od.getCustphonenum().toString());
        tv_address.setText(od.getCustaddress().toString().replace("，","  "));
    }

    @Override
    protected void init(Bundle arg0) {

        setContentView(R.layout.fragment_order_detail);
        ordernumber = getIntent().getExtras().get("OrderNumber").toString();
       // LogUtil.w("zzzzzzz",ordernumber);
        Toast.makeText(getApplicationContext(), ordernumber+"zzzzz", Toast.LENGTH_LONG);
    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        bt_cancle = (Button) findViewById(R.id.bt_cancle_order);
        bitmapUtils = new BitmapUtils(DetailOrderActivity.this);

        iv_product = (ImageView) findViewById(R.id.iv_product);
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_qprice = (TextView) findViewById(R.id.tv_qprice);
        tv_firstpay = (TextView) findViewById(R.id.tv_firstpay);
        tv_stage = (TextView) findViewById(R.id.tv_stage);
        tv_mprice = (TextView) findViewById(R.id.tv_mprice);
        tv_ordernumber = (TextView) findViewById(R.id.tv_ordernumber);
        tv_creatdate = (TextView) findViewById(R.id.tv_createdate);
        tv_custname = (TextView) findViewById(R.id.tv_custname);
        tv_custphone = (TextView) findViewById(R.id.tv_custphonenum);
        tv_address = (TextView) findViewById(R.id.tv_custaddress);
        ll_product = (LinearLayout) findViewById(R.id.ll_product);

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
        map.put("orderNumber", ordernumber);
        map.put("condition", "o_detail");
        startRunnable(new getJsonDataThread(Contants.OrdersOfOne, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

        lv_OrderData = new ArrayList<OrderData>();

        mtb_title.setText(getString(R.string.detail_my_order));
        mtb_title.setLeftImageOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });
    }

    @Override
    protected void initEvent() {
        bt_cancle.setOnClickListener(this);
        ll_product.setOnClickListener(this);
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
