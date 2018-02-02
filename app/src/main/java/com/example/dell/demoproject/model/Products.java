package com.example.dell.demoproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dell on 30-01-2018.
 */

public class Products extends ArrayList<Products> implements Serializable,Parcelable{

    private int id;
    private String name;
    private String date_added;
    private ArrayList<Variants> variants;
    private Tax tax;
    private int view_count;
    private  int order_count;
    private int shares;

    public Products(String rankingName,int id,int x){
        this.id = id;
        if (rankingName.equalsIgnoreCase("Most Viewed Products")) {
            this.view_count = x;
        }
        if (rankingName.equalsIgnoreCase("Most OrdeRed Products")) {
            this.order_count = x;
        }
        if(rankingName.equalsIgnoreCase("Most ShaRed Products")){
            this.shares =x;
        }

    }

    public Products(int id,String name,String date_added, ArrayList<Variants> variants){
        this.id = id;
        this.name = name;
        this.date_added= date_added;
        this.variants = variants;
    }


    protected Products(Parcel in) {
        id = in.readInt();
        name = in.readString();
        date_added = in.readString();
        view_count = in.readInt();
        order_count = in.readInt();
        shares = in.readInt();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(Integer order_count) {
        this.order_count = order_count;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        if(name == null)
        {
            return  name= "Not Available";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public ArrayList<Variants> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<Variants> variants) {
        this.variants = variants;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(date_added);
        parcel.writeInt(view_count);
        parcel.writeInt(order_count);
        parcel.writeInt(shares);
    }


}
