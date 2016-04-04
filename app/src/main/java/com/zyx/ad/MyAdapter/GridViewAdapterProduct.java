package com.zyx.ad.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zyx.R;
import com.zyx.bean.ProductData;

import java.util.List;

/**
 * Created by zyx on 2016/2/18.
 */
public class GridViewAdapterProduct extends BaseAdapter {

    private Context context;
    private List<ProductData> list;

    private BitmapUtils bitmapUtils;
    public GridViewAdapterProduct(Context context, List<ProductData> list){
        this.context = context;
        this.list = list;
        this.bitmapUtils = new BitmapUtils(context);
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
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, null);
            holder.iv_product =(ImageView) convertView.findViewById(R.id.iv_product);
            holder.tv_PName  =(TextView) convertView.findViewById(R.id.tv_product_name);
            holder.tv_Qprice =(TextView) convertView.findViewById(R.id.tv_Qprice);
            holder.tv_Mprice =(TextView) convertView.findViewById(R.id.tv_Mprice);
            //holder.tv_category =(TextView) convertView.findViewById(R.id.tv_categoryId);
            //ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ProductData info = list.get(position);
//		holder.iv.setImageResource(CLASS_IMG[position]);
       //bitmapUtils.display(holder.iv_product, MyURL.gv_content_img+info.getPic());
        bitmapUtils.display(holder.iv_product, info.getImageUrls());
        holder.tv_Qprice.setText(String.valueOf(info.getQuotoPrice()));
        holder.tv_PName.setText(info.getProductName());
        holder.tv_Mprice.setText(String.valueOf(info.getMprice()));
        //holder.tv_category.setText(String.valueOf(info.getCategoryId()));
        return convertView;
    }

    static class ViewHolder{

        private ImageView iv_product;

        private TextView tv_PName;

        private TextView tv_Qprice;

        private TextView tv_Mprice;
        //private TextView tv_category;

    }
}
