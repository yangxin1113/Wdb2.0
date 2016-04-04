package com.zyx.addimg;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zyx.R;
import com.zyx.base.MyBaseAdapter;
import com.zyx.contants.OnItemClickListener;

public class AddImgAdapter01 extends MyBaseAdapter {

	/** 数据相关 */
	private int width;// 控件宽

	/** 接口 */
	private OnItemClickListener l;// 内部控件点击事件

	public AddImgAdapter01(ArrayList<Map<String, Object>> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	public AddImgAdapter01(ArrayList<Map<String, Object>> list,
			Context context, ImageLoader imageLoader,
			DisplayImageOptions options) {
		super(list, context, imageLoader, options);
		// TODO Auto-generated constructor stub
	}

	public AddImgAdapter01(ArrayList<Map<String, Object>> list,
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
			convertView = getInflater.inflate(R.layout.item_add_img01, null);
			holder.fl_addimg = (FrameLayout) convertView
					.findViewById(R.id.fl_addimg);
			holder.iv_addimg = (ImageView) convertView
					.findViewById(R.id.iv_addimg);
			holder.view_click = (View) convertView
					.findViewById(R.id.view_click);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_count);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		LayoutParams lp = holder.fl_addimg.getLayoutParams();
		lp.width = width;
		lp.height = width / 3 * 2;
		holder.fl_addimg.setLayoutParams(lp);
		LayoutParams lp1 = holder.iv_addimg.getLayoutParams();
		lp1.width = width;
		lp1.height = width / 3 * 2;
		holder.iv_addimg.setLayoutParams(lp1);

		Map<String, Object> map = list.get(position);
		String path = null;
		try {
			path = getParse().isNull(
					getParse().parseList(map.get("imageList")).get(0)
							.get("imagePath"));
		} catch (Exception e) {
			// TODO: handle exception
			path = "";
		}
		holder.tv_title.setText(getParse().isNull(map.get("bucketName")));
		holder.tv_count.setText(getParse().isNull(map.get("count")));
		imageLoader.displayImage("file://" + path, holder.iv_addimg, options,
				animateFirstListener);
		holder.view_click.setOnClickListener(new MyOnClickListener(
				holder.view_click, list, position));
		return convertView;
	}

	static class Holder {
		FrameLayout fl_addimg;// 图片父布局
		ImageView iv_addimg;// 图片
		View view_click;// 点击遮罩
		TextView tv_title;// 相册名
		TextView tv_count;// 当前相册中的图片数量
	}

	/**
	 * 设置接口
	 * 
	 * @param l
	 */
	public void setOnItemClickListener(OnItemClickListener l) {
		this.l = l;
	}

	/**
	 * 重写点击事件
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
