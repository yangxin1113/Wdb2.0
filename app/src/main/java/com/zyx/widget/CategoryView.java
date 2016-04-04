package com.zyx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zyx.R;

import java.util.List;

/**
 * Created by zyx on 2015/10/19.
 */
public class CategoryView extends LinearLayout implements
        RadioGroup.OnCheckedChangeListener {
    private LayoutInflater inflater;

    public CategoryView(Context context) {
        this(context, null);
    }

    public CategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
    }


    /**添加方法*/
    public void add(String attr,List<String> list) {
        if (list.size() > 0) {
            //加载布局
            View view = inflater.inflate(R.layout.category_container, null);
            addView(view);

            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.container);
            RadioButton bt = newRadioButton(attr);
            bt.setTextColor(android.graphics.Color.BLUE);
            bt.setBackgroundResource(R.color.white);
            radioGroup.addView(bt);

            // 默认选中
            radioGroup.check(bt.getId());
            // 全部
            for (String str : list) {
                bt = newRadioButton(str);//实例化新的RadioButton
                radioGroup.addView(bt);
            }
            //为当前RadioGroup设置监听器
            radioGroup.setOnCheckedChangeListener(this);
        }
    }

    /**创建RadioButton*/
    private RadioButton newRadioButton(String text) {
        RadioButton button = new RadioButton(getContext());

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);

        //设置内外边距
        params.leftMargin = 8;
        params.rightMargin = 8;
        button.setLayoutParams(params);
        button.setPadding(8, 0, 8, 0);

     /*   button.setTextSize(getResources().getDimension(R.dimen.t));*/
        //设置背景
        button.setBackgroundResource(R.drawable.btn_category_selector);
        //去掉左侧默认的圆点
        button.setButtonDrawable(android.R.color.transparent);
        //设置不同状态下文字颜色，通过ColorStateList，对应的selector放在res/color文件目录中，否则没有效果
        button.setTextColor(getResources().getColor(R.color.black));
        button.setGravity(Gravity.CENTER);
        button.setText(text);

        return button;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(mListener != null){
            mListener.click(group, checkedId);
        }
    }

    /**指定监听器*/
    public void setOnClickCategoryListener(OnClickCategoryListener l){
        mListener = l;
    }
    private OnClickCategoryListener mListener;
    /**回掉接口*/
    public interface OnClickCategoryListener{
        /**点击事件发生*/
        public void click(RadioGroup group, int checkedId);
    }
}
