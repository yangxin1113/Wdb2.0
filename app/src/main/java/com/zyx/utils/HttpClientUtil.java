package com.zyx.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 */
public class HttpClientUtil {

	/**
	 * 域名
	 */
	//public static String URLS = "http://192.168.43.69:8080/wdb/";
	//public static String URLS = "http://192.168.1.143:8080/wdb/";
	//public static String URLS ="http://169.254.254.157:8080/wdb/";
	public static String URLS;
	public static HttpClientUtil httpClientUtil;// 单例模式
	public HttpClient client = new DefaultHttpClient();

	private long totalSize;// 上传文件总大小
	private ArrayList<Long> sizeList;// 上传文件的各个大小集合
	private Handler handler;
	private int msgWhat;

	/**
	 * 私有构造函数
	 */
	private HttpClientUtil() {

	}

	public static HttpClientUtil getInstance() {
		if (httpClientUtil == null) {
			httpClientUtil = new HttpClientUtil();
		}
		return httpClientUtil;
	}

	public String getRequests(String url) throws Exception {
		String result = null;
		HttpGet get = new HttpGet(URLS + url);
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
		}
		return result;
	}

	public HttpEntity getRequest(String url) throws Exception {
		HttpEntity entity = null;
		HttpGet get = new HttpGet(URLS + url);
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			entity = response.getEntity();
		}
		return entity;
	}

	public byte[] getRequestReturnByte(String url) throws Exception {
		byte[] result = null;
		HttpGet get = new HttpGet(URLS + url);
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toByteArray(entity);
		}
		return result;
	}

	// /**
	// * @param url
	// * @param params
	// * @return
	// * @throws IOException
	// * @throws ClientProtocolException
	// * @throws Exception
	// */
	// public HttpEntity postData(String url, Map<String, String> params)
	// throws ClientProtocolException, IOException {
	// HttpEntity entity = null;
	// System.out.println(URLS + url + params.toString());
	// HttpPost post = new HttpPost(URLS + url);
	// client.getParams().setParameter(
	// CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
	// client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
	//
	// List<NameValuePair> list = new ArrayList<NameValuePair>();
	// for (String key : params.keySet()) {
	// list.add(new BasicNameValuePair(key, params.get(key)));
	// }
	// post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
	// HttpResponse response = client.execute(post);
	// if (response.getStatusLine().getStatusCode() == 200) {
	// entity = response.getEntity();
	// System.out.println(EntityUtils.toString(entity));
	// }
	//
	// return entity;
	// }

	/**
	 * 提交数据
	 *
	 * @param url    不带域名的url
	 * @param params 数据map集合key为参数，value为数据
	 * @return HttpEntity
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public HttpEntity postRequest(String url, Map<String, String> params)
			throws ClientProtocolException, IOException {
		String path = null;
		if (url.contains("http://") || url.contains("https://"))
			path = url;
		else
			path = URLS + url;
		HttpEntity entity = null;
		Log.i("fc", "@@@@@" + (path + params.toString()));
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpPost post = null;
		post = new HttpPost(path);
		Log.i("fc", "*******" + path);
		post.setParams(httpParams);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			String str = params.get(key);
			list.add(new BasicNameValuePair(key, params.get(key)));
			Log.i("fc", "key:" + key);
			Log.i("fc", "params.get(key):" + params.get(key));
		}
		Log.i("fc", "%%%%%%" + list.size());
		post.setEntity(new UrlEncodedFormEntity(list, "gbk"));
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// result = EntityUtils.toString(entity);
			// result = EntityUtils.toString(entity, "utf-8");
			entity = response.getEntity();
			return entity;
		}
		return null;
	}

	/**
	 * 提交数据
	 *
	 * @param url     url
	 * @param content 提交字符串
	 * @return HttpEntity
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public HttpEntity postStringData(String url, String content)
			throws ClientProtocolException, IOException {
		String path = null;
		if (url.contains("http://") || url.contains("https://"))
			path = url;
		else
			path = URLS + url;
		HttpEntity entity = null;
		Log.i("fc", "#########" + (path + content));
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpPost post = null;
		post = new HttpPost(path);
		post.setParams(httpParams);
		post.setEntity(new StringEntity(content));
		post.setHeader("Accept", "application/json");
		post.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// result = EntityUtils.toString(entity);
			// result = EntityUtils.toString(entity, "utf-8");
			entity = response.getEntity();
			return entity;
		}
		return null;
	}

	/**
	 * 上传带参数的文件(没有进度值返回)
	 *
	 * @param url      不带域名的url
	 * @param map      参数，key为参数，value为数据
	 * @param fileList 文件路径的ArrayList集合
	 * @return HttpEntity
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpEntity postFile(String url, Map<String, String> map,
							   ArrayList<String> fileList, String contentType)
			throws ClientProtocolException, IOException {
		HttpEntity entity = null;
		HttpPost post = null;
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		post = new HttpPost(URLS + url);
		Log.i("fc", "$$$$$$$$" + URLS + url);
		MultipartEntity me = new MultipartEntity();
		me.addPart("", new StringBody(""));

		if (map != null) {
			// Set<String> s = map.keySet();
			// Iterator<String> itt = s.iterator();
			// for (int j = 0; j < map.size(); j++) {
			// String k = itt.next();
			// }
			for (String key : map.keySet()) {
				// String str = key + ",,,," + map.get(key);
				StringBody sb = new StringBody(map.get(key));
				me.addPart(key, sb);
			}
		}

		if (fileList != null) {
			for (int i = 0; i < fileList.size(); i++) {
				ContentBody file = new FileBody(new File(fileList.get(i)),
						contentType);
				me.addPart("UFInput" + (i + 1), file);
			}
		}
		post.setEntity(me);
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			// result = EntityUtils.toString(entity);
			// result = EntityUtils.toString(entity, "utf-8");
			entity = response.getEntity();
		}
		client.getConnectionManager().shutdown();
		return entity;
	}

	/**
	 * 传递消息队列
	 */
	public void putHandler(Handler handler, int updateProgrssMsgWhat) {
		this.handler = handler;
		this.msgWhat = updateProgrssMsgWhat;
	}

	/**
	 * 上传带参数的文件（有进度值返回）
	 *
	 * @param url                  不带域名的url
	 * @param map                  参数，key为参数，value为数据
	 * @param fileList             文件路径的ArrayList集合
	 * @param contentType          文件类型，例：图片就传jpeg
	 * @param handler              消息队列
	 * @param updateProgrssMsgWhat 更新上传进度消息队列代码，不需要更新传：-1
	 * @return HttpEntity
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpEntity postFileProgress(String url, Map<String, String> map,
									   ArrayList<String> fileList, String contentType, Handler handler,
									   int updateProgrssMsgWhat) throws ClientProtocolException,
			IOException {
		this.handler = handler;
		this.msgWhat = updateProgrssMsgWhat;
		HttpEntity entity = null;
		HttpPost post = null;
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		post = new HttpPost(URLS + url);
		Log.i("fc", URLS + url);
		MyMultipartEntity me = new MyMultipartEntity(new MyProgressListener());
		me.addPart("", new StringBody(""));

		if (map != null) {
			for (String key : map.keySet()) {
				StringBody sb = new StringBody(map.get(key));
				me.addPart(key, sb);
			}
		}
		long mapSize = me.getContentLength();// 数据大小
		long item;
		if (fileList != null) {
			if (sizeList == null) {
				sizeList = new ArrayList<Long>();
			} else {
				sizeList.clear();
			}
			for (int i = 0; i < fileList.size(); i++) {
				item = me.getContentLength();
				ContentBody file = new FileBody(new File(fileList.get(i)),
						contentType);
				me.addPart("UFInput" + (i + 1), file);
				long sizeItem = me.getContentLength() - mapSize;
				Log.e("单张图片大小：", String.valueOf(sizeItem));
				sizeList.add(sizeItem);
			}
		}
		totalSize = me.getContentLength() - mapSize;
		Log.e("加上字符类型总大小：", String.valueOf(me.getContentLength()));
		post.setEntity(me);
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			// result = EntityUtils.toString(entity);
			// result = EntityUtils.toString(entity, "utf-8");
			entity = response.getEntity();
			client.getConnectionManager().shutdown();
			return entity;
		}
		return null;
	}

	/**
	 * 实现上传进度接口
	 *
	 * @author 锐
	 */
	class MyProgressListener implements MyMultipartEntity.ProgressListener {

		@Override
		public void transferred(long num) {
			// TODO Auto-generated method stub
			if (msgWhat != -1) {
				Bundle bundle = new Bundle();
				bundle.putLong("totalSize", totalSize);
				bundle.putLong("num", num);
				bundle.putSerializable("sizeList", sizeList);
				Message msg = new Message();
				msg.what = msgWhat;
				msg.setData(bundle);
				if (handler != null) {
					handler.sendMessage(msg);
				}
			}
		}
	}

	public String postJson(String path, JSONObject param) throws Exception {
		String url = path + param;
		HttpPost request = new HttpPost(URLS + url);
		System.err.println("url_--------_____" + url);
		// 先封装一个 JSON 对象
		// JSONObject param = new JSONObject();
		// param.put("name", "rarnu");
		// param.put("password", "123456");
		// HttpPost httpPost = new HttpPost(path);
		// List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		// nameValuePair.add(new BasicNameValuePair("jsonString", param
		// .toString()));
		// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

		// 绑定到请求 Entry
		System.err.println("param.toString()param.toString()"
				+ param.toString());
		StringEntity se = new StringEntity(param.toString());
		request.setEntity(se);
		// 发送请求
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);
		// 得到应答的字符串，这也是一个 JSON 格式保存的数据
		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		// 生成 JSON 对象
		System.err.println("retSrcretSrcretSrcretSrc" + retSrc);
		JSONObject result = new JSONObject(retSrc);
		// System.err.println(result);
		// String token = (String) result.get("code");
		return "200";
	}

	/**
	 * 解析提交后返回的String类型
	 *
	 * @param src
	 * @param startTag
	 * @param endTag
	 * @return
	 */
	public String getContent(String src, String startTag, String endTag) {
		String content = src;
		int start = src.indexOf(startTag);
		start += startTag.length();

		try {
			if (endTag != null) {
				int end = src.indexOf(endTag);
				content = src.substring(start, end);
			} else {
				content = src.substring(start);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}

	/**
	 * 解析返回的结果
	 *
	 * @throws XmlPullParserException
	 * @throws IOException
	 */

	public String executeHttpPost(String macString) {
		String result = null;
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		try {
			Log.i("fc", "解析返回的结果解析返回的结果");
			url = new URL("http://www.1shi.com.cn:8000/AngelService.asmx/VerifyLicense");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");

			DataOutputStream dop = new DataOutputStream(
					connection.getOutputStream());
			dop.writeBytes("licenseCode=" + macString);
			dop.flush();
			dop.close();

			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("fc", "lzh===============>enter get websvr result ERROR");
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Log.d("reslut", "1234" + result);
		return result;
	}

	// 删除缓存
	public void RecursionDeleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}

	/**
	 * 修改功能 ----》解析返回的
	 *
	 * @param in
	 * @return
	 */
	public String parseResult(InputStream in) {
		String code = null;
		try {
			XmlPullParser pullParser = Xml.newPullParser();
			pullParser.setInput(in, "UTF-8");
			int event = pullParser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if ("result".equals(pullParser.getName())) {
							code = pullParser.getAttributeValue(null, "code");
							System.out.println("pullParser.getAttributeValue----"
									+ pullParser.getAttributeValue(null, "msg"));
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = pullParser.next();
			}
			// in.close();
			return code;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			System.out.println("解析xml错误!");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("读取xml错误!");
			return null;
		}
	}

	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

}