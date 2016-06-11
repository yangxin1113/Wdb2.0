package com.zyx.fragment.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zyx.R;
import com.zyx.ad.Selectschool.ProvinceAdapter;
import com.zyx.ad.Selectschool.SchoolAdapter;
import com.zyx.addimg.AddImgFragmentActivity01;
import com.zyx.application.MyApplication;
import com.zyx.base.MyBaseFragmentActivity;
import com.zyx.bean.Province;
import com.zyx.bean.ProvinceList;
import com.zyx.bean.School;
import com.zyx.bean.SchoolList;
import com.zyx.contants.Contants;
import com.zyx.contants.UpdateUserInfo;
import com.zyx.dialog.AddImageDialog;
import com.zyx.thread.PostDataThread;
import com.zyx.thread.PostFileThread;
import com.zyx.utils.GsonRequest;
import com.zyx.utils.LogUtil;
import com.zyx.utils.MyUtils;
import com.zyx.utils.Resolve;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyx on 2016/2/23.
 */
public class FragmentMeAccount extends MyBaseFragmentActivity implements UpdateUserInfo.onUpdateUserInfo{

    /** 控件相关 */
    private View view_status_bar;// 状态栏
    private View view_navigation_bar;// 虚拟按键
    private ImageView iv_left;// 返回按钮
    private LinearLayout ll_head;
    private ImageView iv_head;
    private LinearLayout ll_nick;
    private TextView tv_nick;
    private LinearLayout ll_pen;
    private TextView tv_pen;
    private LinearLayout ll_phone;
    private TextView tv_phone;
    private LinearLayout ll_idcard;
    private TextView tv_idcard;
    private LinearLayout ll_school;
    private TextView tv_school;
    private LinearLayout ll_gradate;
    private TextView tv_gradate;
    private LinearLayout ll_educate;
    private TextView tv_educate;
    private LinearLayout ll_addr;

    /** 数据相关 */
    private Map<String, Object> user;// 用户资料
    private final int maxImg = 1;// 最大选择数
    private int width;// 屏幕宽
    private ArrayList<Map<String, Object>> photoList;// 选中图片集合
    private String imageFile;// 裁剪后的头像路径
    private DisplayImageOptions userHeadOption;// 图片属性

    /** Dialog */
    private ProgressDialog pd;// 进度条



    /**大学选择*/
    /**
     * popView相关
     **/
    private View parent;
    private ListView mProvinceListView;
    private ListView mSchoolListView;
    private TextView mTitle;
    private PopupWindow mPopWindow;
    /**
     * Volley相关
     **/
    private RequestQueue mRequestQueue;
    /**
     * Adapter相关
     **/
    private ProvinceAdapter mProvinceAdapter;
    private SchoolAdapter mSchoolAdapter;
    private String provinceId;





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.ll_head:
                /*Intent i = new Intent(FragmentMeAccount.this, SettingsUserInfoFragmentActivity1.class);
                startActivity(i);*/
                showDialog();
                break;
            case R.id.ll_nick:
                Intent i = new Intent(FragmentMeAccount.this, FragmentUpdataItem.class);
                i.putExtra("msg","修改昵称");
                startActivityForResult(i, 2);
                break;
            case R.id.ll_custpen:
                i = new Intent(FragmentMeAccount.this, FragmentUpdataItem.class);
                i.putExtra("msg","修改签名");
                startActivityForResult(i,3);
                break;
            case R.id.ll_idcard:
                i = new Intent(FragmentMeAccount.this, FragmentUpdataItem.class);
                startActivityForResult(i,4);
                break;
            case R.id.ll_school:
                showPopWindow();
                break;
            case R.id.ll_gradate:
                i = new Intent(FragmentMeAccount.this, FragmentUpdataItem.class);
                startActivityForResult(i,6);
                break;
            case R.id.ll_educate:
                i = new Intent(FragmentMeAccount.this, FragmentUpdataItem.class);
                i.putExtra("msg","修改学历");
                startActivityForResult(i,7);
                break;
            case R.id.ll_custaddr:
                i = new Intent(FragmentMeAccount.this, FragmentUpdataItem.class);
                i.putExtra("msg","修改地址");
                startActivityForResult(i,8);
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
        switch (msg.what) {
            case 0x4000:
                // 提交文件成功
                Map<String, Map<String, Object>> mapXML1 = (Map<String, Map<String, Object>>) msg.obj;
                Log.w("zyx111", String.valueOf(mapXML1.get("code")));

                if (mapXML1 != null && mapXML1.size() > 0) {

                    utils.showToast(getApplicationContext(), mapXML1.toString());
                    if ("200".equals(getParse().isNull(
                            mapXML1.get("code")))) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("CustomerId", ((MyApplication) getApplication()).getUser().get("CustomerId").toString());
                        startRunnable(new PostDataThread(Contants.USER_RETURN, map,
                                handler, 0x4100, 0x4109));
                        LogUtil.i("zyx", "2222222222222");
                        break;
                    } else {
                        showSubmitImgDialog(getParse().isNull(
                                mapXML1.get("msg")));
                        break;
                    }
                }
            case 0x4009:
                // 提交文件失败
                showSubmitImgDialog("上传头像失败，是否重试？");
                break;

            case 0x4100:
                // 修改头像成功
                Map<String, Map<String, Object>> mapXML2 = (Map<String, Map<String, Object>>) msg.obj;
                Log.w("zyx",mapXML2.toString());

                if (mapXML2 != null && mapXML2.size() > 0) {
                    if ("200".equals(mapXML2.get("code"))) {
                        ArrayList<Map<String, Object>> userinfList = Resolve
                                .getInstance().getList(mapXML2, "userInfo");

                        if (userinfList != null && userinfList.size() > 0) {
                            Map<String, Object> user = userinfList.get(0);
                            if (user.get("CustomerId") != null) {
                                ((MyApplication) getApplication()).setUser(user);
                                utils.showToast(getApplicationContext(), "头像修改成功！");
                                setUserInfo(user);
                                break;
                            }
                        }
                    } else {
                        showSubmitImgDialog(getParse().isNull(
                                mapXML2.get("msg")));
                        break;
                    }
                }
            case 0x4109:
                // 修改头像失败
                showSubmitImgDialog("上传头像失败，是否重试？");
                break;
            default:
                break;
        }
        if (pd.isShowing())
            pd.dismiss();

    }

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.fragment_me_account);
        initPopView();
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

        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        ll_nick = (LinearLayout) findViewById(R.id.ll_nick);
        tv_nick = (TextView) findViewById(R.id.tv_custNick);
        ll_pen = (LinearLayout) findViewById(R.id.ll_custpen);
        tv_pen = (TextView) findViewById(R.id.tv_cutpen);
        ll_phone = (LinearLayout) findViewById(R.id.ll_custphone);
        tv_phone = (TextView) findViewById(R.id.tv_custphonenum);
        ll_idcard = (LinearLayout) findViewById(R.id.ll_idcard);
        tv_idcard = (TextView) findViewById(R.id.tv_idcard);
        ll_school = (LinearLayout) findViewById(R.id.ll_school);
        tv_school = (TextView) findViewById(R.id.tv_school);
        ll_gradate = (LinearLayout) findViewById(R.id.ll_gradate);
        tv_gradate = (TextView) findViewById(R.id.tv_gradate);
        ll_educate = (LinearLayout) findViewById(R.id.ll_educate);
        tv_educate = (TextView) findViewById(R.id.tv_educate);
        ll_addr = (LinearLayout) findViewById(R.id.ll_custaddr);
               /* Typeface face = Typeface.createFromAsset (getAssets() , "fonts/huasong.ttf");
        tv_head.setTypeface (face);*/

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


    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        ll_head.setOnClickListener(this);
        ll_nick.setOnClickListener(this);
        ll_pen.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        ll_idcard.setOnClickListener(this);
        ll_school.setOnClickListener(this);
        ll_gradate.setOnClickListener(this);
        ll_gradate.setOnClickListener(this);
        ll_educate.setOnClickListener(this);
        ll_addr.setOnClickListener(this);

    }

    @Override
    protected void getData() {

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
           // overridePendingTransition(R.anim.no_animation, R.anim.bottom_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setUserInfo(((MyApplication) getApplication()).getUser());

        UpdateUserInfo.getInstance().addOnUpdateUserInfo("my", this);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        UpdateUserInfo.getInstance().remove("my");
    }


    /**
     * 设置用户资料
     *
     * @param user
     */
    private void setUserInfo(Map<String, Object> user) {
        if (user != null) {
            String headimg = getParse().isNull(user.get("CustHead"));
            if (headimg.equals("")) {
                iv_head.setImageResource(R.mipmap.img_head_false);
            } else if (headimg.contains("http://")) {
                imageLoader.displayImage(headimg, iv_head, userHeadOption);

            } else {
                imageLoader.displayImage(getString(R.string.ip) + headimg,
                        iv_head, userHeadOption);
            }
            tv_nick.setText(getParse().isNull(user.get("CustNickName")));
            tv_pen.setText("做最好的自己,GOGOGO!");
            tv_phone.setText(getParse().isNull(user.get("CustPhoneNum")));
            tv_idcard.setText(getParse().isNull(user.get("CustName")));
            tv_school.setText(getParse().isNull(user.get("CustUniversity")));
            tv_gradate.setText(getParse().isNull(user.get("CustInUniversity")));
            tv_educate.setText(getParse().isNull(user.get("CustEducation")));

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

            Map<String, String> map = new HashMap<String, String>();
            map.put("CustomerId", ((MyApplication) getApplication()).getUser().get("CustomerId").toString());
            ArrayList<String> fileList = new ArrayList<String>();
            fileList.add(imageFile);
            startRunnable(new PostFileThread(Contants.SUBMIT_FILE
                    , map, fileList, 0x4000, 0x4009, -1,
                    "jpeg", handler));
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

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 1) {
            // 相册返回
            if (arg1 == RESULT_OK) {
                if (arg2.getExtras() == null) {
                    return;
                }
                photoList = (ArrayList<Map<String, Object>>) arg2
                        .getSerializableExtra("selectedList");
                if (photoList != null && photoList.size() > 0) {
                    String outFile = getParse().isNull(
                            photoList.get(0).get("imagePath")).replace(
                            ".",
                            MyUtils.getInstance().MD5(
                                    String.valueOf(System.currentTimeMillis()))
                                    + ".");
                    startPhotoZoom(
                            Uri.fromFile(new File(getParse().isNull(
                                    photoList.get(0).get("imagePath")))),
                            Uri.fromFile(new File(outFile)));
                }
            }
        } else if (arg0 == 2) {
            // 裁剪图片返回
            if (arg1 == RESULT_OK) {
                File file = null;
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    file = new File(getExternalCacheDir().getPath()
                            + "/images_data");
                } else {
                    file = new File(getCacheDir().getPath() + "/images_data");
                }
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(file.getPath()
                        + "/"
                        + MyUtils.getInstance().MD5(
                        String.valueOf(System.currentTimeMillis()))
                        + ".jpeg");
                Bundle bundle = arg2.getExtras();
                Bitmap bitmap = null;
                if (bundle != null) {
                    bitmap = bundle.getParcelable("data");
                }

                if (bitmap != null) {// 保存图片
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file.getPath());
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        imageFile = file.getPath();
                        bitmap.recycle();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                        utils.showToast(getApplicationContext(), "保存图片失败");
                        imageFile = null;
                    }
                    submitImage();
                }

            }
        } else if (arg0 == 3) {
            // 系统相机返回
            if (MyApplication.fileUri != null) {
                BitmapFactory.Options op = new BitmapFactory.Options();
                op.inSampleSize = 16;
                Bitmap bm = BitmapFactory.decodeFile(
                        MyApplication.fileUri.getPath(), op);
                if (bm == null) {
                    return;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("isCheck", true);
                map.put("imagePath", MyApplication.fileUri);
                photoList.add(0, map);
                if (photoList != null && photoList.size() > 0) {
                    String outFile = getParse().isNull(
                            photoList.get(0).get("imagePath"))
                            .replace(
                                    ".",
                                    utils.MD5(String.valueOf(System
                                            .currentTimeMillis())) + ".");
                    startPhotoZoom(
                            Uri.fromFile(new File(getParse().isNull(
                                    photoList.get(0).get("imagePath")))),
                            Uri.fromFile(new File(outFile)));
                }
            }
        } else if (arg0 == 10) {// 修改其它资料成功返回
            if (arg1 == RESULT_OK) {

            }
        }
    }

    /**
     * 修改个人信息
     */
    @Override
    public void notifyUserInfo(Map<String, Object> userInfo) {

    }



    private void initPopView() {
        parent = this.getWindow().getDecorView();
        View popView = View.inflate(this, R.layout.view_select_province_list, null);
        mTitle = (TextView) popView.findViewById(R.id.list_title);
        mProvinceListView = (ListView) popView.findViewById(R.id.province);
        mSchoolListView = (ListView) popView.findViewById(R.id.school);
        mProvinceListView.setOnItemClickListener(itemListener);
        mSchoolListView.setOnItemClickListener(itemListener);

        mProvinceAdapter = new ProvinceAdapter(this);
        mProvinceListView.setAdapter(mProvinceAdapter);
        mSchoolAdapter = new SchoolAdapter(this);
        mSchoolListView.setAdapter(mSchoolAdapter);

        int width = getResources().getDisplayMetrics().widthPixels * 3 / 4;
        int height = getResources().getDisplayMetrics().heightPixels * 3 / 5;
        mPopWindow = new PopupWindow(popView, width, height);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.setFocusable(true);
        mPopWindow.setTouchable(true);
        mPopWindow.setOutsideTouchable(true);//允许在外侧点击取消

        loadProvince();

        mPopWindow.setOnDismissListener(listener);
    }

    private void showPopWindow() {
        mPopWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    private void loadProvince() {
        mRequestQueue = Volley.newRequestQueue(this);
        GsonRequest<Province> request = new GsonRequest<Province>(Request.Method.POST, Contants.PROVINCE_URL,
                Province.class, new Response.Listener<Province>() {
            @Override
            public void onResponse(Province response) {
                if (response.getData() != null && response.getError_code() == 0) {
                    mProvinceAdapter.setList(response.getData());
                    mProvinceAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }, this);
        mRequestQueue.add(request);
    }

    private void loadSchool() {
        mRequestQueue = Volley.newRequestQueue(this);
        GsonRequest<School> request = new GsonRequest<>(Request.Method.POST, Contants.SCHOOL_URL + provinceId, School.class,
                new Response.Listener<School>() {
                    @Override
                    public void onResponse(School response) {
                        if (response.getData() != null && response.getError_code() == 0){
                            mSchoolAdapter.setList(response.getData());
                            mSchoolAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, this);
        mRequestQueue.add(request);
    }

    /**
     * ListView Item点击事件
     */
    AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mProvinceListView) {
                ProvinceList provinceName = (ProvinceList) mProvinceListView.getItemAtPosition(position);
                provinceId = provinceName.getProvince_id();
                mTitle.setText("选择学校");
                mProvinceListView.setVisibility(View.GONE);
                mSchoolListView.setVisibility(View.VISIBLE);
                loadSchool();
            } else if (parent == mSchoolListView) {
                SchoolList schoolName = (SchoolList) mSchoolListView.getItemAtPosition(position);
                tv_school.setText(schoolName.getSchool_name());
                mPopWindow.dismiss();
            }
        }
    };

    /**
     * popWindow消失监听事件
     */
    PopupWindow.OnDismissListener listener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            mTitle.setText("选择地区");
            mProvinceListView.setVisibility(View.VISIBLE);
            mSchoolAdapter.setList(new ArrayList<SchoolList>());
            mSchoolAdapter.notifyDataSetChanged();
            mSchoolListView.setVisibility(View.GONE);
        }
    };
}
