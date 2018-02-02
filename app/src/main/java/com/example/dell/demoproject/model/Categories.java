package com.example.dell.demoproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 30-01-2018.
 */

public class Categories implements Parcelable, Serializable {
    private int id;
    private String name;
    private JSONArray products;

    private JSONArray childCategories;

    public Categories(int id, String name){
        this.id=id;
        this.name = name;
    }

    public  Categories(int id, String name, JSONArray products,JSONArray childCategories){
        this.id=id;
        this.name = name;
        this.products = products;
        this.childCategories = childCategories;
    }


    protected Categories(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONArray getProducts() {
        return products;
    }

    public void setProducts(JSONArray products) {
        this.products = products;
    }

    public JSONArray getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(JSONArray childCategories) {
        this.childCategories = childCategories;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
