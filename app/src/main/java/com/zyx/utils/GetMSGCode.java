package com.zyx.utils;

import com.zyx.contants.Contants;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信验证类
 * 
 * @author 锐
 * 
 */
public class GetMSGCode {

	public String RID ="0";
	public int BOOL;
	public String VCODE;
	public boolean EXCEPTION;
	public static GetMSGCode mSGCode;
	private HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
	private Map<String, String> map;

	private GetMSGCode() {

	}

	public static GetMSGCode getInstance() {
		if (mSGCode == null) {
			mSGCode = new GetMSGCode();
		}
		return mSGCode;
	}

	/**
	 * 
	 * @param phoneNb
	 *            手机号
	 * @param msg
	 *            信息提示内容(例：找回密码；注册账号等信息（JZ:提示$VCODE$信息；RL:模板ID）)
	 * @param regFpw
	 *            是否注册，reg为是，fpw为否；注册时判断解析出来的RID为0时才发送短信，否则RID不为0时不发送短信（RID == 0
	 *            ？系统没有phoneNb用户 : 系统有phoneNb用户)
	 * @return 返回null，为请求超时，直接告知使用者请求超时；返回"EXCEPTION"为走到异常，须告知使用者数据异常；
	 * 			返回"Y"（仅注册时有效）：代表有该手机号，返回"W"（仅找回密码有效）：代表没有该手机号；如果返回"500"，
	 * 			则代表服务器错误或者短信账户没有钱了；其余情况下都是短信收取到的验证码。
	 */


	public String getMSGCode(String phoneNb, String msg, String regFpw) throws IOException {
		map = new HashMap<String, String>();
		/*try {
			msg = URLEncoder.encode(msg, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}*/
		//msg = new String(msg.getBytes("ISO-8859-1"),"GBK");
		boolean EXCEPTION = true;

			map.put("phoneNum",phoneNb);
			HttpEntity he = httpClientUtil.postRequest(Contants.USER_IsExit, map);

		LogUtil.i("zyx2222", phoneNb + msg + regFpw);
		if (he == null) {
			EXCEPTION = false;
			return null;
		} else {
			BOOL = 0;

				String str = EntityUtils.toString(he, "gbk");
				parseJson(str);
				he = null;
				EXCEPTION = true;
		}
		map.put("msg",msg);
		map.put("job", regFpw);
		if (EXCEPTION) {
			if ("reg".equals(regFpw)) {
				if ("0".equals(RID)) {
						 he = httpClientUtil.postRequest(Contants.GetMsgCode, map);
					if (he == null) {
						return null;
					} else {
						BOOL = 1;
							String str = EntityUtils.toString(he, "gbk");
							parseJson(str);
							he = null;
					}
				} else {
					RID="0";
					return "Y";

				}
			} else {
				if (!"0".equals(RID)) {
						he = httpClientUtil.postRequest(Contants.GetMsgCode, map);
					if (he == null) {
						return null;
					} else {
						BOOL = 1;
							String str = EntityUtils.toString(he, "gbk");
							parseJson(str);

					}
				} else {
					RID="0";
					return "W";
				}
			}
		}
		RID="0";
		return VCODE;
	}


	/**解析返回的json数据*/
	public void parseJson(String str) {
		Map<String, Map<String, Object>> mapXML = null;
		mapXML = (Map<String, Map<String, Object>>) Resolve.getInstance().json(str);
		if ("200".equals(mapXML.get("result"))) {
			;
		} else {
			VCODE = "500";
		}

		if (mapXML.get("userInfo")!= null) {
			switch (BOOL) {
				case 0:
					RID = String.valueOf(mapXML.get("RID"));
					LogUtil.i("zyx1111",RID);
					break;

			}
		}

		/*if (!RID.equals(0)) {
			VCODE = String.valueOf(mapXML.get("MsgCode"));
		}*/

			if (BOOL == 1) {
				VCODE =  String.valueOf(mapXML.get("MsgCode"));
			}
	}
}

	/*public String getMSGCode(String phoneNb, String msg, String regFpw) {
		try {
			msg = URLEncoder.encode(msg, "gbk");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		boolean EXCEPTION = true;
		InputStream data = GetApiData.getInstance().getData(
				"IsUserExit.action?phoneNum=" + phoneNb);
		LogUtil.i("zyx2222",data.toString());
		if (data == null) {
			EXCEPTION = false;
			return null;
		} else {
			BOOL = 0;
			try {
				parseXML(data);
				data.close();
				data = null;
				EXCEPTION = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("data流关闭异常。。。");
				EXCEPTION = false;
				return "EXCEPTION";
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("解析异常。。。");
				EXCEPTION = false;
				return "EXCEPTION";
			}
		}
		if (EXCEPTION) {
			if ("reg".equals(regFpw)) {
				if ("0".equals(RID)) {
					data = GetApiData.getInstance().getData(
							"GetMsgCode.action?mphone=" + phoneNb
									+ "&cmsg=" + msg + "&job=" + regFpw);
					if (data == null) {
						return null;
					} else {
						BOOL = 1;
						try {
							parseXML(data);
							data.close();
							data = null;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							System.out.println("data流关闭异常。。。");
							return "EXCEPTION";
						} catch (XmlPullParserException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							System.out.println("解析异常。。。");
							return "EXCEPTION";
						}
					}
				} else {
					return "Y";
				}
			} else {
				if (!"0".equals(RID)) {
					data = GetApiData.getInstance().getData(
							"GetMsgCode.action?mphone=" + phoneNb
									+ "&cmsg=" + msg + "&job=" + regFpw);
					if (data == null) {
						return null;
					} else {
						BOOL = 1;
						try {
							parseXML(data);
							data.close();
							data = null;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							System.out.println("data流关闭异常。。。");
							return "EXCEPTION";
						} catch (XmlPullParserException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							System.out.println("解析异常。。。");
							return "EXCEPTION";
						}
					}
				} else {
					return "W";
				}
			}
		}
		return VCODE;
	}*/



	/**
	 * XML解析
	 * 
	 * @throws XmlPullParserEXCEPTION
	 * @throws IOEXCEPTION
	 *//*
	@SuppressWarnings("unused")
	public void parseXML(InputStream in) throws XmlPullParserException,
			IOException {
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(in, "UTF-8");
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if ("result".equals(pullParser.getName())) {
					if (pullParser.getAttributeValue(null, "code")
							.equals("200"))
						;
					else {
						VCODE = "500";
					}
				}
				if ("userinf".equals(pullParser.getName())) {
					switch (BOOL) {
					case 0:
						RID = pullParser.getAttributeValue(null, "rid");
						break;

					// case 1:
					// if (RID != 0) {
					// VCODE = pullParser.getAttributeValue(null,
					// "VCODE");
					// }
					// break;

					// case 2:
					// if (VCODE != null) {
					// token = pullParser.getAttributeValue(null, "token");
					// }
					// break;

					default:
						// handler.sendEmptyMessage(990);
						break;
					}

				}
				if ("smsv".equals(pullParser.getName())) {
					if (BOOL == 1) {
						VCODE = pullParser.getAttributeValue(null, "vcode");
					}
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			event = pullParser.next();
		}

	}*/

