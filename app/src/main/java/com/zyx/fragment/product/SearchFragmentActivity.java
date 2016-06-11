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


import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;

import com.zyx.R;
import com.zyx.ad.MyAdapter.GridViewAdapterProduct;
import com.zyx.ad.MyAdapter.PropertyAdapter;
import com.zyx.ad.ScaleView.HackyViewPager;
import com.zyx.ad.SearchAuto.SearchAutoAdapter;
import com.zyx.ad.SearchAuto.SearchAutoData;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.Attr;
import com.zyx.contants.Contants;
import com.zyx.fragment.product.zxing.CaptureActivity;
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

    private ImageView iv_saomiao;
    private ImageView iv_speach;
    // 语音听写UI
    private RecognizerDialog rd;

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
            case R.id.iv_speach:
                showReconigizerDialog();
                break;
            case R.id.iv_saomiao:
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(SearchFragmentActivity.this,CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
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
        sp = getSharedPreferences(SEARCH_HISTORY, 0);
        //创建语音识别dailog对象，appid到讯飞就注册获取
        rd = new RecognizerDialog(this ,"appid=5739dce2");
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

        iv_speach = (ImageView)findViewById(R.id.iv_speach);
        iv_saomiao = (ImageView)findViewById(R.id.iv_saomiao);
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

        saveSearchHistory();

    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        mSearchButtoon.setOnClickListener(this);
        clearAll.setOnClickListener(this);
        clearSearch.setOnClickListener(this);
        iv_speach.setOnClickListener(this);
        iv_saomiao.setOnClickListener(this);
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
                    Intent intent = new Intent(SearchFragmentActivity.this, SearchListActivity.class);
                    intent.putExtra("keyWord", mAutoEdit.getText().toString());
                    startActivity(intent);
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



    private void showReconigizerDialog() {
        //setEngine(String engine,String params,String grammar);
        /**
         * 识别引擎选择，目前支持以下五种
         “sms”：普通文本转写
         “poi”：地名搜索
         “vsearch”：热词搜索
         “vsearch”：热词搜索
         “video”：视频音乐搜索
         “asr”：命令词识别

         params	引擎参数配置列表
         附加参数列表，每项中间以逗号分隔，如在地图搜索时可指定搜索区域：“area=安徽省合肥市”，无附加参数传null
         */
        rd.setEngine("sms", null, null);

        //设置采样频率，默认是16k，android手机一般只支持8k、16k.为了更好的识别，直接弄成16k即可。
        rd.setSampleRate(RATE.rate16k);

        final StringBuilder sb = new StringBuilder();
        Log.i(TAG, "识别准备开始.............");

        //设置识别后的回调结果
        rd.setListener(new RecognizerDialogListener() {
            @Override
            public void onResults(ArrayList<RecognizerResult> result, boolean isLast) {
                for (RecognizerResult recognizerResult : result) {
                    sb.append(recognizerResult.text);
                    Log.i(TAG, "识别一条结果为::" + recognizerResult.text);
                }
            }

            @Override
            public void onEnd(SpeechError error) {
                Log.i(TAG, "识别完成.............");
                mAutoEdit.setText(sb.toString().replace("。", ""));
            }
        });

        mAutoEdit.setText(""); //先设置为空，等识别完成后设置内容
        rd.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            mAutoEdit.setText(scanResult);
        }
    }
}
