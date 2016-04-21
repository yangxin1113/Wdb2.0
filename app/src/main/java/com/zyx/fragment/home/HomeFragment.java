package com.zyx.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zyx.R;
import com.zyx.ad.slidePlayView.AbOnItemClickListener;
import com.zyx.ad.slidePlayView.AbSlidingPlayView;
import com.zyx.application.MyApplication;
import com.zyx.base.BaseFragment;
import com.zyx.base.MyFragmentPagerAdapter;
import com.zyx.contants.Contants;
import com.zyx.contants.UpdateUserInfo;
import com.zyx.fragment.Order.DetailOrderActivity;
import com.zyx.fragment.Order.OrderFragmentActivity;
import com.zyx.fragment.help.HelpFragmentActivity;
import com.zyx.fragment.loan.LoanOrderActivity;
import com.zyx.fragment.login.LoginFragmentActivity;
import com.zyx.fragment.me.FragmentMeInfo;
import com.zyx.fragment.product.ProductFragmentActivity;
import com.zyx.fragment.product.SearchFragmentActivity;
import com.zyx.fragment.repay.FragmentRepayActivity;
import com.zyx.fragment.safe.SafeFragmentActivity;
import com.zyx.thread.PostDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Resolve;
import com.zyx.widget.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyx on 2016/2/15.
 */
public class HomeFragment extends BaseFragment implements UpdateUserInfo.onUpdateUserInfo {

    private RelativeLayout rl_head;
    private CircleImageView iv_head; //个人头像
    private Button bt_search; //搜索
    private RelativeLayout rl_class;
    private ImageView iv_class; //分类
    private ImageView ivBottomLine; //导航栏下划线
    private TextView tvTab1, tvTab2, tvTab3;//导航名
    private TextView[] titles;

    private AbSlidingPlayView viewPager;// 首页轮播
    private ArrayList<View> allListView;//存储首页轮播的界面

    /**
     * 数据相关
     */
    private Map<String, String> userMap;

    /**
     * 菜单信息
     */
    private CircleImageView iv_big_head;
    private TextView tv_custNick;
    private TextView tv_credit;
    private TextView tv_point;

    private ViewPager mfragPager;
    private ArrayList<Fragment> fragmentsList;
    private int[] resId = {R.mipmap.page1, R.mipmap.page3,
            R.mipmap.page2, R.mipmap.page3,
            R.mipmap.page1, R.mipmap.page2};

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    public final static int num = 3;
    private FragmentTab1 fragmentTab1;
    private FragmentTab2 fragmentTab2;
    private FragmentTab3 fragmentTab3;


    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_home_index);

    }

    @Override
    protected void initView(View rootview) {
        rl_head = (RelativeLayout) rootview.findViewById(R.id.rl_head);
        iv_head = (CircleImageView) rootview.findViewById(R.id.iv_head);
        bt_search = (Button) rootview.findViewById(R.id.bt_search);
        rl_class = (RelativeLayout) rootview.findViewById(R.id.rl_class);
        tvTab1 = (TextView) rootview.findViewById(R.id.tv_tab1);
        tvTab2 = (TextView) rootview.findViewById(R.id.tv_tab2);
        tvTab3 = (TextView) rootview.findViewById(R.id.tv_tab3);
        viewPager = (AbSlidingPlayView) rootview.findViewById(R.id.viewPager_shop);
        mfragPager = (ViewPager) rootview.findViewById(R.id.fragPager);
        ivBottomLine = (ImageView) rootview.findViewById(R.id.iv_bottom_line);
        InitWidth(rootview);

    }

    @Override
    protected void setViewData() {
        viewPager.setPlayType(1);
        viewPager.setSleepTime(3000);
        initViewPager();
        InitFragViewPager();
        titles = new TextView[]{tvTab1, tvTab2, tvTab3};
        TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
        /*tvTab2.setTextColor(getResources().getColor(R.color.text_bottom_false));
        tvTab3.setTextColor(getResources().getColor(R.color.text_bottom_false));
        animation.setFillAfter(true);
        animation.setDuration(200);
        ivBottomLine.startAnimation(animation);*/
    }

    @Override
    protected void initEvent() {
        rl_head.setOnClickListener(this);
        rl_class.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        tvTab1.setOnClickListener(new MyOnClickListener(0));
        tvTab2.setOnClickListener(new MyOnClickListener(1));
        tvTab3.setOnClickListener(new MyOnClickListener(2));

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");

                Map<String, Map<String, Object>> mapXML = (Map<String, Map<String, Object>>) (msg.obj);
                LogUtil.i("zyx", "mapXML,mapXML:" + mapXML);

                if (mapXML != null && mapXML.size() > 0) {
                    if ("200".equals(mapXML.get("code"))) {
                        LogUtil.i("zyx", "yes");
                        ArrayList<Map<String, Object>> userinfList = Resolve
                                .getInstance().getList(mapXML, "userInfo");
                        //LogUtil.i("zyx",userinfList.toString());
                        if (userinfList != null && userinfList.size() > 0) {
                            Map<String, Object> user = userinfList.get(0);
                            if (user.get("CustomerId") != null) {
                                // LogUtil.i("zyx1111",user.get("CustomerId").toString());
                                ((MyApplication) getActivity().getApplication()).setUser(user);
                                setUserInfo(((MyApplication) getActivity().getApplication()).getUser());
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_head:
                iv_head.setVisibility(View.INVISIBLE);
                if(isLogin()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userName", ((MyApplication) getActivity().getApplication()).getUser().get("CustPhoneNum").toString());
                    map.put("password", ((MyApplication) getActivity().getApplication()).getUser().get("CustLoginPwd").toString());

                    startRunnable(new PostDataThread(Contants.USER_LOGIN, map,
                            handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
                    SlidingMenu();
                }else{
                    utils.showToast(getContext(),
                            getString(R.string.please_login));
                    startActivityForResult(new Intent(getContext(),
                                    LoginFragmentActivity.class),
                            33);
                    getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.no_animation);
                }
                break;
            case R.id.bt_search:
                Intent i = new Intent(getActivity(), SearchFragmentActivity.class);
                startActivity(i);
                break;

        }

    }


    private void InitWidth(View parentView) {
        ivBottomLine = (ImageView) parentView.findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / num - bottomLineWidth) / 2);
        int avg = (int) (screenW / num);
        position_one = avg + offset;


    }

    /**
     * 轮播图
     */
    private void initViewPager() {
        if (allListView != null) {
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();

        for (int i = 0; i < resId.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(
                    R.layout.img_viewpage_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            imageView.setImageResource(resId[i]);
            allListView.add(view);
        }
        viewPager.addViews(allListView);
        // 开始轮播
        viewPager.startPlay();
        viewPager.setOnItemClickListener(new AbOnItemClickListener() {

            @Override
            public void onClick(int position) {
                // 跳转到详情界面
                Intent intent = new Intent(getActivity(), ProductFragmentActivity.class);
                intent.putExtra("categoryId",5);
                startActivity(intent);
            }

        });
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

    ;

    /**
     * fragment导航栏
     */
    public void InitFragViewPager() {

        fragmentsList = new ArrayList<Fragment>();
        fragmentTab1 = new FragmentTab1();
        fragmentTab2 = new FragmentTab2();
        fragmentTab3 = new FragmentTab3();

        fragmentsList.add(fragmentTab1);
        fragmentsList.add(fragmentTab2);
        fragmentsList.add(fragmentTab3);
        mfragPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
        mfragPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mfragPager.setCurrentItem(0);

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


    @Override
    public void notifyUserInfo(Map<String, Object> userInfo) {

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setUserInfo(((MyApplication) getActivity().getApplication()).getUser());
        UpdateUserInfo.getInstance().addOnUpdateUserInfo("my", this);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        UpdateUserInfo.getInstance().remove("my");
    }


    /**
     * 设置用户数据
     *
     * @param userInfo
     */
    private void setUserInfo(Map<String, Object> userInfo) {
        if (userInfo != null) {
            String headImg = getParse().isNull(userInfo.get("CustHead"));
            if ("".equals(headImg)) {
                iv_head.setImageResource(R.mipmap.img_head_false);
            } else {
                DisplayImageOptions userHeadOption = ((MyApplication) getActivity()
                        .getApplication()).getUserHeadOptions();
                imageLoader.displayImage(getString(R.string.ip) + headImg,
                        iv_head, userHeadOption);
            }
        }
    }


    /**
     * 设置用户数据
     *
     * @param userInfo
     */
    private void setUserInfo1(Map<String, Object> userInfo) {
        if (userInfo != null) {
            String headImg = getParse().isNull(userInfo.get("CustHead"));
            if ("".equals(headImg)) {
                iv_big_head.setImageResource(R.mipmap.img_head_false);
            } else {
                DisplayImageOptions userHeadOption = ((MyApplication) getActivity()
                        .getApplication()).getUserHeadOptions();

                imageLoader.displayImage(getString(R.string.ip) + headImg,
                        iv_big_head, userHeadOption);
            }
            tv_custNick.setText(getParse().isNull(userInfo.get("CustNick")));
            tv_credit.setText(getParse().isNull(userInfo.get("CustCreditRest")));
            tv_point.setText(getParse().isNull(userInfo.get("CustPoint")));
//			tv_integral_num
//					.setText(getParse().isNull(userInfo.get("integral")));
        }
    }

    /**
     * 侧滑菜单
     */
    public void SlidingMenu() {
        SlidingMenu slidingMenu = new SlidingMenu(getActivity());
        //slidingMenu.setMenu(R.layout.slidingmenu_home_left);
        slidingMenu.setMenu(getView());
        //slidingMenu.setBehindWidth(600);
        slidingMenu.setBehindOffset(200);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.attachToActivity(getActivity(), SlidingMenu.SLIDING_WINDOW);
        slidingMenu.toggle();
        slidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {

            @Override
            public void onOpened() {
                iv_head.setVisibility(View.INVISIBLE);



            }
        });
        slidingMenu.setOnClosedListener(new SlidingMenu.OnClosedListener() {

            @Override
            public void onClosed() {
                iv_head.setVisibility(View.VISIBLE);

            }
        });

    }

    public View getView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.slidingmenu_home_left, null);
        iv_big_head = (CircleImageView) view.findViewById(R.id.iv_head_big);
        tv_custNick = (TextView) view.findViewById(R.id.tv_custNick);
        tv_credit = (TextView) view.findViewById(R.id.tv_credit);
        tv_point = (TextView) view.findViewById(R.id.tv_point);
        LinearLayout ll_me = (LinearLayout) view.findViewById(R.id.ll_me);
        LinearLayout ll_bill = (LinearLayout) view.findViewById(R.id.ll_bill);
        LinearLayout ll_order = (LinearLayout) view.findViewById(R.id.ll_order);
        LinearLayout ll_credit = (LinearLayout) view.findViewById(R.id.ll_credit);
        LinearLayout ll_safty = (LinearLayout) view.findViewById(R.id.ll_safty);
        LinearLayout ll_help = (LinearLayout) view.findViewById(R.id.ll_help);

        ll_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FragmentMeInfo.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        setUserInfo1(((MyApplication) getActivity().getApplication()).getUser());
        ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), OrderFragmentActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        ll_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FragmentRepayActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        ll_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), HelpFragmentActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        ll_safty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SafeFragmentActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 33) {
            if (arg1 == Activity.RESULT_OK) {
                // 注册成功返回
                if (arg2.getBooleanExtra("isRegistered", false)
                        || arg2.getBooleanExtra("isLogin", false)) {
                    if (isLogin()) {
                        SlidingMenu();
                    } else {
                        utils.showToast(getContext(),
                                getString(R.string.please_login));
                        startActivityForResult(new Intent(getContext(),
                                        LoginFragmentActivity.class),
                                22);
                        getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.no_animation);
                    }
                }
            }
        }
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
