package com.zyx.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.BaseDialog;

/**
 * Created by zyx on 2016/2/23.
 */
public class AddImageDialog extends BaseDialog implements
        android.view.View.OnClickListener {

    /** 控件相关 */
    private TextView tv_in_the_to_xiangce;// 从手机相册选择
    private TextView tv_in_the_to_xiangji;// 拍照
    private TextView tv_cancel;// 取消

    /** 接口相关 */
    private OnDialogClickListener l;// 控件点击接口

    public AddImageDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    protected AddImageDialog(Context context, boolean cancelable,
                             OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
    }

    public AddImageDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
    }

    public AddImageDialog(Context context, int theme, int width, int height) {
        super(context, theme, width, height);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (l != null) {
            l.dialogClick(this, v);
        }
    }

    @Override
    protected int init() {
        // TODO Auto-generated method stub
        return R.layout.dialog_addimg;
    }

    @Override
    protected void initView(View view) {
        // TODO Auto-generated method stub
        tv_in_the_to_xiangce = (TextView) view
                .findViewById(R.id.tv_in_the_to_xiangce);
        tv_in_the_to_xiangji = (TextView) view
                .findViewById(R.id.tv_in_the_to_xiangji);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
    }

    @Override
    protected void setViewData() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initEvent() {
        // TODO Auto-generated method stub
        tv_in_the_to_xiangce.setOnClickListener(this);
        tv_in_the_to_xiangji.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    public void setOnDialogClickListener(OnDialogClickListener l) {
        this.l = l;
    }

    /**
     * 控件点击事件接口
     */
    public interface OnDialogClickListener {
        public void dialogClick(Dialog dialog, View v);
    }

}

