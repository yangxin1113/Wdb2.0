package com.zyx.application;

import android.content.Context;

import com.zyx.base.BaseApplication;
import com.zyx.contants.Contants;
import com.zyx.utils.Encipher;
import com.zyx.utils.MyUtils;
import com.zyx.utils.SaveListObject;
import com.zyx.R;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyx on 2016/2/11.
 */
public class MyApplication extends BaseApplication {

    public static Context context;// 全局上下文

    private Map<String, Object> user;// 用户资料
    private Map<String, String> userMap;// 用户名与密码
    public static Map<String, Map<String, Object>> configure;// 服务器总配置文件

    public static String search;// 搜索记录

    public static File fileUri;// 拍照后生成的图片路径

    public static int role;

    //private OnGetConfigure onGetConfigure;
    @Override
    protected void init() {
        getUser();
        getUserMap();
        context = this;
    }

    @Override
    public String initDomain() {
        // TODO Auto-generated method stub
        return getString(R.string.ip);
    }

    /** 获取用户资料 */
    public Map<String, Object> getUser() {
        if (user == null) {
            File file = MyUtils.getInstance().getCache(getApplicationContext(),
                    Contants.USER_PATH_PRIVATE, Contants.USER_INFO, true);
            user = (Map<String, Object>) SaveListObject.getInstance()
                    .openObject(file);
        }
        return user;
    }

    /**
     * 设置用户资料
     *
     * @param user
     */
    public void setUser(Map<String, Object> user) {
        if (user == null) {
            MyUtils.getInstance()
                    .getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.USER_INFO,
                            true).delete();
        } else {
            SaveListObject.getInstance().saveObject(
                    MyUtils.getInstance().getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.USER_INFO,
                            true), user);
        }
        this.user = user;
    }

    /** 获取用户名与密码 */
    public Map<String, String> getUserMap() {
        if (userMap == null) {
            userMap = (Map<String, String>) SaveListObject.getInstance()
                    .openObject(
                            MyUtils.getInstance().getCache(
                                    getApplicationContext(),
                                    Contants.USER_PATH_PRIVATE,
                                    Contants.USER_FILE, true));
            if (userMap != null) {
                try {
                    String pwd = Encipher.jieMi(userMap.get(Contants.UPWD));
                    String un = userMap.get(Contants.UN);
                    userMap.put(Contants.UN, un);
                    userMap.put(Contants.UPWD, pwd);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                }
            }
        }
        return userMap;
    }

    /**
     * 设置用户名与密码
     *
     * @param userMap
     */
    public void setUserMap(Map<String, String> userMap) {
        this.userMap = userMap;
        if (userMap != null) {
            try {
                Map<String, String> um = new HashMap<String, String>();
                String un = userMap.get(Contants.UN);
                String pwd = Encipher.jiaMi(userMap.get(Contants.UPWD));
                um.put(Contants.UN, un);
                um.put(Contants.UPWD, pwd);
                SaveListObject.getInstance().saveObject(
                        MyUtils.getInstance().getCache(getApplicationContext(),
                                Contants.USER_PATH_PRIVATE, Contants.USER_FILE,
                                true), um);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }
        } else {
            MyUtils.getInstance()
                    .getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.USER_FILE,
                            true).delete();
        }
    }

  /*  *//**
     * 存储configure
     *
     * @param configure
     *//*
    public synchronized void setConfigure(
            Map<String, Map<String, Object>> configure) {
        MyApplication.configure = configure;
        if (configure != null) {
            SaveListObject.getInstance().saveObject(
                    MyUtils.getInstance().getCache(this, Contants.CONFIGURE,
                            Contants.CONFIGURE, true), configure);
        }
    }

    *//**
     * 获取configure
     *
     * @return
     *//*
    public synchronized void getConfigure(OnGetConfigure onGetConfigure) {
        this.onGetConfigure = onGetConfigure;
        if (configure == null) {
            configure = (Map<String, Map<String, Object>>) SaveListObject
                    .getInstance().openObject(
                            MyUtils.getInstance().getCache(this,
                                    Contants.CONFIGURE, Contants.CONFIGURE,
                                    true));
        }
        if (configure != null) {
            if (onGetConfigure != null)
                onGetConfigure.onGetConfigure(configure);
        } else {
            executorService.execute(new GetDataThread(this,
                    Contants.STATIC_DATA, handler, MyMessageQueue.OK,
                    MyMessageQueue.TIME_OUT, MyMessageQueue.DATA_EXCEPTION,
                    false));
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MyMessageQueue.OK:
                    if (onGetConfigure != null) {
                        if (msg.obj != null) {
                            onGetConfigure
                                    .onGetConfigure((Map<String, Map<String, Object>>) msg.obj);
                            break;
                        }
                    }
                case MyMessageQueue.DATA_EXCEPTION:
                    if (onGetConfigure != null)
                        onGetConfigure.onDataException();
                    break;
                case MyMessageQueue.TIME_OUT:
                    if (onGetConfigure != null)
                        onGetConfigure.onNetworkExecption();
                    break;

                default:
                    break;
            }
        }
    };*/
}
