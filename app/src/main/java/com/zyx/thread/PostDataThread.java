package com.zyx.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zyx.utils.HttpClientUtil;
import com.zyx.utils.LogUtil;
import com.zyx.utils.Resolve;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class PostDataThread implements Runnable {

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
	public PostDataThread(String uri, Map<String, String> map, Handler handler,
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
	@SuppressWarnings("unchecked")
	private void postData(String uri, Map<String, String> map) {
		System.out.println("uri=" + uri);
		try {
			Map<String, Map<String, Object>> mapXML = null;
			HttpEntity he = httpClientUtil.postRequest(uri, map);
			if (he == null) {
				sendEmptyMessage(msgWhat_SUBMISSION_FAILED, null);
				return;
			}
			String str = EntityUtils.toString(he, "gbk");
			Log.e("数据提交成功aaaaa：", str);

			/** 测试代码 */
			// File file = new
			// File(Environment.getExternalStorageDirectory().getPath() +
			// "/小菜哥");
			// if(!file.exists()){
			// file.mkdirs();
			// }
			// file = new File(file.getPath() + "/我喜欢");
			// FileOutputStream fos = new FileOutputStream(file);
			// fos.write(str.getBytes());
			// fos.flush();
			// fos.close();
			/** 测试代码 */

//			InputStream in = new ByteArrayInputStream(str.getBytes());
//			mapXML = Resolve.getInstance().resolveXML(in);
			mapXML = (Map<String, Map<String, Object>>) Resolve.getInstance().json(str);
			//LogUtil.i("zyx",String.valueOf(msgWhat_OK_SUBMISSION));
			sendEmptyMessage(msgWhat_OK_SUBMISSION, mapXML);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Log.e("ClientProtocolException", "提交请求异常");
			sendEmptyMessage(msgWhat_SUBMISSION_FAILED, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Log.e("IOException", "提交时IO流处理异常");
			sendEmptyMessage(msgWhat_SUBMISSION_FAILED, null);
		} 
//		catch (XmlPullParserException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			Log.e("XmlPullParserException", "提交时解析返回数据异常22222");
//			sendEmptyMessage(msgWhat_SUBMISSION_FAILED, null);
//		}
	}

	/**
	 * 发消息
	 * 
	 * @param emptyMessage
	 * @param map
	 */
	private void sendEmptyMessage(int emptyMessage,
			Map<String, Map<String, Object>> map) {
		if (handler != null) {
			if (emptyMessage != -1) {
				Message msg = handler.obtainMessage();
				msg.what = emptyMessage;
				LogUtil.i("zyx",String.valueOf(emptyMessage));
				msg.obj = map;
				handler.sendMessage(msg);
			}
		}
	}
}
