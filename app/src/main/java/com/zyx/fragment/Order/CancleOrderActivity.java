package com.zyx.fragment.Order;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.OrderData;
import com.zyx.contants.Contants;
import com.zyx.json.ParserJsonOrderitem;
import com.zyx.thread.PostDataThread;
import com.zyx.thread.getJsonDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.wdb.MainActivity;
import com.zyx.widget.CustomDialog;
import com.zyx.widget.DialogWidget;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/3/26.
 */
public class CancleOrderActivity extends MyBaseFragmentActivity {

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private DialogWidget mDialogWidget; //取消订单弹框
    private Button bt_cancle_order;
    private ImageView iv_reson1;
    private ImageView iv_reson2;
    private ImageView iv_reson3;
    /** 相关数据 */
    String ordernumber;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_cancle:
                Map<String, String > map = new HashMap<String, String>();
                map.put("ordernumber", ordernumber);
                map.put("condition", "cancle");
                startRunnable(new PostDataThread(Contants.UpdateOrderStatus, map,
                        handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
                break;

            case R.id.iv_reson1:
                iv_reson1.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                iv_reson2.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                iv_reson3.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                break;
            case R.id.iv_reson2:
                iv_reson1.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                iv_reson2.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
                iv_reson3.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                break;
            case R.id.iv_reson3:
                iv_reson1.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                iv_reson2.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_false));
                iv_reson3.setImageDrawable(getResources().getDrawable(R.mipmap.check_img_true));
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
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");
                Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) (msg.obj);
                LogUtil.i("zyx", "data,data:" + data);
                if(data != null ){
                     if("200".equals(data.get("code"))){
                         utils.showToast(getApplicationContext(),String.valueOf(data.get("msg")));
                         Intent i = new Intent(CancleOrderActivity.this, MainActivity.class);
                         startActivity(i);
                         finish();
                     }else{
                         utils.showToast(getApplicationContext(),String.valueOf(data.get("msg")));
                     }
                }
        }
    }



    @Override
    protected void init(Bundle arg0) {

        setContentView(R.layout.fragment_order_cancle);
        ordernumber = getIntent().getExtras().get("OrderNumber").toString();
        LogUtil.w("zzzzzzz",ordernumber);
        Toast.makeText(getApplicationContext(), ordernumber+"zzzzz", Toast.LENGTH_LONG);
    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        bt_cancle_order = (Button) findViewById(R.id.bt_cancle);
        iv_reson1 = (ImageView) findViewById(R.id.iv_reson1);
        iv_reson2 = (ImageView) findViewById(R.id.iv_reson2);
        iv_reson3 = (ImageView) findViewById(R.id.iv_reson3);


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

        mtb_title.setText(getString(R.string.cancle_my_order));
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
        bt_cancle_order.setOnClickListener(this);
        iv_reson1.setOnClickListener(this);
        iv_reson2.setOnClickListener(this);
        iv_reson3.setOnClickListener(this);
    }

    @Override
    protected void getData() {

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
}
