package com.zyx.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zyx.utils.GetApiData;
import com.zyx.utils.MyUtils;
import com.zyx.utils.Resolve;
import com.zyx.utils.SaveListObject;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class GetDataThread implements Runnable {

	private Context context;
	private String uri;
	private Handler handler;
	private int msgWhat_OK;
	private int msgWhat_TIME_OUT;
	private int msgWhat_DATA_EXCEPTION;
	private OnUpdateData onUpdateData;
	private boolean isCache;// 是否缓存,默认为true

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param uri
	 *            获取数据的地址
	 * @param handler
	 *            消息队列
	 * @param msgWhat_OK
	 *            成功消息代码，如果不发消息传:-1
	 * @param msgWhat_TIME_OUT
	 *            获取数据超时代码，如果不发消息传:-1
	 * @param msgWhat_DATA_EXCEPTION
	 *            数据解析异常代码，如果不发消息传:-1
	 */
	public GetDataThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.onUpdateData = null;
		this.isCache = true;
	}

	/**
	 * 构造函数(带是否缓存的参数，true为缓存，false为不缓存）
	 * 
	 * @param context
	 *            上下文
	 * @param uri
	 *            获取数据的地址
	 * @param handler
	 *            消息队列
	 * @param msgWhat_OK
	 *            成功消息代码，如果不发消息传:-1
	 * @param msgWhat_TIME_OUT
	 *            获取数据超时代码，如果不发消息传:-1
	 * @param msgWhat_DATA_EXCEPTION
	 *            数据解析异常代码，如果不发消息传:-1
	 * @param isCache
	 *            是否缓存
	 */
	public GetDataThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			boolean isCache) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.onUpdateData = null;
		this.isCache = isCache;
	}

	/**
	 * 带更新解析下来的数据的构造函数(带是否缓存的参数，true为缓存，false为不缓存）
	 * 
	 * @param context
	 *            上下文
	 * @param uri
	 *            获取数据的地址
	 * @param handler
	 *            消息队列
	 * @param msgWhat_OK
	 *            成功消息代码，如果不发消息传:-1
	 * @param msgWhat_TIME_OUT
	 *            获取数据超时代码，如果不发消息传:-1
	 * @param msgWhat_DATA_EXCEPTION
	 *            数据解析异常代码，如果不发消息传:-1
	 * @param onUpdateData
	 *            更新数据的接口
	 * @param isCache
	 *            是否缓存
	 */
	public GetDataThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			OnUpdateData onUpdateData, boolean isCache) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.onUpdateData = onUpdateData;
		this.isCache = isCache;
	}

	/**
	 * 带更新解析下来的数据的构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param uri
	 *            获取数据的地址
	 * @param handler
	 *            消息队列
	 * @param msgWhat_OK
	 *            成功消息代码，如果不发消息传:-1
	 * @param msgWhat_TIME_OUT
	 *            获取数据超时代码，如果不发消息传:-1
	 * @param msgWhat_DATA_EXCEPTION
	 *            数据解析异常代码，如果不发消息传:-1
	 * @param onUpdateData
	 *            更新数据的接口
	 */
	public GetDataThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			OnUpdateData onUpdateData) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.onUpdateData = onUpdateData;
		this.isCache = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		getData(context, uri, handler, msgWhat_OK, msgWhat_TIME_OUT,
				msgWhat_DATA_EXCEPTION, isCache);
	}

	/**
	 * 获取数据的方法
	 * 
	 * @param context
	 * @param uri
	 * @param handler
	 * @param msgWhat_OK
	 * @param msgWhat_TIME_OUT
	 * @param msgWhat_DATA_EXCEPTION
	 * @param isCache
	 */

	private void getData(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			boolean isCache) {
		File file = null;
		if (isCache)
			file = MyUtils.getInstance().getCache(context, "/data", uri, true);
		Map<String, Map<String, Object>> mapXML = null;
		if (MyUtils.getInstance().isNetworkConnected(context)) {
			InputStream in = GetApiData.getInstance().getData(uri);
			if (in == null) {
				if (!isCache) {
					sendEmptyMessage(msgWhat_TIME_OUT, null);
					return;
				}
				mapXML = (Map<String, Map<String, Object>>) SaveListObject
						.getInstance().openObject(file);
				if (mapXML != null) {
					if (onUpdateData != null) {
						if (mapXML != null)
							onUpdateData.updateCacheData(mapXML);
					}
					sendEmptyMessage(msgWhat_OK, mapXML);
				} else {
					System.out.println("数据集合空");
					sendEmptyMessage(msgWhat_TIME_OUT, mapXML);
				}
				return;
			}
			try {
				mapXML = Resolve.getInstance().resolveXML(in);
				in.close();
				if (onUpdateData != null) {
					if (mapXML != null)
						onUpdateData.updateNetworkData(mapXML);
				}
				if (isCache)
					SaveListObject.getInstance().saveObject(file, mapXML);
				sendEmptyMessage(msgWhat_OK, mapXML);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.e("Exception", "获取数据时，流处理异常");
				sendEmptyMessage(msgWhat_DATA_EXCEPTION, mapXML);
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.e("XmlPullParseException", "获取数据时，解析异常11111");
				sendEmptyMessage(msgWhat_DATA_EXCEPTION, mapXML);
			}

		} else {
			if (!isCache) {
				sendEmptyMessage(msgWhat_TIME_OUT, null);
				return;
			}
			mapXML = (Map<String, Map<String, Object>>) SaveListObject
					.getInstance().openObject(file);
			if (mapXML != null) {
				if (onUpdateData != null) {
					if (mapXML != null)
						onUpdateData.updateCacheData(mapXML);
				}
				sendEmptyMessage(msgWhat_OK, mapXML);
			} else {
				sendEmptyMessage(msgWhat_TIME_OUT, mapXML);
			}
		}

	}

	/**
	 * 发消息
	 */
	private void sendEmptyMessage(int emptyMessage,
			Map<String, Map<String, Object>> map) {
		if (emptyMessage != -1) {
			Message msg = handler.obtainMessage();
			msg.what = emptyMessage;
			msg.obj = map;
			if (handler != null) {
				handler.sendMessage(msg);
			}
		}
	}

	/**
	 * 更新解析下来的数据，比如：插入数据，移除数据
	 * 
	 * @author 锐
	 * 
	 */
	public interface OnUpdateData {
		/** 更新网络数据 */
		public void updateNetworkData(Map<String, Map<String, Object>> mapXML);

		/** 更新缓存数据 */
		public void updateCacheData(Map<String, Map<String, Object>> mapXML);
	}

}
