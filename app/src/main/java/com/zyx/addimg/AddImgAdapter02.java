package com.zyx.addimg;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zyx.R;
import com.zyx.base.MyBaseAdapter;
import com.zyx.contants.OnItemClickListener;

public class AddImgAdapter02 extends MyBaseAdapter {

	/** 数据相关 */
	private int width;// 控件宽

	/** 接口 */
	private OnItemClickListener l;// 点击事件接口

	public AddImgAdapter02(ArrayList<Map<String, Object>> list,
			Context context, ImageLoader imageLoader,
			DisplayImageOptions options) {
		super(list, context, imageLoader, options);
		// TODO Auto-generated constructor stub
	}

	public AddImgAdapter02(ArrayList<Map<String, Object>> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	public AddImgAdapter02(ArrayList<Map<String, Object>> list,
			Context context, ImageLoader imageLoader,
			DisplayImageOptions options, int width) {
		super(list, context, imageLoader, options);
		// TODO Auto-generated constructor stub
		this.width = width;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = getInflater.inflate(R.layout.item_add_img02, null);
			holder.fl_img = (FrameLayout) convertView.findViewById(R.id.fl_img);
			holder.rl_selected = (RelativeLayout) convertView
					.findViewById(R.id.rl_selected);
			holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		LayoutParams lp = holder.fl_img.getLayoutParams();
		lp.width = width;
		lp.height = width;
		holder.fl_img.setLayoutParams(lp);
		LayoutParams lp1 = holder.iv_img.getLayoutParams();
		lp1.width = width;
		lp1.height = width;
		if (getParse().parseBool(list.get(position).get("isCheck"))) {
			holder.rl_selected.setVisibility(View.VISIBLE);
		} else {
			holder.rl_selected.setVisibility(View.GONE);
		}
		imageLoader.displayImage(
				"file://"
						+ getParse()
								.isNull(list.get(position).get("imagePath")),
				holder.iv_img, options, animateFirstListener);
		holder.iv_img.setOnClickListener(new MyOnClickListener(holder.iv_img,
				list, position));
		holder.rl_selected.setOnClickListener(new MyOnClickListener(
				holder.rl_selected, list, position));
		return convertView;
	}

	static class Holder {
		FrameLayout fl_img;// 父布局
		RelativeLayout rl_selected;// 已选中布局
		ImageView iv_img;// 本地图片
	}

	/**
	 * 设置点击事件
	 * 
	 * @param l
	 */
	public void setOnItemClickListener(OnItemClickListener l) {
		this.l = l;
	}

	/**
	 * 设置内部控件点击事件
	 */
	class MyOnClickListener implements OnClickListener {

		private View v;
		private ArrayList<Map<String, Object>> list;
		private int position;

		public MyOnClickListener(View v, ArrayList<Map<String, Object>> list,
				int position) {
			this.v = v;
			this.list = list;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (l != null)
				l.itemClick(v, list, position);
		}
	}
}
