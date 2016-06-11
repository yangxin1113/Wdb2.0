package com.zyx.addimg;

import java.util.ArrayList;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.contants.Contants;
import com.zyx.widget.MyTitleBar;
import com.zyx.widget.PhotoviewPager;

public class ViewImgFragmentActivity01 extends MyBaseFragmentActivity {

	/** 控件相关 */
	private View view_status_bar;// 状态栏
	private View view_navigation_bar;// 虚拟按键、键盘高度
	private MyTitleBar mtb_title;// 标题栏
	private PhotoviewPager pp_view_img;// 自定义ViewPager
	private LinearLayout ll_bottom;// 底部
	private TextView tv_determine;// 确定

	/** 数据相关 */
	private ArrayList<Map<String, Object>> selectedList;// 已选图片
	private int current;// 显示当前点击的图片

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_determine:
			for (int i = 0; i < selectedList.size(); i++) {
				if (!getParse().parseBool(selectedList.get(i).get("isCheck"))) {
					selectedList.remove(i--);
				}
			}
			Intent in = new Intent();
			in.putExtra("selectedList", selectedList);
			in.putExtra("isFinish", true);
			setResult(RESULT_OK, in);
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
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

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void init(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.fragmentactivity_view_img01);
		selectedList = (ArrayList<Map<String, Object>>) getIntent()
				.getSerializableExtra("selectedList");
		utils.showToast(getApplicationContext(),selectedList.toString());
		current = getIntent().getIntExtra("current", 0);
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
		pp_view_img = (PhotoviewPager) findViewById(R.id.pp_view_img);
		ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
		tv_determine = (TextView) findViewById(R.id.tv_determine);
	}

	@Override
	protected void setViewData() {
		// TODO Auto-generated method stub
		view_status_bar.setBackgroundColor(getResources().getColor(
				R.color.main_color));
		int width = utils.dp2px(getApplicationContext(), 50) / 2 / 2;
		mtb_title.setText((current + 1) + "/" + (selectedList.size()));
		mtb_title.setRightImage(R.mipmap.selected_img);
		mtb_title.setRightVisibility(View.VISIBLE).setPadding(width, width,
				width, width);
		pp_view_img.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		pp_view_img.setCurrentItem(current);
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		mtb_title.setLeftImageOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < selectedList.size(); i++) {
					if (!getParse().parseBool(
							selectedList.get(i).get("isCheck"))) {
						selectedList.remove(i--);
					}
				}
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
				if (getParse().parseBool(
						selectedList.get(current).get("isCheck"))) {
					selectedList.get(current).put("isCheck", false);
					mtb_title.setRightImage(R.mipmap.selected_img_false);
				} else {
					selectedList.get(current).put("isCheck", true);
					mtb_title.setRightImage(R.mipmap.selected_img);
				}
			}
		});

		pp_view_img.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				mtb_title.setText((arg0 + 1) + "/" + (selectedList.size()));
				current = arg0;
				if (getParse().parseBool(selectedList.get(arg0).get("isCheck"))) {
					mtb_title.setRightImage(R.mipmap.selected_img);
				} else {
					mtb_title.setRightImage(R.mipmap.selected_img_false);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		tv_determine.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for (int i = 0; i < selectedList.size(); i++) {
				if (!getParse().parseBool(selectedList.get(i).get("isCheck"))) {
					selectedList.remove(i--);
				}
			}
			Intent in = new Intent();
			in.putExtra("selectedList", selectedList);
			setResult(RESULT_OK, in);
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 设置标题栏与底部隐藏与显示
	 */
	public void setTitleBottomVisi() {
		if (mtb_title.getVisibility() == View.VISIBLE) {
			mtb_title.startAnimation(AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.top_out_600));
			mtb_title.setVisibility(View.GONE);
			ll_bottom.startAnimation(AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.bottom_out_600));
			ll_bottom.setVisibility(View.GONE);
		} else {
			mtb_title.setVisibility(View.VISIBLE);
			mtb_title.startAnimation(AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.top_in_600));
			ll_bottom.setVisibility(View.VISIBLE);
			ll_bottom.startAnimation(AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.bottom_in_600));
		}
	}

	/**
	 * ViewPager适配器
	 */
	class MyPagerAdapter extends FragmentStatePagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			ViewImgFragment01 vif = new ViewImgFragment01();
			Bundle bundle = new Bundle();
			bundle.putString(
					"imgURI",
					"file://"
							+ getParse().isNull(
									selectedList.get(arg0).get("imagePath")));
			bundle.putInt(Contants.ACTIVITY_KEY, 0);
			vif.setArguments(bundle);
			return vif;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return selectedList.size();
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
