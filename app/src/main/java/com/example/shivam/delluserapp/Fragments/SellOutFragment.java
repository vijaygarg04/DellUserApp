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

public class SellOutFragment extends Fragment {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    View view;
    MaterialDialog materialDialog;
    private int mColumnCount = 1;
    List<MainProduct> products;
    TinyDB tinyDB ;
    Context context;
    StoreConfigModel storeConfigModel;
    List<String> service_tags_list;
    private SellOUTListener mListener;
    public SellOutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key, StoreConfigModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sell_out_list, container, false);
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
            recyclerView.setAdapter(new SellOutAdapter(products, mListener));
        }
        databaseReference.child("sell_out").child(storeConfigModel.getStoreName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        for (DataSnapshot post_snap : ds.getChildren()){
                            String service_tag = post_snap.getKey();
                            service_tags_list.add(service_tag);
                            Log.e("My Tag - 3 ",service_tag);
                        }

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
                                    recyclerView.setAdapter(new SellOutAdapter(products, mListener));
                                }
                                materialDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SellOUTListener) {
            mListener = (SellOUTListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        this.context = context;
        tinyDB = new TinyDB(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface SellOUTListener {
        // TODO: Update argument type and name
        void sellOUTInteraction(MainProduct item);
    }
}
