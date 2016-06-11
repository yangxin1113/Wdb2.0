package com.zyx.fragment.loan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.ad.Passwordborder.PayPasswordView;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.contants.Contants;
import com.zyx.contants.UpdateUserInfo;
import com.zyx.fragment.product.OrderSuccess;
import com.zyx.fragment.product.ProductFragmentActivity;
import com.zyx.thread.PostDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Resolve;
import com.zyx.widget.DialogWidget;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyx on 2016/2/22.
 */
public class LoanOrderActivity extends MyBaseFragmentActivity implements UpdateUserInfo.onUpdateUserInfo {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private DialogWidget mDialogWidget;
    private Button bt_buy;
    private TextView tv_productName;
    private TextView tv_stage;
    private TextView tv_repayment;
    private TextView tv_name_tel;
    private TextView tv_loanMoney;

    /**支付方式钱包,微信,支付宝*/
    private ImageView pay_z;
    private ImageView pay_b;
    private LinearLayout ll_zfb;
    private LinearLayout ll_bank;

    /**相关数据*/
    private String repayment;
    private String stages;
    private String loanMoney;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_pay_z:
                pay_z.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                pay_b.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                ll_zfb.setVisibility(View.VISIBLE);
                ll_bank.setVisibility(View.GONE);
                break;
            case R.id.iv_pay_b:
                pay_z.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                pay_b.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                ll_zfb.setVisibility(View.GONE);
                ll_bank.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_buy:
                mDialogWidget=new DialogWidget(LoanOrderActivity.this, getDecorViewDialog());
                mDialogWidget.show();
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
        setContentView(R.layout.loan_fragment_creat_order);
        Bundle bundle = getIntent().getExtras();
        stages = bundle.getString("Stages");
        loanMoney = bundle.getString("LoanMoney");
        repayment = bundle.getString("Repayment");

    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        bt_buy = (Button)findViewById(R.id.bt_buy);
        pay_z = (ImageView) findViewById(R.id.iv_pay_z);
        pay_b = (ImageView) findViewById(R.id.iv_pay_b);
        tv_productName = (TextView) findViewById(R.id.tv_product_name);
        tv_stage = (TextView) findViewById(R.id.tv_stage);
        tv_repayment = (TextView) findViewById(R.id.tv_repayment);
        tv_name_tel = (TextView)findViewById(R.id.tv_name_tel);
        tv_loanMoney = (TextView)findViewById(R.id.tv_loanMoney);
        ll_bank = (LinearLayout) findViewById(R.id.ll_bank);
        ll_zfb = (LinearLayout)findViewById(R.id.ll_zfb);

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
        mtb_title.setText(getString(R.string.title_create_order_zh));
        //tv_productName.setText(productName);

    }

    @Override
    protected void initEvent() {
        mtb_title.setLeftImageOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });


        pay_b.setOnClickListener(this);
        pay_z.setOnClickListener(this);
        bt_buy.setOnClickListener(this);



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

    @Override
    protected void getData() {
        tv_productName.setText("大学生分期贷款 " +loanMoney +"元");
        tv_stage.setText(stages);
        tv_loanMoney.setText(loanMoney);
        tv_repayment.setText(repayment);

    }

    protected View getDecorViewDialog() {
        // TODO Auto-generated method stub
        return PayPasswordView.getInstance("0", this, new PayPasswordView.OnPayListener() {

            @Override
            public void onSurePay(String password) {
                // TODO Auto-generated method stub
                /*mDialogWidget.dismiss();
                mDialogWidget = null;*/
                //payTextView.setText(password);
                postData(password);

            }

            @Override
            public void onCancelPay() {
                // TODO Auto-generated method stub
                mDialogWidget.dismiss();
                mDialogWidget = null;
                Toast.makeText(getApplicationContext(), "交易已取消", Toast.LENGTH_SHORT).show();

            }
        }).getView();
    }

    public void postData(String dealPwd){
        Map<String, String > map = new HashMap<String, String>();
        map.put("customerId", String.valueOf(((MyApplication) getApplication()).getUser().get("CustomerId")));
        map.put("loanMoney",loanMoney);
        map.put("stages", stages);
        map.put("repayment", repayment);
        map.put("dealPwd", dealPwd);
        map.put("condition", "loan");
        startRunnable(new PostDataThread(Contants.CreateLoan, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
    }

    @Override
    public void notifyUserInfo(Map<String, Object> userInfo) {

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setUserInfo(((MyApplication) getApplication()).getUser());

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
            String name = getParse().isNull(userInfo.get("CustName"));
            String tel = getParse().isNull(userInfo.get("CustPhoneNum"));
            String addr = getParse().isNull(userInfo.get("CustAddress"));

            if("".equals(name) || "".equals(tel) || "".equals(addr)){
            }else{
                tv_name_tel.setText("收件人  ：" + name +"  " + tel);

            }
        }
    }


    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");

                Map<String, Map<String, Object>> mapXML = (Map<String, Map<String, Object>>) (msg.obj);
                LogUtil.i("zyxcreate11111", "mapXML,mapXML:"+mapXML);

                if (mapXML != null && mapXML.size() > 0) {
                    if ("200".equals(mapXML.get("code"))) {
                        LogUtil.i("zyx", "yes");
                        ArrayList<Map<String, Object>> userinfList = Resolve
                                .getInstance().getList(mapXML, "userInfo");
                        if (mDialogWidget.isShowing())
                            mDialogWidget.dismiss();
                        Toast.makeText(getApplicationContext(), "交易成功", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoanOrderActivity.this, OrderSuccess.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        utils.showToast(getApplicationContext(),
                                getParse().isNull(mapXML.get("msg")));
                        break;
                    }

                }
            /*case MyMessageQueue.TIME_OUT:
                LogUtil.i(TAG, "TIME_OUT,TIME_OUT,TIME_OUT");
                utils.showToast(getApplicationContext(),
                        getString(R.string.login_error_zh_));
                break;*/
            default:
                break;
        }

    }
}
