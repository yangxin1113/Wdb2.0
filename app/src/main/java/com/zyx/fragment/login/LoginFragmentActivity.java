package com.zyx.fragment.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.contants.Contants;
import com.zyx.thread.PostDataThread;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Resolve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyx on 2016/2/15.
 */
public class LoginFragmentActivity extends MyBaseFragmentActivity implements
        OnTouchListener, OnLongClickListener {

    private final static  String TAG ="LoginFragmentActivity";
    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private ImageView iv_left;// 返回按钮
    private TextView tv_right;// 注册按钮
    private EditText et_phone_no;// 帐号输入框
    private EditText et_pwd;// 密码输入框
    private TextView tv_forget_pwd;// 忘记密码
    private Button bt_login;// 登陆按钮

    /** 进度条 */
    private ProgressDialog pd;

    /** 数据相关 */
    private Map<String, String> userMap;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;

            case R.id.bt_login:
                //登录
                if(isLoginOK()) {
                    pd.setCancelable(false);
                    pd.setMessage(getString(R.string.longing_zh));
                    pd.show();
                    utils.setKeyBoardGone(getApplicationContext(), et_phone_no, et_pwd);

                  try {
                        LogUtil.i("zyx", "11111111111");

                       Map<String, String> map = new HashMap<String, String>();
//			jSon.put("uid", et_phone_no.getText().toString());
//			jSon.put("pwd", et_pwd.getText().toString());
//			jSon.put("cmd", "applogin");
//			jSon.put("dwc", utils.getDeviceId(getApplicationContext()));
                        map.put("userName", et_phone_no.getText().toString());
                        map.put("password", et_pwd.getText().toString());

                        startRunnable(new PostDataThread(Contants.USER_LOGIN, map,
                                handler, MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
                        LogUtil.i("zyx", "2222222222222");

                }catch(Exception e1){
                        e1.printStackTrace();
                        utils.showToast(getApplicationContext(),
                                getString(R.string.login_error_zh_));
                    }

                }

                break;
            case R.id.tv_right:
                startActivityForResult(new Intent(getApplicationContext(),
                        RegisterFragmentActivity.class), Activity.RESULT_FIRST_USER);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.tv_forget_pwd:
                Intent in = new Intent(getApplicationContext(),
                        RegisterFragmentActivity.class);
                in.putExtra("isForget", true);
                startActivity(in);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            default:
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
// TODO Auto-generated method stub
        switch (msg.what) {
            case MyMessageQueue.OK:
                LogUtil.i("zyx", "44444444444");

                Map<String, Map<String, Object>> mapXML = (Map<String, Map<String, Object>>) (msg.obj);
                LogUtil.i("zyx", "mapXML,mapXML:"+mapXML);

//	ArrayList<Map<String, Object>> resultList = Resolve.getInstance().getList(mapXML, "result");
//	ArrayList<Map<String, Object>> resultList = Resolve.getInstance().getList(mapXML, "data", "userinf");
//	Log.i("fc", "resultList,resultList:"+resultList.size());
                if (mapXML != null && mapXML.size() > 0) {
                    if ("200".equals(mapXML.get("code"))) {
                        LogUtil.i("zyx","yes");
                        ArrayList<Map<String, Object>> userinfList = Resolve
                                .getInstance().getList(mapXML, "userInfo");
                        //LogUtil.i("zyx",userinfList.toString());
                        if (userinfList != null && userinfList.size() > 0) {
                            Map<String, Object> user = userinfList.get(0);
                            if (user.get("CustomerId") != null) {
                               // LogUtil.i("zyx1111",user.get("CustomerId").toString());
                                ((MyApplication) getApplication()).setUser(user);
                                if (userMap == null)
                                    userMap = new HashMap<String, String>();
                                userMap.put(Contants.UN, et_phone_no.getText()
                                        .toString());
                                userMap.put(Contants.UPWD, et_pwd.getText()
                                        .toString());
                                ((MyApplication) getApplication())
                                        .setUserMap(userMap);
                                utils.showToast(getApplicationContext(),
                                        getString(R.string.login_ok_zh));

                                Intent in = new Intent();
                                in.putExtra("isLogin", true);
                                setResult(Activity.RESULT_OK, in);
                                finish();
                                overridePendingTransition(R.anim.no_animation,
                                        R.anim.bottom_out);
                                break;
                            }
                        }
                    }
                    else {
                        utils.showToast(getApplicationContext(),
                                getParse().isNull(mapXML.get("msg")));
                        break;
                    }
                }

            case MyMessageQueue.TIME_OUT:
                LogUtil.i(TAG, "TIME_OUT,TIME_OUT,TIME_OUT");
                utils.showToast(getApplicationContext(),
                        getString(R.string.login_error_zh_));
                break;

            default:
                break;
        }
        if (pd.isShowing())
            pd.dismiss();
    }

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.fragment_activity_login);
        pd = new ProgressDialog(this);
    }

    @Override
    protected void initView() {
        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        et_phone_no = (EditText) findViewById(R.id.et_phone_no);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
        bt_login = (Button) findViewById(R.id.bt_login);

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
        userMap = ((MyApplication) getApplication()).getUserMap();
        if (userMap != null) {
            et_phone_no.setText(getParse().isNull(userMap.get(Contants.UN)));
            et_pwd.setText(getParse().isNull(userMap.get(Contants.UPWD)));
        }

    }

    @Override
    protected void initEvent() {
        et_phone_no.setOnTouchListener(this);
        et_pwd.setOnTouchListener(this);
        et_pwd.setOnLongClickListener(this);
        iv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        bt_login.setOnClickListener(this);

    }

    @Override
    protected void getData() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        et_phone_no.setFocusable(true);
        et_phone_no.setFocusableInTouchMode(true);
        et_pwd.setFocusable(true);
        et_pwd.setFocusableInTouchMode(true);
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.et_pwd:

                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
// TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.no_animation, R.anim.bottom_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
// TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == Activity.RESULT_FIRST_USER) {
            if (arg1 == Activity.RESULT_OK) {
                // 注册成功返回
                if (arg2.getBooleanExtra("isRegistered", false)) {
                    setResult(arg1, arg2);
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                }
            }
        }
    }

    /**
     * 是否满足登录
     *
     * @return
     */
    private boolean isLoginOK(){
        boolean isLoginOK = true;
        if(TextUtils.isEmpty(et_phone_no.getText())){
            utils.showToast(getApplicationContext(),getString(R.string.input_phone_number));
            isLoginOK = false;
        }else if(TextUtils.isEmpty(et_pwd.getText())){
            utils.showToast(getApplicationContext(), getString(R.string.input_pwd));
            isLoginOK = false;

        }
        return isLoginOK;
    }
}
