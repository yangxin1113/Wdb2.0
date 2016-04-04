package com.zyx.json;

import android.util.Log;

import com.zyx.bean.OrderData;
import com.zyx.bean.ProductData;
import com.zyx.utils.MyUtils;
import com.zyx.utils.Parse;
import com.zyx.utils.Resolve;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zyx on 2016/2/19.
 */
public class ParserJsonOrderitem {


    public static List<OrderData> JsontoOrderItem(String jsonStirng){
        List<OrderData> list = new ArrayList<OrderData>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStirng);
            JSONArray result = jsonObject.getJSONArray("data");
            for(int i=0; i<result.length(); i++){

                JSONObject object = result.getJSONObject(i);
                //Log.i("zyxqwewqeq", "Map<String, Map<String, Object>>:" + object.get("paydate"));

                OrderData od = new OrderData();
                od.setCustomerid(object.getLong("customerid"));
                od.setOrdernumber(object.getString("ordernumber"));
                od.setProductnumber(object.getString("productnumber"));
                od.setCustname(object.getString("custname"));
                od.setOrderdate(object.getString("orderdate"));
                od.setTimes(object.getInt("times"));
                od.setHasfirstpay(object.getInt("hasfirstpay"));
                od.setRepayment(Parse.getInstance().parseFloat(object.getDouble("repayment")));
                od.setEmployeeid(object.getLong("employeeid"));
                od.setOrderstatus(object.getInt("orderstatus"));
                od.setProductname(object.getString("productname"));
                od.setProductdescription(object.getString("productdescription"));
                od.setQuotoprice(Parse.getInstance().parseFloat(object.getDouble("quotoprice")));
                od.setRetailprice(Parse.getInstance().parseFloat(object.getDouble("retailprice")));
                od.setImageurls(object.getString("imageurls"));
                od.setQuantityonhand(object.getInt("quantityonhand"));
                od.setCategoryid(object.getInt("categoryid"));
                od.setTime(object.getInt("time"));
                od.setBegindate(object.getString("begindate"));
                od.setEnddate(object.getString("enddate"));
                //od.setPaydate(object.getString("paydate"));
                od.setCustaddress(object.getString("custaddress"));
                od.setCustphonenum(object.getString("custphonenum"));
                od.setMoney(Parse.getInstance().parseFloat(object.getDouble("money")));
                /*pd.setProductNumber(object.getString("ProductNumber"));
                pd.setProductName(object.getString("ProductName"));
                pd.setPrductDescription(object.getString("ProductDescription"));
                pd.setQuotoPrice(object.getDouble("QuotoPrice"));
                pd.setRetailPrice(object.getDouble("RetailPrice"));
                pd.setImageUrls(object.getString("ImageUrls"));
                pd.setQuantityOnHand(object.getInt("QuantityOnHand"));
                pd.setMprice((int)object.getDouble("Mprice"));
                pd.setCategoryId(object.getInt("CategoryId"));*/
                list.add(od);
                Log.i("zyx11111", od.getOrderdate().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }



    public static List<OrderData> JsontoOrderRecord(String jsonStirng){
        List<OrderData> list = new ArrayList<OrderData>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStirng);
            JSONArray result = jsonObject.getJSONArray("data");
            for(int i=0; i<result.length(); i++){

                JSONObject object = result.getJSONObject(i);
                //Log.i("zyxqwewqeq", "Map<String, Map<String, Object>>:" + object.get("paydate"));

                OrderData od = new OrderData();
                od.setCustomerid(object.getLong("customerid"));
                od.setOrdernumber(object.getString("ordernumber"));
                od.setProductnumber(object.getString("productnumber"));
                od.setCustname(object.getString("custname"));
                od.setOrderdate(object.getString("orderdate"));
                od.setTimes(object.getInt("times"));
                od.setHasfirstpay(object.getInt("hasfirstpay"));
                od.setRepayment(Parse.getInstance().parseFloat(object.getDouble("repayment")));
                od.setEmployeeid(object.getLong("employeeid"));
                od.setOrderstatus(object.getInt("orderstatus"));
                od.setProductname(object.getString("productname"));
                od.setProductdescription(object.getString("productdescription"));
                od.setQuotoprice(Parse.getInstance().parseFloat(object.getDouble("quotoprice")));
                od.setRetailprice(Parse.getInstance().parseFloat(object.getDouble("retailprice")));
                od.setImageurls(object.getString("imageurls"));
                od.setQuantityonhand(object.getInt("quantityonhand"));
                od.setCategoryid(object.getInt("categoryid"));
                od.setTime(object.getInt("time"));
                od.setBegindate(object.getString("begindate"));
                od.setEnddate(object.getString("enddate"));
                od.setPaydate(object.getString("paydate"));
                od.setCustaddress(object.getString("custaddress"));
                od.setCustphonenum(object.getString("custphonenum"));
                od.setMoney(Parse.getInstance().parseFloat(object.getDouble("money")));
                /*pd.setProductNumber(object.getString("ProductNumber"));
                pd.setProductName(object.getString("ProductName"));
                pd.setPrductDescription(object.getString("ProductDescription"));
                pd.setQuotoPrice(object.getDouble("QuotoPrice"));
                pd.setRetailPrice(object.getDouble("RetailPrice"));
                pd.setImageUrls(object.getString("ImageUrls"));
                pd.setQuantityOnHand(object.getInt("QuantityOnHand"));
                pd.setMprice((int)object.getDouble("Mprice"));
                pd.setCategoryId(object.getInt("CategoryId"));*/
                list.add(od);
                Log.i("zyx11111", od.getOrderdate().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }


   /* private OrderData setData(Object object)  {
        OrderData od = new OrderData();
        od.setCustomerid(rs.getLong("CustomerId"));
        od.setOrdernumber(rs.getNString("OrderNumber"));
        od.setProductnumber(rs.getString("ProductNumber"));
        od.setOrderdate(rs.getDate("OrderDate"));
        od.setTimes(rs.getInt("Times"));
        od.setHasfirstpay(rs.getInt("HasFirstPay"));
        od.setRepayment(rs.getFloat("Repayment"));
        od.setEmployeeid(rs.getLong("EmployeeId"));
        od.setOrderstatus(rs.getInt("OrderStatus"));
        od.setProductname(rs.getString("ProductName"));
        od.setProductdescription(rs.getString("ProductDescription"));
        od.setQuotoprice(rs.getFloat("QuotoPrice"));
        od.setRetailprice(rs.getFloat("RetailPrice"));
        od.setImageurls(rs.getString("ImageUrls"));
        od.setQuantityonhand(rs.getInt("QuantityOnHand"));
        od.setCategoryid(rs.getInt("Categoryid"));
        od.setTime(rs.getInt("Time"));
        od.setBegindate(rs.getDate("BeginDate"));
        od.setEnddate(rs.getDate("EndDate"));
        od.setPaydate(rs.getDate("PayDate"));
        od.setCustaddress(rs.getString("CustAddress"));
        return od;
    }*/


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




