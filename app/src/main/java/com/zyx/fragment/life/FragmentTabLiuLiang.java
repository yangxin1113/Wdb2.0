package com.zyx.fragment.life;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.zyx.R;
import com.zyx.base.BaseFragment;
import com.zyx.widget.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/18.
 */
public class FragmentTabLiuLiang extends BaseFragment {



    @Override
    protected void init() {
       setLayoutRes(R.layout.fragment_liuliang);
        //gv_download();
    }

    @Override
    protected void initView(View rootview) {

    }

    @Override
    protected void setViewData() {

    }

    @Override
    protected void initEvent() {
        //intemOnClick();
    }

    /*private void intemOnClick() {
        gv_huafei_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utils.showToast(getActivity(), String.valueOf(mData.get(position).get("money")));
            }
        });
    }*/


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
