package com.zyx.fragment.Order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.zyx.R;
import com.zyx.ad.MyAdapter.ListOrderAdapter;
import com.zyx.application.MyApplication;
import com.zyx.base.BaseFragment;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;
import com.zyx.contants.UpdateUserInfo;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.wdb.MainActivity;
import com.zyx.widget.CustomDialog;
import com.zyx.widget.DialogWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/22.
 */
public class OrderCheckFragment extends BaseFragment implements UpdateUserInfo.onUpdateUserInfo{

    private ListOrderAdapter lv_order_adapter;
    private List<OrderData> lv_OrderData;
    private ListView lv_order_view;
    private View emptyView ;
    private Button bt_gogo;
    private String customerId;
    private CustomDialog mcustomerDialog;


    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_order_pagers);
    }

    @Override
    protected void initView(View rootview) {
        lv_order_view = (ListView) rootview.findViewById(R.id.lv_order);
        bt_gogo = (Button) rootview.findViewById(R.id.bt_gogo);
        emptyView = rootview.findViewById(R.id.rl_empty);
        lv_order_view.setEmptyView(emptyView);
    }

    @Override
    protected void initEvent() {
        intemOnClick();
        bt_gogo.setOnClickListener(this);
    }

    @Override

    protected void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_gogo:
                Intent i = new Intent(getActivity(), MainActivity.class);
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
                    lv_OrderData = ParserJsonOrderitem.JsontoOrderItem(data);
                    // LogUtil.i("zyx", "data,data:"+lv_OrderData.get(1).getProductnumber());
                    lv_order_adapter = new ListOrderAdapter(getActivity(), lv_OrderData,"确认订单", mListener);
                    lv_order_adapter.notifyDataSetChanged();
                    lv_order_view.setAdapter(lv_order_adapter);

                }
        }
    }


    @Override
    public void notifyUserInfo(Map<String, Object> userInfo) {

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //setUserInfo();
        UpdateUserInfo.getInstance().addOnUpdateUserInfo("my", this);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        UpdateUserInfo.getInstance().remove("my");
    }

    /**
     * 设置用户数据
     *
     * @param userInfo
     */
    private void setUserInfo(Map<String, Object> userInfo) {
        if (userInfo != null) {
            customerId = getParse().isNull(userInfo.get("CustomerId"));
            LogUtil.i("zyxxxxx", customerId);
            if ("".equals(customerId)) {
                //iv_head.setImageResource(R.mipmap.img_head_false);
            } else {
                customerId = userInfo.get("CustomerId").toString();
            }
        }
    }


    @Override
    protected void setViewData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("customerId", ((MyApplication) getActivity().getApplication()).getUser().get("CustomerId").toString());
        map.put("condition", "check");
        startRunnable(new getJsonDataThread(Contants.OrderList, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

        lv_OrderData = new ArrayList<OrderData>();
    }

    private void intemOnClick() {
        lv_order_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utils.showToast(getActivity(), String.valueOf(lv_OrderData.get(position).getProductname()));

            }
        });
    }

    /**
     *  实现类，响应按钮点击事件
     *
     */
    private ListOrderAdapter.MyClickListener mListener = new ListOrderAdapter.MyClickListener() {
        @Override
        public void myOnClick(final int position, View v) {
            final Map<String, String> map = new HashMap<String, String>();
            map.put("ordernumber", lv_OrderData.get(position).getOrdernumber());
            map.put("condition","check");
            //自定义dialog
            CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
            builder.setMessage("是否已收到货?");
            builder.setTitle("确认收货提示");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    //quyiAction();
                    //dialog.dismiss();
                    startRunnable(new getJsonDataThread(Contants.UpdateOrderStatus, map,
                            handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
                    lv_order_adapter.notifyDataSetChanged();
                    lv_order_view.setAdapter(lv_order_adapter);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.create().show();


            /*Map<String, String> map = new HashMap<String, String>();
            map.put("ordernumber", lv_OrderData.get(position).getOrdernumber());
            map.put("condition","check");
            startRunnable(new getJsonDataThread(Contants.UpdateOrderStatus, map,
                    handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
            lv_order_adapter.notifyDataSetChanged();
            lv_order_view.setAdapter(lv_order_adapter);*/

        }
    };



    /*protected View getDecorViewDialog() {
        // TODO Auto-generated method stub
        return PayPasswordView.getInstance(repayment.getText().toString(), getActivity(), new PayPasswordView.OnPayListener() {

            @Override
            public void onSurePay(String password) {
                // TODO Auto-generated method stub

                reapyAction(password);


            }

            @Override
            public void onCancelPay() {
                // TODO Auto-generated method stub
                mDialogWidget.dismiss();
                mDialogWidget = null;
                Toast.makeText(getActivity().getApplicationContext(), "交易已取消", Toast.LENGTH_SHORT).show();

            }
        }).getView();
    }*/

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

