package com.zyx.thread;

import android.os.Handler;
import android.os.Message;

/**
 * 倒计时
 * 
 * @author dingrui
 * 
 */

public class CountdownRunnable implements Runnable {

	private long startSeconds;// 开始秒数
	private long endSeconds;// 结束秒数
	private Handler handler;// 消息队列
	private int msgWhat;// 消息代码
	private boolean isStop = false;// 是否终止倒计时

	public CountdownRunnable(long startSeconds, long endSeconds,
			Handler handler, int msgWhat) {
		this.startSeconds = startSeconds;
		this.endSeconds = endSeconds;
		this.handler = handler;
		this.msgWhat = msgWhat;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!isStop || startSeconds >= endSeconds - 1) {
			try {
				sendHandler(handler, startSeconds--, isStop);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	private void sendHandler(Handler handler, long seconds, boolean isStop) {
		if (handler != null && !isStop) {
			Message msg = handler.obtainMessage();
			msg.what = msgWhat;
			msg.obj = seconds;
			handler.sendMessage(msg);
		}
	}
}
