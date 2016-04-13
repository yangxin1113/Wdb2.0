package com.zyx.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zyx.application.MyApplication;
import com.zyx.contants.Contants;
import com.zyx.utils.MyUtils;
import com.zyx.utils.Parse;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zyx on 2016/2/11.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    // protected DLJDataDownLoadHelper downLoadManager;//后台数据下载帮助�?
    // protected ZJDataManager zjDataManager;
    /**
     * 如果没有数据处理 请设置为false ,或�?�发送一个handler信息�?以关闭dialog
     */
    protected boolean isFrist = false;
    protected LayoutInflater inflater;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions options;
    protected DisplayImageOptions pagerOptions;
    protected MyUtils utils;
    protected Animation animation;// 动画对象
    protected Bundle savedInstanceState;// bundle对象
    protected ExecutorService executorService;// 线程池

    // private ProgressDialog pd;
    // private Dialog mDialog;// net loading dialog
    /**
     * 布局文件id
     */
    private int resLayout;
    /**
     * dialog显示的提示文字
     *
     * @return
     */
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLayoutRes() {
        return resLayout;
    }

    /**
     * 设置fragment关联布局
     *
     * @param resLayout 关联布局id
     */
    public void setLayoutRes(int resLayout) {
        this.resLayout = resLayout;
    }

    /**
     * 初始数据 在数据初始化时调用setLayoutRes(int resLayout)方法设置fragment对应的layout资源文件
     */
    protected abstract void init();

    /**
     * 控件初始化
     *
     * @param rootview 在控件初始化前设置layout布局文件id ,
     */
    protected abstract void initView(View rootview);

    /**
     * 设置控件属性
     */
    protected abstract void setViewData();

    /**
     * 初始化控件事件
     */
    protected abstract void initEvent();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        try {
            this.inflater = inflater;
            View view = inflater.inflate(resLayout, container, false);
            initView(view);
            setViewData();
            initEvent();
            return view;
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }

    }

    /**
     * 首次加载数据
     */
    protected void firstLoadData() {
        if (isFrist) {
            // showDialog(getActivity(), message);
            isFrist = false;
            getData();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // zjDataManager = new ZJDataManager(getActivity());
        // downLoadManager = new DLJDataDownLoadHelper(getActivity(), handler);
        executorService = Executors.newCachedThreadPool();
        if (handler == null)
            handler = new Myhandler(this);
        utils = MyUtils.getInstance();
        init();
        options = BaseApplication.options;
        pagerOptions = BaseApplication.pagerOptions;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        handler = null;
        animation = null;
        System.gc();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        firstLoadData();
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    /**
     * 初次获取数据，必须在init()方法里把isFirst设置为true
     */
    protected abstract void getData();

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /**
     * hander处理事件
     */
    protected Myhandler handler = new Myhandler(this);

    public static class Myhandler extends Handler {
        WeakReference<BaseFragment> fragment;

        public Myhandler(BaseFragment fragment) {
            // TODO Auto-generated constructor stub
            this.fragment = new WeakReference<BaseFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            BaseFragment fragment = this.fragment.get();
            if (fragment != null) {
                if (fragment.getActivity() != null)
                    fragment.handlerMessage(msg);
            }
        }
    }

    private final int enterAnim = android.R.anim.slide_in_left;
    private final int exitAnim = android.R.anim.slide_out_right;

    /**
     * 处理handler事件
     */
    protected abstract void handlerMessage(Message msg);

    /**
     * 判断用户是否登录
     *
     * @return true为已，false为未登录
     */
    protected boolean isLogin() {
        Map<String, Object> user = ((MyApplication) getActivity()
                .getApplication()).getUser();
        if (user != null && getParse().parseBool(user.get("CustNick"))) {
            return true;
        }
        Map<String, String> userMap = ((MyApplication) getActivity().getApplication())
                .getUserMap();
        if ((user != null && (user.get("CustPhoneNum") != null))
                && (userMap != null && userMap.get(Contants.UPWD) != null)) {
            return true;
        }
        return false;
    }


    /**
     * 利用线程池启动线程
     *
     * @param runnable
     */
    protected void startRunnable(Runnable runnable) {
        if (runnable == null) {
            MyUtils.getInstance().showToast(getActivity(), "请求失败...");
            return;
        }
        if (executorService == null)
            executorService = BaseApplication.executorService;
        executorService.execute(runnable);
    }

    /**
     * 转型
     */
    protected Parse getParse() {
        return Parse.getInstance();
    }
}
