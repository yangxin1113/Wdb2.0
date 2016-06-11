package com.zyx.fragment.product;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.zyx.utils.Parse;
import com.zyx.utils.Resolve;
import com.zyx.widget.DialogWidget;
import com.zyx.widget.MyTitleBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import c.b.BP;
import c.b.PListener;
import c.b.QListener;

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
    private int payWay; //记录支付方式

    // 此为测试Appid,请将Appid改成你自己的Bmob AppId
    String APPID = "f194a385c4278987a484d6a4ccf93dc1";
    // 此为支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;

    ProgressDialog dialog;

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
                payWay = 0;
                break;
            case R.id.iv_pay_w:
                pay_q.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                pay_z.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                pay_w.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                payWay = 2;
                break;
            case R.id.iv_pay_z:
                pay_q.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                pay_z.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                pay_w.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                payWay = 1;
                break;
            case R.id.bt_buy:
                if (payWay == 0) {
                    mDialogWidget=new DialogWidget(ProductOrderActivity.this, getDecorViewDialog());
                    mDialogWidget.show();
                }else if(payWay==1){
                    pay(true);
                }else if(payWay==2){
                    pay(false);
                }

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
        // 必须先初始化
        BP.init(this, APPID);
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
                        if(payWay==0){
                            if (mDialogWidget.isShowing())
                                mDialogWidget.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "交易成功", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ProductOrderActivity.this, OrderSuccess.class);
                        startActivity(i);
                        finish();
                    } else {
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


    /**
     * 调用支付
     *
     * @param alipayOrWechatPay
     *            支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {
        showDialog("正在获取订单...");
        final String name = productName;

        BP.pay(name, getBody(), getPrice(), alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(ProductOrderActivity.this, "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                //tv.append(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                showDialog("获取订单成功!请等待跳转到支付页面~");
                Map<String, String > map = new HashMap<String, String>();
                map.put("customerId", customerId);
                map.put("productNumber",pronctnumber);
                map.put("stages", stages);
                map.put("firstPay", firstpay);
                map.put("repayment", repayment);
                map.put("dealPwd", ((MyApplication)getApplication()).getUser().get("CustDealPwd").toString());
                startRunnable(new PostDataThread(Contants.CreateOrder, map,
                        handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
                Toast.makeText(ProductOrderActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
                //tv.append(name + "'s pay status is success\n\n");

                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                //order.setText(orderId);
                // tv.append(name + "'s orderid is " + orderId + "\n\n");

            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            ProductOrderActivity.this,
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_LONG).show();
                    installBmobPayPlugin("bp.db");
                } else {
                    Toast.makeText(ProductOrderActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
                //tv.append(name + "'s pay status is fail, error code is \n"
                //      + code + " ,reason is " + reason + "\n\n");
                hideDialog();
            }
        });
    }

   /* // 执行订单查询
    void query() {
        showDialog("正在查询订单...");
        final String orderId = getOrder();

        BP.query(orderId, new QListener() {

            @Override
            public void succeed(String status) {
                Toast.makeText(ProductOrderActivity.this, "查询成功!该订单状态为 : " + status,
                        Toast.LENGTH_SHORT).show();
                //tv.append("pay status of" + orderId + " is " + status + "\n\n");
                hideDialog();
            }

            @Override
            public void fail(int code, String reason) {
                Toast.makeText(ProductOrderActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                // tv.append("query order fail, error code is " + code
                +" ,reason is \n" + reason + "\n\n");
                hideDialog();
            }
        });
    }*/




    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBody() {
        return "首付："+firstpay+" ,期数："+stages+", 月供："+repayment;
    }

    public double getPrice() {
        Parse.getInstance().parseDouble(firstpay,"#.##");
        return 0.01;
    }
}
