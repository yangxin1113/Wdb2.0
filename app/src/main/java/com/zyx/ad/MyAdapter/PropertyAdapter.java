package com.zyx.ad.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zyx.R;
import com.zyx.widget.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Created by zyx on 2016/3/12.
 */
public class PropertyAdapter extends BaseAdapter {
    HashMap<Integer,View> lmap = new HashMap<Integer,View>();
    private Context mContext;
    private ArrayList<LinkedHashMap<String,Object>> mList;
    //private ArrayList<LinkedHashMap<String,TextView[]>> mViewList;
    private Handler mhandler;
    //用于保存用户的属性集合
    private LinkedHashMap<String,String> selectProMap=new LinkedHashMap<String, String>();
    /**
     * 返回选中的属性
     * @return
     *//*
    public LinkedHashMap<String, String> getSelectProMap() {
        return selectProMap;
    }*/


    public void setSelectProMap(LinkedHashMap<String, String> selectProMap) {
        this.selectProMap = selectProMap;
    }

    public PropertyAdapter(Context mContext, ArrayList<LinkedHashMap<String, Object>> mList, Handler handler){
        this.mContext = mContext;
        this.mList = mList;
        //this.mViewList=new ArrayList<LinkedHashMap<String,TextView[]>>();
        this.mhandler = handler;
        MapToString(selectProMap);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (lmap.put(position, convertView) == null) {
            // 获取list_item布局文件的视图
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.lv_property_item, parent, false);
            holder = new ViewHolder();

            // 获取控件对象
            holder.tvPropName= (TextView) convertView
                    .findViewById(R.id.tv_property_name);
            //holder.llPropContents=(LinearLayout)convertView.findViewById(R.id.ll_property_content);
            //holder.tlPropContents=(TableLayout)convertView.findViewById(R.id.ll_property_content);
            // 设置控件集到convertView
            holder.mFlowLayout= (FlowLayout) convertView.findViewById(R.id.myviewgroup);
            lmap.put(position, convertView);
            convertView.setTag(holder);


            if (this.mList != null) {
                //HashMap<String,TextView[]> mapView=new HashMap<String, TextView[]>();
                ArrayList<String> lables = (ArrayList<String>) this.mList.get(position).get("lable");
                String type = (String) this.mList.get(position).get(
                        "type");
                holder.tvPropName.setText(type);//规格名称
                holder.tvPropName.setTextColor(Color.BLACK);
                //动态加载标签
                //判断布局中的子控件是否为0，如果不为0，就不添加了，防止ListView滚动时重复添加
                if(holder.mFlowLayout.getChildCount()==0){
                    TextView[]  textViews = new TextView[lables.size()];
                    //设置每个标签的文本和布局
                    //TableRow tr=new TableRow(mContext);

                    for (int i = 0; i < lables.size(); i++) {
                        TextView textView = new TextView(mContext);

                    /*textView.setGravity(17);
                    textView.setPadding(100, 200, 100, 15);*/

                        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);


                        lp.setMargins(16,4,16,4);
                        textViews[i] = textView;
                        textViews[i].setBackgroundResource(R.drawable.flag_01);
                    /*textView.setPadding(100, 200, 100, 15);*/
                        textViews[i].setText(lables.get(i).toString());
                    /*textViews[i].setPadding(15, 15, 15, 15);*/
                        textViews[i].setTag(i);

                        textViews[i].setTextColor(Color.GRAY);
                        //tr.addView(textViews[i]);
                        // holder.llPropContents.addView(textViews[i]);
                        holder.mFlowLayout.addView(textView,lp);
                    }
                    //holder.tlPropContents.addView(tr);
                    //绑定标签的Click事件
                    for(int j=0;j<textViews.length;j++){
                        textViews[j].setTag(textViews);
                        textViews[j].setOnClickListener(new LableClickListener(type));
                    }

                    //把控件存起来
                    //mapView.put(type, textViews);
                    //mViewList.add(mapView);
                }
                /**判断之前是否已选中标签*/
                if(selectProMap.get(type)!=null){
                    for(int h=0;h<holder.mFlowLayout.getChildCount();h++){
                        TextView v=(TextView) holder.mFlowLayout.getChildAt(h);
                        if(selectProMap.get(type).equals(v.getText().toString())){
                            //v.setBackgroundColor(Color.parseColor("#EE5500"));
                            //v.setTextColor(Color.parseColor("#FFFFFF"));
                            selectProMap.put(type, v.getText().toString());
                        }
                    }
                }

            }


        } else {
            convertView = lmap.get(position);
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    /*定义item对象*/
    public class ViewHolder {
        TextView  tvPropName;
        FlowLayout mFlowLayout;
    }

    class LableClickListener implements View.OnClickListener {
        private String type;
        public LableClickListener(String type){

            this.type=type;
        }
        @Override
        public void onClick(View v) {
            TextView[] textViews=(TextView[])v.getTag();
            TextView tv=(TextView)v;
            for(int i=0;i<textViews.length;i++){
                //让点击的标签背景变成橙色，字体颜色变为白色
                if(tv.equals(textViews[i])){
                    //textViews[i].setBackgroundColor(Color.parseColor("#EE5500"));
                    //textViews[i].setTextColor(Color.parseColor("#000000"));
                    textViews[i].setBackgroundResource(R.drawable.flag_02);
                    selectProMap.put(type, textViews[i].getText().toString());
                    sendEmptyMessage(0X888, selectProMap);
                    //Log.i("zyxaaaa", tv.getText().toString()+selectProMap.toString());
                }else{
                    //其他标签背景变成白色，字体颜色为黑色
                    //textViews[i].setBackgroundDrawable(drawableNormal);
                    //textViews[i].setBackgroundResource(R.mipmap.state_true);
                    //textViews[i].setTextColor(Color.parseColor("#000000"));
                    textViews[i].setBackgroundResource(R.drawable.flag_01);
                }
            }

        }

    }

    //属性顺序固定
    private void MapToString(LinkedHashMap<String, String> selectProMap) {

        for(int i = 0; i<mList.size(); i++){
            String type = mList.get(i).get("type").toString();
            selectProMap.put(type, "");
        }

    }


    /**
     * 发消息
     *
     * @param emptyMessage
     * @param data
     */
    private void sendEmptyMessage(int emptyMessage, LinkedHashMap<String,String> data) {
        if (emptyMessage != -1) {
            if (mhandler != null) {
                Message msg = mhandler.obtainMessage();
                msg.what = emptyMessage;
                msg.obj = data;
                mhandler.sendMessage(msg);
            }
        }
    }
}
