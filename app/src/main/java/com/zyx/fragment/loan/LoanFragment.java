package com.zyx.fragment.loan;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zyx.R;
import com.zyx.base.BaseFragment;
import com.zyx.fragment.life.LifeFragment;
import com.zyx.utils.Parse;
import com.zyx.widget.MyTitleBar;
import com.zyx.widget.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyx on 2016/2/15.
 */
public class LoanFragment extends BaseFragment {

    /**
     * 控件相关
     */
    private MyTitleBar mtb_title;// 标题栏
    private Button bt_next;
    private EditText ed_money;
    private TextView tv_repayment;
    private TextView mMonth;
    private LinearLayout ll_drop;
    private ImageView iv_check;// 勾选框

    private String[] name_month;
    private List<String> mListType = new ArrayList<String>();  //类型列表
    //设置PopWindow
    private SpinerPopWindow<String> mSpinerPopWindow;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_drop:
                showSpinWindow(mSpinerPopWindow);
                break;
            case R.id.bt_next:

                if (isValid(ed_money.getText().toString(), mMonth.getText().toString())) {
                    utils.showToast(getContext(), "fsafsadf");

                }
                break;

            case R.id.iv_check:
                if (getParse().parseBool1(iv_check.getTag())) {
                    iv_check.setTag(false);
                    iv_check.setImageResource(R.mipmap.check_img_false);
                } else {
                    iv_check.setTag(true);
                    iv_check.setImageResource(R.mipmap.check_img_true);
                }
                break;

        }

    }

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_loan_index);
    }

    @Override
    protected void initView(View rootview) {
        mtb_title = (MyTitleBar) rootview.findViewById(R.id.mtb_title);
        mMonth = (TextView) rootview.findViewById(R.id.tv_month);
        ll_drop = (LinearLayout) rootview.findViewById(R.id.ll_drop);
        bt_next = (Button) rootview.findViewById(R.id.bt_next);
        ed_money = (EditText) rootview.findViewById(R.id.ed_money);
        tv_repayment = (TextView) rootview.findViewById(R.id.tv_repayment);
        iv_check = (ImageView) rootview.findViewById(R.id.iv_check);
    }

    @Override
    protected void setViewData() {
        mtb_title.setText(getString(R.string.loan_bottom_text));
        mtb_title.setLeftVisibility(View.INVISIBLE);
        initData();

        mSpinerPopWindow = new SpinerPopWindow<String>(getActivity(), mListType, itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);
    }

    @Override
    protected void initEvent() {
        ll_drop.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        iv_check.setOnClickListener(this);
        //ed_money.addTextChangedListener(watcher);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void handlerMessage(Message msg) {

    }


    private void showSpinWindow(SpinerPopWindow s) {
        Log.e("", "showSpinWindow");
        s.setWidth(mMonth.getWidth());
        s.showAsDropDown(mMonth, 0, -200);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        //初始化数据

        name_month = getResources().getStringArray(R.array.s_stage_s);

        for (int i = 0; i < name_month.length; i++) {
            mListType.add(name_month[i]);
        }
    }

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            /*setTextImage(R.drawable.icon_down);*/
        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            mMonth.setText(mListType.get(position));

            tv_repayment.setText(updaterepay(ed_money.getText().toString(), mListType.get(position)));
            //Toast.makeText(getContext(), "点击了:" + mListType.get(position), Toast.LENGTH_LONG).show();
        }
    };


    private String updaterepay(String money, String month) {
        Double Money = Parse.getInstance().parseDouble(money);
        Double Month = Parse.getInstance().parseDouble(month);
        String tv_money = String.valueOf(Parse.getInstance().parseDouble(String.valueOf((Money / Month)), "#.##"));
        return tv_money;
    }

    private boolean isValid(String money, String month) {
        boolean isNext = true;
        Double Money = Parse.getInstance().parseDouble(money);
        Double Month = Parse.getInstance().parseDouble(month);
        String tv_money = String.valueOf(Parse.getInstance().parseDouble(String.valueOf((Money / Month)), "#.##"));
        tv_repayment.setText(tv_money);
        if (Money != null) {
            double value = Parse.getInstance().parseDouble(Money / 100);
            if (value % 1.0 != 0.0) {
                utils.showToast(getContext(), "请输入100的倍数的贷款金额");
                isNext =  false;
            }
        } else if (!getParse().parseBool1(iv_check.getTag())) {

            utils.showToast(getContext(),
                    getString(R.string.please_agree_agreement_zh_));
            isNext = false;
        } else if(Money == null) {
            utils.showToast(getContext(), "请输入贷款金额");
            isNext =  false;
        }
        return isNext;
    }


    /*private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            updaterepay(ed_money.getText().toString(), mMonth.getText().toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            updaterepay(ed_money.getText().toString(), mMonth.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            updaterepay(ed_money.getText().toString(), mMonth.getText().toString());

        }
    };*/
}
