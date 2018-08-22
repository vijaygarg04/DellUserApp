package com.example.shivam.delluserapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.SchemeModel;
import com.example.shivam.delluserapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class SchemesFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    View view;
    private OnListFragmentInteractionListener mListener;
    List<SchemeModel> schemeModels;
    Context context;
    MaterialDialog materialDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public SchemesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference  = firebaseDatabase.getReference();
        materialDialog = new MaterialDialog.Builder(context)
                .title("Important Information")
                .content("Please Wait")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();
         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_scheme_list, container, false);
        schemeModels = new ArrayList<>();
        // Set the adapter
        materialDialog.show();
        databaseReference.child("schemes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    for (DataSnapshot ps: ds.getChildren()){
                        SchemeModel schemeModel = ps.getValue(SchemeModel.class);
                        schemeModels.add(schemeModel);
                    }
                }
                materialDialog.dismiss();
                if (schemeModels.size()==0){

                    new MaterialDialog.Builder(context)
                            .title("INFORMATION")
                            .content("NO Schemes Found....")
                            .positiveText("OK")
                            .show();
                }
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }
                    recyclerView.setAdapter(new SchemeAdapter(schemeModels, mListener,context));
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
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
            this.context= context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(SchemeModel item);
    }
}
