package com.zyx.fragment.repay;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;


import com.zyx.R;
import com.zyx.ad.Passwordborder.PayPasswordView;
import com.zyx.application.MyApplication;
import com.zyx.base.BaseFragment;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;

import com.zyx.dialog.AddPaywayDialog;
import com.zyx.json.ParserJsonOrderitem;

import com.zyx.thread.PostDataThread;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Parse;
import com.zyx.wdb.MainActivity;
import com.zyx.widget.CustomDialog;
import com.zyx.widget.DialogWidget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c.b.BP;
import c.b.PListener;

/**
 * Created by zyx on 2016/3/28.
 */

public class PresentFragment extends BaseFragment{


    private DialogWidget mDialogWidget; //弹框
    private Button bt_yuqi; //逾期
    private Button bt_repay; //还款
    private TextView tv_product_name;
    private TextView tv_stage;
    private TextView tv_mprice;
    private TextView tv_creatdate;
    private TextView tv_begindate;
    private TextView tv_enddate;
    private LinearLayout ll_content;
    private RelativeLayout rl_empty;
    private Button bt_gogo;


    // 此为测试Appid,请将Appid改成你自己的Bmob AppId
    String APPID = "f194a385c4278987a484d6a4ccf93dc1";
    // 此为支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;
    ProgressDialog dialog;


    /** 相关数据 */
    private List<OrderData> lv_OrderData;
    private String ordernumber;
    private int width;// 屏幕宽


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_yuqi:

                //自定义dialog
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setMessage("提示:此操作将会增加每月还款金额哦");
                builder.setTitle("是否确逾期?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //quyiAction();
                        //dialog.dismiss();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("ordernumber", ordernumber);
                        map.put("condition", "yuqi");
                        startRunnable(new getJsonDataThread(Contants.RepayAction, map,
                                handler, 0X4444444, MyMessageQueue.TIME_OUT));

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();

                break;
            case R.id.bt_repay:
                showDialog();
                break;
            case R.id.bt_gogo:
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                break;
        }

    }


    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");
                String data = (String) (msg.obj);
                LogUtil.i("zyx", "data,data:" + data);
                if(data != null ){
                    lv_OrderData = ParserJsonOrderitem.JsontoOrderItem(data);
                    // LogUtil.i("zyx", "data,data:"+lv_OrderData.get(1).getProductnumber());
                    setOrderData(lv_OrderData);

                }
            case 0X4444444:
                LogUtil.i("zyx", "44444444444");
                data = (String) (msg.obj);
                LogUtil.i("zyx", "data,data:" + data);
                if(data != null ){

                    //utils.showToast(getContext(),"操作成功");
                    // LogUtil.i("zyx", "data,data:"+lv_OrderData.get(1).getProductnumber());
                    //setOrderData(lv_OrderData);
                    if(mDialogWidget!=null){
                        mDialogWidget.dismiss();
                        mDialogWidget = null;
                        setViewData();

                    }

                }


        }

    }


    @Override
    protected void init() {

        setLayoutRes(R.layout.fragment_repay_present);
        ordernumber = getActivity().getIntent().getStringExtra("OrderNumber");
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
    }

    @Override
    protected void initView(View rootview) {

        bt_repay = (Button) rootview.findViewById(R.id.bt_repay);
        bt_yuqi = (Button) rootview.findViewById(R.id.bt_yuqi);
        tv_product_name = (TextView) rootview.findViewById(R.id.tv_product_name);
        tv_stage = (TextView) rootview.findViewById(R.id.tv_stage);
        tv_mprice = (TextView) rootview.findViewById(R.id.tv_mprice);
        tv_creatdate = (TextView) rootview.findViewById(R.id.tv_createdate);
        tv_begindate = (TextView) rootview.findViewById(R.id.tv_begindate);
        tv_enddate = (TextView) rootview.findViewById(R.id.tv_enddate);
        ll_content = (LinearLayout) rootview.findViewById(R.id.ll_content);
        rl_empty = (RelativeLayout) rootview.findViewById(R.id.rl_empty);
        bt_gogo = (Button) rootview.findViewById(R.id.bt_gogo);


    }

    @Override
    protected void setViewData() {

        Map<String, String > map = new HashMap<String, String>();
        map.put("orderNumber", ordernumber);
        map.put("condition", "p_stage");
        startRunnable(new getJsonDataThread(Contants.OrdersOfOne, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
    }

    @Override
    protected void initEvent() {
        bt_repay.setOnClickListener(this);
        bt_yuqi.setOnClickListener(this);
        bt_gogo.setOnClickListener(this);

    }

    @Override
    protected void getData() {

    }


    private void setOrderData(List<OrderData> lv_orderData) {
        if (!lv_orderData.isEmpty()) {
            rl_empty.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);
            OrderData od = lv_orderData.get(0);
            LogUtil.w("zzzzzzz", od.getCategoryid().toString());
            tv_product_name.setText(od.getProductname().toString());
            tv_stage.setText(od.getTime().toString() + "/" + od.getTimes().toString());
            tv_mprice.setText(od.getMoney().toString());
            tv_creatdate.setText(od.getOrderdate().toString().replace(".0", ""));
            tv_begindate.setText(od.getBegindate().toString());
            tv_enddate.setText(od.getEnddate().toString());
        } else {
            ll_content.setVisibility(View.GONE);
            rl_empty.setVisibility(View.VISIBLE);
        }
    }



    protected View getDecorViewDialog() {
        // TODO Auto-generated method stub
        return PayPasswordView.getInstance(tv_mprice.getText().toString(), getContext(), new PayPasswordView.OnPayListener() {

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
                Toast.makeText(getContext(), "交易已取消", Toast.LENGTH_SHORT).show();

            }
        }).getView();
    }


    public void postData(String dealPwd){
        String CustDealPwd =  ((MyApplication) getActivity().getApplication()).getUser().get("CustDealPwd").toString();
        if(!dealPwd.equals(CustDealPwd)){
            utils.showToast(getContext(),"密码错误");
        }else{
            Map<String, String> map = new HashMap<String, String>();
            map.put("ordernumber", ordernumber);
            map.put("condition", dealPwd);
            startRunnable(new getJsonDataThread(Contants.RepayAction, map,
                    handler, 0X4444444, MyMessageQueue.TIME_OUT));
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



    /**
     * 显示选择照片dialog
     */
    private void showDialog() {
        AddPaywayDialog dialog = new AddPaywayDialog(getActivity(), R.style.Theme_dialog,
                width, -1);
        dialog.setCancelable(true);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setOnDialogClickListener(new MyOnDialogClickListenxer());
        dialog.show();
    }



    /**
     * 实现dialog中的点击事件接口
     */

    class MyOnDialogClickListenxer implements AddPaywayDialog.OnDialogClickListener{

        @Override
        public void dialogClick(Dialog dialog, View v) {
            switch (v.getId()){
                case R.id.ll_qian:
                    dialog.dismiss();
                    mDialogWidget=new DialogWidget(getActivity(), getDecorViewDialog());
                    mDialogWidget.show();
                    break;
                case R.id.ll_zhi:
                    dialog.dismiss();
                    pay(true);
                    break;
                case R.id.ll_wei:
                    dialog.dismiss();
                    pay(false);
                    break;
                case R.id.tv_cancel:
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
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
        final String name = tv_product_name.getText().toString();

        BP.pay(name, getBody(), getPrice(), alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(getActivity(), "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                //tv.append(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                showDialog("获取订单成功!请等待跳转到支付页面~");
                Map<String, String> map = new HashMap<String, String>();
                map.put("ordernumber", ordernumber);
                map.put("condition", ((MyApplication)getActivity().getApplication()).getUser().get("CustDealPwd").toString());
                startRunnable(new getJsonDataThread(Contants.RepayAction, map,
                        handler, 0X4444444, MyMessageQueue.TIME_OUT));
                Toast.makeText(getActivity(), "支付成功!", Toast.LENGTH_SHORT).show();
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
                            getActivity(),
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_LONG).show();
                    installBmobPayPlugin("bp.db");
                } else {
                    Toast.makeText(getActivity(), "支付中断!", Toast.LENGTH_SHORT)
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
                Toast.makeText(getActivity(), "查询成功!该订单状态为 : " + status,
                        Toast.LENGTH_SHORT).show();
                //tv.append("pay status of" + orderId + " is " + status + "\n\n");
                hideDialog();
            }

            @Override
            public void fail(int code, String reason) {
                Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                // tv.append("query order fail, error code is " + code
                +" ,reason is \n" + reason + "\n\n");
                hideDialog();
            }
        });
    }*/




    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(getContext());
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
            InputStream is = getActivity().getAssets().open(fileName);
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
        return "付款："+tv_mprice.getText().toString()+","+tv_stage.getText().toString();
    }

    public double getPrice() {
        Parse.getInstance().parseDouble(tv_mprice.getText().toString(), "#.##");
        return 0.01;
    }

}
