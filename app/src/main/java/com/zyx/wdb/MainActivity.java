package com.zyx.wdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.fragment.activity.TravelFragment;
import com.zyx.fragment.home.HomeFragment;
import com.zyx.fragment.life.LifeFragment;
import com.zyx.fragment.loan.LoanFragment;
import com.zyx.fragment.login.LoginFragmentActivity;
import com.zyx.fragment.me.MeFragment;
import com.zyx.utils.LogUtil;
import com.zyx.utils.TwoQuit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/14.
 */
public class MainActivity extends MyBaseFragmentActivity{

    /**
     * 控件相关
     */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    //private GooeyMenu gooeyMenu;

    private LinearLayout ll_home;// 首页
    private ImageView iv_home;// 首页图标
    private TextView tv_home;// 首页文字
    private LinearLayout ll_me;// 我的
    private ImageView iv_me;// 我的图标
    private TextView tv_me;// 我的文字
    private LinearLayout ll_loan;// 微贷
    private ImageView iv_loan;// 贷款图标
    private TextView tv_loan;// 贷款
    private LinearLayout ll_traval;//拼团
    private ImageView iv_traval;//拼团
    private TextView tv_traval;//拼团
    private LinearLayout ll_life;//活动
    private ImageView iv_life;//活动图片
    private TextView tv_life;//活动文字
    private LinearLayout bottom_guide;
    /**
     * Fragment
     */
    private HomeFragment mHomeFragment;//首页
    private LoanFragment mLoanFragment;//贷款
    private TravelFragment mTravelFragment;//活动
    private LifeFragment mLifeFragment;
    private MeFragment mMeFragment;//我的
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    private int oldIndex;// 记录当前展示的Fragment
    private List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();// 所有底部图标与文字

    /**
     * 连按两次返回键退出
     */
    private TwoQuit mTwoQuit;

    /**
     * 用于对Fraggment进行管理
     */
    private FragmentManager fragmentManager;
    /**
     * 数据相关
     */
    private float width;//屏宽

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                //首页
                addOrShowFragment(0);
                break;
            case R.id.ll_loan:
                //贷款
                addOrShowFragment(1);
                break;
            case R.id.ll_traval:
                //活动
                addOrShowFragment(2);
                break;
            case R.id.ll_life:
                //活动
                addOrShowFragment(3);
                break;
            case R.id.ll_me:
                if (isLogin()) {
                    addOrShowFragment(4);
                } else {
                    utils.showToast(getApplicationContext(),
                            getString(R.string.please_login));
                    startActivityForResult(new Intent(getApplicationContext(),
                                    LoginFragmentActivity.class),
                            Activity.RESULT_FIRST_USER);
                    overridePendingTransition(R.anim.bottom_in, R.anim.no_animation);
                }
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
    protected void handlerMessage(Message msg) {

    }

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_main);
        width = getWindowManager().getDefaultDisplay().getWidth();

    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        //gooeyMenu = (GooeyMenu) findViewById(R.id.gooey_menu);

        bottom_guide = (LinearLayout) findViewById(R.id.bottom_guide);
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        tv_home = (TextView) findViewById(R.id.tv_home);
        ll_loan = (LinearLayout) findViewById(R.id.ll_loan);
        iv_loan = (ImageView) findViewById(R.id.iv_loan);
        tv_loan = (TextView) findViewById(R.id.tv_loan);
        ll_traval = (LinearLayout) findViewById(R.id.ll_traval);
        iv_traval = (ImageView) findViewById(R.id.iv_traval);
        tv_traval = (TextView) findViewById(R.id.tv_traval);
        ll_life = (LinearLayout) findViewById(R.id.ll_life);
        iv_life = (ImageView) findViewById(R.id.iv_life);
        tv_life = (TextView) findViewById(R.id.tv_life);
        ll_me = (LinearLayout) findViewById(R.id.ll_me);
        iv_me = (ImageView) findViewById(R.id.iv_me);
        tv_me = (TextView) findViewById(R.id.tv_me);

        mHomeFragment = new HomeFragment();
        mLoanFragment = new LoanFragment();
        mTravelFragment = new TravelFragment();
        mLifeFragment = new LifeFragment();
        mMeFragment = new MeFragment();
        fragmentList.add(mHomeFragment);
        fragmentList.add(mLoanFragment);
        fragmentList.add(mTravelFragment);
        fragmentList.add(mLifeFragment);
        fragmentList.add(mMeFragment);

        Map<String, Object> item0 = new HashMap<String, Object>();
        item0.put("textView", tv_home);
        item0.put("imageView", iv_home);
        item0.put("img_false", R.mipmap.img_home_false);
        item0.put("img_true", R.mipmap.img_home_true);
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("textView", tv_loan);
        item1.put("imageView", iv_loan);
        item1.put("img_false", R.mipmap.img_loan_false);
        item1.put("img_true", R.mipmap.img_loan_true);
        Map<String, Object> item2 = new HashMap<String, Object>();
        item2.put("textView", tv_traval);
        item2.put("imageView", iv_traval);
        item2.put("img_false", R.mipmap.img_activity_false);
        item2.put("img_true", R.mipmap.img_activity_true);
        Map<String, Object> item3= new HashMap<String, Object>();
        item3.put("textView", tv_life);
        item3.put("imageView", iv_life);
        item3.put("img_false", R.mipmap.img_life_false);
        item3.put("img_true", R.mipmap.img_life_true);
        Map<String, Object> item4= new HashMap<String, Object>();
        item4.put("textView", tv_me);
        item4.put("imageView", iv_me);
        item4.put("img_false", R.mipmap.img_me_false);
        item4.put("img_true", R.mipmap.img_me_true);
        viewList.add(item0);
        viewList.add(item1);
        viewList.add(item2);
        viewList.add(item3);
        viewList.add(item4);


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
        getSupportFragmentManager().beginTransaction()
                .add(R.id.framelayout, mHomeFragment).commitAllowingStateLoss();
        oldIndex = 0;


    }

    @Override
    protected void initEvent() {
        bottom_guide.getBackground().setAlpha(100);
        ll_home.setOnClickListener(this);
        ll_loan.setOnClickListener(this);
        ll_traval.setOnClickListener(this);
        ll_life.setOnClickListener(this);
        ll_me.setOnClickListener(this);
        //gooeyMenu.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (oldIndex == 0) {
                if (mTwoQuit == null)
                    mTwoQuit = new TwoQuit();
                mTwoQuit.finish(getApplicationContext());
            } else {
                onClick(ll_home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == Activity.RESULT_FIRST_USER) {
            if (arg1 == Activity.RESULT_OK) {
                // 注册成功返回
                if (arg2.getBooleanExtra("isRegistered", false)
                        || arg2.getBooleanExtra("isLogin", false)) {
                    if (isLogin()) {
                        /*rl_sign.setVisibility(View.GONE);
                        rl_msg.setVisibility(View.GONE);
                        tv_title.setText(getString(R.string.my_zh));*/
                        addOrShowFragment(0);
                    } else {
                        startActivityForResult(new Intent(
                                        getApplicationContext(),
                                        LoginFragmentActivity.class),
                                Activity.RESULT_FIRST_USER);
                        overridePendingTransition(R.anim.bottom_in,
                                R.anim.no_animation);
                    }
                }
            }
        }
    }

    /**
     * 设置当前为要展示的Fragment并设置底部图片、文字
     *
     * @param index
     */
    private void addOrShowFragment(int index) {
        if (fragmentList.get(index) == null || index == oldIndex) {
            LogUtil.i("MainActivity", "zzzzzzzz");
            return;
        }
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.my_alpha_0_1_200,
                R.anim.my_alpha_1_0_200);
        Fragment f = fragmentList.get(index);
        if (f.isAdded()) {
            mFragmentTransaction.hide(fragmentList.get(oldIndex)).show(f).commitAllowingStateLoss();
        } else {
            mFragmentTransaction.hide(fragmentList.get(oldIndex)).add(R.id.framelayout, f).commitAllowingStateLoss();
        }
        setImgText(index);
        LogUtil.i("zyx", "zyxzyxzyx");
        oldIndex = index;

    }

    /**
     * 设置底部图片与文字
     *
     * @param index
     */
    private void setImgText(int index) {
        for (int i = 0; i < viewList.size(); i++) {
            Map<String, Object> item = viewList.get(i);
            if (i == index) {
                ((ImageView) item.get("imageView")).setImageResource(getParse()
                        .parseInt(item.get("img_true")));
                ((TextView) item.get("textView")).setTextColor(getResources()
                        .getColor(R.color.text_bottom_true));
            } else {
                ((ImageView) item.get("imageView")).setImageResource(getParse()
                        .parseInt(item.get("img_false")));
                ((TextView) item.get("textView")).setTextColor(getResources()
                        .getColor(R.color.text_bottom_false));
            }
        }
    }

    /**
     * 跳转到首页
     */
    public void startHomeFragment() {
        onClick(ll_home);
    }


/*
  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void menuOpen() {
        utils.showToast( getApplication(),"Menu Open");

    }

    @Override
    public void menuClose() {
        utils.showToast( getApplication(),"Menu Close");
    }

    @Override
    public void menuItemClicked(int menuNumber) {
        utils.showToast(getApplication(), "Menu item clicked : " + menuNumber);

    }*/


}
