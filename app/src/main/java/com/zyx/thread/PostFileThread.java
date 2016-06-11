package com.zyx.thread;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParserException;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zyx.utils.HttpClientUtil;
import com.zyx.utils.Resolve;

/**
 * 提交文件，带更新进度详解：
 * 
 * if (msgWhat != -1) { Bundle bundle = new Bundle();
 * bundle.putLong("totalSize", totalSize); bundle.putLong("num", num);
 * bundle.putSerializable("sizeList",sizeList); Message msg = new Message();
 * msg.what = msgWhat; msg.setData(bundle); if (handler != null) {
 * handler.sendMessage(msg); } }
 * 
 * 根据以上代码可以发现：使用时在传入的“msgWhat_UPDATE_PROGRESS”消息队列中取bundle， totalSize 是所有文件字节大小；
 * num 是当前进度； sizeList 是各个文件的大小的集合；
 * 
 * @author 锐
 * 
 */
public class PostFileThread implements Runnable {

	private Map<String, String> map;// 图片参数
	private ArrayList<String> fileList;// 文件地址
	private int msgWhat_OK_SUBMISSION;// 上传成功消息队列代码
	private int msgWhat_SUBMISSION_FAILED;// 上传失败消息队列代码
	private int msgWhat_UPDATE_PROGRESS;// 更新上传进度消息队列代码
	private String URLS;// 上传文件的地址
	private String contentType;// 文件类型，例：图片就传jpeg
	private Handler handler;// 消息队列

	/**
	 * 构造函数
	 * 
	 * @param URLS
	 *            上传文件的地址
	 * @param map
	 *            图片参数
	 * @param fileList
	 *            文件地址
	 * @param msgWhat_OK_SUBMISSION
	 *            上传成功消息队列代码，不发消息就传：-1
	 * @param msgWhat_SUBMISSION_FAILED
	 *            上传失败消息队列代码，不发消息就传：-1
	 * @param msgWhat_UPDATE_PROGRESS
	 *            更新上传进度消息队列代码，不需要更新传：-1
	 * @param contentType
	 *            文件类型，例：图片就传jpeg
	 * @param handler
	 *            消息队列
	 */
	public PostFileThread(String URLS, Map<String, String> map,
			ArrayList<String> fileList, int msgWhat_OK_SUBMISSION,
			int msgWhat_SUBMISSION_FAILED, int msgWhat_UPDATE_PROGRESS,
			String contentType, Handler handler) {
		this.map = map;
		this.fileList = fileList;
		this.msgWhat_OK_SUBMISSION = msgWhat_OK_SUBMISSION;
		this.msgWhat_SUBMISSION_FAILED = msgWhat_SUBMISSION_FAILED;
		this.msgWhat_UPDATE_PROGRESS = msgWhat_UPDATE_PROGRESS;
		this.URLS = URLS;
		this.contentType = contentType;
		this.handler = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		postFile();
	}

	/**
	 * 提交方法
	 */
	private void postFile() {
		try {
			HttpEntity he = HttpClientUtil.getInstance().postFileProgress(URLS,
					map, fileList, contentType, handler,
					msgWhat_UPDATE_PROGRESS);
			if (he == null) {
				sendEmptyMessage(msgWhat_SUBMISSION_FAILED, null);
				return;
			}
			String str = EntityUtils.toString(he, "UTF-8");
			Log.e("文件上传成功：", str);
			//InputStream in = new ByteArrayInputStream(str.getBytes());
			Map<String, Map<String, Object>> mapXML = (Map<String, Map<String, Object>>) Resolve.getInstance()
					.json(str);
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
	}

	/**
	 * 重新设置消息队列
	 * 
	 * @param handler
	 *            消息队列
	 * @param msgWhat_OK_SUBMISSION
	 *            上传成功消息队列代码，不发消息就传：-1
	 * @param msgWhat_SUBMISSION_FAILED
	 *            上传失败消息队列代码，不发消息就传：-1
	 * @param msgWhat_UPDATE_PROGRESS
	 *            更新上传进度消息队列代码，不更新就传：-1
	 */
	public void putHandler(Handler handler, int msgWhat_OK_SUBMISSION,
			int msgWhat_SUBMISSION_FAILED, int msgWhat_UPDATE_PROGRESS) {
		this.handler = handler;
		this.msgWhat_OK_SUBMISSION = msgWhat_OK_SUBMISSION;
		this.msgWhat_SUBMISSION_FAILED = msgWhat_SUBMISSION_FAILED;
		this.msgWhat_UPDATE_PROGRESS = msgWhat_UPDATE_PROGRESS;
		HttpClientUtil.getInstance().putHandler(this.handler,
				this.msgWhat_UPDATE_PROGRESS);
	}

	/**
	 * 发消息
	 */
	private void sendEmptyMessage(int emptyMessage,
			Map<String, Map<String, Object>> map) {
		if (emptyMessage != -1) {
			if (handler != null) {
				Message msg = handler.obtainMessage();
				msg.what = emptyMessage;
				msg.obj = map;
				handler.sendMessage(msg);
			}
		}
	}
}
