package com.zyx.fragment.life;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.zyx.R;
import com.zyx.ad.MyAdapter.ListProductAdapter;
import com.zyx.base.BaseFragment;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.ProductData;
import com.zyx.fragment.product.TravelFragmentActivity;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyx on 2016/2/15.
 */
public class PartTimeFragmentActivity extends MyBaseFragmentActivity {

    /**
     * 控件相关
     */
    private MyTitleBar mtb_title;// 标题栏
    private ListProductAdapter lv_adapter;
    private List<ProductData> data;
    private ListView lv_product;






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

    }

    @Override
    protected void init(Bundle arg0) {

    }

    @Override
    protected void initView() {
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        lv_product = (ListView) findViewById(R.id.lv_list);
    }

    @Override
    protected View getStatusBarView() {
        return null;
    }

    @Override
    protected View getBottomView() {
        return null;
    }

    @Override
    protected void setViewData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View v) {

    }


    private List<ProductData> initdate(){

        data = new ArrayList<ProductData>();
        ProductData pd1= new ProductData();
        pd1.setImageUrls("http://s3.lvjs.com.cn//uploads/pc/place2/2015-01-12/6f914743-a476-4214-bb1f-07d7736bab5e.jpg");
        pd1.setProductName("【4.9绍兴吼山赏桃花 跟队自驾特卖·299元／人 】");
        pd1.setPrductDescription("绍兴 绍兴吼山 书圣故里历史街区 ");
        pd1.setQuotoPrice(2980.00);
        data.add(pd1);
        ProductData pd4= new ProductData();
        pd4.setImageUrls("http://s1.lvjs.com.cn//uploads/pc/place2/2014-11-17/e0696081-762a-4d92-b102-fd591f51ba1f.jpg");
        pd4.setProductName("杭州-三亚红树林酒店5天4晚自由行");
        pd4.setPrductDescription("海南 三亚 三亚湾红树林度假世界（木棉酒店）");
        pd4.setQuotoPrice(2980.00);
        data.add(pd4);

        ProductData pd2= new ProductData();
        pd2.setImageUrls("http://s3.lvjs.com.cn//uploads/pc/place2/2015-01-12/6f914743-a476-4214-bb1f-07d7736bab5e.jpg");
        pd2.setProductName("【4.9绍兴吼山赏桃花 跟队自驾特卖·299元／人 】");
        pd2.setPrductDescription("绍兴 绍兴吼山 书圣故里历史街区");
        pd2.setQuotoPrice(2980.00);
        data.add(pd2);
        ProductData pd3= new ProductData();
        pd3.setImageUrls("http://s1.lvjs.com.cn//uploads/pc/place2/2014-11-17/e0696081-762a-4d92-b102-fd591f51ba1f.jpg");
        pd4.setProductName("杭州-三亚红树林酒店5天4晚自由行");
        pd4.setPrductDescription("海南 三亚 三亚湾红树林度假世界（木棉酒店）");
        pd3.setQuotoPrice(2980.00);
        data.add(pd3);






        return data;
    }


    /**
     *  实现类，响应按钮点击事件
     *
     */
    private ListProductAdapter.MyClickListener mListener = new ListProductAdapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            //Toast.makeText(getContext(), "ordernumber" + lv_OrderData.get(position).getProductname(),Toast.LENGTH_LONG);
            Intent i = new Intent(PartTimeFragmentActivity.this, TravelFragmentActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }
    };

}
