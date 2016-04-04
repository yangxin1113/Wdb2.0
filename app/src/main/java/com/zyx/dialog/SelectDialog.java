package com.zyx.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by zyx on 2016/3/10.
 */
public class SelectDialog extends AlertDialog {

    public SelectDialog(Context context, int theme) {
        super(context, theme);
    }

    public SelectDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.slt_cnt_type);
    }
}