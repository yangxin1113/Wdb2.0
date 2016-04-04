package com.zyx.fragment.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.thread.CountdownRunnable;
import com.zyx.thread.GetMSGCodeThread;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.ReadSmsContent;
import com.zyx.widget.MyTitleBar;

import cn.smssdk.SMSSDK;

/**
 * Created by zyx on 2016/2/15.
 */
public class RegisterFragmentActivity extends MyBaseFragmentActivity implements
        OnTouchListener {

    /**
     * 控件相关
     */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private MyTitleBar mtb_title;// 标题栏
    private EditText et_phone_no;// 手机号输入框
    private EditText et_code;// 验证码输入框
    private TextView tv_get_code;// 获取验证码按钮
    private RelativeLayout rl_user_agreement;// 用户协议布局
    private ImageView iv_check;// 勾选框
    private TextView tv_agreement;// 注册协议按钮
    private Button bt_next;// 下一步按钮

    /**
     * 数据相关
     */
    private String phoneNumber;// 手机号码
    private String code;// 短信验证码
    private boolean isForget;// 是否找回密码

    /**
     * 线程相关
     */
    private CountdownRunnable mCountdownRunnable;

    private ReadSmsContent readSmsContent;


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_get_code:
                // 获取验证码
                if (isGetPhone()) {
                    tv_get_code.setClickable(false);
                    phoneNumber = et_phone_no.getText().toString();
                    startRunnable(mCountdownRunnable);
                    if (isForget) {
                        startRunnable(new GetMSGCodeThread(handler,
                                MyMessageQueue.OK, phoneNumber,
                                getString(R.string.msg_forget_code), "fpw"));
                    } else {
                        startRunnable(new GetMSGCodeThread(handler,
                                MyMessageQueue.OK, phoneNumber,
                                getString(R.string.msg_reg_template_zh), "reg"));
                    }

                }
                break;

            case R.id.bt_next:

                if (isNext()) {
                    Intent in = new Intent(getApplicationContext(),
                            SettingsPasswordFragmentActivity.class);
                    in.putExtra("isForget", isForget);
                    in.putExtra("code", code);
                    in.putExtra("phoneNumber", phoneNumber);
                    startActivityForResult(in, Activity.RESULT_FIRST_USER);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                break;

            case R.id.iv_check:
                if (getParse().parseBool(iv_check.getTag())) {
                    iv_check.setTag(false);
                    iv_check.setImageResource(R.mipmap.check_img_false);
                } else {
                    iv_check.setTag(true);
                    iv_check.setImageResource(R.mipmap.check_img_true);
                }
                break;

            case R.id.tv_agreement:
                // startActivity(new Intent(getApplicationContext(),
                //       UserAgreementFragmentActivity.class));
                //overridePendingTransition(R.anim.right_in, R.anim.left_out);
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

        switch (msg.what) {
            case MyMessageQueue.OK:
                if (msg.obj == null) {
                    sendHandler(MyMessageQueue.TIME_OUT, null);
                } else {
                    code = getParse().isNull(msg.obj);
                    if ("EXCEPTION".equals(code)) {
                        utils.showToast(getApplicationContext(),
                                getString(R.string.data_exception_zh));
                    } else if ("Y".equals(code) || "W".equals(code)) {
                        if (isForget) {
                            utils.showToast(
                                    getApplicationContext(),
                                    getString(R.string.phone_number_no_registered_zh));
                        } else {
                            utils.showToast(getApplicationContext(),
                                    getString(R.string.already_registered_zh_));
                        }
                    } else if ("500".equals(code)) {
                        utils.showToast(getApplicationContext(),
                                getString(R.string.server_exception_zh_));
                    } else {
                        cn.smssdk.SMSSDK.getVerificationCode("86", et_phone_no.getText().toString());
                        utils.showToast(getApplicationContext(),
                                getString(R.string.code_send_ok_zh_));
                        /*et_code.setText(code);
                        code = et_code.getText().toString();*/

                    }
                }
                if (mCountdownRunnable != null) {
                    mCountdownRunnable.setStop(true);
                }
                tv_get_code.setText(getString(R.string.get_verification_code_zh));
                tv_get_code.setClickable(true);

                break;

            case 0x4000:
                long s = getParse().parseLong(msg.obj);
                if (s <= -1) {
                    tv_get_code
                            .setText(getString(R.string.get_verification_code_zh));
                    tv_get_code.setClickable(true);
                } else {
                    tv_get_code.setText(s + "S");
                }
                break;

            case MyMessageQueue.TIME_OUT:
                utils.showToast(getApplicationContext(),
                        getString(R.string.network_exception));
                break;
            default:
                break;
        }

    }

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.fragment_activity_register);

        et_code = (EditText) findViewById(R.id.et_code);
        //短信验证
        SMSSDK.initSDK(this, "d841e677c102", "183e40fb8ddc399d3df2a2348a014a7d");

        readSmsContent = new ReadSmsContent(new Handler(), this, et_code);

        //注册短信内容监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, readSmsContent);

        isForget = getIntent().getBooleanExtra("isForget", false);
        if (arg0 != null) {
            phoneNumber = arg0.getString("phoneNumber");
        }
        mCountdownRunnable = new CountdownRunnable(60, 0, handler, 0x4000);

    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
        et_phone_no = (EditText) findViewById(R.id.et_phone_no);

        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        rl_user_agreement = (RelativeLayout) findViewById(R.id.rl_user_agreement);
        iv_check = (ImageView) findViewById(R.id.iv_check);
        tv_agreement = (TextView) findViewById(R.id.tv_agreement);
        bt_next = (Button) findViewById(R.id.bt_next);

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
        if (isForget) {
            mtb_title.setText(getString(R.string.lost_pwd_title_zh));
        } else {
            mtb_title.setText(getString(R.string.register_zh));
        }
        if (isForget)
            rl_user_agreement.setVisibility(View.GONE);


    }

    @Override
    protected void initEvent() {

        mtb_title.setLeftImageOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });

        et_phone_no.setOnTouchListener(this);
        et_code.setOnTouchListener(this);
        tv_get_code.setOnClickListener(this);
        iv_check.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
        bt_next.setOnClickListener(this);

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownRunnable != null) {
            mCountdownRunnable.setStop(true);
        }

        //注销内容监听者
        this.getContentResolver().unregisterContentObserver(readSmsContent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getBooleanExtra("isRegistered", false)) {
                    //注册成功返回
                    setResult(requestCode, data);
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                } else if (data.getBooleanExtra("isPwdOk", false)) {
                    //修改密码成功返回
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);

                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        if (phoneNumber != null) {
            outState.putString("phoneNumber", phoneNumber);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        et_phone_no.setFocusable(true);
        et_phone_no.setFocusableInTouchMode(true);
        et_code.setFocusable(true);
        et_code.setFocusableInTouchMode(true);
        return false;
    }

    /**
     * 判断手机号是否输入
     *
     * @return
     */
    private boolean isGetPhone() {
        boolean isGetPhone = true;
        if (TextUtils.isEmpty(et_phone_no.getText())) {
            utils.showToast(getApplicationContext(), getString(R.string.input_phone_number));
            isGetPhone = false;
        } else if (et_phone_no.getText().toString().length() != 11) {
            utils.showToast(getApplicationContext(),
                    getString(R.string.input_phone_no_error_zh));
            isGetPhone = false;
        }
        return isGetPhone;
    }

    /**
     * 是否进行下一步
     *
     * @return
     */
    private boolean isNext() {
        code = et_code.getText().toString();
        boolean isNext = true;
        if (et_phone_no.getText() == null
                || et_phone_no.getText().toString().length() == 0) {
            utils.showToast(getApplicationContext(),
                    getString(R.string.input_phone_number_zh));
            isNext = false;
        } else if (code == null || code.length() != 6 || phoneNumber == null) {
            utils.showToast(getApplicationContext(),
                    getString(R.string.please_get_code_zh));
            isNext = false;
        } else if (et_code.getText() == null
                || et_code.getText().toString().length() == 0) {
            utils.showToast(getApplicationContext(),
                    getString(R.string.input_verification_code_zh));
            isNext = false;
        } else if (!code.equals(et_code.getText().toString())) {
            utils.showToast(getApplicationContext(),
                    getString(R.string.msg_code_error_zh_));
            isNext = false;
        } else if (!getParse().parseBool(iv_check.getTag())) {
            if (!isForget) {
                utils.showToast(getApplicationContext(),
                        getString(R.string.please_agree_agreement_zh_));
                isNext = false;
            }
        }
        return isNext;
    }

}
