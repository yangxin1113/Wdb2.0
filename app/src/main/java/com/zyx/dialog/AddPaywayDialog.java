package com.zyx.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.base.BaseDialog;

/**
 * Created by zyx on 2016/2/23.
 */
public class AddPaywayDialog extends BaseDialog implements
        View.OnClickListener {

    /** 控件相关 */
    private LinearLayout ll_qian;// 钱包
    private LinearLayout ll_zhi;// 支付宝
    private LinearLayout ll_wei;//微信
    private TextView tv_cancel;// 取消

    /** 接口相关 */
    private OnDialogClickListener l;// 控件点击接口

    public AddPaywayDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    protected AddPaywayDialog(Context context, boolean cancelable,
                              OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
    }

    public AddPaywayDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
    }

    public AddPaywayDialog(Context context, int theme, int width, int height) {
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
        return R.layout.dialog_payway;
    }

    @Override
    protected void initView(View view) {
        // TODO Auto-generated method stub
        ll_qian = (LinearLayout) view
                .findViewById(R.id.ll_qian);
        ll_zhi = (LinearLayout) view
                .findViewById(R.id.ll_zhi);
        ll_wei = (LinearLayout) view
                .findViewById(R.id.ll_wei);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
    }

    @Override
    protected void setViewData() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initEvent() {
        // TODO Auto-generated method stub
        ll_qian.setOnClickListener(this);
        ll_zhi.setOnClickListener(this);
        ll_wei.setOnClickListener(this);
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

