package com.zyx.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyx on 2015/10/19.
 */
public class Attr {
    public String attr;
    public List<String> type;

    public Attr(List<String> data){

        type= new ArrayList<String>();

        for(int i=0;i<data.size();i++){

            if(i==0){

                attr = data.get(0);
            }
            else{
                type.add(i-1, data.get(i));
            }
        }
    }
    public String getAttr(){

        return attr;
    }

    public List<String> getType(){

        return type;
    }
}
