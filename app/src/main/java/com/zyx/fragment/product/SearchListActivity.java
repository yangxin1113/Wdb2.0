package com.zyx.fragment.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zyx.R;
import com.zyx.ad.MyAdapter.GridViewAdapterProduct;
import com.zyx.ad.MyAdapter.ListViewAdapterProduct;
import com.zyx.ad.MyAdapter.PropertyAdapter;
import com.zyx.ad.ScaleView.HackyViewPager;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.Attr;
import com.zyx.bean.ProductData;
import com.zyx.contants.Contants;
import com.zyx.json.ParserJsonProductitem;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.CaculateHelper;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Parse;
import com.zyx.widget.MyTitleBar;
import com.zyx.widget.SpinerPopWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/22.(无用)
 */

public class SearchListActivity extends MyBaseFragmentActivity{

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键

    private ImageView iv_left;
    private ImageView mSearchButtoon;
    private EditText mAutoEdit;


    private ListView search_listview;
    private List<ProductData> lv_ProductData;
    private ListViewAdapterProduct lv_products_adapter;

    private String keyword;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
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
    protected void init(Bundle arg0) {

        setContentView(R.layout.search_list);
        keyword = getIntent().getStringExtra("keyWord");

    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        search_listview = (ListView) findViewById(R.id.search_listview);
        mSearchButtoon = (ImageView) findViewById(R.id.sousuo);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        // 搜索框
        mAutoEdit = (EditText) findViewById(R.id.auto_edit);
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
        map.put("keyWord", keyword);
        map.put("condition", "sort");

        startRunnable(new getJsonDataThread(Contants.SearchProducts, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));

    }

    @Override
    protected void initEvent() {

        intemOnClick();
        iv_left.setOnClickListener(this);
        mSearchButtoon.setOnClickListener(this);

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
                    lv_ProductData = ParserJsonProductitem.JsontoProductItem(data);
                    //LogUtil.i("zyx", "data,data:"+gv_ProductData.get(1).getProductName());
                    lv_products_adapter = new ListViewAdapterProduct(getApplicationContext(), lv_ProductData);
                    lv_products_adapter.notifyDataSetChanged();
                    search_listview.setAdapter(lv_products_adapter);
                }
                break;


        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.no_animation, R.anim.bottom_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void intemOnClick() {
        search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utils.showToast(getApplicationContext(), String.valueOf(lv_ProductData.get(position).getCategoryId()));
                Intent i = new Intent(SearchListActivity.this, ProductFragmentActivity.class);
                i.putExtra("categoryId", lv_ProductData.get(position).getCategoryId());
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }
}
