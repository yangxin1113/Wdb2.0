package com.zyx.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.zyx.application.MyApplication;
import com.zyx.contants.Contants;
import com.zyx.wdb.MainActivity;
import com.zyx.R;

import java.util.Map;


/**
 * Created by zyx on 2016/2/14.
 */
public abstract class MyBaseFragmentActivity extends BaseFragmentActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        isUserMapNull = isUserMapNull();
        isToken = isToken();
        super.onCreate(arg0);
    }

    /**
     *
     * 设置是否检测用户数据是否为空
     *
     * @return true为检测，false为不检测
     */
    protected abstract boolean isUserMapNull();

    /**
     *
     * 设置是否检测其它设备登录
     *
     * @return true为检测，false为不检测
     */
    protected abstract boolean isToken();

    /**
     * 清除除了首页以外的全部活动页面
     */
    protected void startHomeActivity() {
        // CrashHandler.finish();
        // Intent in = new Intent(getApplicationContext(),
        // HomeFragmentActivity.class);
        // // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // startActivity(in);
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// 设置不要刷新将要跳到的界面
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 它可以关掉所要到的界面中间的activity
        startActivity(intent);
        overridePendingTransition(R.anim.top_in_600, R.anim.bottom_out_600);
    }

    /**
     * 判断用户是否登录
     *
     * @return true为已登录，false为未登录
     */
    protected boolean isLogin() {
        Map<String, Object> user = ((MyApplication) getApplication()).getUser();
        if (user != null && getParse().parseBool(user.get("CustNick"))) {
            return true;
        }
        Map<String, String> userMap = ((MyApplication) getApplication())
                .getUserMap();
        if ((user != null && (user.get("CustPhoneNum") != null))
                && (userMap != null && userMap.get(Contants.UPWD) != null)) {
            return true;
        }
        return false;
    }

    /**
     * 拉起系统电话
     */
    protected void startPhone(String uri) {
        if (uri == null || "".equals(uri))
            return;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
