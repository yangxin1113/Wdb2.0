package com.zyx.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zyx.utils.MyUtils;
import com.zyx.utils.Parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/12.
 */
public abstract class MyBaseAdapter extends BaseAdapter {
    protected ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    protected ArrayList<Map<String, Object>> list;// 数据
    protected Context context;// 上下文
    protected ImageLoader imageLoader;// 图片异步加载类
    protected DisplayImageOptions options;// 设置默认图片的options
    protected LayoutInflater getInflater;// 加载布局
    protected MyUtils utils;// 工具类

    /**
     * 构造函数（不需要异步加载图片）
     *
     * @param list
     *            适配器所需集合
     * @param context
     *            上下文
     */
    public MyBaseAdapter(ArrayList<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;
        this.getInflater = LayoutInflater.from(context);
        this.utils = MyUtils.getInstance();
        Log.i("fc", "&&&&&"+list.size());
    }

    /**
     * 构造函数
     *
     * @param list
     *            适配器所需集合
     * @param context
     *            上下文
     * @param imageLoader
     *            图片异步加载类
     * @param options
     *            图片异步加载类属性
     */
    public MyBaseAdapter(ArrayList<Map<String, Object>> list, Context context,
                         ImageLoader imageLoader, DisplayImageOptions options) {
        this.list = list;
        this.context = context;
        this.imageLoader = imageLoader;
        this.options = options;
        this.getInflater = LayoutInflater.from(context);
        this.utils = MyUtils.getInstance();
    }

    @Override
    public int getCount() {
        Log.i("zyx", "MyBaseAdapter,getCount");

        return list != null ? list.size() : 0;
//		return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 重新设置数据并更新数据
     *
     * @param list
     *
     */
    public void setData(ArrayList<Map<String, Object>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 获取当前适配器中集合数据
     *
     * @return List
     */
    public ArrayList<Map<String, Object>> getData() {
        return list != null ? list : new ArrayList<Map<String, Object>>();
    }

    /**
     * 新增数据并刷新数据
     *
     * @param list
     */
    public void addData(ArrayList<Map<String, Object>> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

//	@Override
//	public abstract View getView(int position, View convertView,
//			ViewGroup parent);

    /**
     * 图片加载时动画
     *
     * @author 锐丁
     *
     */
    public static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 2000);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    /**
     * 转型用
     *
     * @return
     */
    protected Parse getParse() {
        return Parse.getInstance();
    }

  /*  *//**
     * 设置textview，可以是HTML文本
     *
     * @param key
     *            集合中的Map的key
     * @param position
     *            当前位置
     * @param textViews
     *            需要设置的TextView，个数与第一个参数的长度一致，并且设置的要一一对应
     *//*
    protected void displayText(String[] key, int position,
                               TextView... textViews) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText(Html.fromHtml(getParse().isNull(
                    list.get(position).get(key[i]))));
        }
    }*/
}
