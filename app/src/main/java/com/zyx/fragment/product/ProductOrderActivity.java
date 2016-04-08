package com.zyx.fragment.product;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zyx.R;
import com.zyx.ad.Passwordborder.PayPasswordView;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.contants.Contants;
import com.zyx.contants.UpdateUserInfo;
import com.zyx.thread.PostDataThread;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Resolve;
import com.zyx.wdb.MainActivity;
import com.zyx.widget.DialogWidget;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyx on 2016/2/22.
 */
public class ProductOrderActivity extends MyBaseFragmentActivity implements UpdateUserInfo.onUpdateUserInfo {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private DialogWidget mDialogWidget;
    private Button bt_buy;
    private ImageView iv_product;
    private TextView tv_productName;
    private TextView tv_Qprice;
    private TextView tv_firstPay;
    private TextView tv_stage;
    private TextView tv_repayment;
    private TextView tv_name_tel;
    private TextView tv_addr ;
    private LinearLayout ll_product;
    private BitmapUtils bitmapUtils;

    /**支付方式钱包,微信,支付宝*/
    private ImageView pay_q;
    private ImageView pay_z;
    private ImageView pay_w;

    /**相关数据*/
    private String pronctnumber;
    private String productName;
    private String productImage;
    private String firstpay;
    private String repayment;
    private String stages;
    private int categoryId;
    private String customerId;



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_pay_q:
                pay_q.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                pay_z.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                pay_w.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                break;
            case R.id.iv_pay_w:
                pay_q.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                pay_z.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                pay_w.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                break;
            case R.id.iv_pay_z:
                pay_q.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                pay_z.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                pay_w.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                break;
            case R.id.bt_buy:
                mDialogWidget=new DialogWidget(ProductOrderActivity.this, getDecorViewDialog());
                mDialogWidget.show();
                break;
            case R.id.ll_product:
                Intent i = new Intent(ProductOrderActivity.this, ProductFragmentActivity.class);
                i.putExtra("categoryId", categoryId);
                startActivity(i);
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
        setContentView(R.layout.product_fragment_creat_order);
        /*imageUrl = getIntent().getStringExtra("imageUrl");
        productName = getIntent().getStringExtra("productName");*/
        Bundle bundle = getIntent().getExtras();
        pronctnumber = bundle.getString("ProductNumber");
        productName = bundle.getString("ProductName");
        productImage = bundle.getString("ProductImage");
        firstpay = bundle.getString("FirstPay");
        stages = bundle.getString("Stages");
        categoryId = bundle.getInt("CategoryId");
        repayment = bundle.getString("Repayment");

    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        bt_buy = (Button)findViewById(R.id.bt_buy);
        pay_q = (ImageView) findViewById(R.id.iv_pay_q);
        pay_z = (ImageView) findViewById(R.id.iv_pay_z);
        pay_w = (ImageView) findViewById(R.id.iv_pay_w);
        ll_product = (LinearLayout)findViewById(R.id.ll_product);
        iv_product = (ImageView) findViewById(R.id.iv_product);
        tv_productName = (TextView) findViewById(R.id.tv_product_name);
        tv_Qprice = (TextView) findViewById(R.id.tv_Qprice);
        tv_firstPay = (TextView) findViewById(R.id.tv_firstPay);
        tv_stage = (TextView) findViewById(R.id.tv_stage);
        tv_repayment = (TextView) findViewById(R.id.tv_repayment);
        tv_addr =(TextView)findViewById(R.id.tv_addr);
        tv_name_tel = (TextView)findViewById(R.id.tv_name_tel);

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

        pay_q.setOnClickListener(this);
        pay_w.setOnClickListener(this);
        pay_z.setOnClickListener(this);
        bt_buy.setOnClickListener(this);
        ll_product.setOnClickListener(this);


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
        bitmapUtils = new BitmapUtils(getApplicationContext());
        bitmapUtils.display(iv_product, productImage);
        tv_productName.setText(productName);
        tv_stage.setText(stages);
        tv_firstPay.setText(firstpay);
        tv_repayment.setText(repayment);

    }

    protected View getDecorViewDialog() {
        // TODO Auto-generated method stub
        return PayPasswordView.getInstance(firstpay, this, new PayPasswordView.OnPayListener() {

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
        map.put("customerId", customerId);
        map.put("productNumber",pronctnumber);
        map.put("stages", stages);
        map.put("firstPay", firstpay);
        map.put("repayment", repayment);
        map.put("dealPwd", dealPwd);
        startRunnable(new PostDataThread(Contants.CreateOrder, map,
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
            customerId = getParse().isNull(userInfo.get("CustomerId"));
            String name = getParse().isNull(userInfo.get("CustName"));
            String tel = getParse().isNull(userInfo.get("CustPhoneNum"));
            String addr = getParse().isNull(userInfo.get("CustAddress"));

            if("".equals(name) || "".equals(tel) || "".equals(addr)){
            }else{
                tv_name_tel.setText("收件人  ：" + name +"  " + tel);
                tv_addr.setText("收件地址  ："+addr);
            }
           /* String headImg = getParse().isNull(userInfo.get("CustHead"));
            if ("".equals(headImg)) {
                iv_head.setImageResource(R.mipmap.img_head_false);
            } else {
                DisplayImageOptions userHeadOption = ((MyApplication) getActivity()
                        .getApplication()).getUserHeadOptions();
                imageLoader.displayImage(getString(R.string.ip) + headImg,
                        iv_head, userHeadOption);
            }*/
           /* tv_nickname.setText(getParse().isNull(userInfo.get("nickname")));
            tv_number.setText(getParse().isNull(userInfo.get("userid")));*/
//			tv_integral_num
//					.setText(getParse().isNull(userInfo.get("integral")));
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
                        Intent i = new Intent(ProductOrderActivity.this, OrderSuccess.class);
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
