package com.zyx.fragment.repay;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.ad.MyAdapter.ListRepayAdapter;
import com.zyx.ad.Passwordborder.PayPasswordView;
import com.zyx.application.MyApplication;
import com.zyx.base.BaseFragment;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;
import com.zyx.fragment.product.ProductFragmentActivity;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.CustomDialog;
import com.zyx.widget.DialogWidget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/28.
 * 每笔订单的还款详情
 */
public class RecordFragment extends BaseFragment {

    /**
     * 相关控件
     */
    private BitmapUtils bitmapUtils;
    private DialogWidget mDialogWidget; //弹框
    private ImageView iv_product;
    private TextView tv_product_name;
    private LinearLayout ll_product;
    private TextView tv_stage;
    private TextView tv_Qprice;
    private ListView lv_order;
    private ListRepayAdapter repayAdapter;


    /**
     * 相关数据
     */
    private List<OrderData> lv_OrderData;
    private String ordernumber;
    private String categoryId;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_product:
                Intent i = new Intent(getActivity(), ProductFragmentActivity.class);
                i.putExtra("categoryId", Integer.valueOf(categoryId));
                startActivity(i);
                break;
        }


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
    protected void init() {

        setLayoutRes(R.layout.fragment_repay_record);
        ordernumber = getActivity().getIntent().getStringExtra("OrderNumber");
    }

    @Override
    protected void initView(View rootview) {


        tv_product_name = (TextView) rootview.findViewById(R.id.tv_product_name);
        tv_stage = (TextView) rootview.findViewById(R.id.tv_stage);
        iv_product = (ImageView) rootview.findViewById(R.id.iv_product);
        ll_product = (LinearLayout) rootview.findViewById(R.id.ll_product);
        tv_Qprice = (TextView) rootview.findViewById(R.id.tv_qprice);
        lv_order = (ListView) rootview.findViewById(R.id.lv_repay);
    }

    @Override
    protected void setViewData() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("orderNumber", ordernumber);
        map.put("condition", "a_stage");
        startRunnable(new getJsonDataThread(Contants.OrdersOfOne, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
    }

    @Override
    protected void initEvent() {
        ll_product.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }


    private void setOrderData(List<OrderData> lv_orderData) {
        if (!lv_orderData.isEmpty()) {
            bitmapUtils = new BitmapUtils(getContext());

            OrderData od = lv_orderData.get(lv_orderData.size() - 1);
            LogUtil.w("zzzzzzz", od.getCategoryid().toString());
            categoryId = od.getCategoryid().toString();
            bitmapUtils.display(iv_product, od.getImageurls());
            tv_product_name.setText(od.getProductname().toString());
            tv_stage.setText(od.getTime().toString() + "/" + od.getTimes().toString());
            tv_Qprice.setText(od.getQuotoprice().toString());

            repayAdapter = new ListRepayAdapter(getContext(), lv_OrderData);
            repayAdapter.notifyDataSetChanged();
            lv_order.setAdapter(repayAdapter);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //fragment可见时执行加载数据或者进度条等
            setViewData();
        } else {
            //不可见时不执行操作
        }
    }

}
