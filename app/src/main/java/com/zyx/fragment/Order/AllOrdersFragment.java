package com.zyx.fragment.Order;

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
import com.zyx.fragment.repay.FragmentDetailActivity;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.wdb.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/22.
 */
public class AllOrdersFragment extends BaseFragment implements UpdateUserInfo.onUpdateUserInfo {

    private ListOrderAdapter lv_order_adapter;
    private List<OrderData> lv_OrderData;
    private ListView lv_order_view;
    private View emptyView ;
    private Button bt_gogo;
    private String customerId;


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
                    lv_order_adapter = new ListOrderAdapter(getActivity(), lv_OrderData, "查看账单", mListener);
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
           /* tv_nickname.setText(getParse().isNull(userInfo.get("nickname")));
            tv_number.setText(getParse().isNull(userInfo.get("userid")));*/
//			tv_integral_num
//					.setText(getParse().isNull(userInfo.get("integral")));
        }
    }


    @Override
    protected void setViewData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("customerId", ((MyApplication) getActivity().getApplication()).getUser().get("CustomerId").toString());
        map.put("condition", "all");
        startRunnable(new getJsonDataThread(Contants.OrderList, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

        lv_OrderData = new ArrayList<OrderData>();
    }


    private void intemOnClick() {
        lv_order_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utils.showToast(getActivity(), String.valueOf(lv_OrderData.get(position).getProductname()));
                /*Intent i = new Intent(getActivity(), ProductFragmentActivity.class);
                i.putExtra("categoryId",lv_OrderData.get(position).getOrdernumber());
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
                /*if(view.getId() == R.id.bt_detail){
                    Intent i = new Intent(getActivity(), ProductFragmentActivity.class);
                    i.putExtra("categoryId",lv_OrderData.get(position).getOrdernumber());
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }*/

            }
        });
    }

    /**
     * 实现类，响应按钮点击事件
     */
    private ListOrderAdapter.MyClickListener mListener = new ListOrderAdapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            //Toast.makeText(getContext(), "ordernumber" + lv_OrderData.get(position).getProductname(),Toast.LENGTH_LONG);
            Intent i = new Intent(getActivity(), FragmentDetailActivity.class);
            i.putExtra("OrderNumber", lv_OrderData.get(position).getOrdernumber());
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }
    };

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
