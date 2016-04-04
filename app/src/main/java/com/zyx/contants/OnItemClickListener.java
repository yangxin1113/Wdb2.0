package com.zyx.contants;

import android.view.View;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zyx on 2016/2/23.
 */
public interface OnItemClickListener {
    /**
     * 接口方法
     *
     * @param v
     *            当前view
     * @param list
     *            当前适配器中的集合数据
     * @param position
     *            当前点击的位置
     */
    public void itemClick(View v, ArrayList<Map<String, Object>> list,
                          int position);
}
