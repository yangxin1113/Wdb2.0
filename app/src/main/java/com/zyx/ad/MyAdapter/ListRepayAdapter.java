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
 * 单笔交易的还款记录
 */
public class ListRepayAdapter extends BaseAdapter {

    private Context context;
    private List<OrderData> list;
    /*private MyClickListener mListener;
    private BitmapUtils bitmapUtils;
    private String bt_text;*/

    public ListRepayAdapter(Context context, List<OrderData> list) {
        this.context = context;
        this.list = list;
        //this.bitmapUtils = new BitmapUtils(context);


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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_reapy, null);
            holder.tv_paydate = (TextView) convertView.findViewById(R.id.tv_paydate);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_repayment = (TextView) convertView.findViewById(R.id.tv_repayment);
            holder.tv_payway = (TextView) convertView.findViewById(R.id.tv_payway);
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
        if(info.getTime() == 0){
            holder.tv_time.setText("首付");
            holder.tv_paydate.setText(info.getPaydate().replace(".0",""));
            holder.tv_repayment.setText(String.valueOf(info.getRepayment()));
            holder.tv_payway.setText("钱包还款");
        }else{
            holder.tv_time.setText("第"+String.valueOf(info.getTime())+"次");
            holder.tv_paydate.setText(info.getPaydate().replace(".0",""));
            holder.tv_repayment.setText(String.valueOf(info.getRepayment()));
            holder.tv_payway.setText("钱包还款");
        }

        //final int p = position;
        /*holder.bt_detail.setOnClickListener(mListener);
        holder.bt_detail.setText(bt_text);
        holder.bt_detail.setTag(position);*/
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

        private TextView tv_time;

        private TextView tv_paydate;

        private TextView tv_repayment;
        private TextView tv_payway;
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
