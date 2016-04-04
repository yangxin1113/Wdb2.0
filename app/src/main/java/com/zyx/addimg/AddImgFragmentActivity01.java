package com.zyx.addimg;

import java.util.ArrayList;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;


import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.contants.OnItemClickListener;
import com.zyx.utils.AlbumHelper;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.MyTitleBar;

public class AddImgFragmentActivity01 extends MyBaseFragmentActivity implements
		OnItemClickListener {

	/** 控件相关 */
	private View view_status_bar;// 状态栏
	private View view_navigation_bar;// 虚拟按键、键盘高度
	private MyTitleBar mtb_title;// 标题栏
	private GridView gv_img_list;// 相册展示列表

	/** 数据相关 */
	private AlbumHelper helper;// 图片集帮助类
	private ArrayList<Map<String, Object>> albumList;// 图片集集合
	private ArrayList<Map<String, Object>> selectedList;// 已选图片集合
	private int maxImg;// 图片选择最大张数

	private int width;// 屏幕宽
	private int viewWidth;// 适配器内部控件宽

	/** 适配器 */
	private AddImgAdapter01 albumAdapter;// 相册适配器

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isUserMapNull() {
		// TODO Auto-generated method stub
		return true;
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
			if (albumList != null && albumList.size() != 0) {
				setAdapter();
				break;
			}
			utils.showToastTop(getApplicationContext(), "暂无本地图片");
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void init(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.fragmentactivity_add_img01);
		width = getWindowManager().getDefaultDisplay().getWidth();
		viewWidth = (width - utils.dp2px(getApplicationContext(),
				(float) (8.5 * 2 + 8))) / 2;
		helper = AlbumHelper.getInstance();
		helper.init(getApplicationContext());
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
		gv_img_list = (GridView) findViewById(R.id.gv_img_list);
	}

	@Override
	protected void setViewData() {
		// TODO Auto-generated method stub
		view_status_bar.setBackgroundColor(getResources().getColor(
				R.color.main_color));
		mtb_title.setText(getString(R.string.select_album));
		gv_img_list.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		mtb_title.setLeftImageOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent in = new Intent();
				// in.putExtra("selectedList", selectedList);
				// setResult(RESULT_OK, in);
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
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				albumList = helper.getImagesBucketList(false);
				if (albumList != null)
					for (int i = 0; i < albumList.size(); i++) {
						if (albumList.get(i) == null) {
							albumList.remove(i--);
						}
					}
				if (handler != null) {
					handler.sendEmptyMessage(MyMessageQueue.OK);
				}
			}
		}.start();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		helper.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Intent in = new Intent();
			// in.putExtra("selectedList", selectedList);
			// setResult(RESULT_OK, in);
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("unchecked")
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
					setResult(RESULT_OK, in);
					finish();
					overridePendingTransition(R.anim.left_in, R.anim.right_out);
				}
			}
		}
	}

	@Override
	public void itemClick(View v, ArrayList<Map<String, Object>> list,
			int position) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.view_click:
			Intent in = new Intent(getApplicationContext(),
					AddImgFragmentActivity02.class);
			in.putExtra("imageList",
					getParse().parseList(list.get(position).get("imageList")));
			in.putExtra("selectedList", selectedList);
			in.putExtra("maxImg", maxImg);
			startActivityForResult(in, RESULT_FIRST_USER);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;

		default:
			break;
		}
	}

	/**
	 * 设置适配器
	 */
	private void setAdapter() {
		if (gv_img_list.getAdapter() == null) {
			if (albumAdapter == null) {
				albumAdapter = new AddImgAdapter01(albumList,
						getApplicationContext(), imageLoader, options,
						viewWidth);
				albumAdapter.setOnItemClickListener(this);
			} else {
				albumAdapter.setData(albumList);
			}
			gv_img_list.setAdapter(albumAdapter);
		} else {
			((AddImgAdapter01) gv_img_list.getAdapter()).setData(albumList);
		}
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
