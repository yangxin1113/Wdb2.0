package com.zyx.widget;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zyx.R;
import com.zyx.ad.MyAdapter.SpinerAdapter;

import java.util.List;

/**
 * Created by zyx on 2016/3/17.
 */
public class SpinerPopWindow1 extends PopupWindow implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private SpinerAdapter mAdapter;
    private SpinerAdapter.IOnItemSelectListener mItemSelectListener;


    public SpinerPopWindow1(Context context)
    {
        super(context);

        mContext = context;
        init();
    }


    public void setItemListener(SpinerAdapter.IOnItemSelectListener listener){
        mItemSelectListener = listener;
    }

    public void setAdatper(SpinerAdapter adapter){
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }


    private void init()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);


        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
    }


    public void refreshData(List<String> list, int selIndex)
    {
        if (list != null && selIndex  != -1)
        {
            if (mAdapter != null){
                mAdapter.refreshData(list, selIndex);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        dismiss();
        if (mItemSelectListener != null){
            mItemSelectListener.onItemClick(arg0 ,pos);
        }
    }

}
