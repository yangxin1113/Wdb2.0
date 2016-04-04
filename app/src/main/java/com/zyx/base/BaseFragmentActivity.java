package com.zyx.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zyx.application.MyApplication;
import com.zyx.contants.Contants;
import com.zyx.contants.UpdateUserInfo;
import com.zyx.thread.GetDataThread;
import com.zyx.utils.CrashHandler;
import com.zyx.utils.MyUtils;
import com.zyx.utils.Parse;
import com.zyx.utils.Resolve;
import com.zyx.wdb.MainActivity;
import com.zyx.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by zyx on 2016/2/11.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener {

    protected String TAG;
    protected  int statusBarHeight;// 状态栏高度
    protected int navigationBarHeight;// 虚拟按键高度
    private  View view_status_bar;// 状态栏
    private View view_bottom;// 虚拟键盘、键盘高度的View
    protected DisplayMetrics dm = new DisplayMetrics();
    protected FragmentTransaction ft;
    protected MyHandler handler ;
    protected Animation animation;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions pagerOptions;
    protected DisplayImageOptions options;
    protected MyUtils utils;


    protected boolean isToken = false;// 是否检测更新
    protected boolean isUserMapNull = false;// 是否检测用户信息是否为空
    protected Context mContext;
    protected ExecutorService executorService;
    private Map<String, Object> user;// 用戶資料


    private Handler baseHan = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10000:
                    Map<String, Map<String, Object>> mapXML = (Map<String, Map<String, Object>>) msg.obj;
                    ArrayList<Map<String, Object>> list = Resolve.getInstance()
                            .getList(mapXML, "result");
                    if (list != null && list.size() > 0) {
                        if (!getParse().isNull(list.get(0).get("code")).equals(
                                "200")) {
                            if (baseHan != null) {
                                baseHan.removeMessages(0x10001);
                                new GetTokenDialog(mContext).init();
                            }
                        } else {
                            ArrayList<Map<String, Object>> userinfList = Resolve
                                    .getInstance().getList(mapXML, "userInfo");
                            if (userinfList != null && userinfList.size() > 0) {
                                user = userinfList.get(0);
                                Map<String, Object> userInfo = ((MyApplication) getApplication())
                                        .getUser();
                                if (userInfo != null) {
                                    user.put("isDsf", Parse.getInstance()
                                            .parseBool(userInfo.get("isDsf")));
                                    ((MyApplication) getApplication())
                                            .setUser(user);
                                    UpdateUserInfo.getInstance().notifyUserInfo(
                                            user);
                                }
                                return;
                            }
                        }
                    }
                    break;

                case 0x10001:
                    if (baseHan != null) {
                        Map<String, Object> userInfo1 = ((MyApplication) getApplication())
                                .getUser();
                        if (userInfo1 != null) {
                            startRunnable(new GetDataThread(
                                    getApplicationContext(),
                                    Contants.UPDATE_USER_INFO
                                            + getParse().isNull(
                                            userInfo1.get("token")),
                                    baseHan, 0x10000, -1, -1, false));
                            baseHan.sendEmptyMessageDelayed(0x10001, 30000);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle arg0) {
        CrashHandler.add(this);
        mContext =this;
        super.onCreate(arg0);
        utils = MyUtils.getInstance();
        handler = new MyHandler(this);
        statusBarHeight = 0;
         navigationBarHeight = 0;
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT
                && VERSION.SDK_INT < VERSION_CODES.LOLLIPOP) {
            // Rect frame = new Rect();
            // getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            // statusBarHeight = frame.top;
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = getResources().getDimensionPixelSize(x);
                navigationBarHeight = utils
                        .getNavigationBarHeight(getApplicationContext());
            } catch (Exception e1) {
                // loge("get status bar height fail");
                // e1.printStackTrace();
            }
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            // getWindow().addFlags(
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = getResources().getDimensionPixelSize(x);
                navigationBarHeight = utils
                        .getNavigationBarHeight(getApplicationContext());
            } catch (Exception e1) {
                loge("get status bar height fail");
                e1.printStackTrace();
            }
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        TAG = this.getLocalClassName();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ft = getSupportFragmentManager().beginTransaction();
        childInit();
        options = BaseApplication.options;
        pagerOptions = BaseApplication.pagerOptions;
        init(arg0);
        initView();
        view_status_bar = getStatusBarView();
        view_bottom = getBottomView();
        setViewData();
        if (view_status_bar != null) {
            LayoutParams lp = view_status_bar.getLayoutParams();
            lp.height = statusBarHeight;
            view_status_bar.setLayoutParams(lp);
        }
        showFragment();
        initEvent();
        ViewTreeObserver vto = getWindow().getDecorView().getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);

                int screenHeight = getWindow().getDecorView().getRootView()
                        .getHeight();
                int bottom = 0;
                if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                    bottom = r.bottom + navigationBarHeight;
                } else {
                    bottom = r.bottom;
                }
                int heightDifference = screenHeight - bottom;// r.top（状态栏）
                onKeyboardHeight(heightDifference);
            }
        });
        getData();
    }

    /**
     * 子类初始化
     */
    protected void childInit() {

    }

    private void loge(String string) {
        // TODO Auto-generated method stub

    }

    /**
     * 向子类传递键盘高度
     *
     * @param height
     */
    protected void onKeyboardHeight(int height) {
        if (view_bottom != null) {
            if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                LayoutParams lp = view_bottom.getLayoutParams();
                lp.height = height + navigationBarHeight;
                view_bottom.setLayoutParams(lp);
            }
        }
    }

    /**
     * 处理handler消息
     *
     * @param msg
     */
    protected abstract void handlerMessage(Message msg);


    /**
     * 初始化数据
     */
    protected abstract void init(Bundle arg0);

    public class MyHandler extends Handler {
        WeakReference<BaseFragmentActivity> fragmentActivity;

        @SuppressLint("HandlerLeak")
        public MyHandler(BaseFragmentActivity fragmentActivity) {
            this.fragmentActivity = new WeakReference<BaseFragmentActivity>(
                    fragmentActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            BaseFragmentActivity fragmentActivity = this.fragmentActivity.get();
            if (fragmentActivity != null) {
                fragmentActivity.handlerMessage(msg);
            }
        }
    }

    /**
     * 初始化view 手动设置setcontentview()
     */
    protected abstract void initView();

    /**
     * 获取状态栏的View
     *
     * @return
     */
    protected abstract View getStatusBarView();

    /**
     * 获取虚拟按键、键盘高度的View
     *
     * @return
     */
    protected abstract View getBottomView();

    /**
     * 初始化控件属性或初始化数据
     */
    protected abstract void setViewData();

    /**
     * 初始化控件事件
     */
    protected abstract void initEvent();

    /**
     * 获取数据
     */
    protected abstract void getData();

    private final int enterAnim = android.R.anim.slide_in_left;
    private final int exitAnim = android.R.anim.slide_out_right;

    /**
     * 添加内容fragment
     */
    protected void showFragment() {

    }

    /**
     * 切换到指定的Activity�? 无传递数�?
     *
     * @param cls
     *            指定的Activity
     */
    public void showItemActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
        // overridePendingTransition(R.anim.push_out, R.anim.scale_out);
    }

    /**
     * 切换到指定的Activity，有数据传�??
     *
     * @param bundle
     *            传�?�的数据
     * @param cls
     *            指定的Activity
     */
    public void showItemActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
        // overridePendingTransition(R.anim.push_out, R.anim.scale_out);
    }

    /**
     * 切换到指定的Activity，无传�?�数据，但需要返回结�?
     *
     * @param cls
     *            指定的Activity
     * @param requestCode
     *            返回结果代码
     */
    public void showItemActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(enterAnim, exitAnim);
        // overridePendingTransition(R.anim.push_out, R.anim.scale_out);
    }

    /**
     * 切换到指定的Activity，传递数据，�?要返回结�?
     *
     * @param bundle
     *            传�?�数�?
     * @param cls
     *            指定的Activity
     * @param requestCode
     *            返回结果代码
     */
    public void showItemActivityForResult(Bundle bundle, Class<?> cls,
                                          int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(enterAnim, exitAnim);
        // overridePendingTransition(R.anim.push_out, R.anim.scale_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isToken) {
            if (((MyApplication) getApplication()).getUser() != null) {
                baseHan.sendEmptyMessage(0x10001);
            }
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (isToken) {
            if (((MyApplication) getApplication()).getUser() != null) {
                baseHan.removeMessages(0x10001);
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        handler = null;
        baseHan.removeMessages(0x10000);
        baseHan.removeMessages(0x10001);
        baseHan = null;
        imageLoader.clearMemoryCache();
        System.gc();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        CrashHandler.remove(this);
    }


    protected void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, false);
    }

    protected void replaceFragment(Fragment fragment, boolean isBack) {
        replaceFragment(fragment, 0, isBack);
    }

    protected void replaceFragment(Fragment fragment, int resid, boolean isBack) {
        ft = getSupportFragmentManager().beginTransaction();
        if (resid != 0) {
            ft.replace(resid, fragment);
        } else {
        }
        if (isBack) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
    }


    /**
     * 转型用
     */
    protected Parse getParse() {
        return Parse.getInstance();
    }

    /**
     * 利用线程池启动线程
     *
     * @param runnable
     */
    protected void startRunnable(Runnable runnable) {
        if (runnable == null) {
            MyUtils.getInstance().showToast(getApplicationContext(), "请求失败...");
            return;
        }
        if (executorService == null)
            executorService = BaseApplication.executorService;
        executorService.execute(runnable);
    }

    /**
     * 判断线程是否活动状态
     *
     * @param thread
     * @return boolean
     */
    protected boolean isAlive(Thread thread) {
        if (thread == null) {
            return false;
        } else {
            if (thread.isAlive()) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 发消息到handler
     *
     * @param what
     * @param obj
     */
    protected void sendHandler(int what, Object obj) {
        if (handler == null)
            return;
        Message msg = handler.obtainMessage();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    /**
     * 发消息到handler
     *
     * @param what
     * @param bundle
     */
    protected void sendHandler(int what, Bundle bundle) {
        if (handler == null)
            return;
        Message msg = handler.obtainMessage();
        msg.what = what;
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    /**
     * 跳转到首页
     */
    protected void startHomeActivity() {
        CrashHandler.clearActivityList();
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.top_in_600, R.anim.bottom_out_600);
    }


    /** 判断用户信息是否为空的弹窗 */
    class TokenDialog extends AlertDialog.Builder {
        public TokenDialog(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @SuppressLint("NewApi")
        public TokenDialog(Context context, int theme) {
            super(context, theme);
            // TODO Auto-generated constructor stub
        }

        public void init() {
            setTitle(getString(R.string.app_name))
                    .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                    .setMessage("您的账号验证已过期，请重新登录！")
                    .setNegativeButton(getString(R.string.determine_zh),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    ((MyApplication) getApplication())
                                            .setUser(null);
                                    // CrashHandler.finish();
                                    // Intent in = new Intent(
                                    // getApplicationContext(),
                                    // MainActivity.class);
                                    // startActivity(in);
                                    CrashHandler.clearActivityList();
                                    Intent intent = new Intent();
                                    intent.setClass(getApplicationContext(),
                                            MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(
                                            R.anim.top_in_600,
                                            R.anim.bottom_out_600);
                                }
                            }).setCancelable(false).show();
        }
    }

    /** 判断别的地方登录的弹窗 */
    class GetTokenDialog extends AlertDialog.Builder {

        public GetTokenDialog(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @SuppressLint("NewApi")
        public GetTokenDialog(Context context, int theme) {
            super(context, theme);
            // TODO Auto-generated constructor stub
        }

        public void init() {
            setTitle(getString(R.string.app_name))
                    .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                    .setMessage("您的账号在其他设备登录，若不是本人操作请尽快更改密码！")
                    .setNegativeButton(getString(R.string.determine_zh),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    ((MyApplication) getApplication())
                                            .setUser(null);
                                    // CrashHandler.finish();
                                    // Intent in = new Intent(
                                    // getApplicationContext(),
                                    // MainActivity.class);
                                    // startActivity(in);
                                    CrashHandler.clearActivityList();
                                    Intent intent = new Intent();
                                    intent.setClass(getApplicationContext(),
                                            MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    // 设置不要刷新将要跳到的界面
                                    // FLAG_ACTIVITY_SINGLE_TOP
                                    // 它可以关掉所要到的界面中间的activity
                                    // FLAG_ACTIVITY_CLEAR_TOP
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(
                                            R.anim.top_in_600,
                                            R.anim.bottom_out_600);
                                }
                            }).setCancelable(false).show();
        }
    }
}
