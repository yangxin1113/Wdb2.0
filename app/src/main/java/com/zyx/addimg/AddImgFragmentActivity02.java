package com.zyx.addimg;

import java.util.ArrayList;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.contants.OnItemClickListener;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.MyTitleBar;

public class AddImgFragmentActivity02 extends MyBaseFragmentActivity implements
		OnItemClickListener {

	/** 控件相关 */
	private View view_status_bar;// 状态栏
	private View view_navigation_bar;// 虚拟按键、键盘高度
	private MyTitleBar mtb_title;// 标题栏
	private GridView gv_addimg;// 图片集列表
	private TextView tv_view;// 预览按钮
	private TextView tv_count;// 已选计数器
	private TextView tv_determine;// 确定按钮

	/** 数据相关 */
	private int width;// 屏幕宽
	private int viewWidth;// gridview内部控件宽
	private int maxImg;// 最多选择张数

	/** 适配器 */
	private AddImgAdapter02 imgAdapter;// 图片集适配器

	private ArrayList<Map<String, Object>> selectedList;// 已选图片集
	private ArrayList<Map<String, Object>> imgList;// 图片集

	/** 线程相关 */
	private MyThread myThread;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_view:
			if (selectedList != null && selectedList.size() > 0) {
				Intent intent = new Intent(getApplicationContext(),
						ViewImgFragmentActivity01.class);
				intent.putExtra("selectedList", selectedList);
				startActivityForResult(intent, RESULT_FIRST_USER);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
			break;

		case R.id.tv_determine:
			if (selectedList != null && selectedList.size() > 0) {
				Intent in = new Intent();
				in.putExtra("selectedList", selectedList);
				in.putExtra("isFinish", true);
				setResult(RESULT_OK, in);
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
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
		return true;
	}

	@Override
	protected void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MyMessageQueue.OK:
			if (imgList != null) {
				setAdapter();
				tv_count.setText(selectedList.size() + "/" + maxImg);
				utils.showToast(getApplicationContext(),String.valueOf(selectedList.size()));
			}
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void init(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.fragmentactivity_add_img02);
		width = getWindowManager().getDefaultDisplay().getWidth();
		viewWidth = (width - utils.dp2px(getApplicationContext(), 10 * 2)) / 3;
		imgList = (ArrayList<Map<String, Object>>) getIntent()
				.getSerializableExtra("imageList");
		maxImg = getIntent().getIntExtra("maxImg", 1);
		selectedList = (ArrayList<Map<String, Object>>) getIntent()
				.getSerializableExtra("selectedList");
		if (selectedList != null) {
			for (int i = 0; i < selectedList.size(); i++) {
				if (getParse().parseBool(selectedList.get(i).get("isAdd"))) {
					selectedList.remove(i--);
				}
			}
		} else {
			selectedList = new ArrayList<Map<String, Object>>();
		}
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		view_status_bar = (View) findViewById(R.id.view_status_bar);
		view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
		mtb_title = (MyTitleBar) findViewById(R.id.mtb_title);
		gv_addimg = (GridView) findViewById(R.id.gv_addimg);
		tv_view = (TextView) findViewById(R.id.tv_view);
		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_determine = (TextView) findViewById(R.id.tv_determine);
	}

	@Override
	protected void setViewData() {
		// TODO Auto-generated method stub
		view_status_bar.setBackgroundColor(getResources().getColor(
				R.color.main_color));
		mtb_title.setText(getString(R.string.select_image));
		tv_count.setText(selectedList.size() + "/" + maxImg);
		tv_view.setText(getString(R.string.preview_zh));
		tv_determine.setText(getString(R.string.determine_zh));
		gv_addimg.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));

	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		mtb_title.setLeftImageOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.putExtra("selectedList", selectedList);
				setResult(RESULT_OK, in);
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
			}
		});
		mtb_title.setRightImageOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startHomeActivity();
			}
		});

		tv_view.setOnClickListener(this);
		tv_determine.setOnClickListener(this);
		//gv_addimg.setOnItemClickListener(this);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		if (!isAlive(myThread)) {
			myThread = new MyThread();
			myThread.start();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent in = new Intent();
			in.putExtra("selectedList", selectedList);
			setResult(RESULT_OK, in);
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == RESULT_FIRST_USER) {
			if (arg1 == RESULT_OK) {
				selectedList = (ArrayList<Map<String, Object>>) arg2
						.getSerializableExtra("selectedList");
				if (arg2.getBooleanExtra("isFinish", false)) {
					Intent in = new Intent();
					in.putExtra("selectedList", selectedList);
					in.putExtra("isFinish", true);
					setResult(RESULT_OK, in);
					finish();
					overridePendingTransition(R.anim.left_in, R.anim.right_out);
					return;
				}
				if (!isAlive(myThread)) {
					myThread = new MyThread();
					myThread.start();
				}
			}
		}
	}

	@Override
	public void itemClick(View v, ArrayList<Map<String, Object>> list,
			int position) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_selected:
			utils.showToast(getApplicationContext(),"fdsafdsfs"+list.get(position).get("imagePath").toString());
			for (int i = 0; i < selectedList.size(); i++) {
				if (getParse().isNull(selectedList.get(i).get("imagePath"))
						.equals(getParse().isNull(
								list.get(position).get("imagePath")))) {
					selectedList.remove(i--);
				}/*else{
					selectedList.add(i, (Map<String, Object>) list.get(position).get("imagePath")
							);
				}*/
			}
			list.get(position).put("isCheck", false);
			break;

		case R.id.iv_img:
			if (selectedList.size() >= maxImg) {
				utils.showToast(getApplicationContext(), "最多只能选择 " + maxImg
						+ " 张哦");
				return;
			}
			list.get(position).put("isCheck", true);
			selectedList.add(0, list.get(position));
			break;

		default:
			break;
		}
		((AddImgAdapter02) gv_addimg.getAdapter()).setData(list);
		tv_count.setText(selectedList.size() + "/" + maxImg);
	}

	/**
	 * 设置适配器
	 */
	private void setAdapter() {
		if (gv_addimg.getAdapter() == null) {
			if (imgAdapter == null) {
				imgAdapter = new AddImgAdapter02(imgList,
						getApplicationContext(), imageLoader, options,
						viewWidth);
				imgAdapter.setOnItemClickListener(this);
			} else {
				imgAdapter.setData(imgList);
			}
			gv_addimg.setAdapter(imgAdapter);
		} else {
			((AddImgAdapter02) gv_addimg.getAdapter()).setData(imgList);
		}
	}

	/**
	 * 设置已选中相册的线程
	 */
	class MyThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			setSelectedList();
		}
	}

	/**
	 * 设置已选中相册的方法
	 */
	private synchronized void setSelectedList() {
		for (int i = 0; i < imgList.size(); i++) {
			imgList.get(i).put("isCheck", false);
			for (int j = 0; j < selectedList.size(); j++) {
				if (getParse().isNull(imgList.get(i).get("imagePath"))
						.equals(getParse().isNull(
								selectedList.get(j).get("imagePath")))) {
					if (getParse()
							.parseBool(selectedList.get(j).get("isCheck"))) {
						imgList.get(i).put("isCheck", true);
					} else {
						imgList.get(i).put("isCheck", false);
						if (!getParse().parseBool(
								selectedList.get(j).get("isAdd")))
							selectedList.remove(j--);
					}
				}
			}
		}
		if (handler != null)
			handler.sendEmptyMessage(MyMessageQueue.OK);
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
}
