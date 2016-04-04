package com.zyx.ad.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.bean.OrderData;
import com.zyx.bean.ProductData;
import com.zyx.utils.Parse;

import java.util.List;


/**
 * Created by zyx on 2016/2/18.
 */
public class ListProductAdapter extends BaseAdapter {

    private Context context;
    private List<ProductData> list;
    private MyClickListener mListener;
    private BitmapUtils bitmapUtils;
    private String bt_text;

    public ListProductAdapter(Context context, List<ProductData> list, String text,
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder.tv_PName = (TextView) convertView.findViewById(R.id.tv_product_name);
            holder.bt_stage = (Button) convertView.findViewById(R.id.bt_stage);
            holder.tv_describe1 = (TextView) convertView.findViewById(R.id.tv_describe1);
            holder.tv_describe2 = (TextView) convertView.findViewById(R.id.tv_describe2);
            holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            holder.tv_qprice = (TextView) convertView.findViewById(R.id.tv_qprice);
            holder.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
            //holder.tv_category =(TextView) convertView.findViewById(R.id.tv_categoryId);
            //ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductData info = list.get(position);
//		holder.iv.setImageResource(CLASS_IMG[position]);
        //bitmapUtils.display(holder.iv_product, MyURL.gv_content_img+info.getPic());
        //bitmapUtils.display(holder.iv_product, info.getImageUrls());
        bitmapUtils.display(holder.iv_product, info.getImageUrls());
        holder.tv_PName.setText(info.getProductName());
        holder.tv_describe1.setText(String.valueOf(info.getPrductDescription()));
        holder.tv_describe2.setText("项目：门票，住宿");
        double aa = Parse.getInstance().parseDouble(info.getQuotoPrice() / 3,"#,##");
        holder.tv_money.setText("339.00");
        //holder.tv_qprice.setText(String.valueOf(Parse.getInstance().parseDouble(info.getQuotoPrice(), "#,##")));
        holder.tv_qprice.setText("998.00");

        //final int p = position;
        holder.bt_stage.setOnClickListener(mListener);
        holder.bt_stage.setText(bt_text);
        holder.bt_stage.setTag(position);
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

        private ImageView iv_product;

        private TextView tv_PName;

        private TextView tv_describe1;
        private TextView tv_describe2;

        private TextView tv_money;
        private TextView tv_qprice;
        private Button bt_stage;
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
