package com.zyx.fragment.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

import com.zyx.R;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.contants.Contants;
import com.zyx.thread.PostDataThread;
import com.zyx.utils.MyMessageQueue;
import com.zyx.utils.Resolve;
import com.zyx.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 设置密码
 * 
 * @author dingrui
 * 
 */
public class SettingsPasswordFragmentActivity extends MyBaseFragmentActivity
		implements OnTouchListener {

	/** 控件相关 */
	private View view_status_bar;// 状态栏
	private View view_navigation_bar;// 虚拟键盘、键盘高度
	private MyTitleBar mtb_title;// 标题栏
	private EditText et_set_pwd;// 设置密码
	private EditText et_confirm_pwd;// 确认密码
	private Button bt_confirm;// 确定

	/** 数据相关 */
	private String phoneNumber;// 手机号
	private boolean isForget;// 是否忘记密码
	private String code;// 验证码

	/** DiaLog */
	private ProgressDialog pd;// 进度条

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_confirm:
			    Map<String, String> map = new HashMap<String, String>();
				if (isForget) {
					if (isModefyPwd()) {

						map.put("CustPhoneNum", phoneNumber);
						map.put("CustLoginPwd", et_confirm_pwd.getText().toString());
						pd.show();
						/*startRunnable(new GetDataThread(getApplicationContext(),
								String.format(Contants.MSG_AUTHORIZATION,
										phoneNumber, code), handler, 0x4000,
								0x4999, 0x4998));*/
						startRunnable(new PostDataThread(
								Contants.UPDATE_USER_INFO , map,
								handler, MyMessageQueue.OK_SUBMISSION,
								MyMessageQueue.SUBMISSION_FAILED));
					}
				} else {

					if(isRegistered()){

					    utils.setKeyBoardGone(getApplicationContext(), et_set_pwd,
							et_confirm_pwd);

						//JSONObject jSon = new JSONObject();
					   //try {
						/*jSon.put("pwd", et_confirm_pwd.getText().toString());
						jSon.put("dwc",
								utils.getDeviceId(getApplicationContext()));
						jSon.put("mbp", phoneNumber);
						jSon.put("tfn", phoneNumber);
						jSon.put("tjh", "");*/

						map.put("CustPhoneNum", phoneNumber);
						map.put("CustLoginPwd",et_set_pwd.getText().toString());
						map.put("CustDealPwd",et_confirm_pwd.getText().toString());
						pd.show();
						startRunnable(new PostDataThread(
								Contants.USER_REGSTEP1, map, handler,
								MyMessageQueue.OK, MyMessageQueue.TIME_OUT));
					   /* } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						sendHandler(MyMessageQueue.TIME_OUT, null);
					    }*/
					}
				}
			break;

		default:
			break;
		}
	}

	@Override
	protected boolean isUserMapNull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isToken() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MyMessageQueue.OK:
			Map<String, Map<String, Object>> mapXML = (Map<String, Map<String, Object>>) msg.obj;


				if ("200".equals(getParse().isNull(
						mapXML.get("code")))) {
					ArrayList<Map<String, Object>> userinfList = Resolve
							.getInstance().getList(mapXML, "userInfo");
					if (userinfList != null && userinfList.size() > 0) {
						Map<String, Object> user = userinfList.get(0);
						Map<String, String> userMap = new HashMap<String, String>();
						userMap.put(Contants.UN, phoneNumber);
						userMap.put(Contants.UPWD, et_confirm_pwd.getText()
								.toString());
						((MyApplication) getApplication()).setUser(user);
						((MyApplication) getApplication()).setUserMap(userMap);
						utils.showToast(getApplicationContext(),
								getString(R.string.registered_ok_zh));
						Intent in = new Intent();
						in.putExtra("isRegistered", true);
						setResult(Activity.RESULT_OK, in);
						finish();
						overridePendingTransition(R.anim.left_in,
								R.anim.right_out);
						break;
					}else {
						utils.showToast(getApplicationContext(),
								getParse().isNull(mapXML.get("msg")));
						break;
					}
				} else {
					utils.showToast(getApplicationContext(),
							getParse().isNull(R.string.registered_error_zh_));
					break;
				}

		case 0x4000:
			Map<String, Map<String, Object>> mapXML1 = (Map<String, Map<String, Object>>) msg.obj;
			ArrayList<Map<String, Object>> resultList1 = Resolve.getInstance()
					.getList(mapXML1, "result");
			if (resultList1 != null && resultList1.size() > 0) {
				if ("200".equals(resultList1.get(0).get("code"))) {
					ArrayList<Map<String, Object>> userinfList1 = Resolve
							.getInstance().getList(mapXML1, "userinf");
					if (userinfList1 != null && userinfList1.size() > 0) {
						String token = getParse().isNull(
								userinfList1.get(0).get("token"));
						if (!"".equals(token)) {
							Map<String, String> map = new HashMap<String, String>();
							map.put("field_11", et_confirm_pwd.getText()
									.toString());
							startRunnable(new PostDataThread(
									Contants.UPDATE_USER_INFO + token, map,
									handler, MyMessageQueue.OK_SUBMISSION,
									MyMessageQueue.SUBMISSION_FAILED));
							return;
						}
					}
				} else {
					utils.showToast(getApplicationContext(),
							getParse().isNull(resultList1.get(0).get("msg")));
					break;
				}
			}
			utils.showToast(getApplicationContext(),
					getString(R.string.update_pwd_error_zh));
			break;

		case 0x4998:
			utils.showToast(getApplicationContext(),
					getString(R.string.data_exception_zh));
			break;

		case 0x4999:
			utils.showToast(getApplicationContext(),
					getString(R.string.network_exception));
			break;

		case MyMessageQueue.OK_SUBMISSION:
			Map<String, Map<String, Object>> mapXML2 = (Map<String, Map<String, Object>>) msg.obj;


				if ("200".equals(getParse().isNull(
						mapXML2.get("code")))) {
					utils.showToast(getApplicationContext(),
							getString(R.string.pwd_update_ok_zh));
					Intent intent = new Intent();
					intent.putExtra("isPwdOk", true);
					setResult(Activity.RESULT_OK, intent);
					finish();
					overridePendingTransition(R.anim.left_in, R.anim.right_out);
					break;
				} else {
					utils.showToast(getApplicationContext(),
							getParse().isNull(mapXML2.get("msg")));
					if (pd.isShowing())
						pd.dismiss();
					break;
				}

		case MyMessageQueue.SUBMISSION_FAILED:
			// 修改密码失败
			utils.showToast(getApplicationContext(),
					getString(R.string.update_pwd_error_zh));
			break;

		case MyMessageQueue.TIME_OUT:
			utils.showToast(getApplicationContext(),
					getString(R.string.registered_error_zh_));
			break;

		default:
			break;
		}
		if (pd.isShowing())
			pd.dismiss();
	}

	@Override
	protected void init(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.fragment_activity_set_pwd);
		phoneNumber = getIntent().getStringExtra("phoneNumber");
		isForget = getIntent().getBooleanExtra("isForget", false);
		code = getIntent().getStringExtra("code");
		pd = new ProgressDialog(this);
		if (isForget) {
			pd.setMessage(getString(R.string.submission_zh_));
		} else {
			pd.setMessage(getString(R.string.loading_registered_zh_));
		}
		pd.setCancelable(false);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		view_status_bar = (View) findViewById(R.id.view_status_bar);
		view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
		mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
		et_set_pwd = (EditText) findViewById(R.id.et_set_pwd);
		et_confirm_pwd = (EditText) findViewById(R.id.et_confirm_pwd);
		bt_confirm = (Button) findViewById(R.id.bt_confirm);
	}

	@Override
	protected View getStatusBarView() {
		// TODO Auto-generated method stub
		return view_status_bar;
	}

	@Override
	protected View getBottomView() {
		// TODO Auto-generated method stub
		return view_navigation_bar;
	}

	@Override
	protected void setViewData() {
		// TODO Auto-generated method stub
		view_status_bar.setBackgroundColor(getResources().getColor(
				R.color.main_color));
		mtb_title.setText(getString(R.string.set_pwd_zh));
		if (!isForget) {
			et_confirm_pwd.setHint(R.string.pwd_length_error_zh);
		}

	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		mtb_title.setLeftImageOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onKeyDown(KeyEvent.KEYCODE_BACK, null);
			}
		});
		et_set_pwd.setOnTouchListener(this);
		et_confirm_pwd.setOnTouchListener(this);

		bt_confirm.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		et_set_pwd.setFocusable(true);
		et_set_pwd.setFocusableInTouchMode(true);
		et_confirm_pwd.setFocusable(true);
		et_confirm_pwd.setFocusableInTouchMode(true);
		return false;
	}

	/**
	 * 是否可以注册
	 * 
	 * @return
	 */
	private boolean isRegistered() {
		boolean isRegistered = true;
		if (TextUtils.isEmpty(et_set_pwd.getText())) {
			utils.showToast(getApplicationContext(),
					getString(R.string.input_login_pwd_zh));
			isRegistered = false;
		} else if (TextUtils.isEmpty(et_confirm_pwd.getText())) {
			utils.showToast(getApplicationContext(),
					getString(R.string.input_deal_pwd_zh));
			isRegistered = false;
		} else if (et_confirm_pwd.getText().toString().length() != 6) {
			utils.showToast(getApplicationContext(),
					getString(R.string.pwd_length_error_zh));
			isRegistered = false;
		} else {
			String pwd = et_set_pwd.getText().toString();
			boolean isNumber = false;
			for (int i = 0; i < pwd.length(); i++) {
				char c = pwd.charAt(i);
				if (c < 48 || c > 57) {
					isNumber = true;
				}
			}
			if (!isNumber) {
				utils.showToast(getApplicationContext(),
						getString(R.string.pwd_concat_one_letter_zh));
				isRegistered = false;
			}
		}
		return isRegistered;
	}

	/**
	 * 是否可以修改密码
	 * @return
	 */

	private boolean isModefyPwd() {
		boolean isModefyPwd = true;
		if (et_set_pwd.getText() == null
				|| et_set_pwd.getText().toString().length() == 0) {
			utils.showToast(getApplicationContext(),
					getString(R.string.input_login_pwd_zh));
			isModefyPwd = false;
		} else if (et_confirm_pwd.getText() == null
				|| et_confirm_pwd.getText().toString().length() == 0) {
			utils.showToast(getApplicationContext(),
					getString(R.string.please_confirm_pwd_zh));
			isModefyPwd = false;
		} else if (!et_set_pwd.getText().toString()
				.equals(et_confirm_pwd.getText().toString())) {
			utils.showToast(getApplicationContext(),
					getString(R.string.input_tow_pwd_error_zh));
			isModefyPwd = false;
		} else if (et_confirm_pwd.getText().toString().length() < 6) {
			utils.showToast(getApplicationContext(),
					getString(R.string.pwd_length_error_zh));
			isModefyPwd = false;
		} else {
			String pwd = et_confirm_pwd.getText().toString();
			boolean isNumber = false;
			for (int i = 0; i < pwd.length(); i++) {
				char c = pwd.charAt(i);
				if (c < 48 || c > 57) {
					isNumber = true;
				}
			}
			if (!isNumber) {
				utils.showToast(getApplicationContext(),
						getString(R.string.pwd_concat_one_letter_zh));
				isModefyPwd = false;
			}
		}
		return isModefyPwd;
	}

}
