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

public class Rankings implements Parcelable,Serializable{

    private String ranking;
    private JSONArray products;

    public Rankings(String ranking){
        this.ranking=ranking;

    }

    public Rankings(String ranking, JSONArray products){
        this.ranking = ranking;
        this.products = products;

    }


    protected Rankings(Parcel in) {
        ranking = in.readString();
    }

    public static final Creator<Rankings> CREATOR = new Creator<Rankings>() {
        @Override
        public Rankings createFromParcel(Parcel in) {
            return new Rankings(in);
        }

        @Override
        public Rankings[] newArray(int size) {
            return new Rankings[size];
        }
    };

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public JSONArray getProducts() {
        return products;
    }

    public void setProducts(JSONArray products) {
        this.products = products;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ranking);
    }
}
