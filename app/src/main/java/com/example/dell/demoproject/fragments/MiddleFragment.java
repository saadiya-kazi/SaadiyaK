package com.example.dell.demoproject.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.demoproject.R;
import com.example.dell.demoproject.model.Categories;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MiddleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MiddleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiddleFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Categories> categoriesArrayList;
    ArrayList<Categories> categoriesArrayListPass;

    LinearLayout linear_layout_container;

    private OnFragmentInteractionListener mListener;

    public MiddleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiddleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiddleFragment newInstance(String param1, String param2) {
        MiddleFragment fragment = new MiddleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            categoriesArrayList=getArguments().getParcelableArrayList("categoryArrayList");
            categoriesArrayListPass=getArguments().getParcelableArrayList("categoryArrayList1");

            Log.d("CatMiddleFrag",categoriesArrayList.size()+"");
//            Log.d("categoriesArrayListPass",categoriesArrayListPass.size()+"");

        }
    }


    private void callMakeButton() {

        Log.d("categoryArray List Size",categoriesArrayList.size()+"");

        for(int i = 0; i < categoriesArrayList.size(); i ++){
            final Categories c= categoriesArrayList.get(i);
            Button categoryButton = new Button(getContext());
            categoryButton.setId(c.getId());
            categoryButton.setText(c.getName());
            categoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    BottomFragment bottomFragment = new BottomFragment();
                    Bundle bundle= new Bundle();
                    bundle.putSerializable("categoryButton",c);
                    bundle.putParcelableArrayList("categoriesArrayListPass",categoriesArrayListPass);
                    bottomFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.bottomFragment, bottomFragment);
//                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
//            Log.d("ButtonName",categoryButton.getText()+"");
            linear_layout_container.addView(categoryButton);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_middle, container, false);

        linear_layout_container = (LinearLayout)view.findViewById(R.id.linear_layout_container);

        TextView textView = new TextView(getContext());
        textView.setTextSize(20);
        textView.setText("Select Category");
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null, Typeface.BOLD);
        linear_layout_container.addView(textView);

        callMakeButton();
        return  view;
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
