package com.zyx.fragment.repay;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.BaseFragment;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.wdb.MainActivity;
import com.zyx.widget.DialogWidget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/28.
 */
public class NextFragment extends BaseFragment{

    private DialogWidget mDialogWidget; //弹框
    private Button bt_auto; //自动还款

    private TextView tv_product_name;
    private TextView tv_stage;
    private TextView tv_mprice;
    private TextView tv_creatdate;
    private TextView tv_begindate;
    private TextView tv_enddate;

    private LinearLayout ll_content;
    private RelativeLayout rl_empty;
    private Button bt_gogo;

    /** 相关数据 */
    private List<OrderData> lv_OrderData;
    private String  categoryId;
    private String ordernumber;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_auto:

                break;
            case R.id.bt_gogo:
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
        }
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

    @Override
    protected void init() {

        setLayoutRes(R.layout.fragment_repay_next);
        ordernumber = getActivity().getIntent().getStringExtra("OrderNumber");
    }

    @Override
    protected void initView(View rootview) {

        bt_auto = (Button) rootview.findViewById(R.id.bt_auto);
        tv_product_name = (TextView) rootview.findViewById(R.id.tv_product_name);
        tv_stage = (TextView) rootview.findViewById(R.id.tv_stage);
        tv_mprice = (TextView) rootview.findViewById(R.id.tv_mprice);
        tv_creatdate = (TextView) rootview.findViewById(R.id.tv_createdate);
        tv_begindate = (TextView) rootview.findViewById(R.id.tv_begindate);
        tv_enddate = (TextView) rootview.findViewById(R.id.tv_enddate);

        ll_content = (LinearLayout) rootview.findViewById(R.id.ll_content);
        rl_empty = (RelativeLayout) rootview.findViewById(R.id.rl_empty);
        bt_gogo = (Button) rootview.findViewById(R.id.bt_gogo);
    }

    @Override
    protected void setViewData() {

        Map<String, String > map = new HashMap<String, String>();
        map.put("orderNumber", ordernumber);
        map.put("condition", "n_stage");
        startRunnable(new getJsonDataThread(Contants.OrdersOfOne, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

    }

    @Override
    protected void initEvent() {
        bt_auto.setOnClickListener(this);
        bt_gogo.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }


    private void setOrderData(List<OrderData> lv_orderData) {
        if(!lv_orderData.isEmpty()){
            rl_empty.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);
            OrderData od = lv_orderData.get(0);
            categoryId = od.getCategoryid().toString();
            LogUtil.w("zzzzzzz", od.getCategoryid().toString());
            tv_product_name.setText(od.getProductname().toString());
            tv_stage.setText(od.getTime().toString()+"/"+od.getTimes().toString());
            tv_mprice.setText(od.getMoney().toString());
            tv_creatdate.setText(od.getOrderdate().toString().replace(".0",""));
            tv_begindate.setText(od.getBegindate().toString());
            tv_enddate.setText(od.getEnddate().toString());
        }else {
            ll_content.setVisibility(View.GONE);
            rl_empty.setVisibility(View.VISIBLE);
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
