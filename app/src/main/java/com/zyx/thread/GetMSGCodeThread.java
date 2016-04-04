package com.zyx.thread;

import android.os.Handler;
import android.os.Message;

import com.zyx.utils.GetMSGCode;

import java.io.IOException;


public class GetMSGCodeThread implements Runnable {

	private Handler handler;// 消息队列
	private int msgWhat;// 消息代码
	private String phoneNb;// 手机号
	private String msg;// 信息提示内容(例：找回密码；注册账号等信息（JZ:提示$VCODE$信息；RL:模板ID）)
	private String regFpw;// 是否注册，reg为是，fpw为否；注册时判断解析出来的RID为0时才发送短信，否则RID不为0时不发送短信（RID
							// == 0？系统没有phoneNb用户 : 系统有phoneNb用户)

	/**
	 * @param handler
	 *            消息队列
	 * @param msgWhat
	 *            消息代码
	 * @param phoneNb
	 *            手机号
	 * @param msg
	 *            信息提示内容(例：找回密码、注册账号等信息（JZ:提示$vcode$信息；RL:模板ID）)
	 * @param regFpw
	 *            是否注册，reg为是，fpw为否；注册时判断解析出来的RID为0时才发送短信，否则RID不为0时不发送短信（RID == 0
	 *            ？系统没有phoneNb用户 : 系统有phoneNb用户)
	 */
	public GetMSGCodeThread(Handler handler, int msgWhat, String phoneNb,
			String msg, String regFpw) {
		this.handler = handler;
		this.msgWhat = msgWhat;
		this.phoneNb = phoneNb;
		this.msg = msg;
		this.regFpw = regFpw;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String code = null;
		try {
			code = GetMSGCode.getInstance().getMSGCode(phoneNb, msg, regFpw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (handler != null) {
			Message m = new Message();
			m.what = msgWhat;
			m.obj = code;
			handler.sendMessage(m);
		}
	}
}
