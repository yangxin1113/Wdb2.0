package com.zyx.fragment.life;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.zyx.R;
import com.zyx.ad.MyAdapter.GridViewAdapterProduct;
import com.zyx.base.BaseFragment;
import com.zyx.bean.ProductData;
import com.zyx.contants.Contants;
import com.zyx.fragment.product.ProductFragmentActivity;
import com.zyx.json.ParserJsonProductitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/18.
 */
public class FragmentTabHuaFei extends BaseFragment {

    private MyGridView gv_huafei_view; //
    private List<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();
    private SimpleAdapter adapter;

    @Override
    protected void init() {
       setLayoutRes(R.layout.fragment_huafei);
        //gv_download();
    }

    @Override
    protected void initView(View rootview) {
        gv_huafei_view = (MyGridView) rootview.findViewById(R.id.gv_huafei_view);
        huafeiData();
    }

    @Override
    protected void setViewData() {

    }

    @Override
    protected void initEvent() {
        intemOnClick();
    }

    private void intemOnClick() {
        gv_huafei_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utils.showToast(getActivity(), String.valueOf(mData.get(position).get("money")));
            }
        });
    }


    @Override
    protected void getData() {

    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            /*case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");
                String data = (String) (msg.obj);
                LogUtil.i("zyx", "data,data:"+data);
                if(data != null ){
                    gv_ProductData = ParserJsonProductitem.JsontoProductItem(data);
                    LogUtil.i("zyx", "data,data:"+gv_ProductData.get(1).getProductName());
                    gv_products_adapter = new GridViewAdapterProduct(getActivity(), gv_ProductData);
                    gv_products_adapter.notifyDataSetChanged();
                    gv_product_view.setAdapter(gv_products_adapter);

                }
                break;*/


        }
    }

    private void huafeiData(){
        Map<String,Object> item0 = new HashMap<String,Object>();
        item0.put("money", "10元");
        item0.put("pay", "9.95元");
        mData.add(item0);

        Map<String,Object> item1 = new HashMap<String,Object>();
        item1.put("money", "30元");
        item1.put("pay", "29元");
        mData.add(item1);

        Map<String,Object> item2 = new HashMap<String,Object>();
        item2.put("money", "50元");
        item2.put("pay", "48元");
        mData.add(item2);

        Map<String,Object> item3 = new HashMap<String,Object>();
        item3.put("money", "100元");
        item3.put("pay", "97元");
        mData.add(item3);

        Map<String,Object> item4 = new HashMap<String,Object>();
        item4.put("money", "200元");
        item4.put("pay", "196元");
        mData.add(item4);

        Map<String,Object> item5 = new HashMap<String,Object>();
        item5.put("money", "500元");
        item5.put("pay", "495元");
        mData.add(item5);


        adapter = new SimpleAdapter(
                getActivity(),
                mData,
                R.layout.item_huadei,
                new String[]{"money","pay"},
                new int[]{R.id.tv_money,R.id.tv_pay});
        gv_huafei_view.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {

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
