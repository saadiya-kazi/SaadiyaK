package com.example.dell.demoproject.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dell.demoproject.R;
import com.example.dell.demoproject.model.Categories;
import com.example.dell.demoproject.model.Products;
import com.example.dell.demoproject.model.Rankings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopFragment extends Fragment  {


    ArrayList<Rankings> rankingsArrayList;
    Button btnMostViewed , btnMostOrdered , btnMostShared;
    String rankName;
    ArrayList<Categories> categoriesArrayList;
    ArrayList<Products> productsArrayListMostShared;
    ArrayList<Products> productsArrayListMostViewed;
    ArrayList<Products> productsArrayListMostOrdered;

    LinearLayout rankingLinearLayout ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public TopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopFragment newInstance(String param1, String param2) {
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rankingsArrayList = new ArrayList<>();
        categoriesArrayList = new ArrayList<>();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            rankingsArrayList = getArguments().getParcelableArrayList("rankingArrayList");
            categoriesArrayList = getArguments().getParcelableArrayList("categoryArrayList");
            Log.d("rankings ArrayList",rankingsArrayList+"");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        rankingLinearLayout =  view.findViewById(R.id.ranking_linear_layout);
        btnMostOrdered = view.findViewById(R.id.most_ordered);
        btnMostViewed = view.findViewById(R.id.most_viewed);
        btnMostShared = view.findViewById(R.id.most_shared);

        btnMostOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    callMostOrdered();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnMostViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    callMostViewed();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnMostShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    callMostShared();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }

    private void callMostShared() throws JSONException {
        productsArrayListMostShared = new ArrayList<>();
        rankingLinearLayout.removeAllViews();

        for (int i = 0 ; i < rankingsArrayList .size() ; i++){
            if(rankingsArrayList.get(i).getRanking().equalsIgnoreCase("Most ShaRed Products")){
                TextView rankingName = new TextView(getContext());
                rankingName.setText("Most Shared Products");
                rankingName.setTextColor(Color.GREEN);
                rankingName.setGravity(Gravity.CENTER_HORIZONTAL);
                rankingName.setTypeface(null, Typeface.BOLD);
                rankingLinearLayout.addView(rankingName);

                Log.d("call : " ,"MostSHared");
                JSONArray productRank = rankingsArrayList.get(i).getProducts();
                for (int k = 0; k < productRank.length() ; k ++){
                    JSONObject prodRankObject = rankingsArrayList.get(i).getProducts().getJSONObject(k);
//                        Log.d("productsRank",productRank+"");
                    int productRankId = prodRankObject.getInt("id");
                    int productShares = prodRankObject.getInt("shares");
                    Log.d("Rank id_Shares", productRankId + " " + productShares);
                    productsArrayListMostShared.add(k,new Products(rankingsArrayList.get(i).getRanking(),productRankId,productShares));

                }
            }
        }

        for(Categories c: categoriesArrayList){
            JSONArray products=  c.getProducts();
            TableLayout prodDetailsTableLayout = new TableLayout(getContext());
            for (int i = 0 ; i < products.length(); i ++){
                JSONObject prodDetails = products.getJSONObject(i);
                int prodId = prodDetails.getInt("id");
                for(int x = 0 ; x < productsArrayListMostShared.size() ; x ++){
                    int prodRankId = productsArrayListMostShared.get(x).getId();
                    if(prodId == prodRankId){
                        TableRow tableRowProd = new TableRow(getContext());
                        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);
                        params.setMargins(7,10,4,10);
                        TextView textViewProdName = new TextView(getContext());
                        textViewProdName.setText(prodDetails.getString("name"));
                        textViewProdName.setLayoutParams(params);
//                        Log.d("ProdNameMS",prodDetails.getString("name"));
                        TextView textViewProdShares = new TextView(getContext());
                        textViewProdShares.setTextColor(Color.GRAY);
                        textViewProdShares.setText("Shares : "+ productsArrayListMostShared.get(x).getShares()+"");
                        tableRowProd.addView(textViewProdName);
                        tableRowProd.addView(textViewProdShares);
                        prodDetailsTableLayout.addView(tableRowProd);
                    }

                }
            }
            rankingLinearLayout.addView(prodDetailsTableLayout);

        }


    }

    private void callMostViewed() throws JSONException {

        productsArrayListMostViewed = new ArrayList<>();
        rankingLinearLayout.removeAllViews();
        for (int i = 0; i < rankingsArrayList.size(); i++) {
            if (rankingsArrayList.get(i).getRanking().equalsIgnoreCase("Most Viewed Products")) {
                Log.d("call : ", "MostViewed");
                TextView rankingName = new TextView(getContext());
                rankingName.setText("Most Viewed Products");
                rankingName.setTextColor(Color.BLUE);
                rankingName.setGravity(Gravity.CENTER_HORIZONTAL);
                rankingName.setTypeface(null, Typeface.BOLD);
                rankingLinearLayout.addView(rankingName);
                {
                    JSONArray productRank = rankingsArrayList.get(i).getProducts();
                    for (int k = 0; k < productRank.length(); k++) {
                        JSONObject prodRankObject = rankingsArrayList.get(i).getProducts().getJSONObject(k);
//                        Log.d("productsRank",productRank+"");
                        int productRankId = prodRankObject.getInt("id");
                        int productViewCount = prodRankObject.getInt("view_count");
                        Log.d("Rank id_ViewCount", productRankId + " " + productViewCount);
                        productsArrayListMostViewed.add(k, new Products(rankingsArrayList.get(i).getRanking(), productRankId, productViewCount));

                    }
                }
            }
        }

        for (Categories c : categoriesArrayList) {
            JSONArray products = c.getProducts();
            TableLayout prodDetailsTableLayout = new TableLayout(getContext());
            for (int i = 0; i < products.length(); i++) {
                JSONObject prodDetails = products.getJSONObject(i);
                int prodId = prodDetails.getInt("id");
                for (int x = 0; x < productsArrayListMostViewed.size(); x++) {
                    int prodRankId = productsArrayListMostViewed.get(x).getId();
                    if (prodId == prodRankId) {
                        TableRow tableRowProd = new TableRow(getContext());
                        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);
                        params.setMargins(7,10,4,10);
                        TextView textViewProdName = new TextView(getContext());
                        textViewProdName.setText(prodDetails.getString("name"));
                        textViewProdName.setLayoutParams(params);
                        TextView textViewProdMostViewed = new TextView(getContext());
                        textViewProdMostViewed.setTextColor(Color.GRAY);
                        textViewProdMostViewed.setText("Views : " + productsArrayListMostViewed.get(x).getView_count() + "");
                        tableRowProd.addView(textViewProdName);
                        tableRowProd.addView(textViewProdMostViewed);
                        prodDetailsTableLayout.addView(tableRowProd);
                    }

                }
            }
            rankingLinearLayout.addView(prodDetailsTableLayout);


        }
    }

    private void callMostOrdered() throws JSONException {
        productsArrayListMostOrdered = new ArrayList<>();
        rankingLinearLayout.removeAllViews();

        for (int i = 0 ; i < rankingsArrayList .size() ; i++){
            if(rankingsArrayList.get(i).getRanking().equalsIgnoreCase("Most OrdeRed Products")){
                Log.d("call : " ,"MostOrdered");

                TextView rankingName = new TextView(getContext());
                rankingName.setText("Most Ordered Products");
                rankingName.setTextColor(Color.RED);
                rankingName.setGravity(Gravity.CENTER_HORIZONTAL);
                rankingName.setTypeface(null, Typeface.BOLD);
//                Log.d("Tv",rankingName.getText()+"");
                rankingLinearLayout.addView(rankingName);
                JSONArray productRank = rankingsArrayList.get(i).getProducts();

                for (int k = 0; k < productRank.length() ; k ++){
                    JSONObject prodRankObject = rankingsArrayList.get(i).getProducts().getJSONObject(k);
                    int productRankId = prodRankObject.getInt("id");
                    int productOrderCount = prodRankObject.getInt("order_count");
                    Log.d("Rank id_OrderCount", productRankId + " " + productOrderCount);
                    productsArrayListMostOrdered.add(k,new Products(rankingsArrayList.get(i).getRanking(),productRankId,productOrderCount));
                }
            }
        }
        for (Categories c : categoriesArrayList) {
            JSONArray products = c.getProducts();
            TableLayout prodDetailsTableLayout = new TableLayout(getContext());

            for (int i = 0; i < products.length(); i++) {
                JSONObject prodDetails = products.getJSONObject(i);
                int prodId = prodDetails.getInt("id");
                for (int x = 0; x < productsArrayListMostOrdered.size(); x++) {
                    int prodRankId = productsArrayListMostOrdered.get(x).getId();
                    if (prodId == prodRankId) {
                        TableRow tableRowProd = new TableRow(getContext());
                        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);
                        params.setMargins(7,10,4,10);
                        TextView textViewProdName = new TextView(getContext());
                        textViewProdName.setLayoutParams(params);
                        textViewProdName.setText(prodDetails.getString("name"));
                        TextView textViewProdOrderCount = new TextView(getContext());
                        textViewProdOrderCount.setTextColor(Color.GRAY);
                        textViewProdOrderCount.setText("Ordered : " + productsArrayListMostOrdered.get(x).getOrder_count() + "");
                        tableRowProd.addView(textViewProdName);
                        tableRowProd.addView(textViewProdOrderCount);
                        prodDetailsTableLayout.addView(tableRowProd);
                    }

                }
            }
            rankingLinearLayout.addView(prodDetailsTableLayout);
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
