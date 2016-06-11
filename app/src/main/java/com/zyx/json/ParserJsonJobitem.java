package com.zyx.json;

import android.util.Log;

import com.zyx.bean.JobItem;
import com.zyx.bean.ProductData;
import com.zyx.utils.Parse;
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
public class ParserJsonJobitem {


    public static List<JobItem> JsontoProductItem(String jsonStirng){
        List<JobItem> list = new ArrayList<JobItem>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStirng);
            JSONArray result = jsonObject.getJSONArray("data");
            for(int i=0; i<result.length(); i++){

                JSONObject object = result.getJSONObject(i);
                JobItem job = new JobItem();
                job.setId(Parse.getInstance().parseInt(object.get("id")));
                job.setTitle(object.get("title").toString());
                job.setDate(object.get("date").toString());
                job.setAddress(object.get("address").toString());
                job.setLink(object.get("link").toString());
                job.setSalary(object.get("salary").toString());
                job.setJobstatus(object.get("jobstatus").toString());
                job.setImgLink(object.get("imgLink").toString());
                list.add(job);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }



    public static List<ProductData> toProductItem(Map<String, Map<String, Object>> map){
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




