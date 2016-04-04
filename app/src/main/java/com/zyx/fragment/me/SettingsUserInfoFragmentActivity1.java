package com.zyx.fragment.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zyx.R;
import com.zyx.addimg.AddImgFragmentActivity01;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.contants.Contants;
import com.zyx.contants.UpdateUserInfo;
import com.zyx.dialog.AddImageDialog;
import com.zyx.thread.GetDataThread;
import com.zyx.utils.MyMessageQueue;
import com.zyx.widget.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zyx on 2016/2/23.
 */
public class SettingsUserInfoFragmentActivity1 extends MyBaseFragmentActivity implements UpdateUserInfo.onUpdateUserInfo {


    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键、键盘高度
    private ImageView iv_left;// 返回按钮
    private TextView tv_save;// 保存
    private CircleImageView iv_head;//头像按钮
    private LinearLayout ll_modify_head;//修改头像按钮
    private RelativeLayout rl_show_head;// 显示大图布局
    private ImageView iv_show_head;// 显示大图控件

    private EditText et_custNick;
    private EditText et_phoneNum;
    private EditText et_idCard;
    private EditText et_custName;

    /** 数据相关 */
    private Map<String, Object> user;// 用户资料
    private final int maxImg = 1;// 最大选择数
    private int width;// 屏幕宽
    private ArrayList<Map<String, Object>> photoList;// 选中图片集合
    private String imageFile;// 裁剪后的头像路径
    /*private String lockID;// lockID
    private String licenseID;// licenseID*/
    private DisplayImageOptions userHeadOption;// 图片属性

    /** Dialog */
    private ProgressDialog pd;// 进度条



    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;

            case R.id.iv_head:
                rl_show_head.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.my_alpha_0_1_200);
                animation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        rl_show_head.clearAnimation();
                    }
                });
                rl_show_head.startAnimation(animation);
                break;

            case R.id.ll_modify_head:
                showDialog();
                break;

            case R.id.bt_login_out:
                ((MyApplication) getApplication()).setUser(null);
                startHomeActivity();
                break;

            case R.id.rl_show_head:
                animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.my_alpha_1_0_200);
                animation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        rl_show_head.clearAnimation();
                        rl_show_head.setVisibility(View.GONE);
                    }
                });
                v.startAnimation(animation);
                break;

            default:
                break;
        }

    }


    @Override
    protected boolean isUserMapNull() {
        return false;
    }

    @Override
    protected boolean isToken() {
        return false;
    }

    @Override
    protected void handlerMessage(Message msg) {

    }

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.fragmnet_activity_set_userinfo1);
        width = getWindowManager().getDefaultDisplay().getWidth();
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        userHeadOption = ((MyApplication) getApplication())
                .getUserHeadOptions();

    }

    @Override
    protected void initView() {

        view_status_bar = (View) findViewById(R.id.view_status_bar);
        view_navigation_bar = (View) findViewById(R.id.view_navigation_bar);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_save = (TextView) findViewById(R.id.tv_save);
        rl_show_head = (RelativeLayout) findViewById(R.id.rl_show_head);
        iv_show_head = (ImageView) findViewById(R.id.iv_show_head);
        ll_modify_head = (LinearLayout) findViewById(R.id.ll_modify_head);
        et_custNick = (EditText)findViewById(R.id.et_custNick);
        et_custName = (EditText)findViewById(R.id.et_custName);
        et_idCard = (EditText)findViewById(R.id.et_custIdCard);
        et_phoneNum = (EditText)findViewById(R.id.et_custPhoneNum);

    }

    @Override
    protected View getStatusBarView() {
        return view_status_bar;
    }

    @Override
    protected View getBottomView() {
        return view_navigation_bar;
    }

    @Override
    protected void setViewData() {

        view_status_bar.setBackgroundColor(getResources().getColor(
                R.color.main_color));
        ViewGroup.LayoutParams lp = iv_show_head.getLayoutParams();
        lp.width = width - utils.dp2px(getApplicationContext(), 20);
        lp.height = lp.width;
        iv_show_head.setLayoutParams(lp);



    }

    @Override
    protected void initEvent() {

        iv_left.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        rl_show_head.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        ll_modify_head.setOnClickListener(this);

    }

    @Override
    protected void getData() {

    }


    /**
     * 设置用户资料
     *
     * @param user
     */
    private void setUserInfo(Map<String, Object> user) {
        if (user != null) {
            String headimg = getParse().isNull(user.get("headimg"));
            if (headimg.equals("")) {
                iv_head.setImageResource(R.mipmap.img_head_false);
                iv_show_head.setImageResource(R.mipmap.img_head_false);
            } else if (headimg.contains("http://")) {
                imageLoader.displayImage(headimg, iv_head, userHeadOption);
                imageLoader.displayImage(headimg, iv_show_head, userHeadOption);
            } else {
                imageLoader.displayImage(getString(R.string.ip) + headimg,
                        iv_head, userHeadOption);
                imageLoader.displayImage(getString(R.string.ip) + headimg,
                        iv_show_head, userHeadOption);
            }
            et_custNick.setText(getParse().isNull(user.get("CustNick")));
            String phoneNo = getParse().isNull(user.get("PhoneNum"));
            if (phoneNo.length() == 11) {
                String[] phoneNos = new String[2];
                phoneNos[0] = phoneNo.substring(0, 3);
                phoneNos[1] = phoneNo.substring(phoneNo.length() - 4,
                        phoneNo.length());
                et_phoneNum.setText(phoneNos[0] + "****" + phoneNos[1]);
            }
            et_idCard.setText(getParse().isNull(user.get("CustIdCard")));
            et_custName.setText(getParse().isNull(user.get("CustName")));
        }
    }


    /**
     * 提交头像
     */
    private void submitImage() {
        if (imageFile != null) {
            imageLoader.displayImage("file://" + imageFile, iv_head,
                    ((MyApplication) getApplication()).getUserHeadOptions());
            pd.setMessage("上传头像中...");
            pd.show();
            startRunnable(new GetDataThread(getApplicationContext(),
                    Contants.UPDATE_USER_INFO
                            + utils.getDeviceId(getApplicationContext())
                            + "&AppName=52", handler, MyMessageQueue.OK,
                    MyMessageQueue.DATA_EXCEPTION,
                    MyMessageQueue.DATA_EXCEPTION, false));
        }
    }


    /**
     * 显示选择照片dialog
     */
    private void showDialog() {
        AddImageDialog dialog = new AddImageDialog(this, R.style.Theme_dialog,
                width, -1);
        dialog.setCancelable(true);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setOnDialogClickListener(new MyOnDialogClickListener());
        dialog.show();
    }


    /**
     * 实现dialog中的点击事件接口
     */
    class MyOnDialogClickListener implements AddImageDialog.OnDialogClickListener {

        @Override
        public void dialogClick(Dialog dialog, View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.tv_in_the_to_xiangce:
                    dialog.dismiss();
                    if (photoList == null)
                        photoList = new ArrayList<Map<String, Object>>();
                    else
                        photoList.clear();
                    Intent in = new Intent(getApplicationContext(),
                            AddImgFragmentActivity01.class);
                    in.putExtra("selectedList", photoList);
                    in.putExtra("maxImg", maxImg);
                    startActivityForResult(in, 1);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    break;

                case R.id.tv_in_the_to_xiangji:
                    dialog.dismiss();
                    if (photoList == null)
                        photoList = new ArrayList<Map<String, Object>>();
                    else
                        photoList.clear();
                    startXiangJi();
                    break;

                case R.id.tv_cancel:
                    dialog.dismiss();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 启动系统相机
     */
    private void startXiangJi() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            MyApplication.fileUri = null;
            MyApplication.fileUri = new File(Environment
                    .getExternalStorageDirectory().getPath() + "/KEDLL_images");
            if (!MyApplication.fileUri.exists())
                MyApplication.fileUri.mkdirs();
            MyApplication.fileUri = new File(MyApplication.fileUri.getPath()
                    + "/" + System.currentTimeMillis() + ".jpeg");
            Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            in.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(MyApplication.fileUri));
            startActivityForResult(in, 3);
        } else {
            utils.showToast(getApplicationContext(), "该设备不支持");
        }
    }

    /**
     * 跳转到裁剪图片
     *
     * @param uri
     *            读取路径
     * @param outUri
     *            裁剪后输出路径
     */
    public void startPhotoZoom(Uri uri, Uri outUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 2);
    }

    /**
     * 是否重新提交头像
     */
    private void showSubmitImgDialog(String msg) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("提示");
        ab.setMessage(msg);
        ab.setCancelable(false);
        ab.setNegativeButton("重  试", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                submitImage();
            }
        });
        ab.setPositiveButton("取  消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                setUserInfo(user);
            }
        });
        ab.show();
    }

    /**
     * 修改个人信息
     */
    @Override
    public void notifyUserInfo(Map<String, Object> userInfo) {

    }
}
