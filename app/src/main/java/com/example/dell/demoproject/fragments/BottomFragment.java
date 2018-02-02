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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dell.demoproject.MainActivity;
import com.example.dell.demoproject.R;
import com.example.dell.demoproject.model.Categories;
import com.example.dell.demoproject.model.ChildCategories;
import com.example.dell.demoproject.model.Products;
import com.example.dell.demoproject.model.Variants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BottomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomFragment extends Fragment {


    Categories categoriesRecievedFromOnClick;

    ArrayList<Categories> categoriesArrayList;
    ArrayList<Variants> variantsArrayList;
    ArrayList<Products> productsArrayList;
    ArrayList<ChildCategories> childCategoriesArrayList;

    LinearLayout bottomFragmentLinearLayout;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BottomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomFragment newInstance(String param1, String param2) {
        BottomFragment fragment = new BottomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesArrayList = new ArrayList<>();
        variantsArrayList = new ArrayList<>();
        productsArrayList = new ArrayList<>();
        childCategoriesArrayList = new ArrayList<>();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            categoriesRecievedFromOnClick = (Categories) getArguments().getSerializable("categoryButton");
            categoriesArrayList = getArguments().getParcelableArrayList("categoryArrayListBottom");
//            Log.d("CategoryArrayListBottom",categoriesArrayList.size()+"");


//            Log.d("catRecvedFromOnClick",categoriesRecievedFromOnClick.getName()+"");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        bottomFragmentLinearLayout = view.findViewById(R.id.bottomFragment);
        if (categoriesRecievedFromOnClick != null) {
            categoriesArrayList = getArguments().getParcelableArrayList("categoriesArrayListPass");

            Log.d("BottonCAtList", categoriesArrayList.size() + "");
            try {
                bottomFragmentLinearLayout.removeAllViews();
                designUiProduct();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return view;
    }


    private void designUiProduct() throws JSONException {


        TextView catNameTextView = new TextView(getContext());
        catNameTextView.setTextSize(18);
        catNameTextView.setText(categoriesRecievedFromOnClick.getName());
        catNameTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        catNameTextView.setTypeface(null, Typeface.BOLD);

        bottomFragmentLinearLayout.addView(catNameTextView);

        for (int i = 0; i < categoriesArrayList.size(); i++) {
            if (categoriesArrayList.get(i).getId() == categoriesRecievedFromOnClick.getId()) {
                Log.d("category Name clicked", categoriesArrayList.get(i).getName() + "");



                Log.d("cat-prod-size", categoriesArrayList.get(i).getProducts().length() + "");

                Log.d("ChildCat", categoriesArrayList.get(i).getChildCategories().length() + "");

                JSONArray childCatList = categoriesArrayList.get(i).getChildCategories();
                for (int y = 0; y < childCatList.length(); y++) {
                    int childCatId = childCatList.getInt(y);
                    childCategoriesArrayList.add(y, new ChildCategories(childCatId));

                }

               Log.d("ChildCatArraySize", childCategoriesArrayList.size() + "");
                if(childCategoriesArrayList.size() == 0){
                   callDrawLayout(i);
                }
                else
                {
                    callParticularProducts(childCategoriesArrayList);
                }
                }




        }

//
    }

    private void callParticularProducts(ArrayList<ChildCategories> childCategoriesArrayList) throws JSONException {

        for(int i = 0 ;i < categoriesArrayList.size();i++) {
            for(ChildCategories cc : childCategoriesArrayList){
                if(cc.getId() == categoriesArrayList.get(i).getId()){
                  callDrawLayout(i);
                }
             }
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

    public void callDrawLayout(int i) throws JSONException {
        LinearLayout productLinearLayout = new LinearLayout(getContext());
        productLinearLayout.setOrientation(LinearLayout.VERTICAL);
        Log.d("Child Cat PrdName",categoriesArrayList.get(i).getName()+"");

        for (int k = 0; k < categoriesArrayList.get(i).getProducts().length(); k++) {

            Log.d("ProductName", categoriesArrayList.get(i).getProducts().getJSONObject(k) + "");

            JSONObject p = categoriesArrayList.get(i).getProducts().getJSONObject(k);
            int product_id = p.getInt("id");
            String product_name = p.getString("name");
            String date_added = p.getString("date_added");
            Log.d("Product idNameDate", product_id + " " + product_name + " " + date_added);

            JSONArray variants = p.getJSONArray("variants");
            Log.d("Variants size", variants.length() + "");

            //looping through all variants of product
            for (int y = 0; y < variants.length(); y++) {
                JSONObject var = variants.getJSONObject(y);
                int var_id = var.getInt("id");
                String var_color = var.getString("color");
                String var_size = var.getString("size");
                String var_price = var.getString("price");
                variantsArrayList.add(y, new Variants(var_id, var_color, var_size, var_price));
//                        Log.d("Variant idColorSizePrce", variantsArrayList.get(y).getPrice()+);
            }


//                            JSONObject tax = p.getJSONObject("tax");
//                            String tax_name = tax.getString("name");
//                            Integer tax_value = tax.getInt("value");

//                            Log.d("TAX nameValue", tax_name + " " + tax_value);

            productsArrayList.add(new Products(product_id, product_name, date_added, variantsArrayList));

            LinearLayout brand_layout = new LinearLayout(getContext());
            brand_layout.setOrientation(LinearLayout.VERTICAL);
            TextView brandName = new TextView(getContext());
            brandName.setTextColor(Color.BLUE);
            brandName.setText(product_name);
            brandName.setTextSize(15);
            brandName.setTypeface(null, Typeface.BOLD_ITALIC);
            brand_layout.addView(brandName);

            TableLayout variantTableLayout = new TableLayout(getContext());

            for (int j = 0; j < variantsArrayList.size(); j++) {

                TableRow tableRow_variant_layout = new TableRow(getContext());
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

                tableRow_variant_layout.setOrientation(LinearLayout.HORIZONTAL);

                if (!variantsArrayList.get(j).getSize().equalsIgnoreCase("-")){
                    TextView size = new TextView(getContext());
                    params.setMargins(7, 10, 0, 2);
                    size.setLayoutParams(params);
                    size.setText("Size: " + variantsArrayList.get(j).getSize() + "");
                    tableRow_variant_layout.addView(size);
                }
                TextView color = new TextView(getContext());
                color.setLayoutParams(params);
                color.setText("Color: " + variantsArrayList.get(j).getColor() + "");
                TextView price = new TextView(getContext());
                price.setLayoutParams(params);
                price.setText("Price: " + variantsArrayList.get(j).getPrice() + "");

                tableRow_variant_layout.addView(color);
                tableRow_variant_layout.addView(price);

                variantTableLayout.addView(tableRow_variant_layout);

            }
            brand_layout.addView(variantTableLayout);

            productLinearLayout.addView(brand_layout);
        }
        bottomFragmentLinearLayout.addView(productLinearLayout);

    }

}
