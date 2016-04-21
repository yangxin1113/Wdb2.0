package com.zyx.fragment.me;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zyx.R;
import com.zyx.application.MyApplication;
import com.zyx.base.BaseFragment;
import com.zyx.contants.UpdateUserInfo;
import com.zyx.fragment.Order.OrderFragmentActivity;
import com.zyx.fragment.repay.FragmentRepayActivity;
import com.zyx.widget.CircleImageView;
import com.zyx.widget.MyTitleBar;

import java.util.Map;

/**
 * Created by zyx on 2016/2/15.
 */
public class MeFragment extends BaseFragment implements UpdateUserInfo.onUpdateUserInfo{

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private RelativeLayout rl_me;
    private ImageView iv_to_rignt;
    private CircleImageView iv_head_big;
    private TextView tv_level;

    private TextView tv_credit;
    private TextView tv_point;
    private TextView tv_pocketmoney;
    private TextView tv_bank_count;
    private TextView tv_bill_count;
    private TextView tv_order_count;
    private LinearLayout ll_pocketmoney;
    private LinearLayout ll_bank;
    private LinearLayout ll_bill;
    private LinearLayout ll_order;





    @Override
    public void onClick(View v) {
        switch(v.getId()){
            /*case R.id.iv_to_right:
                Intent i = new Intent(getActivity(), FragmentMeInfo.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in,R.anim.left_out);
                break;*/
            /*case R.id.ll_bill:
                i = new Intent(getActivity(), FragmentRepayActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_order:
                i = new Intent(getActivity(), OrderFragmentActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;*/
        }

    }

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_activity_me);

    }

    @Override
    protected void initView(View rootview) {
        /*view_status_bar = (View) rootview.findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) rootview.findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) rootview.findViewById(R.id.mtb_title);
        rl_me = (RelativeLayout) rootview.findViewById(R.id.rl_me);
        iv_to_rignt = (ImageView) rootview.findViewById(R.id.iv_to_right);
        iv_head_big = (CircleImageView) rootview.findViewById(R.id.iv_head_big);
        tv_level = (TextView) rootview.findViewById(R.id.tv_level);
        tv_credit = (TextView) rootview.findViewById(R.id.tv_credit);
        tv_point = (TextView) rootview.findViewById(R.id.tv_point);
        tv_pocketmoney = (TextView) rootview.findViewById(R.id.tv_pocketmoney);*/
        /*tv_bank_count = (TextView) rootview.findViewById(R.id.tv_bank_count);
        tv_bill_count = (TextView) rootview.findViewById(R.id.tv_bill_count);
        tv_order_count = (TextView) rootview.findViewById(R.id.tv_order_count);
        ll_pocketmoney = (LinearLayout) rootview.findViewById(R.id.ll_pocketmoney);
        ll_bank = (LinearLayout) rootview.findViewById(R.id.ll_bank);
        ll_bill = (LinearLayout) rootview.findViewById(R.id.ll_bill);
        ll_order = (LinearLayout) rootview.findViewById(R.id.ll_order);*/

    }

    @Override
    protected void setViewData() {
       /* view_status_bar.setBackgroundColor(getResources().getColor(
                R.color.main_color));
        mtb_title.setText(getString(R.string.me_bottom_text));
        mtb_title.setLeftVisibility(View.INVISIBLE);*/
    }

    @Override
    protected void initEvent() {
        /*rl_me.setOnClickListener(this);
        iv_to_rignt.setOnClickListener(this);
        ll_pocketmoney.setOnClickListener(this);
        ll_bank.setOnClickListener(this);*/
        /*ll_bill.setOnClickListener(this);
        ll_order.setOnClickListener(this);*/

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void handlerMessage(Message msg) {

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
        /*if (userInfo != null) {
            String headImg = getParse().isNull(userInfo.get("CustHead"));
            if ("".equals(headImg)) {
                iv_head_big.setImageResource(R.mipmap.img_head_false);
            } else {
                DisplayImageOptions userHeadOption = ((MyApplication) getActivity()
                        .getApplication()).getUserHeadOptions();
                imageLoader.displayImage(getString(R.string.ip) + headImg,
                        iv_head_big, userHeadOption);
            }

            tv_level.setText(getParse().isNull(userInfo.get("CustlevelId")));
            tv_credit.setText(getParse().isNull(userInfo.get("CustCreditRest")));
            tv_credit.setText(getParse().isNull(userInfo.get("CustPoint")));
            tv_pocketmoney.setText(getParse().isNull((userInfo.get("CustPocketMoney"))));*/
            //tv_bank_count.setText(getParse().isNull((userInfo.get("CustPocketMoney"))));
            /*tv_bill_count.setText(getParse().isNull((userInfo.get("tv_bill_count"))));
            tv_order_count.setText(getParse().isNull((userInfo.get("tv_order_count"))));*/

        //}
    }

    @Override
    public void notifyUserInfo(Map<String, Object> userInfo) {

    }
}
