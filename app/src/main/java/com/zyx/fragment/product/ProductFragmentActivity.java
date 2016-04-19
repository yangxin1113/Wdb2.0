package com.zyx.fragment.product;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zyx.R;

import com.zyx.ad.MyAdapter.PropertyAdapter;
import com.zyx.ad.MyAdapter.SpinerAdapter;
import com.zyx.ad.ScaleView.HackyViewPager;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.Attr;
import com.zyx.contants.Contants;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.CaculateHelper;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Parse;
import com.zyx.widget.CategoryView;
import com.zyx.widget.MyTitleBar;
import com.zyx.widget.SpinerPopWindow;
import com.zyx.widget.SpinerPopWindow1;

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

public class ProductFragmentActivity extends MyBaseFragmentActivity{

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private TextView tv_ProductName;
    private TextView tv_Mprice;
    private TextView tv_Qprice;
    private TextView tv_Sprice;
    private TextView tv_onHand;
    private ListView lv_property;
    private TextView tv_property;
    private LinearLayout ll_proverty;

    private PropertyAdapter propertyAdapter;
    //private Spinner firstPay;
    private Spinner s_stages;
    private Button bt_stage;
    private LinearLayout ll_firstpay;

    /**是否首付*/
    private ImageView iv_yes;
    private ImageView iv_no;
    private LinearLayout ll_drop;
    private LinearLayout ll_drop1;

    private ArrayList<LinkedHashMap<String,Object>> mList;


    /**图片*/
    private HackyViewPager viewPager;
    private ArrayList<View> allListView;
    //private int[] resId ={R.mipmap.shouji, R.mipmap.shouji, R.mipmap.shouji, R.mipmap.shouji, R.mipmap.shouji, R.mipmap.shouji };
    private String[] imageUrl;
    /**ViewPager当前显示页的下标*/
    private int position=0;


    /** 数据相关 */
    private int categoryId;// 所要商品
    private boolean isfirstpay=false;//是否首付
    private boolean isProperty =false; //判断属性打开

    //首付
    private List<String> mListType = new ArrayList<String>();  //类型列表
    private List<String> mListType1 = new ArrayList<String>();  //类型列表
    private TextView mTView;
    private TextView mTmonth;
    private String[] name_firstpay;
    private String[] name_month;
    //设置PopWindow
    private SpinerPopWindow<String> mSpinerPopWindow;
    private SpinerPopWindow<String> mSpinerPopWindow1;

    private  int[] Qprice;
    private int [] Onhand;
    private String product_name;
    private String product_image;




    /**商品选择对比记录, 商品编号和详情*/
    LinkedHashMap<Integer, String> plist = new LinkedHashMap<Integer, String>();
    /**商品选择对比记录, 商品价格和详情*/
    LinkedHashMap<String, Double> Mlist = new LinkedHashMap<String, Double>();
    /**商品选择对比记录, 商品价格和详情*/
    LinkedHashMap<Integer, String> OnHandlist = new LinkedHashMap<Integer, String>();
    /**商品选择返回的属性*/
    LinkedHashMap<String,String> datadetail;
    String productNum;

    /**单属性*/
    private List<String> list;
    /**属性list*/
    private List<Attr> l_attr;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_stage:
                productNum =new CaculateHelper(tv_Qprice.getText().toString(),
                        mTmonth.getText().toString(), mTView.getText().toString(), OnHandlist, plist, Mlist, datadetail).getProductNum();
                if(isEmpty()){
                    Intent i = new Intent(getApplicationContext(), ProductOrderActivity.class);
                    String firstpay = new CaculateHelper(tv_Qprice.getText().toString(),
                            mTmonth.getText().toString(), mTView.getText().toString(), OnHandlist, plist, Mlist, datadetail).getFirstpay();
                    String stages = mTmonth.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("ProductNumber", productNum);
                    LogUtil.w("zzzzz", productNum);
                    bundle.putString("ProductName", product_name);
                    bundle.putString("ProductImage", product_image);
                    bundle.putString("FirstPay", firstpay);
                    bundle.putString("Stages", stages.replace("个月", ""));
                    bundle.putInt("CategoryId", Integer.valueOf(categoryId));
                    bundle.putString("Repayment", tv_Mprice.getText().toString());
                    i.putExtras(bundle);
                    startActivity(i);
                }
                break;
            case R.id.iv_yes:
                if(!isfirstpay){
                    ll_firstpay.setVisibility(View.VISIBLE);
                    iv_yes.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true1));
                    iv_no.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false1));
                    isfirstpay = true;
                }
                break;
            case R.id.iv_no:
                if(isfirstpay){
                    ll_firstpay.setVisibility(View.GONE);
                    iv_yes.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false1));
                    iv_no.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true1));
                    isfirstpay = false;
                }
                break;
            case R.id.ll_drop:
                showSpinWindow(mSpinerPopWindow);
                break;
            case R.id.ll_drop1:
                showSpinWindow1(mSpinerPopWindow1);
                break;
            case R.id.s_stages:
                break;
            case R.id.firstPay:
                break;
            case R.id.tv_property:
                if(!isProperty){
                    ll_proverty.setVisibility(View.VISIBLE);
                    isProperty = true;
                }else{
                    ll_proverty.setVisibility(View.GONE);
                    isProperty = false;
                }

                break;
        }

    }

    private boolean isEmpty() {
        boolean isNext = true;
        if(datadetail == null){
            utils.showToast(getApplicationContext(), "请选择商品属性！");
            ll_proverty.setVisibility(View.VISIBLE);
            isProperty = true;
            isNext = false;
        }else if(productNum == null){
            utils.showToast(getApplicationContext(), "请将商品属性选择完整！");
            isNext = false;
        }

        return isNext;
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

        setContentView(R.layout.product_fragment_detail);
        categoryId = getIntent().getIntExtra("categoryId",0);
    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        tv_ProductName = (TextView)findViewById(R.id.tv_product_name);
        tv_Mprice = (TextView) findViewById(R.id.tv_Mprice);
        tv_Qprice = (TextView) findViewById(R.id.tv_Qprice);
        tv_Sprice = (TextView) findViewById(R.id.tv_sPrice);
        tv_onHand = (TextView) findViewById(R.id.tv_onHand);
        lv_property = (ListView) findViewById(R.id.lv_property);
        iv_yes = (ImageView)findViewById(R.id.iv_yes);
        iv_no = (ImageView)findViewById(R.id.iv_no);
        //firstPay = (Spinner)findViewById(R.id.firstPay);
        s_stages = (Spinner)findViewById(R.id.s_stages);
        ll_firstpay = (LinearLayout) findViewById(R.id.ll_firstpay);
        bt_stage = (Button)findViewById(R.id.bt_stage);
        tv_property = (TextView)findViewById(R.id.tv_property);
        ll_proverty = (LinearLayout)findViewById(R.id.ll_property);

        mTView = (TextView) findViewById(R.id.tv_value);
        mTmonth = (TextView) findViewById(R.id.tv_month);

        ll_drop = (LinearLayout) findViewById(R.id.ll_drop);
        ll_drop1 = (LinearLayout) findViewById(R.id.ll_drop1);
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
        mtb_title.setText(getString(R.string.title_product_zh));
        Map<String, String > map = new HashMap<String, String>();
        map.put("categoryId", String.valueOf(categoryId));
        startRunnable(new getJsonDataThread(Contants.Product_One, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));


        initData();
        mSpinerPopWindow = new SpinerPopWindow<String>(this, mListType,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);
        mSpinerPopWindow1 = new SpinerPopWindow<String>(this, mListType1,itemClickListener1);
        mSpinerPopWindow1.setOnDismissListener(dismissListener);
       /* mAdapter = new SpinerAdapter(this,mListType);
        mAdapter.refreshData(mListType, 0);
        mAdapter1 = new SpinerAdapter(this,mListType1);
        mAdapter1.refreshData(mListType1, 0);*/

        /*//初始化PopWindow
        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter);

        mSpinerPopWindow1 = new SpinerPopWindow(this);
        mSpinerPopWindow1.setAdatper(mAdapter1);*/

    }

    @Override
    protected void initEvent() {
//        categoryView.setOnClickCategoryListener(this);
       /* s_stages.setOnClickListener(this);
        firstPay.setOnClickListener(this);*/
        iv_no.setOnClickListener(this);
        iv_yes.setOnClickListener(this);
        bt_stage.setOnClickListener(this);
        mtb_title.setLeftImageOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });

        tv_property.setOnClickListener(this);
        ll_drop.setOnClickListener(this);
        ll_drop1.setOnClickListener(this);
       /* mSpinerPopWindow1.setItemListener(this);
        mSpinerPopWindow11.setItemListener(this);*/


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
                    LogUtil.i("zyx", "data,data:" + data.toString());
                    setProductdata(data);
                    initViewPager(imageUrl);
                    /*Iterator it = l_attr.iterator();
                    while (it.hasNext()) {
                        Attr attr = (com.zyx.bean.Attr) it.next();
                        categoryView.add(attr.getAttr(), attr.getType());
                    }*/
                    propertyAdapter = new PropertyAdapter(getApplicationContext(), mList, handler);
                    lv_property.setAdapter(propertyAdapter);
                }
                break;

            case 0X888:
                LogUtil.i("zyx", "property");
                 datadetail = (LinkedHashMap<String,String>) (msg.obj);

                //LogUtil.i("zyx", "aaa,data:"+tv_Qprice.getText().toString()+"aaa"+mTmonth.getText().toString());
                if(datadetail != null ){
                    LogUtil.i("zyx", "data,data:" + datadetail.toString());
                    LogUtil.i("zyx", "data,data:" + Mlist.toString());
                    tv_Mprice.setText(new CaculateHelper(tv_Qprice.getText().toString(),
                            mTmonth.getText().toString(), mTView.getText().toString(), OnHandlist, plist, Mlist, datadetail).caculate());
                    String chageprice = new CaculateHelper(tv_Qprice.getText().toString(),
                            mTmonth.getText().toString(), mTView.getText().toString(), OnHandlist, plist, Mlist, datadetail).changeprice();
                    LogUtil.i("zyx", "chageprice:" + chageprice);
                    tv_Qprice.setText(chageprice);

                }
                break;
        }

    }

    /**
     * 处理后台出来的数据处理
     *
     */
    private void setProductdata(String reslut) {
        try {

            JSONObject data = new JSONObject(reslut);
            JSONArray products =data.getJSONArray("data");
            imageUrl = new String[products.length()];
            Qprice = new int[products.length()];
            Onhand = new int[products.length()];
            for(int i=0; i<products.length(); i++){
                JSONObject item = products.getJSONObject(i);
                //LogUtil.i("zzzzyyyy", item.get("ProductDescription").toString());
                plist.put(item.getInt("ProductNumber"), item.get("ProductDescription").toString());
                Mlist.put(item.get("ProductDescription").toString(), item.getDouble("QuotoPrice"));
                OnHandlist.put(item.getInt("QuantityOnHand"), item.get("ProductDescription").toString());
                tv_ProductName.setText(item.getString("ProductName").toString());
                product_name = item.getString("ProductName").toString();
                tv_Mprice.setText(String.valueOf(Parse.getInstance().parseDouble(item.getDouble("Mprice"), "#.##")));
                tv_Qprice.setText(String.valueOf(item.getDouble("QuotoPrice")));
                tv_Sprice.setText(String.valueOf(item.getDouble("QuotoPrice")+111));
                tv_onHand.setText(String.valueOf(item.getInt("QuantityOnHand")));
                Qprice[i] = (int)item.getDouble("QuotoPrice");
                Onhand[i] = item.getInt("QuantityOnHand");
                imageUrl[i] = item.getString("ImageUrls");
                product_image = imageUrl[0];
            }
            int count = data.getInt("count");
            JSONObject attrs =data.getJSONObject("attrs");
            l_attr = new ArrayList<Attr>();
            mList = new ArrayList<LinkedHashMap<String,Object>>();
            for (int j=0; j<count; j++){
                JSONArray attr = attrs.getJSONArray("attr"+j);
                //LogUtil.i("sdfsf", attrs.getString("attr"+j);
                list = new ArrayList<String>();
                LinkedHashMap<String,Object> hashMap  = new LinkedHashMap<String,Object>();
                for(int k=0; k<attr.length(); k++){
                    if(k==0){
                        hashMap.put("type", attr.get(k));
                    }else {
                        list.add(attr.get(k).toString());
                    }
                }
                hashMap.put("lable",list);

                mList.add(hashMap);
                /*attrdata = new Attr(list);
                l_attr.add(attrdata);*/
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 商品图片
     * */
    private void initViewPager(String[] urls) {

        if(allListView!=null){
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();


        for(int i=0; i<imageUrl.length; i++){
            View view = LayoutInflater.from(this).inflate(R.layout.dpic_item,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            ImageLoader.getInstance().displayImage(urls[i], imageView, options);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    /*//跳转到大图界面
                    Intent intent = new Intent(BabyActivity.this, ShowBigPicture.class);
                    intent.putExtra("position", position);
                    startActivity(intent);*/
                }

            });
            allListView.add(view);
        }


        viewPager = (HackyViewPager)findViewById(R.id.iv_baby);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                position = arg0;

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub

            }

        });
        viewPager.setAdapter(adapter);
    }


    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return allListView.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == (View)arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = allListView.get(position);
            container.addView(view);
            return view;
        }
    }


    private void showSpinWindow(SpinerPopWindow s){
        Log.e("", "showSpinWindow");
        s.setWidth(mTView.getWidth());
        s.showAsDropDown(mTView, 0, -200);
    }

    private void showSpinWindow1(SpinerPopWindow s){
        Log.e("", "showSpinWindow");
        s.setWidth(mTmonth.getWidth());
        s.showAsDropDown(mTmonth, 0, -300);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //初始化数据
        name_firstpay = getResources().getStringArray(R.array.firstPay);
        name_month = getResources().getStringArray(R.array.s_stage);
        for(int i = 0; i < name_firstpay.length; i++){
            mListType.add(name_firstpay[i]);
        }
        for(int i = 0; i < name_month.length; i++){
            mListType1.add(name_month[i]);
        }
    }

    /**
     * 监听popupwindow取消
     */
    private OnDismissListener  dismissListener=new OnDismissListener() {
        @Override
        public void onDismiss() {
            /*setTextImage(R.drawable.icon_down);*/
        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopWindow.dismiss();
            mTView.setText(mListType.get(position));
            Toast.makeText(getApplicationContext(), "点击了:" + mListType.get(position), Toast.LENGTH_LONG).show();
            tv_Mprice.setText(new CaculateHelper(tv_Qprice.getText().toString(),
                    mTmonth.getText().toString(), mTView.getText().toString(), OnHandlist, plist, Mlist, datadetail).caculate());
        }
    };

    private OnItemClickListener itemClickListener1 = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopWindow1.dismiss();
            mTmonth.setText(mListType1.get(position));
            Toast.makeText(getApplicationContext(), "点击了:" + mListType1.get(position), Toast.LENGTH_LONG).show();
            tv_Mprice.setText(new CaculateHelper(tv_Qprice.getText().toString(),
                    mTmonth.getText().toString(), mTView.getText().toString(), OnHandlist, plist, Mlist, datadetail).caculate());
        }
    };

    /**
     * 给TextView右边设置图片
     * @param resId
     *//*
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        tvValue.setCompoundDrawables(null, null, drawable, null);
    }*/
}
