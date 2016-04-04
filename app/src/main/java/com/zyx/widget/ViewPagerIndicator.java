package com.zyx.widget;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zyx.R;


/**
 * view pager 导航条
 *
 */
public class ViewPagerIndicator extends LinearLayout implements
		OnCheckedChangeListener, OnPageChangeListener {

	/**
	 * 加载布局文件
	 */
	private View View;
	/**
	 * 水平滚动条
	 */
	private HorizontalScrollView hsv;
	/**
	 * 单选按钮组
	 */
	private RadioGroup rg;
	/**
	 * 进度条
	 */
	private View indicator;
	/**
	 * 数据列表
	 */
	private List<String> datas = new ArrayList<String>();

	// 操作数据变化的viewpager控件
	private ViewPager pager;
	private float fromX;
	private int dx = 0;

	@SuppressLint("NewApi")
	public ViewPagerIndicator(Context context, AttributeSet attrs,
							  int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		LayoutInflater.from(context).inflate(R.layout.layout_pager_indicator,
				this);
		if (!isInEditMode()) {

			initView(context);
		}
	}

	public ViewPagerIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	/**
	 * 初始化 相应控件 并且为相应控件赋予相应的事件
	 * @param context
	 */
	@SuppressLint("InflateParams")
	private void initView(Context context) {

		hsv = (HorizontalScrollView) findViewById(R.id.hsv);

		rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(this);
		indicator = findViewById(R.id.indicator);

	}

	public ViewPagerIndicator(Context context) {
		this(context, null);
	}

	public List<String> getDatas() {
		return datas;
	}

	/**
	 * 为标题条目动态赋值
	 */
	public void setDatas(List<String> datas) {
		this.datas = datas;
		addView();
	}

	/**
	 * 将标题控件动态添加radiobuttonGroup控件中去
	 */
	@SuppressLint("ResourceAsColor")
	private void addView() {
		for (int i = 0; i < datas.size(); i++) {
			RadioButton radioButton = new RadioButton(getContext());
			radioButton.setText(datas.get(i));
			radioButton.setId(10000 + i);
			radioButton.setBackgroundResource(android.R.color.white);
			radioButton.setButtonDrawable(null);
			radioButton.setButtonDrawable(android.R.color.transparent);
			radioButton.setTextColor(getResources().getColor(R.color.black));
			LayoutParams layoutParams = new LayoutParams(Dp2Px(getContext(),
					100), LayoutParams.WRAP_CONTENT);
			radioButton.setLayoutParams(layoutParams);
			radioButton.setGravity(Gravity.CENTER);
			int px = Dp2Px(getContext(), 8);
			radioButton.setPadding(px, px, px, px);

			rg.addView(radioButton);
		}

	}

	public int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	public int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public ViewPager getPager() {
		return pager;
	}

	/**
	 * 设置viewpager对象
	 */
	public void setPager(ViewPager pager) {
		this.pager = pager;
		setPagerListener();
	}

	/**
	 * 对viewpager翻页进行监听
	 */
	private void setPagerListener() {
		pager.setOnPageChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int currentItem = 0;
		int count = group.getChildCount();
		for (int i = 0; i < count; i++) {
			if (checkedId == group.getChildAt(i).getId()) {
				currentItem = i;
				break;
			}
		}
		if (pager != null) {
			pager.setCurrentItem(currentItem);

		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 页面滚动 指示条随之移动 当条目滚出屏幕的时候，水平滚动条也随之移动
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset,
							   int positionOffsetPixels) {
		int sum = 0;
		sum = (int) ((position+positionOffset)*rg.getChildAt(position).getWidth());

		int green = (pager.getWidth() - rg.getChildAt(position).getWidth()) / 2;
		dx = sum - green;// 计算出要滑出去的距离
		hsv.scrollTo(dx, 0);
		tempx=hsv.getScrollX();
		indicatorScroll(position, positionOffset);

	}

	/**
	 * 指示条随着页面的移动也跟随着滑动
	 */
	int tempx=0;
	private void indicatorScroll(int position, float positionOffset) {
		RadioButton button = (RadioButton) rg.getChildAt(position);
		int[] location = new int[2];
		button.getLocationInWindow(location);
		// 开始做位移滑动
		TranslateAnimation animation = new TranslateAnimation(fromX,
				location[0] + positionOffset * button.getWidth()+tempx, 0, 0);
		animation.setDuration(50);// 动画持续事件
		animation.setFillAfter(true);
		fromX = (int) (location[0] + button.getWidth() * positionOffset+tempx);
		indicator.startAnimation(animation);// 线开始动画
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isInEditMode()) {

		}
	}

}
