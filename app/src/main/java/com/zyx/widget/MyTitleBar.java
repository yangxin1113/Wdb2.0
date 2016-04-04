package com.zyx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyx.R;

/**
 * Created by zyx on 2016/2/10.
 */
public class MyTitleBar extends RelativeLayout {
    /** 控件相关 */
    private View view_top;// 状态栏同步的view
    private ImageView iv_left;// 左边按钮
    private ImageView iv_right;// 右边按钮
    private TextView tv_title;// 标题

    public MyTitleBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public MyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public MyTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context);
    }

    /** 初始化 */
    void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.my_title,null);
        view_top = view.findViewById(R.id.view_top);
        iv_left = (ImageView) view.findViewById(R.id.iv_left);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        addView(view);
    }

    /**
     * 设置与状态栏同步的高度
     *
     * @param h
     */
    public void setTopHeight(int h){
        android.view.ViewGroup.LayoutParams lp = view_top.getLayoutParams();
        lp.height = h;
        view_top.setLayoutParams(lp);
        android.view.ViewGroup.LayoutParams lp1 = getLayoutParams();
        lp1.height += h;
        setLayoutParams(lp1);

    }

    /**
     * 设置标题
     *
     * @param text
     */
    public void setText(CharSequence text){
        if(text == null)
            text = "";
        tv_title.setText(text);
    }

    /**
     * 获取标题
     *
     * @return CharSequence
     */
    public CharSequence getText(){
        return tv_title.getText()!=null ? tv_title.getText() : "";
    }

    /**
     * 设置标题颜色
     *
     * @param  color
     */
    public void setTextColor(int color){
        tv_title.setText(color);
    }

    /**
     * 获取标题对象，可以设置更多属性
     *
     * @return TextView
     */
    public TextView getTitleTextView() {
        return tv_title;
    }

    /**
     * 设置左边图标src
     *
     * @param resId
     */
    public void setLeftImage(int resId) {
        iv_left.setImageResource(resId);
    }

    /**
     * 设置左边图标是否隐藏
     *
     * @param visibility
     */
    public ImageView setLeftVisibility(int visibility) {
        iv_left.setVisibility(visibility);
        return iv_left;
    }

    /**
     * 设置左边图标是否可点击
     *
     * @param isClickble
     */
    public void setLeftImgClickable(boolean isClickble) {
        iv_left.setClickable(isClickble);
    }

    /**
     * 设置右边图标src
     *
     * @param resId
     */
    public void setRightImage(int resId) {
        iv_right.setImageResource(resId);
    }

    /**
     * 设置右边图标是否隐藏
     *
     * @param visibility
     */
    public ImageView setRightVisibility(int visibility) {
        iv_right.setVisibility(visibility);
        return iv_right;
    }

    /**
     * 设置右边图标是否可点击
     *
     * @param isClickble
     */
    public void setRightImgClickable(boolean isClickble) {
        iv_right.setClickable(isClickble);
    }

    /**
     * 设置标题TextView的点击事件
     *
     * @param clickListener
     */
    public void setTitleOnClickListener(OnClickListener clickListener){
        if(clickListener != null)
            tv_title.setOnClickListener(clickListener);
    }

    /**
     * 设置左边图标点击事件
     *
     * @param clickListener
     */
    public void setLeftImageOnClickListener(OnClickListener clickListener) {
        if (clickListener != null)
            iv_left.setOnClickListener(clickListener);
    }

    /**
     * 设置右边图标点击事件
     * @param clickListener
     */
    public void setRightImageOnClickListener(OnClickListener clickListener) {
        if (clickListener != null)
            iv_right.setOnClickListener(clickListener);
    }

    /**
     * 获得左边图标
     * @return iv_left
     */
    public ImageView getLeftImageView(){
        return iv_left;
    }

    /**
     * 获得右边图标
     * @return iv_right
     */
    public ImageView getRightImageView(){
        return iv_right;
    }
}
