package com.zyx.utils;

/**
 * 消息队列
 */
public class MyMessageQueue {

	/** handler为null 消息代码：0x5000 */
	public static final int HANDLER_NULL = 0x5000;

	/** 上传文件的进度 消息代码：0x7000 */
	public static final int UPDATE_PROGRESS = 0x7000;

	/** 加载成功 消息代码：0x8000 */
	public static final int OK = 0x8000;

	/** 获取广告成功 消息代码：0x8001 */
	public static final int OK_BANNER = 0x8001;

	/** 提交成功 消息代码：0x8002 */
	public static final int OK_SUBMISSION = 0x8002;

	/** 获取短信验证码（注册） 消息代码：0x8003 */
	public static final int OK_REG_MSG = 0x8003;

	/** 获取短信验证码（找回） 消息代码：0x8003 */
	public static final int OK_FPW_MSG = 0x8004;

	/** 检查更新成功，可以跳过 消息代码：0x8100 */
	public static final int UPDATE_OK = 0x8100;

	/** 提交失败 消息代码：0x9997 */
	public static final int SUBMISSION_FAILED = 0x9997;

	/** 数据异常 消息代码：0x9998 */
	public static final int DATA_EXCEPTION = 0x9998;

	/** 连接超时 消息代码：0x9999 */
	public static final int TIME_OUT = 0x9999;

}
