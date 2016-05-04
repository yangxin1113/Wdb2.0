package com.zyx.json;

import android.util.Log;

import com.zyx.bean.ProductData;
import com.zyx.bean.ProjectData;
import com.zyx.utils.Resolve;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/19.
 */
public class ParserJsonProjectitem {


    public static List<ProjectData> JsontoProjectItem(String jsonStirng){
        List<ProjectData> list = new ArrayList<ProjectData>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStirng);
            JSONArray result = jsonObject.getJSONArray("data");
            for(int i=0; i<result.length(); i++){

                JSONObject object = result.getJSONObject(i);
                Log.i("zyxqwewqeq", "Map<String, Map<String, Object>>:" + object.get("proName"));

                ProjectData pd = new ProjectData();
                pd.setProId(Integer.valueOf(object.getString("proId")));
                pd.setProNum(object.getString("proNum"));
                pd.setProName(object.getString("proName"));
                pd.setProRestMoney((float) object.getDouble("proRestMoney"));
                pd.setProTotalMoney((float)object.getDouble("proTotalMoney"));
                pd.setMinPrice((float) object.getDouble("minPrice"));
                pd.setMaxPrice((float) object.getDouble("maxPrice"));
                pd.setLastTime(object.getString("lastTime"));
                pd.setRepayLastTime(object.getString("repayLastTime"));
                pd.setStartTime(object.getString("startTime"));
                pd.setRate(object.getString("rate"));
                pd.setPerCount(object.getInt("perCount"));
                pd.setProImg(object.getString("proImg"));
                pd.setProDetail(object.getString("proDetail"));

                list.add(pd);
                Log.i("zyx11111", String.valueOf(list.size()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }



    public static List<ProductData> toProjectItem(Map<String, Map<String, Object>> map){
        List<ProductData> list = new ArrayList<ProductData>();
        ArrayList<Map<String, Object>> data1 = Resolve
                .getInstance().getList(map, "data");
        //Log.i("zyxqwewqeq", "Map<String, Map<String, Object>>:" + data.get(0));

        try {
            JSONObject jsonObject = new JSONObject(map.toString());
            JSONArray data = jsonObject.getJSONArray("data");
            for(int i=0; i<data.length(); i++){
                Log.i("zyxqwewqeq", "Map<String, Map<String, Object>>:" + data.get(0).toString());
                JSONObject object = data.getJSONObject(i);
                ProductData pd = new ProductData();
                pd.setProductNumber(object.getString("ProductNumber"));
                pd.setProductName(object.getString("ProductName"));
                pd.setPrductDescription(object.getString("productDescription"));
                pd.setQuotoPrice(object.getDouble("QuotoPrice"));
                pd.setRetailPrice(object.getDouble("RetailPrice"));
                pd.setImageUrls(object.getString("ImageUrls"));
                pd.setQuantityOnHand(object.getInt("QuantityOnHand"));
                list.add(pd);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }

}
        /*try {
            JSONObject jsonObject = new JSONObject(jsonStirng);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i=0; i<jsonArray.length(); i++){
                ProductData pd = new ProductData();
                JSONObject object = jsonArray.getJSONObject(i);
                pd.setProductNumber(object.getString("ProductNumber"));
                pd.setProductName(object.getString("ProductName"));
                pd.setPrductDescription(object.getString("productDescription"));
                pd.setQuotoPrice(object.getDouble("QuotoPrice"));
                pd.setRetailPrice(object.getDouble("RetailPrice"));
                pd.setImageUrls(object.getString("ImageUrls"));
                pd.setQuantityOnHand(object.getInt("QuantityOnHand"));
                list.add(pd);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
        */




