package com.example.dell.demoproject.model;

import android.util.Log;

/**
 * Created by dell on 30-01-2018.
 */

public class Variants {

    private int id;
    private String color;
    private String size;
    private String price;

    public Variants(int id, String color,String size,String price){
        this.id = id ;
        this.color = color ;
        this.size = size;
        this.price = price ;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        if (size.equalsIgnoreCase("null")){
            return  "-";
        }
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }




}
