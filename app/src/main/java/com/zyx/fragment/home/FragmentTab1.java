package com.zyx.fragment.home;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zyx.R;
import com.zyx.ad.MyAdapter.GridViewAdapterProduct;
import com.zyx.application.MyApplication;
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
public class FragmentTab1 extends BaseFragment {

    private MyGridView gv_product_view; //
    private List<ProductData> gv_ProductData;

    private GridViewAdapterProduct gv_products_adapter;

    @Override
    protected void init() {
       setLayoutRes(R.layout.fragment_home_tab1);
        //gv_download();
    }

    @Override
    protected void initView(View rootview) {
        gv_product_view = (MyGridView) rootview.findViewById(R.id.gv_product_view);
    }

    @Override
    protected void setViewData() {
        Map<String, String > map = new HashMap<String, String>();
        map.put("condition", "tuijian");
        startRunnable(new getJsonDataThread(Contants.Product_List, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

        gv_ProductData = new ArrayList<ProductData>();



    }

    @Override
    protected void initEvent() {
        intemOnClick();
    }

    private void intemOnClick() {
        gv_product_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utils.showToast(getActivity(), String.valueOf(gv_ProductData.get(position).getCategoryId()));
                Intent i = new Intent(getActivity(), ProductFragmentActivity.class);
                i.putExtra("categoryId", gv_ProductData.get(position).getCategoryId());
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
                    gv_ProductData = ParserJsonProductitem.JsontoProductItem(data);
                    //LogUtil.i("zyx", "data,data:"+gv_ProductData.get(1).getProductName());
                    gv_products_adapter = new GridViewAdapterProduct(getActivity(), gv_ProductData);
                    gv_products_adapter.notifyDataSetChanged();
                    gv_product_view.setAdapter(gv_products_adapter);

                }
                break;


        }
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
