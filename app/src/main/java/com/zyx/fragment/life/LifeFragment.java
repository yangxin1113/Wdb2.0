package com.zyx.fragment.life;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.zyx.R;
import com.zyx.base.BaseFragment;
import com.zyx.widget.MyTitleBar;

/**
 * Created by zyx on 2016/2/15.
 */
public class LifeFragment extends BaseFragment {

    /**
     * 控件相关
     */
    private MyTitleBar mtb_title;// 标题栏

    private LinearLayout ll_chongzhi;
    private LinearLayout ll_xiaoyuan;
    private LinearLayout ll_bus;
    private LinearLayout ll_parttime;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_chongzhi:
                Intent i = new Intent(getActivity(), ChongzhiFragmentActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_xiaoyuan:
                i = new Intent(getActivity(), SchoolFragmentActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_bus:
                i = new Intent(getActivity(), BusFragmentActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_parttime:
                i = new Intent(getActivity(), JobFragmentActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;


        }

    }

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_life_index);
    }

    @Override
    protected void initView(View rootview) {
        mtb_title = (MyTitleBar) rootview.findViewById(R.id.mtb_title);

        ll_chongzhi = (LinearLayout) rootview.findViewById(R.id.ll_chongzhi);
        ll_xiaoyuan = (LinearLayout) rootview.findViewById(R.id.ll_xiaoyuan);
        ll_bus = (LinearLayout) rootview.findViewById(R.id.ll_bus);
        ll_parttime = (LinearLayout) rootview.findViewById(R.id.ll_parttime);

    }

    @Override
    protected void setViewData() {
        mtb_title.setText(getString(R.string.life_bottom_text));
        mtb_title.setLeftVisibility(View.INVISIBLE);

    }

    @Override
    protected void initEvent() {
        ll_chongzhi.setOnClickListener(this);
        ll_xiaoyuan.setOnClickListener(this);
        ll_bus.setOnClickListener(this);
        ll_parttime.setOnClickListener(this);

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void handlerMessage(Message msg) {

    }

}
