package com.zyx.utils;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyx on 2016/2/11.
 */
public class CrashHandler {

    public static final String TAG = "CrashHandler";

    //CrashHandler实例
    private static CrashHandler instance;

    private Context mContext;

    private Map<String, String> infos = new HashMap<String, String>();


    //private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static ArrayList<Activity> activityList;// activity管理器
    //private String toast;// 崩溃时的提示信息

    /**
     * 向管理器添加
     *
     * param a
     */
    public static void add(Activity a){
        if(activityList == null)
            activityList = new ArrayList<Activity>();
        activityList.add(a);
    }

    /**
     * 从管理器删除
     *
     * @param ac
     */
    public static void remove(Activity ac){
        if(activityList != null){
            activityList.remove(ac);
            System.out.println(activityList.size());
        }
    }

    /**
     * 获取管理器大小
     *
     * @return int
     */
    public static int getActivityListSize(){
        if(activityList == null)
            activityList = new ArrayList<Activity>();
        return activityList.size();
    }

    /**
     * 完全退出
     */
    public static void exit(){
        if(activityList != null){
            while(activityList.size() != 0){
                Activity ac = activityList.get(activityList.size() - 1);
                ac.finish();
            }
            activityList.clear();
            System.out.println(activityList.size());
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 关闭所有
     */
    public static void finish() {
        if (activityList != null) {
            while (activityList.size() != 0) {
                try {
                    Activity ac = activityList.get(activityList.size() - 1);
                    ac.finish();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            activityList.clear();
            System.out.println(activityList.size());
        }
    }

    /**
     * 清除管理器
     */
    public static void clearActivityList() {
        if (activityList != null)
            activityList.clear();
    }

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler(){

    }

    /** 获取CrashHandler实例，单例模式*/
    public static CrashHandler getInstance(){
        if(instance == null){
            instance = new CrashHandler();
        }

        return instance;
    }



}
