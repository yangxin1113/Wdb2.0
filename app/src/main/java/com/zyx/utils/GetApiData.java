package com.zyx.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetApiData {

	/**
	 * 域名
	 */
	public static String URLS;
	public static GetApiData getApiData;// 单例模式

	/**
	 * 私有构造函数
	 */
	private GetApiData() {

	}

	public static GetApiData getInstance() {
		if (getApiData == null) {
			getApiData = new GetApiData();
		}
		return getApiData;
	}

	/**
	 * 获取数据
	 * 
	 * @param url
	 *            请求地址
	 * @param isRealmName
	 *            是否包含域名
	 * @return InputStream
	 */
	public InputStream getData(String url) {
		InputStream in = null;
		String path = null;
		if (url.contains("http://") || url.contains("https://"))
			path = url;
		else
			path = URLS + url;
		Log.i("fc", path);
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) new URL(path).openConnection();
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);
			con.setRequestMethod("GET");
			int i = con.getResponseCode();
			if (i == 200) {
				in = con.getInputStream();
				return in;
			}
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public InputStream getLngLat(String url) {
		InputStream in = null;
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setConnectTimeout(5000);
			con.setRequestMethod("GET");
			int i = con.getResponseCode();
			if (i == 200) {
				in = con.getInputStream();
				return in;
			}
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

}
