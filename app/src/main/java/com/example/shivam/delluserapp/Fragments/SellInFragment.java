package com.example.shivam.delluserapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.R;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellInFragment extends Fragment {
    //This shows the list of all
    private int mColumnCount = 1;
    List<MainProduct> products;
    TinyDB tinyDB;
    private SellINListener mListener;
    MaterialDialog materialDialog;
    StoreConfigModel storeConfigModel;
    Context context;
    List<String> service_tags_list;
    String passed_date;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    View view;
    public SellInFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key, StoreConfigModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_item_sell_in_list, container, false);
        products = new ArrayList<>();
        service_tags_list = new ArrayList<>();
        materialDialog = new MaterialDialog.Builder(context)
                .title("Important Information")
                .content("Please Wait")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();
        materialDialog.show();
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new SellInAdapter(products, mListener));
        }
        databaseReference.child("sell_in").child(storeConfigModel.getUnique_store_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   for (DataSnapshot ds: dataSnapshot.getChildren()){
                       //Dates

                       if (Integer.valueOf(passed_date) <= Integer.valueOf(ds.getKey())) {
                           Log.e("My Test Tag : ", passed_date + "    " + ds.getKey());

                           for (DataSnapshot post_snap : ds.getChildren()) {
                               String service_tag = post_snap.getKey();
                               service_tags_list.add(service_tag);

                           }
                       }
                   }
                   if (service_tags_list.size()==0){
                       materialDialog.dismiss();
                       new MaterialDialog.Builder(context)
                               .title("INFORMATION")
                               .content("NO MORE PRODUCTS WERE SELL IN AFTER " + myDateFormatter(passed_date))
                               .positiveText("OK")
                               .show();
                       //Toast.makeText(context,"No Product Was Added After This Date",Toast.LENGTH_LONG).show();
                   }
                   for (String s : service_tags_list){
                       databaseReference.child("msa").child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                            MainProduct mainProduct  = dataSnapshot.getValue(MainProduct.class);
                            products.add(mainProduct);
                               if (view instanceof RecyclerView) {
                                   Context context = view.getContext();
                                   RecyclerView recyclerView = (RecyclerView) view;
                                   if (mColumnCount <= 1) {
                                       recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                   } else {
                                       recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                   }
                                   recyclerView.setAdapter(new SellInAdapter(products, mListener));
                                   materialDialog.dismiss();
                               }
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {
                               new MaterialDialog.Builder(context)
                                       .title("INFORMATION")
                                       .content("DATABASE ERROR, TRY AGAIN LATER...This may occur due to slow or low network connectivity. Make sure you are in network range and try again ")
                                       .positiveText("OK")
                                       .show();
                           }
                       });

                   }
               }else {
                   materialDialog.dismiss();
                   new MaterialDialog.Builder(context)
                           .title("INFORMATION")
                           .content("NO SELL IN DATA WAS FOUND FOR THIS USER")
                           .positiveText("OK")
                           .show();
                   //Toast.makeText(context,"Data Not Found For This User",Toast.LENGTH_LONG).show();
               }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                new MaterialDialog.Builder(context)
                        .title("INFORMATION")
                        .content("DATABASE ERROR, TRY AGAIN LATER...This may occur due to slow or low network connectivity. Make sure you are in network range and try again ")
                        .positiveText("OK")
                        .show();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SellINListener) {
            mListener = (SellINListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        tinyDB = new TinyDB(context);
        this.context = context;
        passed_date = tinyDB.getString("date_string");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public String myDateFormatter(String date){
        String a = date;
        if (date.length()>5) {
            a = date.substring(6,8) +"/" +date.substring(4,6) + "/"+ date.substring(0,4);
        }
        return a;
    }

    public interface SellINListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(MainProduct item);
    }
}
