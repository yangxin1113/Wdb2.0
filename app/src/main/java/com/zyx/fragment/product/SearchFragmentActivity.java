package com.zyx.fragment.product;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.zyx.ad.MyAdapter.PropertyAdapter;
import com.zyx.ad.ScaleView.HackyViewPager;
import com.zyx.ad.SearchAuto.SearchAutoAdapter;
import com.zyx.ad.SearchAuto.SearchAutoData;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.Attr;
import com.zyx.contants.Contants;
import com.zyx.json.ParserJsonProductitem;
import com.zyx.thread.PostDataThread;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.CaculateHelper;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Parse;
import com.zyx.utils.Resolve;
import com.zyx.widget.MyTitleBar;
import com.zyx.widget.SpinerPopWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/22.
 */

public class SearchFragmentActivity extends MyBaseFragmentActivity{

    /** 控件相关 */
    private ImageView iv_left;
    private ImageButton clearSearch;
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键

    public static final String SEARCH_HISTORY = "search_history";
    private ListView mAutoListView;
    private ImageView mSearchButtoon;
    private EditText mAutoEdit;
    private SearchAutoAdapter mSearchAutoAdapter;
    private TextView clearAll;
    SharedPreferences sp;
    private ProgressDialog pd;

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.search_clear:
                mAutoEdit.getText().clear();
                break;
            case R.id.sousuo:
                saveSearchHistory();
                qury();
                mSearchAutoAdapter.initSearchHistory();
                break;
            case R.id.auto_add:
                SearchAutoData data = (SearchAutoData) v.getTag();
                mAutoEdit.setText(data.getContent());
                break;
            case R.id.clear_all:
                mSearchAutoAdapter.clearSearchHistory();
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

        setContentView(R.layout.search);
        sp = getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE);

    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mSearchAutoAdapter = new SearchAutoAdapter(SearchFragmentActivity.this, -1, this);
        mSearchAutoAdapter.initSearchHistory();
        mAutoListView = (ListView) findViewById(R.id.auto_listview);
        mAutoListView.setAdapter(mSearchAutoAdapter);
        mSearchButtoon = (ImageView) findViewById(R.id.sousuo);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        clearAll = (TextView)findViewById(R.id.clear_all);
        // 搜索框
        mAutoEdit = (EditText) findViewById(R.id.auto_edit);
        // 搜索框中清除button
        clearSearch = (ImageButton)findViewById(R.id.search_clear);
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

    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        mSearchButtoon.setOnClickListener(this);
        clearAll.setOnClickListener(this);
        clearSearch.setOnClickListener(this);
        mAutoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
                SearchAutoData data = (SearchAutoData) mSearchAutoAdapter.getItem(position);
                mAutoEdit.setText(data.getContent());
                mSearchButtoon.performClick();
            }
        });

        mAutoEdit.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mSearchAutoAdapter.performFiltering(s);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
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
                    if(pd.isShowing())
                        pd.dismiss();
                    utils.showToast(SearchFragmentActivity.this, "搜索成功");
                    /*Intent intent = new Intent(SearchFragmentActivity.this, SearchListActivity.class);
                    intent.putExtra("keyWord", mAutoEdit.getText().toString());
                    startActivity(intent);*/
                }
            default:
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


    /*
     * 保存搜索记录
     */
    private void saveSearchHistory() {
        String text = mAutoEdit.getText().toString().trim();
        if (text.length() < 1) {
            return;
        }

        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");
        ArrayList<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (text.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, text);
        }
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
        }
    }



    public void qury(){
        pd = new ProgressDialog(this);
        pd.setMessage("正在搜索...");
        pd.show();

        Map<String, String > map = new HashMap<String, String>();
        map.put("keyWord", mAutoEdit.getText().toString());
        map.put("condition", "sort");

        startRunnable(new getJsonDataThread(Contants.SearchProducts, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));


    }


}
