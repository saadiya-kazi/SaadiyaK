package com.example.dell.demoproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.dell.demoproject.fragments.BottomFragment;
import com.example.dell.demoproject.fragments.MiddleFragment;
import com.example.dell.demoproject.fragments.TopFragment;
import com.example.dell.demoproject.model.Categories;
import com.example.dell.demoproject.model.Rankings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity implements TopFragment.OnFragmentInteractionListener,MiddleFragment.OnFragmentInteractionListener,BottomFragment.OnFragmentInteractionListener {



    SharedPreferences sharedPreferences;
    Gson gson;

    ArrayList<Categories> categoriesArrayList ;

    ArrayList<Categories> categoriesArrayListMiddleFrag ;

    ArrayList<Rankings> rankingsArrayList;

    TopFragment topFragment;
    MiddleFragment middleFragment;
    BottomFragment bottomFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();
        sharedPreferences = this.getSharedPreferences("APP_LOCAL_STORAGE", Context.MODE_PRIVATE);
        if((getResponseCategory() != null )
                && (getResponseRanking() != null )){
            categoriesArrayList = getResponseCategory();
            rankingsArrayList = getResponseRanking();
            callFragments();
        }
        else{
        if(categoriesArrayList == null && rankingsArrayList == null){
            categoriesArrayList = new ArrayList<>();
            rankingsArrayList =  new ArrayList<>();
        }
          categoriesArrayListMiddleFrag = new ArrayList<>();
          AsynDataClass jsonAsync = new AsynDataClass();
          jsonAsync.execute("");

        }



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
//
    public void setResponseCategory(JSONArray response){
        sharedPreferences.edit().putString("Categories", String.valueOf(response));
    }

    public ArrayList<Categories> getResponseCategory(){
        String responseString= sharedPreferences.getString("Categories","");
        return gson.fromJson(responseString,new TypeToken<ArrayList<Categories>>() {}.getType());
    }

    public void setResponseRanking(JSONArray response){
        sharedPreferences.edit().putString("Rankings", String.valueOf(response));
    }

    public ArrayList<Rankings> getResponseRanking(){
        String responseString= sharedPreferences.getString("Rankings","");
        return gson.fromJson(responseString,new TypeToken<ArrayList<Rankings>>() {}.getType());
    }


    private class AsynDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


                // TODO Auto-generated method stub
                HttpHandler sh = new HttpHandler();
                // Making a request to url and getting response
                String url = "https://stark-spire-93433.herokuapp.com/json";
                String jsonStr = sh.makeServiceCall(url);

                Log.d("TAG", "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        setResponseCategory(categories);

                        // looping through All Categories
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject c = categories.getJSONObject(i);
                            int id = c.getInt("id");
                            String name = c.getString("name");
                            categoriesArrayListMiddleFrag.add(i,new Categories(id,name));

//                        Log.d("Category : id name :", id + " " + name);

                            JSONArray products = c.getJSONArray("products");

                            //Child_categories
                            JSONArray child_cat=c.getJSONArray("child_categories");

                            categoriesArrayList.add(new Categories(id,name,products,child_cat));

                        }

                        //For Ranking

                        JSONArray ranking = jsonObj.getJSONArray("rankings");
                        Log.d("Ranking Length", ranking.length() + "");
                        setResponseRanking(ranking);

                        //loop for Ranking
                        for (int i = 0; i < ranking.length(); i++) {
                            JSONObject rank = ranking.getJSONObject(i);
                            String rankingName = rank.getString("ranking");
                            Log.d("RankName", rankingName);

                        JSONArray rankingProductArray = rank.getJSONArray("products");
                            Log.d("rankingProdarray",rankingProductArray+"");

                            rankingsArrayList.add(i,new Rankings(rankingName,rankingProductArray));


                            Log.d("rankingArrayList",rankingsArrayList.size()+"");


                        }


                    } catch (final JSONException e) {
                        Log.d("TAG", "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                } else {
                    Log.d("TAG", "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }


            return jsonStr;


        }

        @Override

        protected void onPreExecute() {

            // TODO Auto-generated method stub

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

//            Log.d("TAG", "Resulted Value: " + result);
            callFragments();
        }



    }

    private void callFragments() {

        topFragment = new TopFragment();
        middleFragment = new MiddleFragment();
        bottomFragment = new BottomFragment();

        Bundle bundleTopFrag = new Bundle();
        Bundle bundleMiddlefrag= new Bundle();
        Bundle bundlebottomFrag =  new Bundle();


        bundleMiddlefrag.putParcelableArrayList("categoryArrayList",categoriesArrayListMiddleFrag);
        bundleTopFrag.putParcelableArrayList("rankingArrayList", rankingsArrayList);
        bundlebottomFrag.putParcelableArrayList("categoryArrayListBottom",categoriesArrayList);
        bundleMiddlefrag.putParcelableArrayList("categoryArrayList1",categoriesArrayList);
        bundleTopFrag.putParcelableArrayList("categoryArrayList",categoriesArrayList);

        middleFragment.setArguments(bundleMiddlefrag);
        topFragment.setArguments(bundleTopFrag);
        bottomFragment.setArguments(bundlebottomFrag);

        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager

        FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.topFragment, topFragment, "Frag_Top_tag");
        transaction.add(R.id.middleFragment, middleFragment, "Frag_Middle_tag");
        transaction.add(R.id.bottomFragment, bottomFragment, "Frag_Bottom_tag");


        transaction.commit();


    }


}