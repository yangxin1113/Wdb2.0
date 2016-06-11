package com.zyx.ad.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.bean.JobItem;
import com.zyx.bean.ProductData;
import com.zyx.utils.Parse;

import java.util.List;


/**
 * Created by zyx on 2016/2/18.
 */
public class ListJobAdapter extends BaseAdapter {

    private Context context;
    private List<JobItem> list;
    private MyClickListener mListener;
    private BitmapUtils bitmapUtils;
    private String bt_text;

    public ListJobAdapter(Context context, List<JobItem> list, MyClickListener listener) {
        this.context = context;
        this.list = list;
        this.bitmapUtils = new BitmapUtils(context);
        this.mListener = listener;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.job_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            holder.salary = (TextView) convertView.findViewById(R.id.salary);
            holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.container = (RelativeLayout) convertView.findViewById(R.id.job_item);
            //holder.bt_take = (Button) convertView.findViewById(R.id.take);
            //holder.tv_category =(TextView) convertView.findViewById(R.id.tv_categoryId);
            //ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JobItem info = list.get(position);
//		holder.iv.setImageResource(CLASS_IMG[position]);
        //bitmapUtils.display(holder.iv_product, MyURL.gv_content_img+info.getPic());
        //bitmapUtils.display(holder.iv_product, info.getImageUrls());
        bitmapUtils.display(holder.iv_pic, info.getImgLink());
        holder.title.setText(info.getTitle());
        holder.time.setText(info.getDate());
        holder.address.setText(info.getAddress());
        holder.status.setText(info.getJobstatus());
        holder.salary.setText(info.getSalary());
        holder.container.setOnClickListener(mListener);
        holder.container.setTag(position);
        return convertView;
    }

    static class ViewHolder {

        private ImageView iv_pic;

        private TextView title;

        private TextView time;
        private TextView address;

        private TextView status;
        private TextView salary;
        private RelativeLayout container;

    }

    /**
     * 用于回调的抽象类
     *
     * @author Ivan Xu
     *         2014-11-26
     */
    public static abstract class MyClickListener implements OnClickListener {
        /**
         * 基类的onClick方法
         */
        @Override
        public void onClick(View v) {
            myOnClick((Integer) v.getTag(), v);
        }

        public abstract void myOnClick(int position, View v);
    }

}
