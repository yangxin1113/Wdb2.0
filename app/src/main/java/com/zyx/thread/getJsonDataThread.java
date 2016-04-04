package com.zyx.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zyx.utils.HttpClientUtil;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;


public class getJsonDataThread implements Runnable {

	private String uri;
	private Map<String, String> map;
	private Handler handler;
	private int msgWhat_OK_SUBMISSION;
	private int msgWhat_SUBMISSION_FAILED;
	private HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();

	/**
	 * 提交线程的构造函数
	 * 
	 * @param uri
	 *            提交地址
	 * @param map
	 *            提交的数据
	 * @param handler
	 *            消息队列
	 * @param msgWhat_OK_SUBMISSION
	 *            提交成功发的消息代码，如果不发消息就传：-1
	 * @param msgWhat_SUBMISSION_FAILED
	 *            提交失败发的消息代码，如果不发消息就传：-1
	 */
	public getJsonDataThread(String uri, Map<String, String> map, Handler handler,
			int msgWhat_OK_SUBMISSION, int msgWhat_SUBMISSION_FAILED) {
		this.uri = uri;
		this.map = map;
		this.handler = handler;
		this.msgWhat_OK_SUBMISSION = msgWhat_OK_SUBMISSION;
		this.msgWhat_SUBMISSION_FAILED = msgWhat_SUBMISSION_FAILED;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		postData(uri, map);
	}

	/**
	 * 提交数据方法
	 * 
	 * @param uri
	 *            提交地址
	 * @param map
	 *            提交的数据
	 */
	private void postData(String uri,  Map<String, String> map) {
		System.out.println("uri=" + uri);
		try {
			
			HttpEntity he = httpClientUtil.postRequest(uri, map);
			if (he == null) {
				sendEmptyMessage(msgWhat_SUBMISSION_FAILED, null);
				return;
			}
			String str = EntityUtils.toString(he, "gbk");
			Log.e("数据提交成功666：", str);	
				
				sendEmptyMessage(msgWhat_OK_SUBMISSION, str);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Log.e("ClientProtocolException", "提交请求异常");
			sendEmptyMessage(msgWhat_SUBMISSION_FAILED, null);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 * 发消息
	 * 
	 * @param emptyMessage
	 * @param data
	 */
	private void sendEmptyMessage(int emptyMessage, String data) {
		if (emptyMessage != -1) {
			if (handler != null) {
				Message msg = handler.obtainMessage();
				msg.what = emptyMessage;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		}
	}
}
