package com.zyx.fragment.life;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.base.MyFragmentPagerAdapter;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;

/**
 * Created by zyx on 2016/2/22.(无用)
 */

public class ChongzhiFragmentActivity extends MyBaseFragmentActivity{

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏


    private ImageView ivBottomLine; //导航栏下划线
    private TextView tvTab1, tvTab2;//导航名
    private TextView[] titles;


    private ViewPager mfragPager;
    private ArrayList<Fragment> fragmentsList;
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    public final static int num = 2;
    private FragmentTabHuaFei fragmentTab1;
    private FragmentTabLiuLiang fragmentTab2;



    @Override
    public void onClick(View v) {
        switch(v.getId()){

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

        setContentView(R.layout.activity_chongzhi);

    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);

        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        tvTab1 = (TextView) findViewById(R.id.tv_tab1);
        tvTab2 = (TextView) findViewById(R.id.tv_tab2);

        mfragPager = (ViewPager) findViewById(R.id.fragPager);
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        InitWidth();

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
        mtb_title.setText(getString(R.string.phone_zh));


        InitFragViewPager();
        titles = new TextView[]{tvTab1, tvTab2};
        TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);

    }

    @Override
    protected void initEvent() {
//
        mtb_title.setLeftImageOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });

        tvTab1.setOnClickListener(new MyOnClickListener(0));
        tvTab2.setOnClickListener(new MyOnClickListener(1));

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

                }
                break;
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
     * fragment导航栏
     */
    public void InitFragViewPager() {

        fragmentsList = new ArrayList<Fragment>();
        fragmentTab1 = new FragmentTabHuaFei();
        fragmentTab2 = new FragmentTabLiuLiang();


        fragmentsList.add(fragmentTab1);
        fragmentsList.add(fragmentTab2);

        mfragPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mfragPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mfragPager.setCurrentItem(0);

    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mfragPager.setCurrentItem(index);
        }
    }


    /**
     * fragPager监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bottomLineWidth;// 页卡1 -> 页卡2 偏移量

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            /*switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, offset, 0, 0);
                        tvTab2.setTextColor(getResources().getColor(R.color.text_bottom_false));
                        tvTab3.setTextColor(getResources().getColor(R.color.text_bottom_false));
                    }
                    tvTab1.setTextColor(getResources().getColor(R.color.text_bottom_true));
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        tvTab1.setTextColor(getResources().getColor(R.color.text_bottom_false));
                        tvTab3.setTextColor(getResources().getColor(R.color.text_bottom_false));
                    }
                    tvTab2.setTextColor(getResources().getColor(R.color.text_bottom_true));
                    break;
                case 2:
                    if (currIndex == 2) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        tvTab1.setTextColor(getResources().getColor(R.color.text_bottom_false));
                        tvTab2.setTextColor(getResources().getColor(R.color.text_bottom_false));
                    }
                    tvTab3.setTextColor(getResources().getColor(R.color.text_bottom_true));
                    break;
            }*/
            animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);

            titles[arg0].setTextColor(getResources().getColor(R.color.text_bottom_true));
            titles[arg0].setTextColor(getResources().getColor(R.color.text_bottom_true));
            for (int i = 0; i < titles.length; i++) {
                if (i != arg0) {
                    titles[i].setTextColor(getResources().getColor(R.color.text_bottom_false));
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private void InitWidth() {
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / num - bottomLineWidth) / 2);
        int avg = (int) (screenW / num);
        position_one = avg + offset;


    }
}
