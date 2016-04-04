package com.zyx.fragment.home;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zyx.R;
import com.zyx.ad.MyAdapter.GridViewAdapterProduct;
import com.zyx.base.BaseFragment;
import com.zyx.bean.ProductData;
import com.zyx.contants.Contants;
import com.zyx.json.ParserJsonProductitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/18.
 */
public class FragmentTab2 extends BaseFragment {
    private GridView gv_product_view;
    private List<ProductData> gv_ProductData;

    private GridViewAdapterProduct gv_products_adapter;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_home_tab1);
        //gv_download();
    }

    @Override
    protected void initView(View rootview) {
        gv_product_view = (GridView) rootview.findViewById(R.id.gv_product_view);
    }

    @Override
    protected void setViewData() {
        Map<String, String > map = new HashMap<String, String>();
        map.put("condition", "electronic");
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
                utils.showToast(getActivity(), gv_ProductData.get(position).getProductName());
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
                    LogUtil.i("zyx", "data,data:"+gv_ProductData.get(1).getProductName());
                    gv_products_adapter = new GridViewAdapterProduct(getActivity(), gv_ProductData);
                    gv_products_adapter.notifyDataSetChanged();
                    gv_product_view.setAdapter(gv_products_adapter);

                }


        }
    }


    /*public void gv_download() {
        httpUtils.send(HttpRequest.HttpMethod.GET, Contants.Product_List, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;
                gv_ProductData = ParserJsonProductitem.JsontoProductItem(jsonString);
                gv_products_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {

            }
        });
    }*/

    @Override
    public void onClick(View v) {

    }
}
