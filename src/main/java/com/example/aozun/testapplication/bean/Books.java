package com.example.aozun.testapplication.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HHD-H-I-0369 on 2016/12/29.
 */
public class Books extends DataSupport{
    private int id;
    private String name;
    private String price;
    private String date;

    public Books(){

    }
    public Books(String name,String price,String date){
        this.name=name;
        this.price=price;
        this.date=date;

    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price = price;
    }


}
