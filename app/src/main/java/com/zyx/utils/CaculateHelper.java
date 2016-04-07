package com.zyx.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by zyx on 2016/4/4.
 */
public class CaculateHelper {

    private String price;  //商品价格
    private String month; //分期月数
    private String firstpay; //首付金额
    private LinkedHashMap<Integer, String>  productdescrib; //商品编号
    private LinkedHashMap<Double, String>  productprice; //商品价格
    private LinkedHashMap<Integer, String>  productonhand; //商品价格
    private LinkedHashMap<String,String> productdetail;  //商品属性
    private Iterator iter;
    private Iterator iter1;
    private Iterator iter2;

    public CaculateHelper (String price, String month, String firstpay, LinkedHashMap<Integer, String>  productonhand,
                           LinkedHashMap<Integer, String>  productdescrib, LinkedHashMap<Double, String>  productprice, LinkedHashMap<String,String> productdetail){

        this.price = price;
        this.month = month;
        this.firstpay = firstpay;
        this.productdescrib = productdescrib;
        this.productprice = productprice;
        this.productonhand = productonhand;
        this.productdetail = productdetail;
    }




    //计算月供
    public String caculate() {
        String result = "";
        //商品价格减去首付
        double exceptfirsprice = Parse.getInstance().parseDouble(price, "#.##") * (1 - Parse.getInstance().parseDouble(firstpay.replace("%",""), "#.##")*0.01);
        //除去每个月乘以0.09得每月还款
        double mMonthprice = Parse.getInstance().parseDouble(exceptfirsprice / Parse.getInstance().parseInt(month.replace("个月", "")), "#.##") * 1.09;
        result = String.valueOf(Parse.getInstance().parseDouble(mMonthprice,"#.##"));
        return result;
    }


    //首付金额
    public String getFirstpay(){
        double getfirstpay = Parse.getInstance().parseDouble(price, "#.##") * (Parse.getInstance().parseDouble(firstpay.replace("%",""), "#.##")*0.01);
        return String.valueOf(Parse.getInstance().parseDouble(getfirstpay,"#.##"));
    }

    //返回商品编号
    public String getProductNum() {
        String result = "";
        if(productdetail ==null)
            return null;
        iter1 = productdetail.entrySet().iterator();
        while (iter1.hasNext()){
            LinkedHashMap.Entry entry =(LinkedHashMap.Entry)iter1.next();
            if(!iter1.hasNext())
                result = result + entry.getValue();
            else
                result = result + entry.getValue()+"，";
        }

        iter = productdescrib.entrySet().iterator();
        while(iter.hasNext()){
            LinkedHashMap.Entry entry =(LinkedHashMap.Entry)iter.next();
            if(entry.getValue().toString().equals(result))
                return String.valueOf(entry.getKey());
        }
        return null;
    }

    //对应商品价格变动
    public String changeprice(){
        String result = "";
        /*List<String> list = new*/

        iter1 = productdetail.entrySet().iterator();
        while (iter1.hasNext()){
            LinkedHashMap.Entry entry =(LinkedHashMap.Entry)iter1.next();
            if(!iter1.hasNext())
                result = result + entry.getValue().toString();
            else
                result = result + entry.getValue().toString()+"，";

        }

         iter = productprice.entrySet().iterator();
        while(iter.hasNext()){
            LinkedHashMap.Entry entry =(LinkedHashMap.Entry)iter.next();
            if(entry.getValue().toString().equals(result.trim()))
                return String.valueOf(entry.getKey());
        }
        return price;
    }



    //对应商品库存
    public String onhand(){
        String result = "";
        /*List<String> list = new*/

        iter1 = productdetail.entrySet().iterator();
        while (iter1.hasNext()){
            LinkedHashMap.Entry entry =(LinkedHashMap.Entry)iter1.next();
            if(!iter1.hasNext())
                result = result + entry.getValue().toString();
            else
                result = result + entry.getValue().toString()+"，";

        }

        iter2 = productonhand.entrySet().iterator();
        while(iter2.hasNext()){
            LinkedHashMap.Entry entry =(LinkedHashMap.Entry)iter2.next();
            if(entry.getValue().toString().equals(result.trim()))
                return String.valueOf(entry.getKey());
        }
        return String.valueOf("531");
    }


}
