package com.zyx.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zyx.utils.CrashHandler;
import com.zyx.utils.MyUtils;

import java.lang.ref.WeakReference;

public abstract class BaseActivity extends Activity implements OnClickListener {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;

	protected MyHandler handler;

	protected MyUtils utils;// 工具类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		CrashHandler.add(this);
		super.onCreate(savedInstanceState);
		options = BaseApplication.options;
		utils = MyUtils.getInstance();
		if (handler == null)
			handler = new MyHandler(this);
		init();
		initView();
		setViewData();
		initEvent();
		getData();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler = null;
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		CrashHandler.remove(this);
		super.finish();
	}

	public class MyHandler extends Handler {
		WeakReference<BaseActivity> activity;

		@SuppressLint("HandlerLeak")
		public MyHandler(BaseActivity activity) {
			this.activity = new WeakReference<BaseActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			BaseActivity activity = this.activity.get();
			if (activity != null) {
				activity.handlerMessage(msg);
			}
		}
	}

	/**
	 * handler抽象方法，用于处理消息
	 */
	public abstract void handlerMessage(Message msg);

	/**
	 * 初始化布局、数据
	 */
	protected abstract void init();

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 初始化控件属性、数据
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
}