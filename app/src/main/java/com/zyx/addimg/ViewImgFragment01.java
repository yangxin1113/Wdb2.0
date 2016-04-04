package com.zyx.addimg;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zyx.R;
import com.zyx.base.BaseFragment;
import com.zyx.contants.Contants;
import com.zyx.photoview.PhotoView;

public class ViewImgFragment01 extends BaseFragment {

	/** 控件相关 */
	private PhotoView pv_view_img;// 显示图片控件
	private RelativeLayout rl_progressbar;// 加载进度条

	private String uri;// 图片地址
	private int isActivity;// 记录哪一个Activity

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.pv_view_img:
			switch (isActivity) {
			case 0:
				((ViewImgFragmentActivity01) getActivity())
						.setTitleBottomVisi();
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setLayoutRes(R.layout.fragment_view_img01);
		Bundle bd = getArguments();
		uri = bd.getString("imgURI");
		isActivity = bd.getInt(Contants.ACTIVITY_KEY, 0);
	}

	@Override
	protected void initView(View rootview) {
		// TODO Auto-generated method stub
		pv_view_img = (PhotoView) rootview.findViewById(R.id.pv_view_img);
		rl_progressbar = (RelativeLayout) rootview
				.findViewById(R.id.rl_progressbar);
	}

	@Override
	protected void setViewData() {
		// TODO Auto-generated method stub
		rl_progressbar.setBackgroundColor(0x00000000);
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		imageLoader.displayImage(getParse().isNull(uri), pv_view_img,
				pagerOptions, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// TODO Auto-generated method stub
						rl_progressbar.setVisibility(rl_progressbar.VISIBLE);
						super.onLoadingStarted(imageUri, view);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// TODO Auto-generated method stub
						rl_progressbar.setVisibility(rl_progressbar.GONE);
						super.onLoadingFailed(imageUri, view, failReason);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						rl_progressbar.setVisibility(rl_progressbar.GONE);
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
				});
		pv_view_img.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void handlerMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
