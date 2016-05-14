package com.zyx.fragment.life;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ipaynow.plugin.api.IpaynowPlugin;
import com.ipaynow.plugin.utils.MerchantTools;
import com.ipaynow.plugin.utils.PreSignMessageUtil;
import com.zyx.R;
import com.zyx.ad.MyAdapter.GridViewAdapterProduct;
import com.zyx.ad.Passwordborder.PayPasswordView;
import com.zyx.application.MyApplication;
import com.zyx.base.BaseFragment;
import com.zyx.bean.ProductData;
import com.zyx.contants.Contants;
import com.zyx.fragment.product.OrderSuccess;
import com.zyx.fragment.product.ProductFragmentActivity;
import com.zyx.json.ParserJsonProductitem;
import com.zyx.thread.PostDataThread;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.HttpUtil;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Resolve;
import com.zyx.widget.DialogWidget;
import com.zyx.widget.MyGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by zyx on 2016/2/18.
 */
public class FragmentTabHuaFei extends BaseFragment {

    private MyGridView gv_huafei_view; //
    private List<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();
    private SimpleAdapter adapter;
    private DialogWidget mDialogWidget;
    private String payMoney;
    private EditText et_phone_no;// 帐号输入框


    private static final String GETORDERMESSAGE_URL = "http://192.168.1.103:8080/wdb_android/payway.action";
    private static Activity act=null;
    private static ProgressDialog progressDialog=null;
    private static String preSignStr=null;
    private PreSignMessageUtil preSign=new PreSignMessageUtil();

    @Override
    protected void init() {
       setLayoutRes(R.layout.fragment_huafei);
        //gv_download();
        act = getActivity();
        prePayMessage();
    }

    @Override
    protected void initView(View rootview) {
        gv_huafei_view = (MyGridView) rootview.findViewById(R.id.gv_huafei_view);
        et_phone_no = (EditText) rootview.findViewById(R.id.et_phone_no);
        huafeiData();
    }

    @Override
    protected void setViewData() {

    }

    @Override
    protected void initEvent() {
        intemOnClick();
    }

    private void intemOnClick() {
        gv_huafei_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //utils.showToast(getActivity(), String.valueOf(mData.get(position).get("money")));
                payMoney = String.valueOf(mData.get(position).get("money"));
                if (isNext()) {
                    mDialogWidget = new DialogWidget(getActivity(), getDecorViewDialog());
                    mDialogWidget.show();
                }
            }
        });
    }

    private boolean isNext() {
        boolean isNext = true;
        if(TextUtils.isEmpty(et_phone_no.getText())){
            utils.showToast(getContext(),getString(R.string.input_phone_number));
            isNext = false;
        }
        return isNext;
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
                LogUtil.i("zyxcreate11111", "mapXML,mapXML:"+mapXML);

                if (mapXML != null && mapXML.size() > 0) {
                    if ("200".equals(mapXML.get("code"))) {
                        LogUtil.i("zyx", "yes");
                        ArrayList<Map<String, Object>> userinfList = Resolve
                                .getInstance().getList(mapXML, "userInfo");
                        if (mDialogWidget.isShowing())
                            mDialogWidget.dismiss();
                        Toast.makeText(getContext(), "交易成功", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), OrderSuccess.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                    else {
                        utils.showToast(getContext(),
                                getParse().isNull(mapXML.get("msg")));
                        break;
                    }

                }


        }
    }

    private void huafeiData(){
        Map<String,Object> item0 = new HashMap<String,Object>();
        item0.put("money", "10");
        item0.put("pay", "9.95");
        mData.add(item0);

        Map<String,Object> item1 = new HashMap<String,Object>();
        item1.put("money", "30");
        item1.put("pay", "29");
        mData.add(item1);

        Map<String,Object> item2 = new HashMap<String,Object>();
        item2.put("money", "50");
        item2.put("pay", "48");
        mData.add(item2);

        Map<String,Object> item3 = new HashMap<String,Object>();
        item3.put("money", "100");
        item3.put("pay", "97");
        mData.add(item3);

        Map<String,Object> item4 = new HashMap<String,Object>();
        item4.put("money", "200");
        item4.put("pay", "196");
        mData.add(item4);

        Map<String,Object> item5 = new HashMap<String,Object>();
        item5.put("money", "500");
        item5.put("pay", "495");
        mData.add(item5);


        adapter = new SimpleAdapter(
                getActivity(),
                mData,
                R.layout.item_huadei,
                new String[]{"money","pay"},
                new int[]{R.id.tv_money,R.id.tv_pay});
        gv_huafei_view.setAdapter(adapter);

    }



    @Override
    public void onClick(View v) {

    }


    protected View getDecorViewDialog() {
        // TODO Auto-generated method stub
        return PayPasswordView.getInstance(payMoney, getActivity(), new PayPasswordView.OnPayListener() {

            @Override
            public void onSurePay(String password) {
                // TODO Auto-generated method stub
                /*mDialogWidget.dismiss();
                mDialogWidget = null;*/
                //payTextView.setText(password);
                //postData(password);
                preSign.mhtOrderNo=new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
                goToPay("12");

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
        Map<String, String > map = new HashMap<String, String>();
        map.put("customerId", String.valueOf(((MyApplication) getActivity().getApplication()).getUser().get("CustomerId")));
        map.put("loanMoney",payMoney);
        map.put("stages", "0");
        map.put("repayment", "0");
        map.put("dealPwd", dealPwd);
        map.put("condition", "huafei");
        startRunnable(new PostDataThread(Contants.CreateLoan, map,
                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
    }





    private void goToPay(String flag){
        ConnectivityManager manager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if (info!=null&&info.isConnected()) {
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("进度提示-商户提示框");
            progressDialog.setMessage("获取签名中...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            preSign.payChannelType=flag;
            preSignStr=preSign.generatePreSignMessage();
            Log.i("待签名字符串", preSignStr);
            GetMessage gM=new GetMessage();
            gM.execute("paydata="+ MerchantTools.urlEncode(preSignStr));
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(act);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("网络状态");
            builder.setMessage("没有可用网络,是否进入设置面板");
            builder.setPositiveButton("是",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(act, "联网失败", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
        }
    }
    private void prePayMessage(){
        preSign.appId="1462966660185981";//
        preSign.mhtOrderName="PPS游戏充";
        preSign.mhtOrderType="01";
        preSign.mhtCurrencyType="156";
        preSign.mhtOrderAmt="10";
        preSign.mhtOrderDetail="PPS游戏充";
        preSign.mhtOrderTimeOut="3600";
        preSign.mhtOrderStartTime=new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA).format(new Date());
        preSign.notifyUrl="http://testmobile.pay.game.pps.tv/payment/api/now_notify_url";
        preSign.mhtCharset="UTF-8";
        preSign.consumerId="1176445156";
        preSign.consumerName="1176445156";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String respCode = data.getExtras().getString("respCode");
        String errorCode = data.getExtras().getString("errorCode");
        String respMsg = data.getExtras().getString("respMsg");
//      AlertDialog.Builder builder = new AlertDialog.Builder(this);
//      builder.setTitle("支付结果通知");
        StringBuilder temp=new StringBuilder();
        if (respCode.equals("00")) {
            temp.append("交易状态:成功");
        }

        if (respCode.equals("02")) {
            temp.append("交易状态:取消");
        }

        if (respCode.equals("01")) {
            temp.append("交易状态:失败").append("\n").
                    append("错误码:"+errorCode).
                    append("原因:"+respMsg);
        }

        if (respCode.equals("03")) {
            temp.append("交易状态:未知").append("\n").
                    append("错误码:"+errorCode).
                    append("原因:"+respMsg);
        }
        Toast.makeText(act, temp.toString(), Toast.LENGTH_LONG).show();
//      builder.setMessage(temp.toString());
//      builder.setInverseBackgroundForced(true);
//      builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialog, int which) {
//              dialog.dismiss();
//          }
//      });
//      builder.create().show();
    }

    public static class GetMessage extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... params) {
            String msg = params[0];
            Log.i("发送给服务器", msg);
            String needcheckmsg = HttpUtil.post(GETORDERMESSAGE_URL, msg);
            needcheckmsg=preSignStr+"&"+needcheckmsg;
            return needcheckmsg;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("收到的请求信息", result);
            IpaynowPlugin.setPayLoading(progressDialog);
            IpaynowPlugin.pay(act,result);
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
