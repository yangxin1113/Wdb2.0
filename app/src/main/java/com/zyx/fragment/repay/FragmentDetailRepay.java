package com.zyx.fragment.repay;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.fragment.Order.AllOrdersFragment;
import com.zyx.fragment.Order.DuringStageFragment;
import com.zyx.fragment.Order.OrderCheckFragment;
import com.zyx.fragment.Order.WaitGoodsFragment;
import com.zyx.widget.MyTitleBar;
import com.zyx.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyx on 2016/3/22.
 */
public class FragmentDetailRepay extends MyBaseFragmentActivity{

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private List<String> list = new ArrayList<String>();
    private ViewPagerIndicator myViewPagerIndicator;
    private ViewPager vp;

    /**相关数据*/
    private List<Fragment> fragmentList = new ArrayList<Fragment>();


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
        setContentView(R.layout.fragmentactivity_viewpager);

    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        myViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.myViewPagerIndicator);
        vp = (ViewPager) findViewById(R.id.vp);
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
        mtb_title.setText(getString(R.string.side_my_bill));
        mtb_title.setLeftImageOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {

        fragmentList.add(new PresentFragment());
        fragmentList.add(new NextFragment());
        fragmentList.add(new RecordFragment());

        list.add("本期还款");
        list.add("下期还款");
        list.add("还款记录");

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(),fragmentList);
        vp.setAdapter(adapter);

        myViewPagerIndicator.setDatas(list);
        myViewPagerIndicator.setPager(vp);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

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



    private class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> list =null;
        public MyAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            // TODO Auto-generated constructor stub
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        // 有多少页
        @Override
        public int getCount() {
            return list.size();
        }

    }


}
