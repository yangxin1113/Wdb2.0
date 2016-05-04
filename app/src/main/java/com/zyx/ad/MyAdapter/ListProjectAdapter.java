package com.zyx.ad.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.bean.ProductData;
import com.zyx.bean.ProjectData;
import com.zyx.utils.Parse;

import java.util.List;


/**
 * Created by zyx on 2016/2/18.
 *
 *中筹项目
 */
public class ListProjectAdapter extends BaseAdapter {

    private Context context;
    private List<ProjectData> list;
    private BitmapUtils bitmapUtils;

    public ListProjectAdapter(Context context, List<ProjectData> list
                              ) {
        this.context = context;
        this.list = list;
        this.bitmapUtils = new BitmapUtils(context);
        this.bitmapUtils.configDefaultLoadingImage(R.mipmap.zhong);//默认背景图片
        this.bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhong);//加载失败图片
        //this.bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);//设置图片压缩类型
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_zhongchou, null);

            holder.iv_project = (ImageView) convertView.findViewById(R.id.iv_project);
            holder.tv_proName = (TextView) convertView.findViewById(R.id.tv_proName);
            holder.p_proBar = (ProgressBar) convertView.findViewById(R.id.p_proBar);
            holder.tv_pecent = (TextView) convertView.findViewById(R.id.tv_pecent);
            holder.tv_getMoney = (TextView) convertView.findViewById(R.id.tv_getMoney);
            holder.tv_getPer = (TextView) convertView.findViewById(R.id.tv_getPer);
            //holder.tv_category =(TextView) convertView.findViewById(R.id.tv_categoryId);
            //ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProjectData info = list.get(position);
//		holder.iv.setImageResource(CLASS_IMG[position]);
        //bitmapUtils.display(holder.iv_product, MyURL.gv_content_img+info.getPic());
        //bitmapUtils.display(holder.iv_product, info.getImageUrls());
        bitmapUtils.display(holder.iv_project, info.getProImg());
        holder.tv_proName.setText(info.getProName());
        float process = info.getProTotalMoney() - info.getProRestMoney();
        float process1 = process/info.getProTotalMoney();
        float process2 = process1*100;
        holder.p_proBar.setProgress((int)process2);
        holder.tv_pecent.setText(String.valueOf((int)process2)+"%");
        holder.tv_getMoney.setText(String.valueOf(info.getProTotalMoney() - info.getProRestMoney()));
        holder.tv_getPer.setText(String.valueOf(info.getPerCount()));
        return convertView;
    }

    static class ViewHolder {

        private ImageView iv_project;

        private TextView tv_proName;

        private ProgressBar p_proBar;
        private TextView tv_pecent;

        private TextView tv_getMoney;
        private TextView tv_getPer;
    }


    /**
     * 用于回调的抽象类
     *
     * @author Ivan Xu
     *         2014-11-26
     *//*
    public static abstract class MyClickListener implements OnClickListener {
        *//**
         * 基类的onClick方法
         *//*
        @Override
        public void onClick(View v) {
            myOnClick((Integer) v.getTag(), v);
        }

        public abstract void myOnClick(int position, View v);
    }*/

}
