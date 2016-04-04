package com.zyx.ad.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.bean.OrderData;

import java.util.List;


/**
 * Created by zyx on 2016/2/18.
 */
public class ListOrderAdapter extends BaseAdapter {

    private Context context;
    private List<OrderData> list;
    private MyClickListener mListener;
    private BitmapUtils bitmapUtils;
    private String bt_text;

    public ListOrderAdapter(Context context, List<OrderData> list,String text,
                            MyClickListener listener) {
        this.context = context;
        this.list = list;
        this.bitmapUtils = new BitmapUtils(context);
        this.bt_text = text;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, null);
            holder.tv_PName = (TextView) convertView.findViewById(R.id.tv_product_name);
            holder.tv_stage = (TextView) convertView.findViewById(R.id.tv_stage);
            holder.tv_repayment = (TextView) convertView.findViewById(R.id.tv_mprice);
            holder.bt_detail = (Button) convertView.findViewById(R.id.bt_detail);
            //holder.tv_category =(TextView) convertView.findViewById(R.id.tv_categoryId);
            //ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderData info = list.get(position);
//		holder.iv.setImageResource(CLASS_IMG[position]);
        //bitmapUtils.display(holder.iv_product, MyURL.gv_content_img+info.getPic());
        //bitmapUtils.display(holder.iv_product, info.getImageUrls());
        holder.tv_stage.setText(String.valueOf(info.getTimes()));
        holder.tv_PName.setText(info.getProductname());
        holder.tv_repayment.setText(String.valueOf(info.getRepayment()));
        //final int p = position;
        holder.bt_detail.setOnClickListener(mListener);
        holder.bt_detail.setText(bt_text);
        holder.bt_detail.setTag(position);
       /* holder.bt_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "ordernumber" + list.get(p).getProductname(),Toast.LENGTH_LONG);
                Intent i = new Intent(context, DetailOrderActivity.class);
                i.putExtra("OrderNumber", list.get(p).getOrdernumber());
                context.startActivity(i);
                //overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });*/

        //holder.tv_category.setText(String.valueOf(info.getCategoryId()));
        return convertView;
    }

    static class ViewHolder {

        //private ImageView iv_product;

        private TextView tv_PName;

        private TextView tv_stage;

        private TextView tv_repayment;
        private Button bt_detail;
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
